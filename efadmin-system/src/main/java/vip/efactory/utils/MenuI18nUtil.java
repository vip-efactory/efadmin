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
 * MenuI18nUtil - 低改动安全版（对 ErrorCodeUtil.getMessage 做兜底，避免 NPE）
 */
public class MenuI18nUtil {

    public static Collection<Menu> handleLocale(Collection<Menu> menus) {
        if (menus == null || menus.isEmpty()) {
            return menus;
        }
        for (Menu menu : menus) {
            try {
                String key = createKey(menu);
                String msg = null;
                try {
                    // 尝试调用 ErrorCodeUtil（可能内部依赖的服务为 null）
                    msg = ErrorCodeUtil.getMessage(key);
                } catch (Exception e) {
                    // 记录（可以改为 logger）
                    System.err.println("ErrorCodeUtil.getMessage failed for key=" + key + ", error=" + e.getMessage());
                }
                // 如果没有国际化文本，保持原 name 或使用 key
                if (msg == null || msg.isEmpty()) {
                    // 保持原有名称（更友好），或者使用 key: menu.setName(key);
                    continue;
                }
                menu.setName(msg);
            } catch (Throwable t) {
                // 防止单个 menu 处理失败影响整体
                System.err.println("handleLocale error for menu " + (menu == null ? "null" : menu.getLocaleKey()) + ": " + t.getMessage());
            }
        }
        return menus;
    }

    private static String createKey(Menu menu) {
        StringBuilder sb = new StringBuilder(menu.getClass().getSimpleName());
        sb.append(".").append(menu.getType()).append(".").append(menu.getLocaleKey());
        return sb.toString();
    }

    private static String installResourceEntry(Menu menu) {
        StringBuilder sb = new StringBuilder(menu.getClass().getSimpleName());
        sb.append(".").append(menu.getType()).append(".").append(menu.getLocaleKey())
                .append("=").append(ErrorCodeUtil.toUnicodeString(menu.getName()));
        return sb.toString();
    }

    @SneakyThrows
    public static String geni18nPropertiesFile(String fileName, List<Menu> entities) {
        String pathanme = System.getenv("PWD") + File.separator + fileName + ".properties";
        List<String> lines = new ArrayList<String>();
        lines.add("# This file create at " + new Date() + " ,by MenuI18nUtil! \n");
        String lastClass = "";
        String currClass = "";
        for (Menu menu : entities) {
            currClass = Menu.class.getName();
            if (!lastClass.equals(currClass)) {
                if (!lastClass.isEmpty()) {
                    lines.add("\n");
                }
                lines.add("#" + currClass + "\n");
                lastClass = currClass;
            }

            if (!StringUtils.isEmpty(menu.getLocaleKey())) {
                lines.add(installResourceEntry(menu) + "\n");
            }
        }
        FileUtil.writeFileByLines(lines, pathanme);
        System.out.println("文件写出完毕!");
        System.out.println("请到此目录找生成的文件:" + pathanme);
        return pathanme;
    }
}
