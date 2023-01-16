package org.cardinalis.tweetservice.Util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cardinalis.tweetservice.DTO.TweetAuthorDTO;
import org.cardinalis.tweetservice.Tweet.Tweet;
import org.cardinalis.tweetservice.Tweet.TweetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Reusable {
    @Autowired
    RestTemplate restTemplate;


    static public Map<String, Object> createResponse(HttpStatus status, Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status.is2xxSuccessful());
        response.put("code", status.value());
        response.put("data", data);
        response.put("message", message);
        return response;
    }
    static public Map<String, Object> createResponse(HttpStatus status, Object data) {
        return createResponse(status, data, null);
    }

    static public Map<String, Object> createResponse(HttpStatus status) {
        return createResponse(status, null, null);
    }
    
    static public ResponseEntity<Map<String, Object>> illegalArgResponse(IllegalArgumentException e) {
        e.printStackTrace();
        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST, null, "illegal argument");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    static public ResponseEntity<Map<String, Object>> errorResponse(Exception e) {
        e.printStackTrace();
        Map<String, Object> response = createResponse(
                HttpStatus.NOT_IMPLEMENTED,null, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .body(response);
    }

    static public ResponseEntity<Map<String, Object>> unauthorizedResponse(Exception e) {
        e.printStackTrace();
        Map<String, Object> response = createResponse(
                HttpStatus.UNAUTHORIZED,null, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    static public Map<String, Object> createPageResponse(Object data, int currentPage, boolean hasNext, int totalPage, int pageElements, int pageCapacity) {
        Map<String, Object> response = new HashMap<>();
        response.put("data",data);
        response.put("currentPage",currentPage);
        response.put("hasNext",hasNext);
        response.put("totalPage", totalPage);
        response.put("pageElements", pageElements);
        response.put("pageCapacity", pageCapacity);

        return response;
    }

    public static String getUserMailFromHeader(String header) throws Exception {
        String[] parts = header.split(" ");
        if (parts.length != 2) throw new IllegalArgumentException("incorrect header format for token");
        String token = parts[1];
        System.out.println(token);
        String payload = JWTDecoderUtil.decodeJWTToken(token, "dejavu");
        Map<String, String> map = Arrays.stream(payload.replaceAll("[{}\"]", "").split(","))
                .map(s -> s.split(":", 2))
                .collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()));
        return map.get("sub"); //the mail
    }

    public List<TweetDTO> getTweetDTOList(List<Tweet> tweets) throws Exception {
        List<String> emails = getEmailsFromTweets(tweets);
        Map<String, TweetAuthorDTO>  authorDTOMap = getUserInfo(emails);

        List<TweetDTO> tweetDTOS = tweets
                .stream()
                .map(tweet -> new TweetDTO(tweet, authorDTOMap.get(tweet.getEmail())))
                .collect(Collectors.toList());
        return tweetDTOS;
    }

    public List<TweetDTO> getTweetDTOList(List<Tweet> tweets, String email) throws JsonProcessingException {
        TweetAuthorDTO authorDTO = getUserInfo(email);
        List<TweetDTO> tweetDTOS = tweets.stream().map(tweet -> new TweetDTO(tweet,authorDTO)).collect(Collectors.toList());
        return tweetDTOS;
    }

    public static List<String> getEmailsFromTweets(List<Tweet> tweets) {
        List<String> emails = tweets.stream().map(tweet -> tweet.getEmail()).collect(Collectors.toList());
        return emails;
    }


    public Map<String, TweetAuthorDTO> getUserInfo(List<String> emails) throws NullPointerException, JsonProcessingException {
        try {
            String base_url = "http://cardinalis-be.live";

            List<String> emails_distinct = emails.stream().distinct().collect(Collectors.toList());

            Map<String, TweetAuthorDTO> result = new HashMap<>();

            for (String email: emails_distinct) {
                String url = base_url + "/user/serverExchangeData/" + email;
                ResponseEntity<Map> tweetAuthorDTOResponseEntity = restTemplate.getForEntity(url, Map.class);
                Map<String, Object> map = tweetAuthorDTOResponseEntity.getBody();
                String m = (String) map.get("data");
                TweetAuthorDTO authorDTO = new ObjectMapper().readValue(m, TweetAuthorDTO.class);
                result.put(email, authorDTO);
            }
            return result;

        } catch (NullPointerException e) {
            throw new NullPointerException("no user found");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public TweetAuthorDTO getUserInfo(String email) throws NullPointerException, JsonProcessingException {
        try {
//        String base_url = "http://localhost:3003";
            String base_url = "http://cardinalis-be.live";
            String url = base_url + "/user/serverExchangeData/" + email;
            ResponseEntity<Map> tweetAuthorDTOResponseEntity = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> map = tweetAuthorDTOResponseEntity.getBody();
            String m = (String) map.get("data");
            TweetAuthorDTO authorDTO = new ObjectMapper().readValue(m, TweetAuthorDTO.class);
            return authorDTO;

        } catch (NullPointerException e) {
            throw new NullPointerException("no user found");
        }
        catch (Exception e) {
            throw e;
        }
    }



}
