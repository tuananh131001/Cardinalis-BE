package org.cardinalis.tweetservice.Tweet;

import lombok.*;

import javax.persistence.Column;
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

    private String avatar;
    private String content;
    public static TweetDTOKafka createTweetDTOKafkaFromTweet(Tweet message) {
        TweetDTOKafka tweetDTO = new TweetDTOKafka();
        tweetDTO.setId(message.getId());
        tweetDTO.setContent(message.getContent());
        tweetDTO.setEmail(message.getEmail());
        tweetDTO.setAvatar(message.getAvatar());
        tweetDTO.setUsername(message.getUsername());
        tweetDTO.setUserid(message.getUserid());
        return tweetDTO;
    }
}
