package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.QuestionDao;
import com.telusko.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getAllQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteQuestionById(Integer id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question qs) {
        Question foundedQuestion = questionDao.findById(id).orElseThrow(() ->
                new RuntimeException("Question not found"));
        try {
            foundedQuestion.setQuestionTitle(qs.getQuestionTitle());
            foundedQuestion.setOption1(qs.getOption1());
            foundedQuestion.setOption2(qs.getOption2());
            foundedQuestion.setOption3(qs.getOption3());
            foundedQuestion.setOption4(qs.getOption4());
            foundedQuestion.setRightAnswer(qs.getRightAnswer());
            foundedQuestion.setDifficulty_level(qs.getDifficulty_level());
            foundedQuestion.setCategory(qs.getCategory());
            questionDao.save(foundedQuestion);
            return new ResponseEntity<>(foundedQuestion, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
