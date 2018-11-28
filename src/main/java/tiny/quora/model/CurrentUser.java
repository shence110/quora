package tiny.quora.model;

import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    private ThreadLocal<Integer> user = new ThreadLocal<>();


    public void setUserId(Integer userId) {
        user.set(userId);
    }

    public Integer getUserId() {
        return user.get();
    }

    public void clear() {
        user.remove();
    }

}
