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

    @Column
    private Long userid;

    @Column
    private String username;

    @Column
    private String avatar;


    private String content;
}
