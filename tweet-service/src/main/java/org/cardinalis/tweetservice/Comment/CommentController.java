package org.cardinalis.tweetservice.Comment;

//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.*;


@RestController
@RequestMapping("")
public class CommentController {
    @Autowired
    CommentService commentService;

//    @Autowired
//    Producer producer;

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> saveComment(@RequestBody Comment comment) {
//        producer.send("saveComment", comment);
        try {
            Comment commented = commentService.saveComment(comment);
            Map<String, Object> response = createResponse(HttpStatus.OK, commented, "saved comment");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot saveComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no tweet with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot saveComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @PutMapping("/comment")
    public ResponseEntity<Map<String, Object>> editComment(@RequestBody Comment comment) {
        try {
            Comment commented = commentService.editComment(comment);
            Map<String, Object> response = createResponse(HttpStatus.OK, commented, "saved comment");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot saveComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no tweet with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot saveComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Map<String, Object>> deleteComment(
            @RequestParam(defaultValue = "") String tweetId,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String id) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("tweetId", tweetId);
//            message.put("username", username);
//            producer.send("deleteComment", message);
            Comment comment;
            if (!id.isEmpty()) {
                comment = commentService.deleteCommentById(Long.parseLong(id));
            }
            else {
                comment = commentService.deleteCommentByTweet_IdAndUsername(Long.parseLong(tweetId), username);
            }
            Map<String, Object> response = createResponse(HttpStatus.OK, comment, "deleted comment");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "comment not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot deleteComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<Map<String, Object>> getComment(
            @RequestParam(defaultValue = "") String tweetId,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String id) {
        try {
            Comment comment;
            if (!id.isEmpty()) {
                comment = commentService.getCommentById(Long.parseLong(id));
            }
            else {
                comment = commentService.getCommentByTweet_IdAndUsername(Long.parseLong(tweetId), username);
            }
            Map<String, Object> response = createResponse(HttpStatus.OK, comment, "comment found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {e.printStackTrace();
            System.out.println("cannot getComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<Map<String, Object>> getComments(
            @RequestParam(defaultValue = "") String tweetId,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> comments;
            if (!tweetId.isEmpty()) {
                comments = commentService.getCommentsByTweet(Long.parseLong(tweetId), sort, pageNo, pageSize);
            }
            else {
                comments = commentService.getAll(sort, pageNo, pageSize);
            }
            Map<String, Object> response = createResponse(HttpStatus.OK, comments, "no comments found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getById IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {
            System.out.println("cannot getById Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }
}
