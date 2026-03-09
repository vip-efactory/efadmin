package vip.efactory.service.impl;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.domain.AlipayConfig;
import vip.efactory.domain.vo.TradeVo;
import vip.efactory.exception.BadRequestException;
import vip.efactory.repository.AliPayRepository;
import vip.efactory.service.AliPayService;
import vip.efactory.utils.StringUtils;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "alipay")
@SuppressWarnings("all")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AliPayServiceImpl extends BaseServiceImpl<AlipayConfig, Long, AliPayRepository> implements AliPayService {


    @Override
    public String toPayAsPc(AlipayConfig alipay, TradeVo trade) throws Exception {
        if(alipay.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        // 1. 校验金额格式（避免转换失败）
        double totalAmount;
        try {
            totalAmount = Double.parseDouble(trade.getTotalAmount());
        } catch (NumberFormatException e) {
            throw new BadRequestException("支付金额必须为数字（如1.00）");
        }
        // 2. 校验金额范围
        if(totalAmount <= 0 || totalAmount >= 5000){
            throw new BadRequestException("支付金额需大于0且小于5000元");
        }
        // 3. 强制使用沙箱网关（彻底避免网关配置错误）
        alipay.setGatewayUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipay.getGatewayUrl(),
                alipay.getAppId(),
                alipay.getPrivateKey(),
                "JSON", // 固定格式，避免配置错误
                "utf-8", // 固定编码，避免配置错误
                alipay.getPublicKey(),
                "RSA2" // 固定签名方式，避免配置错误
        );

        // 4. 构建请求（修复out_trade_no空格+去掉多余参数）
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        JSONObject bizContent = new JSONObject();
        // 关键：out_trade_no去掉空格
        bizContent.put("out_trade_no", StringUtils.trimToEmpty(trade.getOutTradeNo()));
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        // 关键：金额格式化为两位小数（支付宝强制要求）
        bizContent.put("total_amount", String.format("%.2f", totalAmount));
        // 关键：subject/body去掉空格
        bizContent.put("subject", StringUtils.trimToEmpty(trade.getSubject()));
        bizContent.put("body", StringUtils.trimToEmpty(trade.getBody()));
        // 【已删除】去掉extend_params（自营商户不需要）
        request.setBizContent(bizContent.toString());

        // 5. 生成表单并返回
        return alipayClient.pageExecute(request, "GET").getBody();
    }

    @Override
    public String toPayAsWeb(AlipayConfig alipay, TradeVo trade) throws Exception {
        if(alipay.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        // 1. 校验金额格式+范围
        double totalAmount;
        try {
            totalAmount = Double.parseDouble(trade.getTotalAmount());
        } catch (NumberFormatException e) {
            throw new BadRequestException("支付金额必须为数字（如1.00）");
        }
        if(totalAmount <= 0 || totalAmount >= 5000){
            throw new BadRequestException("支付金额需大于0且小于5000元");
        }
        // 2. 强制使用沙箱网关
        alipay.setGatewayUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipay.getGatewayUrl(),
                alipay.getAppId(),
                alipay.getPrivateKey(),
                "JSON",
                "utf-8",
                alipay.getPublicKey(),
                "RSA2"
        );

        // 3. 构建请求（修复所有参数问题）
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        request.setBizContent("{" +
                "    \"out_trade_no\":\""+StringUtils.trimToEmpty(trade.getOutTradeNo())+"\"," + // 去空格
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\""+String.format("%.2f", totalAmount)+"\"," + // 两位小数
                "    \"subject\":\""+StringUtils.trimToEmpty(trade.getSubject())+"\"," + // 去空格
                "    \"body\":\""+StringUtils.trimToEmpty(trade.getBody())+"\"" + // 去空格
                "  }");
        return alipayClient.pageExecute(request, "GET").getBody();
    }

    @Override
    @Cacheable(key = "'1'")
    public AlipayConfig find() {
        Optional<AlipayConfig> alipayConfig = br.findById(1L);
        return alipayConfig.orElseGet(AlipayConfig::new);
    }

    @Override
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public AlipayConfig update(AlipayConfig alipayConfig) {
        return br.save(alipayConfig);
    }
}