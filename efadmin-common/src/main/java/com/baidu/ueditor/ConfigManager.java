package com.baidu.ueditor;

import  com.baidu.ueditor.define.ActionMap;
import io.netty.util.internal.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理器
 *
 * @author hancong03@baidu.com
 */
@Slf4j
public final class ConfigManager {

    private final String rootPath;
    private final String originalPath;
    private final String contextPath;
    private static final String configFileName = "config.json";
    private static final String MAX_SIZE = "maxSize";
    private static final String ALLOW_FILES = "allowFiles";
    private static final String FIELD_NAME = "fieldName";
    private String parentPath = null;
    private JSONObject jsonConfig = null;
    // 涂鸦上传filename定义
    private final static String SCRAWL_FILE_NAME = "scrawl";
    // 远程图片抓取filename定义
    private final static String REMOTE_FILE_NAME = "remote";

    /*
     * 通过一个给定的路径构建一个配置管理器， 该管理器要求地址路径所在目录下必须存在config.properties文件
     */
    private ConfigManager(String rootPath, String contextPath, String uri) throws FileNotFoundException, IOException {

        rootPath = rootPath.replace("\\", "/");

        this.rootPath = rootPath;
        this.contextPath = contextPath;

        if (contextPath.length() > 0) {
            this.originalPath = this.rootPath + uri.substring(contextPath.length());
        } else {
            this.originalPath = this.rootPath + uri;
        }

        this.initEnv();

    }

    /**
     * 配置管理器构造工厂
     *
     * @param rootPath    服务器根路径
     * @param contextPath 服务器所在项目路径
     * @param uri         当前访问的uri
     * @return 配置管理器实例或者null
     */
    public static ConfigManager getInstance(String rootPath, String contextPath, String uri) {

        try {
            return new ConfigManager(rootPath, contextPath, uri);
        } catch (Exception e) {
            log.warn("百度富文本编辑器的配置管理器，实例化失败："+ ThrowableUtil.stackTraceToString(e));
            return null;
        }

    }

    // 验证配置文件加载是否正确
    public boolean valid() {
        return this.jsonConfig != null;
    }

    public JSONObject getAllConfig() {
        return this.jsonConfig;
    }

    public Map<String, Object> getConfig(int type) {

        Map<String, Object> conf = new HashMap<>();
        String savePath = null;

        switch (type) {
            case ActionMap.UPLOAD_FILE:
                conf.put("isBase64", "false");
                conf.put(MAX_SIZE, this.jsonConfig.getLong("fileMaxSize"));
                conf.put(ALLOW_FILES, this.getArray("fileAllowFiles"));
                conf.put(FIELD_NAME, this.jsonConfig.getString("fileFieldName"));
                savePath = this.jsonConfig.getString("filePathFormat");
                break;

            case ActionMap.UPLOAD_IMAGE:
                conf.put("isBase64", "false");
                conf.put(MAX_SIZE, this.jsonConfig.getLong("imageMaxSize"));
                conf.put(ALLOW_FILES, this.getArray("imageAllowFiles"));
                conf.put(FIELD_NAME, this.jsonConfig.getString("imageFieldName"));
                savePath = this.jsonConfig.getString("imagePathFormat");
                break;

            case ActionMap.UPLOAD_VIDEO:
                conf.put(MAX_SIZE, this.jsonConfig.getLong("videoMaxSize"));
                conf.put(ALLOW_FILES, this.getArray("videoAllowFiles"));
                conf.put(FIELD_NAME, this.jsonConfig.getString("videoFieldName"));
                savePath = this.jsonConfig.getString("videoPathFormat");
                break;

            case ActionMap.UPLOAD_SCRAWL:
                conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
                conf.put(MAX_SIZE, this.jsonConfig.getLong("scrawlMaxSize"));
                conf.put(FIELD_NAME, this.jsonConfig.getString("scrawlFieldName"));
                conf.put("isBase64", "true");
                savePath = this.jsonConfig.getString("scrawlPathFormat");
                break;

            case ActionMap.CATCH_IMAGE:
                conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
                conf.put("filter", this.getArray("catcherLocalDomain"));
                conf.put(MAX_SIZE, this.jsonConfig.getLong("catcherMaxSize"));
                conf.put(ALLOW_FILES, this.getArray("catcherAllowFiles"));
                conf.put(FIELD_NAME, this.jsonConfig.getString("catcherFieldName") + "[]");
                savePath = this.jsonConfig.getString("catcherPathFormat");
                break;

            case ActionMap.LIST_IMAGE:
                conf.put(ALLOW_FILES, this.getArray("imageManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
                break;

            case ActionMap.LIST_FILE:
                conf.put(ALLOW_FILES, this.getArray("fileManagerAllowFiles"));
                conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
                conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
                break;
            default:

        }

        conf.put("savePath", savePath);
        conf.put("rootPath", this.rootPath);
        return conf;
    }

    private void initEnv() throws IOException {

        File file = new File(this.originalPath);

        if (!file.isAbsolute()) {
            file = new File(file.getAbsolutePath());
        }

        this.parentPath = file.getParent();

//        String configContent = this.readFile(this.getConfigPath());
//        String configJsonPath = null;
//        try {
//            configJsonPath = this.getClass().getClassLoader().getResource("config.json").toURI().getPath();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        String configContent = this.readFile(configJsonPath);
        // 上面注释的代码在IDE的开发环境运行正常，但是在服务器上用jar运行则找不到配置文件!
        Resource resource = new ClassPathResource("config.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuffer message = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            message.append(line);
        }
        String configContent = message.toString().replaceAll("/\\*(.|[\\r\\n])*?\\*/", "");
        try {
            JSONObject jsonConfig = new JSONObject(configContent);
            this.jsonConfig = jsonConfig;
        } catch (Exception e) {
            log.warn("configContent:" + configContent);
            log.warn("百度富文本编辑器,初始化配置环境失败:" + ThrowableUtil.stackTraceToString(e));
            this.jsonConfig = null;
        }

    }

    private String getConfigPath() {
        return this.parentPath + File.separator + ConfigManager.configFileName;
    }

    private String[] getArray(String key) {

        JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
        String[] result = new String[jsonArray.length()];

        for (int i = 0, len = jsonArray.length(); i < len; i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;
    }

    private String readFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
            try (BufferedReader bfReader = new BufferedReader(reader)) {
                String tmpContent = null;
                while ((tmpContent = bfReader.readLine()) != null) {
                    builder.append(tmpContent);
                }
            }

        } catch (UnsupportedEncodingException e) {
            log.warn("百度富文本编辑器,读取文件失败:" + ThrowableUtil.stackTraceToString(e));
        }

        return this.filter(builder.toString());
    }

    // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
    private String filter(String input) {
        return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
    }

}
