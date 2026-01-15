package com.example.quizapp.repository;

import com.example.quizapp.model.QuizQuestion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    private final RowMapper<QuizQuestion> rowMapper = (rs, rowNum) -> {
        QuizQuestion q = new QuizQuestion();
        q.setId(rs.getInt("id"));
        q.setQuestion(rs.getString("question"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        return q;
    };

    // Get all questions
    public List<QuizQuestion> findAll() {
        String sql = "SELECT * FROM quiz_question";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Get question by id
    public QuizQuestion findById(int id) {
        String sql = "SELECT * FROM quiz_question WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    // Save new question (Admin API)
    public void save(QuizQuestion question) {
        String sql = """
                INSERT INTO quiz_question
                (question, option_a, option_b, option_c, option_d, correct_option)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                question.getQuestion(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectOption()
        );
    }
}
