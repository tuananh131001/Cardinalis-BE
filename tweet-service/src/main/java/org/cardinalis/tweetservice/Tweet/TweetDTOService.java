package org.cardinalis.tweetservice.Tweet;

import lombok.AllArgsConstructor;
import org.cardinalis.tweetservice.Comment.CommentRepository;
import org.cardinalis.tweetservice.FavoriteTweet.FavoriteTweetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TweetDTOService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    private final ModelMapper mapper;

    TweetDTO mapTweetTweetDTO(Tweet tweet) {
        TweetDTO tweetDTO = mapper.map(tweet, TweetDTO.class);
        tweetDTO.setTotalFav(favoriteTweetRepository.countByTweet_Id(tweetDTO.getId()));
        tweetDTO.setTotalComment(commentRepository.countByTweet_Id(tweetDTO.getId()));
        return tweetDTO;
    }
}
