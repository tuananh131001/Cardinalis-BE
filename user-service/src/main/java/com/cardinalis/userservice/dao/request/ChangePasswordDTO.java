package com.cardinalis.userservice.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;


}
