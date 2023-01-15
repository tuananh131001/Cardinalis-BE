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
            FavoriteTweet find = findFavorite(favoriteTweet.getTweet().getId(), favoriteTweet.getEmail());
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
    public FavoriteTweet deleteFavorite(Long tweetId, String email) {
        try {
            FavoriteTweet find = findFavorite(tweetId, email);
            favoriteTweetRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public FavoriteTweet findFavorite(Long tweetId, String email) {
        FavoriteTweet find = favoriteTweetRepository.findByEmailAndTweet_Id(email, tweetId)
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
