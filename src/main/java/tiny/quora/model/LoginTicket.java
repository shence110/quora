package tiny.quora.model;

public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private String expireTime;
    private int status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpired() {
        return expireTime;
    }

    public void setExpired(String expired) {
        this.expireTime = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
