package com.baidu.ueditor.define;

import com.baidu.ueditor.Encoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author dusuanyun
 */
public class BaseState implements State {

    private boolean state = false;
    private String info = null;

    private final Map<String, String> infoMap;

    public BaseState() {
        this.state = true;
        infoMap = new HashMap<>();
    }

    public BaseState(boolean state) {
        this.setState(state);
        infoMap = new HashMap<>();
    }

    public BaseState(boolean state, String info) {
        this(state);
        this.info = info;
    }

    public BaseState(boolean state, int infoCode) {
        this(state);
        this.info = AppInfo.getStateInfo(infoCode);
    }

    @Override
    public boolean isSuccess() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfo(int infoCode) {
        this.info = AppInfo.getStateInfo(infoCode);
    }

    @Override
    public String toJSONString() {
        return this.toString();
    }

    public String toString() {

        String key = null;
        String stateVal = this.isSuccess() ? AppInfo.getStateInfo(AppInfo.SUCCESS) : this.info;

        StringBuilder builder = new StringBuilder();
        builder.append("{\"state\": \"").append(stateVal).append("\"");
        Iterator<String> iterator = this.infoMap.keySet().iterator();

        while (iterator.hasNext()) {
            key = iterator.next();
            builder.append(",\"" + key + "\": \"" + this.infoMap.get(key) + "\"");
        }
        builder.append("}");

        return Encoder.toUnicode(builder.toString());

    }

    @Override
    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }

    @Override
    public void putInfo(String name, long val) {
        this.putInfo(name, val + "");
    }

}
