package com.example.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.dto.OptionDTO;
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.exception.ResourceNotFoundException;
import com.example.quiz.response.ResponseHandler;
import com.example.quiz.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAll")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("/cat/{cat}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String cat) {
        try {
            return new ResponseEntity<>(questionService.getQuestionsByCategory(cat), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/level/{diff}")
    public ResponseEntity<?> getQuestionsByDefficulty(@PathVariable String diff) {
        try {
            return new ResponseEntity<>(questionService.getQuestionsByDefficulty(diff), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cat/{cat}/level/{diff}")
    public ResponseEntity<?> getQuestionsByCategoryAndDefficultyLevel(@PathVariable String cat, @PathVariable String diff) {
        try {
            return new ResponseEntity<>(questionService.getQuestionsByCategoryAndDefficultyLevel(cat, diff), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/answer/{id}")
    public ResponseEntity<?> getQuestionAnswer(@PathVariable int id) {
        try {
            return new ResponseEntity<>(questionService.getQuestionAnswer(id), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/checkAns")
    public ResponseEntity<?> checkCorrectOption(@RequestBody OptionDTO optionDTO) {
        try {
            return questionService.checkCorrectOption(optionDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
