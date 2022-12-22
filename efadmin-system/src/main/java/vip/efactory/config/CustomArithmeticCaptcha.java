package vip.efactory.config;

import com.wf.captcha.ArithmeticCaptcha;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import java.awt.*;

/**
 * @description: 自定义图验证码
 *               重写部分方法以便适配jdk17运行
 */
public class CustomArithmeticCaptcha extends ArithmeticCaptcha {
    public CustomArithmeticCaptcha() {
        super();
    }

    public CustomArithmeticCaptcha(int width, int height) {
        super(width, height);
    }

    public CustomArithmeticCaptcha(int width, int height, int len) {
        super(width, height, len);
    }

    public CustomArithmeticCaptcha(int width, int height, int len, Font font) {
        super(width, height, len, font);
    }

    @Override
    protected char[] alphas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(num(10));
            if (i < len - 1) {
                int type = num(1, 4);
                if (type == 1) {
                    sb.append("+");
                } else if (type == 2) {
                    sb.append("-");
                } else if (type == 3) {
                    sb.append("x");
                }
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        // 添加这一行 该行要把外部js引擎注入js引擎管理里；其他不用调整，维持即可
        manager.registerEngineName("customScriptEngineFactory", new NashornScriptEngineFactory());
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            chars = String.valueOf(engine.eval(sb.toString().replaceAll("x", "*")));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        sb.append("=?");
        setArithmeticString(sb.toString());
        return chars.toCharArray();
    }
}
