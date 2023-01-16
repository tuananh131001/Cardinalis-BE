package org.cardinalis.tweetservice.FavoriteTweet;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.createPageResponse;
import static org.cardinalis.tweetservice.Util.Reusable.getResultList;

@Slf4j
@Service
public class FavoriteTweetService {

    @Autowired
    FavoriteTweetRepository favoriteTweetRepository;

    public FavoriteTweet saveFavorite(FavoriteTweet favoriteTweet) throws Exception {
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

    public FavoriteTweet deleteFavorite(Long tweetId, String email) throws Exception {
        try {
            FavoriteTweet find = findFavorite(tweetId, email);
            favoriteTweetRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    public FavoriteTweet findFavorite(Long tweetId, String email) throws Exception{
        FavoriteTweet find = favoriteTweetRepository.findByEmailAndTweet_Id(email, tweetId)
                .orElseThrow(() -> new NoContentFoundException("no fav found"));
        return find;
    }

    public List<FavoriteTweet> findFavByTweet(Long tweetId) throws Exception {
        return favoriteTweetRepository.findByTweet_Id(tweetId);
    }

    public Map<String, Object> findFavByUser(String email, int pageNo, int pageSize) throws Exception{
        Page<FavoriteTweet> page = favoriteTweetRepository.findByEmailOrderByCreatedAt(email, PageRequest.of(pageNo, pageSize, Sort.Direction.DESC));
        List<Tweet> tweets = page.getContent().stream().map(favoriteTweet -> favoriteTweet.getTweet()).collect(Collectors.toList());
        return  createPageResponse(getResultList(tweets, true), page.getNumber(), page.hasNext(), page.getTotalPages(), page.getNumberOfElements(), page.getSize());

    }

}
