package vip.efactory.utils;

import lombok.Getter;

/**
 * 支付状态
 *
 * @author dusuanyun
 */
public enum AliPayStatusEnum {

    /**
     * 交易成功
     */
    FINISHED("交易成功", "TRADE_FINISHED"),

    /**
     * 支付成功
     */
    SUCCESS("支付成功", "TRADE_SUCCESS"),

    /**
     * 交易创建
     */
    BUYER_PAY("交易创建", "WAIT_BUYER_PAY"),

    /**
     * 交易关闭
     */
    CLOSED("交易关闭", "TRADE_CLOSED");

    @Getter
    private final String name;
    @Getter
    private final String value;

    AliPayStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
