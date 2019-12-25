package vip.efactory.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.entity.Picture;
import vip.efactory.exception.BadRequestException;
import vip.efactory.repository.PictureRepository;
import vip.efactory.service.PictureService;
import vip.efactory.service.dto.PictureQueryCriteria;
import vip.efactory.utils.*;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service(value = "pictureService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PictureServiceImpl extends BaseServiceImpl<Picture, Long, PictureRepository> implements PictureService {

    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Override
    public Object queryAll(PictureQueryCriteria criteria, Pageable pageable) {
        return PageUtil.toPage(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username) {
        File file = FileUtil.toFile(multipartFile);

        HashMap<String, Object> paramMap = new HashMap<>(1);

        paramMap.put("smfile", file);
        String result = HttpUtil.post(EfAdminConstant.Url.SM_MS_URL, paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Picture picture = null;
        if (!jsonObject.get(CODE).toString().equals(SUCCESS)) {
            throw new BadRequestException(jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUsername(username);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) + "." + FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        br.save(picture);
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;

    }

    @Override
    public Optional<Picture> findById(Long id) {
        Optional<Picture> picture = br.findById(id);
        ValidationUtil.isNull(picture, "Picture", "id", id);
        return picture;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Picture picture) {
        try {
            String result = HttpUtil.get(picture.getDelete());
            br.delete(picture);
        } catch (Exception e) {
            br.delete(picture);
        }

    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            delete(findById(id).get());
        }
    }
}
