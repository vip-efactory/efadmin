package vip.efactory;

import cn.hutool.core.util.RuntimeUtil;

import javax.sound.midi.SoundbankResource;
import java.io.*;

/**
 * Description:
 *
 * @Author dusuanyun
 * @Date 2021-09-01
 */
public class Demo {

    public static void main(String[] args) throws Exception {
        String str = RuntimeUtil.execForStr("ping 127.0.0.1 -n 4");
        System.out.println(str);
    }
}
