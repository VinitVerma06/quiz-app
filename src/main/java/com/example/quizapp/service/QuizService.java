package com.example.quizapp.service;

import com.example.quizapp.model.QuizQuestion;
import com.example.quizapp.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    /**
     * Fetch questions for frontend
     * IMPORTANT: Do NOT expose correct answers
     */
    public List<Map<String, Object>> getQuizQuestions() {

        List<QuizQuestion> questions = quizRepository.findAll();

        return questions.stream().map(q -> {
            Map<String, Object> response = new HashMap<>();
            response.put("id", q.getId());
            response.put("question", q.getQuestion());
            response.put("options", List.of(
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD()
            ));
            return response;
        }).toList();
    }

    /**
     * Submit answers and calculate score
     */
    public Map<String, Integer> submitQuiz(Map<Integer, String> answers) {

        int score = 0;
        int total = answers.size();

        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            int questionId = entry.getKey();
            String userAnswer = entry.getValue();

            QuizQuestion question = quizRepository.findById(questionId);

            if (question.getCorrectOption().equalsIgnoreCase(userAnswer)) {
                score++;
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("score", score);
        result.put("total", total);

        return result;
    }

    /**
     * Admin: Add new quiz question
     */
    public void addQuestion(QuizQuestion question) {
        quizRepository.save(question);
    }
}
