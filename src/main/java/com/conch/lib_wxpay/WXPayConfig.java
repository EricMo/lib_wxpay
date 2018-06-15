package com.conch.lib_wxpay;

import com.tencent.mm.opensdk.modelpay.PayReq;

import java.util.Map;

/**
 * @Description 调用支付的必要条件
 * @Author Benjamin
 * @CreateDate 2018-06-15 10:11
 **/
public class WXPayConfig {
    private WXPayCallBack callBack = null;
    private PayReq req = null;
    //自动关闭微信结果
    private Boolean isAutoFinish = false;
    private WXPayConfig(Builder builder) {
        this.req = builder.req;
        this.isAutoFinish = builder.isAutoFinish;
        this.callBack = builder.callBack;
    }

    public PayReq getReq() {
        return req;
    }

    public WXPayCallBack getCallBack() {
        return callBack;
    }

    public Boolean getAutoFinish() {
        return isAutoFinish;
    }

    public static final class Builder {
        private WXPayCallBack callBack;
        private PayReq req;
        private Boolean isAutoFinish = false;
        //设置支付参数
        public Builder setPayInfo(Map payInfo) {
            req = new PayReq();
            req.appId = payInfo.get("appid").toString();
            req.partnerId = payInfo.get("partnerid").toString();
            req.prepayId = payInfo.get("prepayid").toString();
            req.nonceStr = payInfo.get("noncestr").toString();
            req.timeStamp = payInfo.get("timestamp").toString();
            req.packageValue = payInfo.get("package").toString();
            req.sign = payInfo.get("sign").toString();
            return this;
        }
        //是否自动关闭结果界面
        public Builder setIsAutoFinish(Boolean isAutoFinish) {
            this.isAutoFinish = isAutoFinish;
            return this;
        }

        //设置回调
        public Builder setCallBack(WXPayCallBack callBack) {
            this.callBack = callBack;
            return this;
        }
        public WXPayConfig Build() {
            return new WXPayConfig(this);
        }
    }
}
