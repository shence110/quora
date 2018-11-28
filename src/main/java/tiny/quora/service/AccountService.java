package tiny.quora.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiny.quora.dao.AccountDao;
import tiny.quora.dao.TicketDao;
import tiny.quora.dao.UserDao;
import tiny.quora.model.Account;
import tiny.quora.model.LoginTicket;
import tiny.quora.model.User;
import tiny.quora.util.Cipher;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AccountService {
    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    TicketDao ticketDao;

    private Random random = new Random();


    //用户注册业务
    public Map<String, String> register(String name, String accountVal, String password, String password2) {
        Map<String, String> msgMap = new HashMap<>();

        if (StringUtils.isBlank(accountVal)) {
            msgMap.put("errMsg", "账号不能为空！");
            return msgMap;
        }
        if (StringUtils.isBlank(password) || StringUtils.isBlank(password2)) {
            msgMap.put("errMsg", "密码/确认密码不能为空！");
            return msgMap;
        }
        if (!StringUtils.equals(password, password2)) {
            msgMap.put("errMsg", "两次输入密码不一致！");
            return msgMap;
        }
        if (userDao.existByName(name) != null) {
            msgMap.put("errMsg", "昵称已注册，请换一个昵称！");
            return msgMap;
        }
        if (accountDao.existByAccountVal(accountVal) != null) {
            msgMap.put("errMsg", "账号已注册，请直接登录！");
            return msgMap;
        }

        User user = new User();
        user.setName(name);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        user.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        userDao.addUser(user);

        Account account = new Account();
        account.setUserId(user.getId());
        account.setAccountVal(accountVal);
        String salt = UUID.randomUUID().toString().substring(0, 10);
        account.setPassword(Cipher.MD5(password+salt));
        account.setSalt(salt);
        account.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        accountDao.addAccount(account);

        String ticket = addTicket(user.getId());
        msgMap.put("ticket", ticket);
        return msgMap;
    }

    //用户登录业务
    public Map<String, String> login(String accountVal, String password) {
        Map<String, String> msgMap = new HashMap<>();

        if (StringUtils.isBlank(accountVal)) {
            msgMap.put("errMsg", "账号不能为空！");
            return msgMap;
        }
        if (StringUtils.isBlank(password)) {
            msgMap.put("errMsg", "密码不能为空！");
            return msgMap;
        }

        Account account = accountDao.searchByAccountVal(accountVal);
        if (account == null) {
            msgMap.put("errMsg", "用户名或密码错误！");
            return msgMap;
        }
        if (!account.getPassword().equals(Cipher.MD5(password + account.getSalt()))) {
            msgMap.put("errMsg", "用户名或密码错误！");
            return msgMap;
        }

        String ticket = addTicket(account.getUserId());
        msgMap.put("ticket", ticket);
        return msgMap;
    }

    //用户登出业务
    public void logout(String ticket) {
        ticketDao.setExpired(ticket);
    }


    //给登录成功的账号注册 ticket
    private String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date d = new Date();
        d.setTime(60480000+d.getTime());//1周
        loginTicket.setExpired(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d));
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        ticketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
