package com.App.StudentManagement.Service;

import com.App.StudentManagement.ErrorHandler.QuestionNotFoundException;
import com.App.StudentManagement.Model.Answer;
import com.App.StudentManagement.Model.Question;
import com.App.StudentManagement.Repository.AnswerRepository;
import com.App.StudentManagement.Repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
@Data
@AllArgsConstructor

@Service
public class QuestionAnsweringService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Soru bulunamadı."));
    }

    public String answerQuestion(Long questionId, String answerText) {
        // Soruyu al
        Question question = getQuestionById(questionId);

        // Yeni bir cevap oluştur
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerText(answerText);

        // Cevabı veritabanına kaydet
        answerRepository.save(answer);

        // Geri bildirim ver
        return "Soru başarıyla cevaplandı.";
    }
}
