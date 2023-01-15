package org.cardinalis.tweetservice.Comment;

//import org.cardinalis.tweetservice.engine.Producer;
import org.apache.kafka.common.errors.AuthorizationException;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.*;


@RestController
@RequestMapping("/tweet")
public class CommentController {
    @Autowired
    CommentService commentService;

//    @Autowired
//    Producer producer;

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> saveComment(
            @RequestHeader("Authorization") String token,
            @RequestBody Comment comment) {
//        producer.send("saveComment", comment);
        try {
            String mail = getUserMailFromHeader(token);
            comment.setEmail(mail);
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
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (AuthorizationException e) {
            System.out.println("cannot saveFav AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot saveComment Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @PutMapping("/comment")
    public ResponseEntity<Map<String, Object>> editComment(
            @RequestHeader("Authorization") String token,
            @RequestBody Comment comment) {
        try {
            String mail = getUserMailFromHeader(token);
            comment.setEmail(mail);
            Comment commentEdited = commentService.editComment(comment);
            Map<String, Object> response = createResponse(HttpStatus.OK, commentEdited, "saved comment");
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
        } catch (AuthorizationException e) {
            System.out.println("cannot saveFav AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot saveComment Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Map<String, Object>> deleteComment(
            @RequestHeader("Authorization") String token,
            @RequestParam Long id) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("tweetId", tweetId);
//            message.put("email", email);
//            producer.send("deleteComment", message);
            String mail = getUserMailFromHeader(token);
            Comment comment = commentService.getCommentById(id);
            if (!mail.equals(comment.getEmail())) throw new AuthorizationException("unauthorized user");
            commentService.deleteComment(comment);
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

        } catch (AuthorizationException e) {
            System.out.println("cannot saveFav AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteComment Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<Map<String, Object>> getCommentById(@RequestParam Long id) {
        try {
            Comment comment = commentService.getCommentById(id);
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

        }  catch (Exception e) {
            System.out.println("cannot getComment Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping("/comments")
    public ResponseEntity<Map<String, Object>> getCommentsOfTweet(
            @RequestParam Long tweetId,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> comments = commentService.getCommentsOfTweet(tweetId, sort, pageNo, pageSize);

            Map<String, Object> response = createResponse(HttpStatus.OK, comments, "no comments found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getCommentsOfTweet IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {
            System.out.println("cannot getCommentsOfTweet Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }
}
