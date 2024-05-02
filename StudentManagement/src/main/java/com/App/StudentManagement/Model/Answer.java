package com.App.StudentManagement.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity


public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answerText;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    private Question question;
}
