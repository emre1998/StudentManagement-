package com.App.StudentManagement.Service;

import com.App.StudentManagement.Model.Answer;
import com.App.StudentManagement.Repository.AnswerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@AllArgsConstructor


@Service
public class ScoreService {

    private final AnswerRepository answerRepository;


    public int getScoreByUserId(Long userId) {
        List<Answer> userAnswers = answerRepository.findByUserId(userId);
        int totalScore = 0;
        for (Answer answer : userAnswers) {
            totalScore += answer.getQuestion().getScore();
        }
        return totalScore;
    }

    public int getScoreByQuestionId(Long questionId) {
        List<Answer> questionAnswers = answerRepository.findByQuestionId(questionId);
        int totalScore = 0;
        for (Answer answer : questionAnswers) {
            if (answer.isCorrect()) {
                totalScore += answer.getQuestion().getScore();
            }
        }
        return totalScore;
    }
}

