package com.example.quiz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OptionDTO {
    private int id;

    @NotNull(message = "Select the option")
    private String selectedOption;
}
