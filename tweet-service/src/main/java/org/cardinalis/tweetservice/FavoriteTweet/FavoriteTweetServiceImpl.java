package org.cardinalis.tweetservice.FavoriteTweet;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FavoriteTweetServiceImpl implements FavoriteTweetService {

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    @Override
    public FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet) {
        try {
            FavoriteTweet find = findFavorite(favoriteTweet.getTweet().getId(), favoriteTweet.getUsername());
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
    public FavoriteTweet deleteFavorite(Long tweetId, String username) {
        try {
            FavoriteTweet find = findFavorite(tweetId, username);
            favoriteTweetRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public FavoriteTweet findFavorite(Long tweetId, String username) {
        FavoriteTweet find = favoriteTweetRepository.findByUsernameAndTweet_Id(username, tweetId)
                .orElseThrow(() -> new NoContentFoundException("no fav found"));
        return find;
    }

    @Override
    public FavoriteTweet findFavoriteById(Long id) {
        return favoriteTweetRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("no fav found"));
    }


}
