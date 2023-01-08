package org.cardinalis.tweetservice.Ultilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

//@Data
//@Builder
//@AllArgsConstructor
public class Reusable {
//    Map<String, Object> response;

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

}
