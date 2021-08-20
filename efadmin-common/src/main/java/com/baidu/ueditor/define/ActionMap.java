package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义请求action类型
 *
 * @author hancong03@baidu.com
 */
@SuppressWarnings("serial")
public final class ActionMap {

    public static final Map<String, Integer> mapping = new HashMap<>();
    // 获取配置请求
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    static {
        mapping.put("config", ActionMap.CONFIG);
        mapping.put("uploadimage", ActionMap.UPLOAD_IMAGE);
        mapping.put("uploadscrawl", ActionMap.UPLOAD_SCRAWL);
        mapping.put("uploadvideo", ActionMap.UPLOAD_VIDEO);
        mapping.put("uploadfile", ActionMap.UPLOAD_FILE);
        mapping.put("catchimage", ActionMap.CATCH_IMAGE);
        mapping.put("listfile", ActionMap.LIST_FILE);
        mapping.put("listimage", ActionMap.LIST_IMAGE);
    }

    public static int getType(String key) {
        return ActionMap.mapping.get(key);
    }

    private ActionMap() {

    }
}
