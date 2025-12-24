package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.springframework.web.multipart.MultipartFile;  // 使用 Spring Boot 自带的 MultipartFile
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dusuanyun
 */
public class BinaryUploader {

    public static final State save(HttpServletRequest request, Map<String, Object> conf) {
        MultipartFile file = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        try {
            file = getMultipartFile(request);
            if (file == null) {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }

            String savePath = (String) conf.get("savePath");
            String originFileName = file.getOriginalFilename();
            String suffix = FileType.getSuffixByFilename(originFileName);

            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = ((Long) conf.get("maxSize")).longValue();

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

            String physicalPath = (String) conf.get("rootPath") + savePath;

            // 调用实际的保存文件方法
            State storageState = saveFile(file, physicalPath, maxSize);

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        } catch (IOException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        }
    }

    private static boolean isMultipartContent(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/");
    }

    private static MultipartFile getMultipartFile(HttpServletRequest request) throws IOException {
        MultipartFile file = null;
        for (MultipartFile f : ((org.springframework.web.multipart.MultipartHttpServletRequest) request).getFileMap().values()) {
            if (f != null && !f.isEmpty()) {
                file = f;
                break;
            }
        }
        return file;
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }

    /**
     * 实际保存文件的方法
     * @param file 文件
     * @param physicalPath 文件保存路径
     * @param maxSize 文件最大大小
     * @return 状态
     * @throws IOException 异常
     */
    private static State saveFile(MultipartFile file, String physicalPath, long maxSize) throws IOException {
        // 判断文件大小
        if (file.getSize() > maxSize) {
            return new BaseState(false, AppInfo.MAX_SIZE_EXCEEDED);
        }

        // 创建目录结构
        File destFile = new File(physicalPath);
        File parentDir = destFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        // 保存文件
        file.transferTo(destFile);

        // 如果文件保存成功，返回成功状态
        return new BaseState(true, AppInfo.SUCCESS);
    }

    private BinaryUploader() {
    }
}
