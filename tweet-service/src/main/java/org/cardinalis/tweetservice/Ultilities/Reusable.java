package org.cardinalis.tweetservice.Ultilities;

import org.apache.kafka.common.errors.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Reusable {

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

    static public ResponseEntity<Map<String, Object>> internalErrorResponse(Exception e) {
        e.printStackTrace();
        Map<String, Object> response = createResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,null, "error from our server");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
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

}
