package vip.efactory.utils;

import vip.efactory.common.i18n.enums.Entityi18nUtil;

import vip.efactory.domain.*;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.modules.mnt.domain.*;
import vip.efactory.modules.monitor.domain.Server;
import vip.efactory.modules.monitor.domain.Visits;
import vip.efactory.modules.quartz.domain.QuartzJob;
import vip.efactory.modules.quartz.domain.QuartzLog;
import vip.efactory.modules.system.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成项目中需要的实体国际化的文件！
 */
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
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
        // 定时任务
        classes.add(QuartzJob.class);
        classes.add(QuartzLog.class);
        // 监控部分
        classes.add(Server.class);
        classes.add(Visits.class);
        // 部署管理部分
        classes.add(App.class);
        classes.add(Database.class);
        classes.add(Deploy.class);
        classes.add(DeployHistory.class);
        classes.add(ServerDeploy.class);

        Entityi18nUtil.copyToLocale(Entityi18nUtil.geni18nPropertiesFile("messages", classes));
    }


    public static void main(String[] args) {
        geni18nFiles();
    }

}
