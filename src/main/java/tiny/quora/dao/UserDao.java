package tiny.quora.dao;

import org.apache.ibatis.annotations.*;
import tiny.quora.model.User;

@Mapper
public interface UserDao {

    int addUser(@Param("user") User user);

    void updateById(@Param("user") User user);
    void updateByName(@Param("user") User user);

    User searchById(@Param("id") int id);
    User searchByName(@Param("name") String name);

    User existById(@Param("id") int id);
    User existByName(@Param("name") String name);
}
