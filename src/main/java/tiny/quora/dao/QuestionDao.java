package tiny.quora.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tiny.quora.model.Question;

import java.util.List;

@Mapper
public interface QuestionDao {

    @Insert({" insert into question " +
            " (user_id,title,content,comment_cnt,create_date,update_date) " +
            " values " +
            " (#{userId},#{title},#{content},#{commentCnt},#{createDate},#{createDate}) "})
    int addQuestion(Question question);


    List<Question> searchQuestions(@Param("userId") int userId,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

}
