package vip.efactory.modules;

import cn.hutool.core.lang.Dict;
import vip.efactory.common.i18n.enums.Entityi18nUtil;

import vip.efactory.domain.*;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.modules.system.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成项目中需要的实体国际化的文件！
 */
public class GenPropertiesUtil {

    public static void geni18nFiles() {
        // 生成实体的国际化资源文件模板
        List<Class> classes = new ArrayList<>();
        classes.add(BaseEntity.class);
        // 代码生成模块
        classes.add(ColumnInfo.class);
        classes.add(GenConfig.class);
        // 日志模块的实体
        classes.add(SysLog.class);
        // 工具模块实体
        classes.add(AlipayConfig.class);
        classes.add(EmailConfig.class);
        classes.add(LocalStorage.class);
        classes.add(Picture.class);
        classes.add(QiniuConfig.class);
        classes.add(QiniuContent.class);
        classes.add(VerificationCode.class);
        // 系统管理模块
        classes.add(Dept.class);
        classes.add(Dict.class);
        classes.add(DictDetail.class);
        classes.add(Employee.class);
        classes.add(Job.class);
        classes.add(Menu.class);
        classes.add(Role.class);
        classes.add(User.class);
        classes.add(UserAvatar.class);

        Entityi18nUtil.copyToLocale(Entityi18nUtil.geni18nPropertiesFile("messages", classes));
    }


    public static void main(String[] args) {
        geni18nFiles();
    }

}
