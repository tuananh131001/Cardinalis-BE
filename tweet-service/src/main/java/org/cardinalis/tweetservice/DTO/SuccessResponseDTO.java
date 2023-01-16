package org.cardinalis.tweetservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SuccessResponseDTO {
    private Object data;
    private String code;
    private boolean success;
    private String errors_message;

}