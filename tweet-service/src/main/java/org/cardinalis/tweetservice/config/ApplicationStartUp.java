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

                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet1).email("thanhnn2@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet4).email("jjeanjacques10@github.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet3).email("thanhnn2@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet2).email("thanhnn2@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet2).email("user3@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet1).email("user3@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet4).email("user3@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet5).email("thanhnn2@gmail.com").build());
                favoriteTweetRepository.save(FavoriteTweet.builder().tweet(tweet5).email("jjeanjacques10@github.com").build());

                Comment commnent1 = commentRepository.save(Comment.builder().tweet(tweet1).email("thanhnn2@gmail.com").content("tuyet").build());
                Comment commnent2 = commentRepository.save(Comment.builder().tweet(tweet4).email("jjeanjacques10@github.com").content("ok").build());
                Comment commnent3 = commentRepository.save(Comment.builder().tweet(tweet3).email("thanhnn2@gmail.com").content("haha vui ghe").build());
                Comment commnent4 = commentRepository.save(Comment.builder().tweet(tweet2).email("thanhnn2@gmail.com").content("an tet nao").build());
                Comment commnent5 = commentRepository.save(Comment.builder().tweet(tweet2).email("user3@gmail.com").content("bun ngu").build());
                Comment commnent6 = commentRepository.save(Comment.builder().tweet(tweet1).email("user3@gmail.com").content("bun").build());
                Comment commnent7 = commentRepository.save(Comment.builder().tweet(tweet4).email("user3@gmail.com").content("mong duoc hd").build());
                Comment commnent8 = commentRepository.save(Comment.builder().tweet(tweet5).email("thanhnn2@gmail.com").content("haha").build());
                Comment commnent9 = commentRepository.save(Comment.builder().tweet(tweet5).email("jjeanjacques10@github.com").content("hihi").build());

                commentRepository.save(commnent1);
                commentRepository.save(commnent2);
                commentRepository.save(commnent3);
                commentRepository.save(commnent4);
                commentRepository.save(commnent5);
                commentRepository.save(commnent6);
                commentRepository.save(commnent7);
                commentRepository.save(commnent8);
                commentRepository.save(commnent9);

                replyRepository.save(Reply.builder().comment(commnent2).email("thanhnn2@gmail.com").content("tuyet").build());
                replyRepository.save(Reply.builder().comment(commnent1).email("jjeanjacques10@github.com").content("ok").build());
                replyRepository.save(Reply.builder().comment(commnent8).email("user3@gmail.com").content("zui z").build());
                replyRepository.save(Reply.builder().comment(commnent3).email("jjeanjacques10@github.com").content("ok").build());
                replyRepository.save(Reply.builder().comment(commnent9).email("thanhnn2@gmail.com").content("mac gi cuoi").build());


            }
        };
    }

    public Tweet saveTweet1() {
        return new Tweet().builder()
                .content("xin chao the gioi")
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .username("Thanh NN 2")
                .email("thanhnn2@gmail.com")
                .build();
    }

    public Tweet saveTweet2() {
        return new Tweet().builder()
                .content("hello")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("jjeanjacques10@github.com")
                .username("jjeanjacques10")
                .build();
    }

    public Tweet saveTweet3() {
        return new Tweet().builder()
                .content("tui nek")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("user3@gmail.com")
                .username("user3")
                .build();
    }

    public Tweet saveTweet4() {
        return new Tweet().builder()
                .content("bun ngu")
                .avatar("https://i.pinimg.com/736x/d4/15/95/d415956c03d9ca8783bfb3c5cc984dde.jpg")
                .username("Thanh NN 2")
                .email("thanhnn2@gmail.com")
                .build();
    }

    public Tweet saveTweet5() {
        return new Tweet().builder()
                .id(95L)
                .content("muon nghi tet")
                .avatar("https://photo-cms-plo.epicdn.me/w850/Uploaded/2023/zreyxqnexq/2015_03_19/happy-dog_VUJG.jpg")
                .email("user3@gmail.com")
                .username("user3")
                .build();
    }




}