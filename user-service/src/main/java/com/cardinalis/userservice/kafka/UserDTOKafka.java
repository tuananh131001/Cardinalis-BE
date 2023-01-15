//package com.cardinalis.userservice.kafka;
//
//import com.cardinalis.userservice.model.UserEntity;
//import lombok.*;
//
//import java.io.Serializable;
//
//@Builder
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDTOKafka implements Serializable {
//    private Long id;
//
//    private String username;
//
//    private String avatar;
//
//    private String usermail;
//
//    static public UserDTOKafka mapToUserDTOKafka(UserEntity user) {
//        return UserDTOKafka.builder()
//                .id(user.getId())
//                .usermail(user.getEmail())
//                .avatar(user.getAvatar())
//                .username(user.getUsername()).build();
//    }
//}
