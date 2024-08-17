package com.example.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT qs FROM Question qs WHERE qs.category LIKE %:category%")
    List<Question> findByCategory(@Param("category") String cat);

    @Query("SELECT qs FROM Question qs WHERE qs.level LIKE %:level%")
    List<Question> findByLevel(@Param("level") String diff);

    @Query("SELECT qs FROM Question qs WHERE qs.category LIKE %:cat% AND qs.level LIKE %:level%")
    List<Question> findByCategoryAndLevel(@Param("cat") String cat,@Param("level") String diff);

}
