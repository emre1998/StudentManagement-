package com.App.StudentManagement.Service;

import com.App.StudentManagement.ErrorHandler.InsufficientPermissionException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.App.StudentManagement.Model.Question;
import com.App.StudentManagement.Model.User;
import com.App.StudentManagement.Repository.QuestionRepository;

@Data
@AllArgsConstructor
@Service
public class QuestionWritingService {

    private final QuestionRepository questionRepository;

    public void createQuestion(Question question) {
        // Kullanıcının rolünü kontrol et
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = user.getRole();

        // Sadece "LECTURER" rolüne sahip kullanıcılar tarafından soru oluşturulabilir
        if ("LECTURER".equals(userRole)) {
            // Kullanıcı "LECTURER" rolüne sahip ise, soruyu kaydet
            questionRepository.save(question);
        } else {
            // Kullanıcı "LECTURER" rolüne sahip değilse, yetki hatası fırlat
            throw new InsufficientPermissionException("Soru oluşturma yetkiniz yok.");
        }
    }
}
