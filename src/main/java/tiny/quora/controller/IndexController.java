package tiny.quora.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tiny.quora.model.Question;
import tiny.quora.model.ViewObject;
import tiny.quora.service.QuestionService;
import tiny.quora.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    public String getIndexPage(Model model) {
        model.addAttribute("vos", getQuestionView(0, 0, 10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String getUserIndexPage(@PathVariable("userId") int userId,
                                   Model model) {
        model.addAttribute("vos", getQuestionView(userId, 0, 10));
        return "index";
    }


    //获取问题和用户信息
    private List<ViewObject> getQuestionView(int userId, int offset, int limit) {
        List<ViewObject> vos = new ArrayList<>();

        List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        for (Question question: questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
