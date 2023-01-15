package org.cardinalis.tweetservice.FavoriteTweet;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@EnableKafka
public class FavoriteTweetServiceImpl implements FavoriteTweetService {

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Override
    public FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet) {
        try {
            FavoriteTweet find = findFavorite(favoriteTweet.getTweet().getId(), favoriteTweet.getUsermail());
            if (find != null) throw new IllegalArgumentException("fav already exists");
            return null;
        } catch (NoContentFoundException e) {
            try {
                return favoriteTweetRepository.save(favoriteTweet);
            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public FavoriteTweet deleteFavorite(Long tweetId, String usermail) {
        try {
            FavoriteTweet find = findFavorite(tweetId, usermail);
            favoriteTweetRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public FavoriteTweet findFavorite(Long tweetId, String usermail) {
        FavoriteTweet find = favoriteTweetRepository.findByUsermailAndTweet_Id(usermail, tweetId)
                .orElseThrow(() -> new NoContentFoundException("no fav found"));
        return find;
    }

    @Override
    public List<FavoriteTweet> findAllFavoritesOfTweet(Long tweetId) {
        try {
            return favoriteTweetRepository.findByTweet_Id(tweetId);
        }
        catch (Exception e) {
            throw e;
        }
    }


}
