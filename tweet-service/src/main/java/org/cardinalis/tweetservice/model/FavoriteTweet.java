package org.cardinalis.tweetservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteTweet {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Type(type = "org.hibernate.type.UUIDCharType")
//    @Column(nullable = false, length = 36)
//    private UUID id;

    private UUID tweetId;

    private String username;

//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    private LocalDateTime likedAt;

    private Boolean favState = null;

}
