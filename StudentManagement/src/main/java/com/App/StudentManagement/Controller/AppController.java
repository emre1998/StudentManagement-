package com.App.StudentManagement.Controller;

import com.App.StudentManagement.ErrorHandler.InvalidCredentialsException;
import com.App.StudentManagement.Model.Question;
import com.App.StudentManagement.Model.User;
import com.App.StudentManagement.Request.LoginRequest;
import com.App.StudentManagement.Service.QuestionAnsweringService;
import com.App.StudentManagement.Service.QuestionWritingService;
import com.App.StudentManagement.Service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data

@RestController
@RequestMapping("/api")
public class AppController {
    private final UserService userService;
    private final QuestionWritingService questionWritingService;
    private final QuestionAnsweringService questionAnsweringService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registration successful");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws InvalidCredentialsException {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String token = userService.login(username, password);
        return ResponseEntity.ok("{token :}"  + token + "\"}");
    }
    @PreAuthorize("hasRole('LECTURER')")  // Authorization check (user can update their own info)
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId); // Ensure user ID matches path variable
        userService.updateUser(user);
        return ResponseEntity.ok("User information updated successfully");
    }
    @PreAuthorize("hasRole('LECTURER')")
    @GetMapping("/user/schoolNo/{schoolNo}")
    public ResponseEntity<User> getUserBySchoolNo(@PathVariable String schoolNo) {
        User user = userService.getUserBySchoolNo(Integer.parseInt(schoolNo));
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasRole('ADMIN')")  // Authorization check (only admins can get all users)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);

    }
    @PreAuthorize("hasRole('ADMIN')")  // Authorization check (only admins can delete users)
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PreAuthorize("hasRole('LECTURER')")
    @PostMapping("/question")
    public ResponseEntity<String> createQuestion(@RequestBody Question question) {
        questionWritingService.createQuestion(question);
        return ResponseEntity.ok("Question created successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionAnsweringService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }

    // Answer Question (POST /{questionId}/answer)
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{questionId}/answer")
    public ResponseEntity<String> answerQuestion(@PathVariable Long questionId, @RequestBody String answerText) {
        Question question = questionAnsweringService.getQuestionById(questionId); // Get the question

        if (question.getQuestionText().equals(answerText)) { // Check if answer matches correct answer
            // Answer is correct
            return ResponseEntity.ok("Doğru!:" + question.getScore());
        } else {
            // Answer is incorrect
            return ResponseEntity.ok("Yanlış! Score:0");
        }
    }







}
