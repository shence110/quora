package tiny.quora.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {

    private Map<String, Object> map = new HashMap<>();

    public Object get(String key) {
        return map.get(key);
    }

    public void set(String key, Object val) {
        this.map.put(key, val);
    }
}
