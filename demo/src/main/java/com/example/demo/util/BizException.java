package com.example.demo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizException extends RuntimeException {
    private final List<Map<Integer, String>> errorMessage = new ArrayList();

    public BizException(String message) {
        super(message);
        Map map = new HashMap();
        map.put("message", message);
        this.errorMessage.add(map);
    }

    public BizException(Integer errorCode, String message) {
        super(message);
        Map map = new HashMap();
        map.put("errorCode", errorCode);
        map.put("message", message);
        this.errorMessage.add(map);
    }

    public BizException(List<String> messages) {
        messages.forEach((p) -> {
            Map map = new HashMap();
            map.put("message", p);
            this.errorMessage.add(map);
        });
    }

    public List<Map<Integer, String>> getMessagesInfo() {
        return this.errorMessage;
    }

    public List<String> getMessages() {
        List<String> res = new ArrayList();
        this.errorMessage.forEach((p) -> {
            res.add(p.get("message"));
        });
        return res;
    }

    public String toString() {
        List<String> res = new ArrayList();
        this.errorMessage.forEach((p) -> {
            res.add((String)p.get("message") + "\n");
        });
        return res.toString();
    }
}
