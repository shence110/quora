package tiny.quora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiny.quora.dao.QuestionDao;
import tiny.quora.model.Question;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDao.searchQuestions(userId, offset, limit);
    }
}
