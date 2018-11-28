package tiny.quora.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tiny.quora.model.Account;

import java.util.List;

@Mapper
public interface AccountDao {

    int addAccount(@Param("account") Account account);

    void updateById(@Param("account") Account account);
    void updateByAccountVal(@Param("account") Account account);

    List<Account> searchByUserId(@Param("userId") int userId);
    Account searchByAccountVal(@Param("accountVal") String accountVal);

    Account existByAccountVal(@Param("accountVal") String accountVal);
}
