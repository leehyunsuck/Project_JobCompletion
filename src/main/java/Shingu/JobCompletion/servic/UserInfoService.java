package Shingu.JobCompletion.servic;

import Shingu.JobCompletion.dto.QuestionsForm;
import Shingu.JobCompletion.dto.UserInfoForm;
import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.entity.UserInfo;
import Shingu.JobCompletion.repository.QuestionsRepository;
import Shingu.JobCompletion.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@Serevic
@RestController
public class UserInfoService {
    public boolean isEmailExists(String email) {
        return userInfoRepository.findByEmail(email) != null;
    }

    public UserInfo saveUser(UserInfo user) {
        return userInfoRepository.save(user);
    }
    @Autowired
    private UserInfoRepository userInfoRepository;

    @PostMapping(path = "/save/userInfo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createQuestions(@ModelAttribute UserInfoForm form) {
        UserInfo userInfo = form.toEntity();

        userInfoRepository.save(userInfo);
        return ResponseEntity.ok().body("Questions saved successfully");
    }
}