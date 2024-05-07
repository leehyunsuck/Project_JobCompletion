package Shingu.JobCompletion.repository;

import Shingu.JobCompletion.entity.Questions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionsRepository extends CrudRepository<Questions, Long> {
    List<Questions> findByEmail (String Email);
}
