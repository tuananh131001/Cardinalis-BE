package org.cardinalis.tweetservice.config;
import lombok.RequiredArgsConstructor;
import org.cardinalis.tweetservice.Comment.Comment;
import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweet;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.cardinalis.tweetservice.ReplyComment.Reply;
import org.cardinalis.tweetservice.ReplyComment.ReplyRepository;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationStartUp {
    @Bean
    public CommandLineRunner loadData(TweetRepository tweetRepository, FavoriteTweetRepository favoriteTweetRepository, CommentRepository commentRepository, ReplyRepository replyRepository) {
        return (args) -> {
            List<Tweet> tweets = tweetRepository.findAll();
            if (ObjectUtils.isEmpty(tweets)) {
                Tweet tweet1 = tweetRepository.save(saveTweet1());
                Tweet tweet2 = tweetRepository.save(saveTweet2());
                Tweet tweet3 = tweetRepository.save(saveTweet3());
                tweetRepository.save(tweet1);
                tweetRepository.save(tweet2);
                tweetRepository.save(tweet3);


            }
        };
    }

    public Tweet saveTweet1() {
        return new Tweet().builder()
                .content("xin chao the gioi")
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .username("Thanh NN 2")
                .lastEdit(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .email("thanhnn2@gmail.com")
                .build();
    }

    public Tweet saveTweet2() {
        return new Tweet().builder()
                .content("hello")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("jjeanjacques10@github.com")
                .lastEdit(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .username("jjeanjacques10")
                .build();
    }

    public Tweet saveTweet3() {
        return new Tweet().builder()
                .content("tui nek")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("user3@gmail.com")
                .lastEdit(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .username("user3")
                .build();
    }

    public Tweet saveTweet4() {
        return new Tweet().builder()
                .content("bun ngu")
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .username("Thanh NN 2")
                .email("thanhnn2@gmail.com")
                .lastEdit(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Tweet saveTweet5() {
        return new Tweet().builder()
                .id(95L)
                .content("muon nghi tet")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("user3@gmail.com")
                .lastEdit(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .username("user3")
                .build();
    }




}