package com.App.StudentManagement.Repository;

import com.App.StudentManagement.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
    Question findByTopic(String topic);
}
