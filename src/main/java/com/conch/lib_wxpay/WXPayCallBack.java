package com.conch.lib_wxpay;

/**
 * @Description 微信支付回调
 * @Author Benjamin
 * @CreateDate 2018-06-14 17:45
 **/
public interface WXPayCallBack {
    //支付成功
    void paySuccess();

    //支付失败
    void payFail(int errorCode, String errorMsg);
}
