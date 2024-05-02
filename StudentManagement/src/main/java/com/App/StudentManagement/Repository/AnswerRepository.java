package com.App.StudentManagement.Repository;

import com.App.StudentManagement.Model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestionTopicAndUserId(String topic, Long userId);

    List<Answer> findByUserId(Long userId);

    List<Answer> findByQuestionId(Long questionId);
}
