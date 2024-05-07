package Shingu.JobCompletion.dto;

import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.entity.UserInfo;
import lombok.Data;

@Data
public class UserInfoForm {
    private String email;
    private String password;

    public UserInfo toEntity() {
        return UserInfo.builder()
                .email(email)
                .password(password)
                .build();
    }
}
