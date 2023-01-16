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
                Tweet tweet4 = tweetRepository.save(saveTweet4());
                Tweet tweet5 = tweetRepository.save(saveTweet5());
                tweetRepository.save(tweet1);
                tweetRepository.save(tweet2);
                tweetRepository.save(tweet3);
                tweetRepository.save(tweet4);
                tweetRepository.save(tweet5);

                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet1).email("thanhnn2@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet4).email("jjeanjacques10@github.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet3).email("thanhnn2@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet2).email("thanhnn2@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet2).email("user3@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet1).email("user3@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet4).email("user3@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet5).email("thanhnn2@gmail.com").createdAt(LocalDateTime.now()).build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet5).email("jjeanjacques10@github.com").createdAt(LocalDateTime.now()).build());

                Comment commnent1 = commentRepository.save(Comment.builder().tweet(tweet1).email("thanhnn2@gmail.com").content("tuyet").createdAt(LocalDateTime.now()).build());
                Comment commnent2 = commentRepository.save(Comment.builder().tweet(tweet4).email("jjeanjacques10@github.com").content("ok").createdAt(LocalDateTime.now()).build());
                Comment commnent3 = commentRepository.save(Comment.builder().tweet(tweet3).email("thanhnn2@gmail.com").content("haha vui ghe").createdAt(LocalDateTime.now()).build());
                Comment commnent4 = commentRepository.save(Comment.builder().tweet(tweet2).email("thanhnn2@gmail.com").content("an tet nao").createdAt(LocalDateTime.now()).build());
                Comment commnent5 = commentRepository.save(Comment.builder().tweet(tweet2).email("user3@gmail.com").content("bun ngu").createdAt(LocalDateTime.now()).build());
                Comment commnent6 = commentRepository.save(Comment.builder().tweet(tweet1).email("user3@gmail.com").content("bun").createdAt(LocalDateTime.now()).build());
                Comment commnent7 = commentRepository.save(Comment.builder().tweet(tweet4).email("user3@gmail.com").content("mong duoc hd").createdAt(LocalDateTime.now()).build());
                Comment commnent8 = commentRepository.save(Comment.builder().tweet(tweet5).email("thanhnn2@gmail.com").content("haha").createdAt(LocalDateTime.now()).build());
                Comment commnent9 = commentRepository.save(Comment.builder().tweet(tweet5).email("jjeanjacques10@github.com").content("hihi").createdAt(LocalDateTime.now()).build());

                commentRepository.save(commnent1);
                commentRepository.save(commnent2);
                commentRepository.save(commnent3);
                commentRepository.save(commnent4);
                commentRepository.save(commnent5);
                commentRepository.save(commnent6);
                commentRepository.save(commnent7);
                commentRepository.save(commnent8);
                commentRepository.save(commnent9);

                replyRepository.save(Reply.builder().comment(commnent2).email("thanhnn2@gmail.com").content("tuyet").createdAt(LocalDateTime.now()).build());
                replyRepository.save(Reply.builder().comment(commnent1).email("jjeanjacques10@github.com").content("ok").createdAt(LocalDateTime.now()).build());
                replyRepository.save(Reply.builder().comment(commnent8).email("user3@gmail.com").content("zui z").createdAt(LocalDateTime.now()).build());
                replyRepository.save(Reply.builder().comment(commnent3).email("jjeanjacques10@github.com").content("ok").createdAt(LocalDateTime.now()).build());
                replyRepository.save(Reply.builder().comment(commnent9).email("thanhnn2@gmail.com").content("mac gi cuoi").createdAt(LocalDateTime.now()).build());


            }
        };
    }

    public Tweet saveTweet1() {
        return new Tweet().builder()
                .content("xin chao the gioi")
                .email("thanhnn2@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Tweet saveTweet2() {
        return new Tweet().builder()
                .content("hello")
                .email("jjeanjacques10@github.com")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Tweet saveTweet3() {
        return new Tweet().builder()
                .content("tui nek")
                .email("user3@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Tweet saveTweet4() {
        return new Tweet().builder()
                .content("bun ngu")
                .email("thanhnn2@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Tweet saveTweet5() {
        return new Tweet().builder()
                .id(95L)
                .content("muon nghi tet")
                .email("user3@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();
    }




}