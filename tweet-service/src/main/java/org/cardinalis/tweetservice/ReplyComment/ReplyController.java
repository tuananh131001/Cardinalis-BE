package org.cardinalis.tweetservice.ReplyComment;

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
public class ReplyController {
    @Autowired
    ReplyService replyService;

//    @Autowired
//    Producer producer;

    @PostMapping("/reply")
    public ResponseEntity<Map<String, Object>> saveReply(
            @RequestHeader("Authorization") String token,
            @RequestBody Reply reply) {
        try {
            String mail = getUserMailFromHeader(token);
            reply.setUsermail(mail);
            Reply commented = replyService.saveReply(reply);
            Map<String, Object> response = createResponse(HttpStatus.OK, commented, "saved reply");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot saveReply IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);
        } catch (DataIntegrityViolationException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, "no comment with this id");
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (AuthorizationException e) {
            System.out.println("cannot saveReply AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot saveReply Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @PutMapping("/reply")
    public ResponseEntity<Map<String, Object>> editReply(
            @RequestHeader("Authorization") String token,
            @RequestBody Reply reply) {
        try {
            String mail = getUserMailFromHeader(token);
            reply.setUsermail(mail);
            Reply replyEdited = replyService.editReply(reply);
            Map<String, Object> response = createResponse(HttpStatus.OK, replyEdited, "saved reply");
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
        } catch (AuthorizationException e) {
            System.out.println("cannot editReply AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot editReply Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @DeleteMapping("/reply")
    public ResponseEntity<Map<String, Object>> deleteReplyComment(
            @RequestHeader("Authorization") String token,
            @RequestParam Long id) {
        try {
//            Map<String, Object> message = new HashMap<>();
//            message.put("commentId", commentId);
//            message.put("usermail", usermail);
//            producer.send("deleteComment", message);
            String mail = getUserMailFromHeader(token);
            Reply reply = replyService.getReplyById(id);
            if (!mail.equals(reply.getUsermail())) throw new AuthorizationException("unauthorized user");
            replyService.deleteReplyById(id);
            Map<String, Object> response = createResponse(HttpStatus.OK, reply, "deleted reply");
            return ResponseEntity.ok(response);
        } catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (IllegalArgumentException e) {
            System.out.println("cannot deleteReply IllegalArgumentException: " + e.getMessage());
            return illegalArgResponse(e);

        } catch (AuthorizationException e) {
            System.out.println("cannot deleteReply AuthorizationException: " + e.getMessage());
            return unauthorizedResponse(e);

        } catch (Exception e) {
            System.out.println("cannot deleteReply Exception: " + e.getMessage());
            return errorResponse(e);
        }
    }

    @GetMapping("/reply")
    public ResponseEntity<Map<String, Object>> getReplyById(@RequestParam Long id) {
        try {
            Reply reply = replyService.getReplyById(id);

            Map<String, Object> response = createResponse(HttpStatus.OK, reply, "reply found");
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
            return errorResponse(e);
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
            Map<String, Object> response = createResponse(HttpStatus.OK, comments, null);
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
            return errorResponse(e);
        }
    }
}
