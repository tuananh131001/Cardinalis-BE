package com.cardinalis.timelineservice.model;

import lombok.*;
import org.cardinalis.tweetservice.model.Tweet;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@RedisHash("TIMELINE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeline implements Serializable {
    private static final long serialVersionUID = 10L;

//    @Id
//    private String username;

    private List<Tweet> userTimeline;
}
