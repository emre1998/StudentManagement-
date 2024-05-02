package com.App.StudentManagement.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionText;

    @Column(nullable = false)
    private String lecture;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = true)
    private double score;

    @Column(nullable = false)
    private String difficulty;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Answer> answers;

}
