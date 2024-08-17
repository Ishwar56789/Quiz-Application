package com.example.quiz.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private int id;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String category;
    private String level;
}
