package org.cardinalis.tweetservice.ReplyComment;

//import org.cardinalis.tweetservice.engine.Producer;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.*;


@RestController
@RequestMapping("/tweet")
public class ReplyController {
    @Autowired
    ReplyService replyService;

//    @Autowired
//    Producer producer;

    @PostMapping("/reply")
    public ResponseEntity<Map<String, Object>> saveReplyComment(@RequestBody Reply reply) {
//        producer.send("saveComment", comment);
        try {
            Reply commented = replyService.saveReply(reply);
            Map<String, Object> response = createResponse(HttpStatus.OK, commented, "saved replyComment");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot saveReplyComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no comment with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot saveReplyComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @PutMapping("/reply")
    public ResponseEntity<Map<String, Object>> editReply(@RequestBody Reply reply) {
        try {
            Reply replyEdited = replyService.editReply(reply);
            Map<String, Object> response = createResponse(HttpStatus.OK, replyEdited, "saved replyComment");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot editReply IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no comment with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            System.out.println("cannot editReply Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @DeleteMapping("/reply")
    public ResponseEntity<Map<String, Object>> deleteReplyComment(@RequestParam Long id) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("commentId", commentId);
//            message.put("username", username);
//            producer.send("deleteComment", message);
            Reply reply = replyService.deleteReplyById(id);
            Map<String, Object> response = createResponse(HttpStatus.OK, reply, "deleted replyComment");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "comment not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot deleteRepl IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteReply Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/reply")
    public ResponseEntity<Map<String, Object>> getReplyById(@RequestParam Long id) {
        try {
            Reply reply = replyService.getReplyById(id);

            Map<String, Object> response = createResponse(HttpStatus.OK, reply, "comment found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getReply IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {e.printStackTrace();
            System.out.println("cannot getReply Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }

    @GetMapping("/replies")
    public ResponseEntity<Map<String, Object>> getRepliesOfComment(
            @RequestParam String commentId,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize) {
        try {
            Map<String, Object> comments = replyService.getRepliesOfComment(Long.parseLong(commentId), sort, pageNo, pageSize);

            Map<String, Object> response = createResponse(HttpStatus.OK, comments, "no replyComments found");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_FOUND, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot getRepliesOfComment IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        }  catch (Exception e) {
            System.out.println("cannot getRepliesOfComment Exception: " + e.getMessage());
            return internalErrorResponse(e);
        }
    }
}
