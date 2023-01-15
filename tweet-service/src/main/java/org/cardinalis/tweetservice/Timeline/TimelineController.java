package org.cardinalis.tweetservice.Timeline;

import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.*;

@RestController
@RequestMapping(value = "/tweet/timeline")
public class TimelineController {
    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Autowired
    TimelineService timelineService;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getUserTimeline(
            @RequestParam(defaultValue = "") String usermail,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "6") int pageSize){
        try {
            Map<String, Object> result;
            if(usermail.isEmpty())  result = timelineService.getAll(pageNo, pageSize);
            else result = timelineService.getTimelineForUser(usermail, pageNo, pageSize);
            Map<String, Object> response = createResponse(HttpStatus.OK, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return illegalArgResponse(e);
        }  catch (NoContentFoundException e) {
            Map<String, Object> response = createResponse(
                    HttpStatus.NOT_IMPLEMENTED, null, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_IMPLEMENTED)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return errorResponse(e);
        }
    }

}
