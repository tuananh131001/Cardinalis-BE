package com.cardinalis.timelineservice.controller;

import com.cardinalis.timelineservice.model.Timeline;
import com.cardinalis.timelineservice.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/timeline")
public class TimelineController {

    private static final Logger LOG = LoggerFactory.getLogger(TimelineController.class);

    @Autowired
    TimelineService timelineService;

    @PostMapping("/update/{username}")
    public String updateTimeline(@PathVariable("username") final String username){
        try {
            timelineService.updateTimeline(username);
            return "Successfully updated timeline for username = " + username;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "cannot update timeline";
    }

    @GetMapping("/get/{username}")
    public Timeline getTimeline(@PathVariable("username") final String username) {
        LOG.info("Fetching timeline with username = " + username);
        return timelineService.getTimeline(username);
    }

    @DeleteMapping("/delete/{username}")
    public String deleteTimeline(@PathVariable("username") final String username) {
        LOG.info("Deleting timeline with username = " + username);
        timelineService.deleteTimeline(username);
        return "deleted";
    }

    @GetMapping("/get")
    public Map<String, Timeline> getAll() {
        return timelineService.getAll();
    }
}
