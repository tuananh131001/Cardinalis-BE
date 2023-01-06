package com.cardinalis.timelineservice.controller;

import com.cardinalis.timelineservice.model.Timeline;
import com.cardinalis.timelineservice.service.TimelineService;
import org.cardinalis.tweetservice.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Autowired
    TimelineService timelineService;

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getUserTimeline(@RequestParam("username") final String username){
        try {
            List<Tweet> timeline= timelineService.createTimelineForUser(username);
            Map<String, Object> response = createResponse(
                    HttpStatus.OK,
                    timeline
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }  catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "an error occurs - cannot update timeline"
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

//    @GetMapping("/get/{username}")
//    public ResponseEntity<Map<String, Object>> getTimeline(@PathVariable("username") final String username) {
//        LOG.info("Fetching timeline with username = " + username);
//        try {
//            Timeline timeline = timelineService.getTimeline(username);
//            Map<String, Object> response = createResponse(
//                    HttpStatus.OK,
//                    timeline
//            );
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);
//
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }  catch (Exception e) {
//            e.printStackTrace();
//            Map<String, Object> response = createResponse(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    null,
//                    "an error occurs - cannot get timeline"
//            );
//
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(response);
//        }
//    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Map<String, Object>> deleteTimeline(@PathVariable("username") final String username) {
        LOG.info("Deleting timeline with username = " + username);
        try {
//            timelineService.deleteTimeline(username);
            Map<String, Object> response = createResponse(HttpStatus.OK);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = createResponse(HttpStatus.BAD_REQUEST);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }  catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "an error occurs - cannot delete timeline"
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAll() {
        try {
            Map<String, Tweet> allTimeline = timelineService.getAll();
            Map<java.lang.String, Object> response = createResponse(
                    HttpStatus.OK,
                    allTimeline
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<java.lang.String, Object> response = createResponse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }  catch (Exception e) {
            e.printStackTrace();
            Map<java.lang.String, Object> response = createResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "an error occurs - cannot get timelines"
            );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
    }

    private Map<String, Object> createResponse(HttpStatus status, Object data, String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status.is2xxSuccessful());
        response.put("code", status.value());
        response.put("data", data);
        response.put("errors_message", errorMessage);
        return response;
    }
    private Map<String, Object> createResponse(HttpStatus status, Object data) {
        return createResponse(status, data, null);
    }

    private Map<String, Object> createResponse(HttpStatus status) {
        return createResponse(status, null, null);
    }
}
