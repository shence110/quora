package tiny.quora;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tiny.quora.dao.AccountDao;
import tiny.quora.dao.QuestionDao;
import tiny.quora.dao.UserDao;
import tiny.quora.model.Account;
import tiny.quora.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTests {

    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;

    private Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    //同时添加账号和用户信息，
    // 账号和信息必须一同添加，应为一个session
    private void addAccount() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName(String.format("name%d", i));
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            userDao.addUser(user);
            logger.info(String.format("lalalalla%d", user.getId()));

            Account account = new Account();
            account.setUserId(user.getId());
            account.setAccountVal(String.format("zhanghao%d", i));
            account.setPassword(String.format("mima%d", i));
            account.setSalt("salt");
            account.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountDao.addAccount(account);

            Account account2 = new Account();
            account2.setUserId(user.getId());
            account2.setAccountVal(String.format("zhanghao%d-2", i));
            account2.setAccountType(1);
            account2.setPassword(String.format("mima%d", i));
            account2.setSalt("salt");
            account2.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountDao.addAccount(account2);
        }
    }

    //更新账号
    private void updateAccountById() {
        for (int i = 0; i < 10; i++) {
            Account account = new Account();
            account.setId(153+i);
            account.setPassword(String.format("mima--%d", i));
            account.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountDao.updateById(account);
        }
    }
    private void updateAccountByAccount() {
        for (int i = 0; i < 10; i++) {
            Account account = new Account();
            account.setAccountVal(String.format("zhanghao%d", i));
            account.setSalt("salt++");
            account.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            accountDao.updateByAccountVal(account);
        }
    }

    //查询账号
    private void searchAccountByUserId() {
        for (int i = 0; i < 3; i++) {
            List<Account> accountList = accountDao.searchByUserId(63+i);
            for (Account account: accountList) {
                if (account != null) {
                    logger.info("lala1 " + account.getAccountVal()
                            + "  " + account.getAccountType()
                            + "  " +account.getPassword()
                            + "  " +account.getSalt());
                } else {
                    logger.info("lalala");
                }
            }
        }
    }
    private void searchAccountByAccount() {
        for (int i = 0; i < 5; i++) {
            Account account = accountDao.searchByAccountVal(String.format("zhanghao%d", i));
            if (account != null) {
                logger.info("lala2 " + account.getUserId()
                        + "  " + account.getAccountType()
                        + "  " +account.getPassword()
                        + "  " +account.getSalt());
            } else {
                logger.info("lalala");
            }
        }
    }

    //查询账号是否存在
    private void existAccountByAccount() {
        for (int i = 0; i < 5; i++) {
            Account account = accountDao.existByAccountVal(String.format("zhanghao%d", i));
            if (account != null) {
                logger.info("lala2 yes" + account.getId());
            } else {
                logger.info("lalala");
            }
        }
    }


    //更新用户信息
    private void updateUserById() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(63+i);
            user.setSex(1);
            user.setQuestionCnt(i);
            user.setCommentCnt(i);
            user.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            userDao.updateById(user);
        }
    }
    private void updateUserByName() {
        for (int i = 5; i < 10; i++) {
            User user = new User();
            user.setName(String.format("name%d", i));
            user.setQuestionCnt(i);
            user.setCommentCnt(i);
            user.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            userDao.updateById(user);
        }
    }

    //查询用户
    private void selectUserById() {
        for (int i = 0; i < 5; i++) {
            User user = userDao.searchById(63+i);
            if (user != null) {
                logger.info("lala1 " + user.getName()
                        + "  " + user.getSex()
                        + "  " +user.getHeadUrl()
                        + "  " +user.getQuestionCnt()
                        + "  " +user.getCommentCnt());
            } else {
                logger.info("lalala");
            }
        }
    }
    private void selectUserByName() {
        for (int i = 0; i < 5; i++) {
            User user = userDao.searchByName(String.format("name%d", i));
            if (user != null) {
                logger.info("lala2 " + user.getId()
                        + "  " + user.getSex()
                        + "  " +user.getHeadUrl()
                        + "  " +user.getQuestionCnt()
                        + "  " +user.getCommentCnt());
            } else {
                logger.info("lalala");
            }
        }
    }

    //查询用户是否存在
    private void existUserById() {
        for (int i = 0; i < 5; i++) {
            User user = userDao.existById(63+i);
            if (user != null) {
                logger.info("lala1 yes" + user.getName());
            } else {
                logger.info("lalala");
            }
        }
    }
    private void existUserByName() {
        for (int i = 0; i < 5; i++) {
            User user = userDao.existByName(String.format("name%d", i));
            if (user != null) {
                logger.info("lala2 yes" + user.getId());
            } else {
                logger.info("lalala");
            }
        }
    }



    @Test
    public void initDatabase() {
        addAccount();
//        updateAccountById();
//        updateAccountByAccount();
//        searchAccountByUserId();
//        searchAccountByAccount();
//        existAccountByAccount();
//        updateUserById();
//        updateUserByName();
//        selectUserById();
//        selectUserByName();
//        existUserById();
//        existUserByName();
    }
}
