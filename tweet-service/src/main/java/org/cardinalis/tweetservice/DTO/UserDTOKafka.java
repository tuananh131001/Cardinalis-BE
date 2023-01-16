package org.cardinalis.tweetservice.DTO;

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
