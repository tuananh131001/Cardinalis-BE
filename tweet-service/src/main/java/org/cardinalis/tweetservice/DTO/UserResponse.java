package org.cardinalis.tweetservice.DTO;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String bio;
    private String avatar;

    private boolean isWaitingForApprove;

    private boolean isFollower;
}