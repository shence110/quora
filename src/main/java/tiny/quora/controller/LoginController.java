package tiny.quora.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tiny.quora.service.AccountService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
public class LoginController {
    @Autowired
    AccountService accountService;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);


    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET})
    public String registerPage(@RequestParam(value = "redirectPage", required = false) String redirectPage,
                               Model model) {
        model.addAttribute("redirectPage", redirectPage);
        return "register";
    }

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.POST})
    public String register(@RequestParam("name") String name,
                           @RequestParam("accountVal") String accountVal,
                           @RequestParam("password") String password,
                           @RequestParam("password2") String password2,
                           @RequestParam("redirectPage") String redirectPage,
                           @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberMe,
                           HttpServletResponse response,
                           Model model) {

        try {
            Map<String, String> msgMap = accountService.register(name, accountVal, password, password2);
            //成功
            if (containsTicket(msgMap, response))
                return StringUtils.isNotBlank(redirectPage) ? "redirect:" + redirectPage : "redirect:/";
            //失败
            if (msgMap.containsKey("errMsg")) {
                model.addAttribute("errMsg", msgMap.get("errMsg"));
                model.addAttribute("redirectPage", redirectPage);
            }
            return "register";
        } catch (Exception e) {
            logger.error("注册异常 " + e.getMessage());
            model.addAttribute("errMsg", "登录失败，请重新登录！");
            model.addAttribute("redirectPage", redirectPage);
            return "register";
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET})
    public String loginPage(@RequestParam(value = "redirectPage", required = false) String redirectPage,
                            Model model) {
        model.addAttribute("redirectPage", redirectPage);
        return "login";
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    public String login(@RequestParam("accountVal") String accountVal,
                        @RequestParam("password") String password,
                        @RequestParam("redirectPage") String redirectPage,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberMe,
                        HttpServletResponse response,
                        Model model) {
        try {
            Map<String, String> msgMap = accountService.login(accountVal, password);
            //成功
            if (containsTicket(msgMap, response))
                return StringUtils.isNotBlank(redirectPage) ? "redirect:" + redirectPage : "redirect:/";
            if (msgMap.containsKey("errMsg")) {
                model.addAttribute("errMsg", msgMap.get("errMsg"));
            }
            return "login";
        } catch (Exception e) {
            logger.error("登录异常 " + e.getMessage());
            return "login";
        }
    }

    //账号退出
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        accountService.logout(ticket);
        return "redirect:/";
    }

    //判断是否注册或登录成功，如果成功在msgMap中会存有"ticket"
    private boolean containsTicket(Map<String, String> msgMap, HttpServletResponse response) {
        if (msgMap.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", msgMap.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            return true;
        }
        return false;
    }
}
