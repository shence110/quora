package tiny.quora.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tiny.quora.model.LoginTicket;

@Mapper
public interface TicketDao {

    int addTicket(@Param("loginTicket") LoginTicket loginTicket);

    void setExpired(@Param("ticket") String ticket);

    LoginTicket selectByTicket(@Param("ticket") String ticket);

}
