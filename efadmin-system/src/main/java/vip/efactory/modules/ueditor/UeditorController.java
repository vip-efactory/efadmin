package vip.efactory.modules.ueditor;

import cn.hutool.core.util.ObjectUtil;
import com.baidu.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.annotation.AnonymousAccess;
import vip.efactory.common.base.utils.DateTimeUtil;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;
import vip.efactory.exception.BadRequestException;
import vip.efactory.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UeditorController {

    @Value("${file.ueditor}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;
    @Value("${file.host}")
    private String host;

    private List<String> uploadTypes = new ArrayList<>();

    // 初始化uploadTypes
    {
        uploadTypes.add("uploadimage"); // 支持图片上传
        uploadTypes.add("uploadvideo"); // 支持视频上传
        uploadTypes.add("uploadfile"); // 文件上传--附件上传
    }

    /**
     * 获取配置的请求是允许匿名的！
     *
     * @param request
     * @param response
     * @param upfile
     */
    @AnonymousAccess
    @RequestMapping(value = "/editor/config", params = {"action=config", "callback"}, method = RequestMethod.GET)
    public void ueditorConfigOnly(HttpServletRequest request, HttpServletResponse response, MultipartFile upfile) {
        this.ueditorConfig(request, response, upfile);
    }

    @RequestMapping(value = "/editor/config",method = RequestMethod.POST)
    @PreAuthorize("@p.check()")  // 检查token
    public void ueditorConfig(HttpServletRequest request, HttpServletResponse response, MultipartFile upfile) {

        response.setContentType("application/x-javascript");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = "";
            String actionType = request.getParameter("action");
            if (uploadTypes.contains(actionType) && !upfile.isEmpty()) {
                // 做上传操作
                exec = uploadImage(upfile);
            } else {
                request.setCharacterEncoding("utf-8");
                exec = new ActionEnter(request, rootPath).exec();
            }
            response.setCharacterEncoding("utf-8"); // 处理写出中文乱码的问题
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("UeditorController#ueditorConfig exception:", e);
        }
    }

    private String uploadImage(MultipartFile file) {
        JSONObject jsonResult = null;
        try {
            String fileName = file.getOriginalFilename();
            String extraName = fileName.substring(fileName.lastIndexOf("."));
            // 保存文件到服务器的制定目录，并返回访问的url
            String url = upload(file);
            jsonResult = new JSONObject(resultMap("SUCCESS", url, file.getSize(), fileName, fileName, extraName));

        } catch (Exception e) {
            log.warn("UeditorController#uploadImage exception:", e);
            jsonResult = new JSONObject(resultMap("文件上传失败", "", 0, "", "", ""));
        }
        return jsonResult.toString();
    }

    //{"state": "SUCCESS","original": "111.jpg","size": "124147","title": "1535961757878095151.jpg","type": ".jpg","url": "/1535961757878095151.jpg"}

    private Map<String, Object> resultMap(String state, String url, long size, String title, String original, String type) {
        Map<String, Object> result = new HashMap<>();
        result.put("state", state);
        result.put("original", original);
        result.put("size", size);
        result.put("title", title);
        result.put("type", type);
        result.put("url", url);
        return result;
    }

    // 保存文件到服务器的制定目录，并返回访问的url
    public String upload(MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        String today = DateTimeUtil.getTodayShort();
        // 文件最终存储目录地址
        String finalPath = path + TenantHolder.getTenantId() + File.separator;  // 加上租户信息，以便数据隔离
        File file = FileUtil.upload(multipartFile, finalPath + type + File.separator + today + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
            // 最终访问的url
            StringBuffer sb = new StringBuffer(host).append(File.separator).append("file").append(File.separator)
                    .append(TenantHolder.getTenantId()).append(File.separator).append(type).append(File.separator)
                    .append(today).append(File.separator).append(file.getName());
            return sb.toString();
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
    }

}
