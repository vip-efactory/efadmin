package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import vip.efactory.annotation.AnonymousAccess;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.i18n.enums.CommHttpStatusEnum;
import vip.efactory.domain.AlipayConfig;
import vip.efactory.domain.vo.TradeVo;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.service.AliPayService;
import vip.efactory.utils.AliPayStatusEnum;
import vip.efactory.utils.AlipayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/aliPay")
@Api(tags = "工具：支付宝管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
@Slf4j
public class AliPayController extends BaseController<AlipayConfig, AliPayService, Long> {
    private final AlipayUtils alipayUtils;

    @GetMapping
    public R get() {
        return R.ok(entityService.find());
    }

    @Log("配置支付宝")
    @ApiOperation("配置支付宝")
    @PutMapping
    public R payConfig(@Validated @RequestBody AlipayConfig alipayConfig) {
        alipayConfig.setId(1L);
        entityService.update(alipayConfig);
        return R.ok();
    }

    @Log("支付宝PC网页支付")
    @ApiOperation("PC网页支付")
    @PostMapping(value = "/toPayAsPC")
    public R toPayAsPc(@Validated @RequestBody TradeVo trade) throws Exception {
        AlipayConfig aliPay = entityService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = entityService.toPayAsPc(aliPay, trade);
        return R.ok(payUrl);
    }

    @Log("支付宝手机网页支付")
    @ApiOperation(value = "手机网页支付")
    @PostMapping(value = "/toPayAsWeb")
    public R toPayAsWeb(@Validated @RequestBody TradeVo trade) throws Exception {
        AlipayConfig alipay = entityService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = entityService.toPayAsWeb(alipay, trade);
        return R.ok(payUrl);
    }

    /**
    * Description:
    * @param request http请求
    * @param response http响应
    * @return {@link R}
    */
    @ApiIgnore
    @GetMapping("/return")
    @AnonymousAccess
    @ApiOperation("支付之后跳转的链接")
    public R returnPage(HttpServletRequest request, HttpServletResponse response) {
        AlipayConfig alipay = entityService.find();
        response.setContentType("text/html;charset=" + alipay.getCharset());
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request, alipay)) {
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            log.info("商户订单号" + outTradeNo + "  " + "第三方交易号" + tradeNo);

            // 根据业务需要返回数据，这里统一返回OK
            return R.ok("payment successful");
        } else {
            // 根据业务需要返回数据
            return R.error(CommHttpStatusEnum.BAD_REQUEST);
        }
    }

    @ApiIgnore
    @RequestMapping("/notify")
    @AnonymousAccess
    @SuppressWarnings("all")
    @ApiOperation("支付异步通知(要公网访问)，接收异步通知，检查通知内容app_id、out_trade_no、total_amount是否与请求中的一致，根据trade_status进行后续业务处理")
    public R notify(HttpServletRequest request) {
        AlipayConfig alipay = entityService.find();
        Map<String, String[]> parameterMap = request.getParameterMap();
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request, alipay)) {
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //验证
            if (tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue()) || tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())) {
                // 验证通过后应该根据业务需要处理订单
            }
            return R.ok();
        }
        return R.error(CommHttpStatusEnum.BAD_REQUEST);
    }
}
