package vip.efactory.utils;

/**
 * 这个工具类用来生成服务器qq邮箱的SMTP授权码密文，此处生成的是我个人测试时所使用的授权码密文不是真正的服务器授权码密文，真正的密文应该生成后填入数据库中对应的服务器邮箱配置处。
 */

public class EncryptTest {
    public static void main(String[] args) throws Exception {
        // 把你的QQ授权码明文填在这里
        String qqAuthCode = "cjyahnlcibgjchba";
        // 用项目自带工具加密
        String encryptedPass = EncryptUtils.desEncrypt(qqAuthCode);
        // 复制控制台输出的加密密文
        System.out.println("加密后的pass值：" + encryptedPass);
    }
}