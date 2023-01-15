package org.cardinalis.tweetservice.DTOUser;

import lombok.*;


@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOKafka {
    private Long id;

    private String username;

    private String avatar;

    private String usermail;


}
