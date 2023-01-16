package org.cardinalis.tweetservice.DTO;

import lombok.*;
import org.cardinalis.tweetservice.Tweet.Tweet;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetDTOKafka {
    private Long id;
    private String email;
    private Long userid;
    private String username;

    private String fullname;
    private String avatar;
    private String content;

    public static TweetDTOKafka createTweetDTOKafka(Tweet tweet, TweetAuthorDTO authorDTO) {
        TweetDTOKafka tweetDTO = new TweetDTOKafka();
        tweetDTO.setId(tweet.getId());
        tweetDTO.setContent(tweet.getContent());
        tweetDTO.setEmail(authorDTO.getEmail());
        tweetDTO.setAvatar(authorDTO.getAvatar());
        tweetDTO.setUsername(authorDTO.getUsername());
        tweetDTO.setUserid(authorDTO.getId());
        tweetDTO.setFullname(authorDTO.getFullName());
        return tweetDTO;
    }
}
