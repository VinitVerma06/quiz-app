package com.example.quizapp.controller;

import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // GET quiz questions (Frontend API)
    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions() {
        return ResponseEntity.ok(quizService.getQuizQuestions());
    }

    // Submit quiz answers
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(@RequestBody Map<String, Map<Integer, String>> request) {

        Map<Integer, String> answers = request.get("answers");

        if (answers == null || answers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Answers cannot be empty");
        }

        return ResponseEntity.ok(quizService.submitQuiz(answers));
    }

    // Admin: Add new question
    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody QuizQuestion question) {
        quizService.addQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question added successfully");
    }
}
