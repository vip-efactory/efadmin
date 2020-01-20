package vip.efactory.utils;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import vip.efactory.common.i18n.enums.ErrorCodeUtil;
import vip.efactory.common.i18n.util.FileUtil;
import vip.efactory.modules.system.domain.Menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 本工具类是为了快速地将项目中的数据库硬编码的菜单快速地转换为国际化的key和value
 */

public class MenuI18nUtil {

    /**
     * 处理所有国际化硬编码的菜单数据
     * @param menus 菜单实体列表
     * @return 处理过的集合
     */
    public static Collection<Menu> handleLocale(Collection<Menu> menus) {
        if (!menus.isEmpty()) {
            for (Menu menu : menus) {
                menu.setName(ErrorCodeUtil.getMessage(createKey(menu)));
            }
        }
        return menus;
    }

    // 获取生成的key,用来国际化查询
    private static String createKey(Menu menu) {
        StringBuilder sb = new StringBuilder(menu.getClass().getSimpleName());
        sb.append(".").append(menu.getType()).append(".").append(menu.getLocaleKey());
        return sb.toString();
    }

    // 对每一个菜单实例,生成:key = value格式的条目!
    private static String installResourceEntry(Menu menu) {
        StringBuilder sb = new StringBuilder(menu.getClass().getSimpleName());
        sb.append(".").append(menu.getType()).append(".").append(menu.getLocaleKey())
                .append("=").append(ErrorCodeUtil.toUnicodeString(menu.getName()));
        return sb.toString();
    }

    /**
     * 利用菜单生成国际化的资源文件,非处理国际化的情况下,请勿使用!
     *
     * @param fileName 仅仅是文件名，不包含路径与后缀
     * @param entities
     */
    @SneakyThrows
    public static String geni18nPropertiesFile(String fileName, List<Menu> entities) {
        //生成的错误码文件的存放位置,直接在项目的指定位置,注意,如果手动修改过此文件,谨慎执行此main方法.
//        Map<String, String> envs = System.getenv();
        String pathanme = System.getenv("PWD") + File.separator + fileName + ".properties";
        //容纳所有的条目
        List<String> lines = new ArrayList<String>();
        //生成的时间写入注释头中
        lines.add("# This file create at " + new Date() + " ,by MenuI18nUtil! \n");
        String lastClass = "";
        String currClass = "";      //当前正在处理的菜单类
        for (Menu menu : entities) {
            currClass = Menu.class.getName();         //通过反射获取到当前菜单的类名
            if (!lastClass.equals(currClass)) {
                if (!lastClass.isEmpty()) {
                    lines.add("\n");                        //添加一个换行
                }
                lines.add("#" + currClass + "\n");          //添加一个当前菜单类的注释
                lastClass = currClass;
            }

            // 仅当有国际化属性时才处理该条记录!
            if (!StringUtils.isEmpty(menu.getLocaleKey())) {
                lines.add(installResourceEntry(menu) + "\n"); //生成菜单对应的键值对
            }
        }
        //将上面的菜单值生成文本文件,以便拷贝到项目里面来用
        FileUtil.writeFileByLines(lines, pathanme);         //将所有的键值对写出!

        System.out.println("文件写出完毕!");
        System.out.println("请到此目录找生成的文件:" + pathanme);
        return pathanme;
    }

}
