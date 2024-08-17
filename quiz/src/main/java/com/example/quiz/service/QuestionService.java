package com.example.quiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quiz.dto.AnswerDTO;
import com.example.quiz.dto.OptionDTO;
import com.example.quiz.dto.QuestionDTO;
import com.example.quiz.exception.ResourceNotFoundException;
import com.example.quiz.model.Question;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.response.ResponseHandler;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    private QuestionDTO convertToQuestionDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setOption1(question.getOption1());
        dto.setOption2(question.getOption2());
        dto.setOption3(question.getOption3());
        dto.setOption4(question.getOption4());
        dto.setCategory(question.getCategory());
        dto.setLevel(question.getLevel());
        return dto;
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream().map(this::convertToQuestionDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByCategory(String cat) throws ResourceNotFoundException {
        List<Question> questonsByCategory = questionRepository.findByCategory(cat);

        if (questonsByCategory.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for category " + cat);
        }
        return questonsByCategory.stream().map(this::convertToQuestionDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByDefficulty(String diff) throws ResourceNotFoundException {
        List<Question> questionByLevel = questionRepository.findByLevel(diff);

        if (questionByLevel.isEmpty()) {
            throw new ResourceNotFoundException("There are only three difficulty level Easy, Medium and Hard");
        }
        return questionByLevel.stream().map(this::convertToQuestionDTO).collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByCategoryAndDefficultyLevel(String cat, String diff) throws ResourceNotFoundException {
        List<Question> questionsByLevelAndCat = questionRepository.findByCategoryAndLevel(cat, diff);
        
        if (questionsByLevelAndCat.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for specific category or difficulty level");
        }
        return questionsByLevelAndCat.stream().map(this::convertToQuestionDTO).collect(Collectors.toList());
    }

    private AnswerDTO convertToAnswerDTO(Question question) {
        AnswerDTO dto = new AnswerDTO();
        dto.setAsnwer(question.getAnswer());
        return dto;
    }

    public AnswerDTO getQuestionAnswer(int id) throws ResourceNotFoundException {
        Optional<Question> data = questionRepository.findById(id);

        if (data.isPresent()) {
            return convertToAnswerDTO(data.get());
        }
        throw new ResourceNotFoundException("Question is not found for Id: " + id);
    }

    public ResponseEntity<?> checkCorrectOption(OptionDTO optionDTO) throws ResourceNotFoundException{
        Optional<Question> data = questionRepository.findById(optionDTO.getId());

        if (data.isPresent()) {
            Question question = data.get();

            if (optionDTO.getSelectedOption().equals(question.getAnswer())) {
                return ResponseHandler.responseBuilder("You selected right option", HttpStatus.OK);
            } 
            return ResponseHandler.responseBuilder("You selected wrong option", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Question not found for Id: " + optionDTO.getId());
        }
    }
}

