package Shingu.JobCompletion.repository;

import Shingu.JobCompletion.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);
    UserInfo findByEmailAndPassword(String email, String password);
}
