package org.cardinalis.tweetservice.DTO;

import lombok.*;
import org.cardinalis.tweetservice.Tweet.Tweet;

import java.io.Serializable;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetAuthorDTO implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String avatar;

}