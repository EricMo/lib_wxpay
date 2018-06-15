package com.conch.lib_wxpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @Description 微信支付支付逻辑
 * @Author Benjamin
 * @CreateDate 2018-06-14 17:36
 **/
public class WXPayApi {

    private static WXPayApi payApi;

    private IWXAPI api;

    private WXPayConfig config;

    private WXPayHandler handler;

    private WXPayApi(Context mContext) {
        api = WXAPIFactory.createWXAPI(mContext, WXClient.getWxAppId(), true);
        api.registerApp(WXClient.getWxAppId());
        handler = new WXPayHandler();
    }


    public static WXPayApi getInstance(Context mContext) {
        if (payApi == null) {
            payApi = new WXPayApi(mContext);
        }
        return payApi;
    }

    //处理回调
    public void handleIntent(Activity activity, Intent intent) {
        handler.setActivity(activity);
        api.handleIntent(intent, handler);
    }

    //发起支付
    public void doWXPay(WXPayConfig config) {
        this.config = config;
        if (!api.isWXAppSupportAPI() || !api.isWXAppInstalled()) {
            config.getCallBack().payFail(3, "Not Installed or Not Support");
        }
        PayReq payReq = config.getReq();
        //
        if (payReq.checkArgs()) {
            //调起支付
            api.sendReq(config.getReq());
        } else {
            config.getCallBack().payFail(4, "Pay args not legal");
        }
    }

    //微信支付回调
    private class WXPayHandler implements IWXAPIEventHandler {

        private Activity activity;

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onReq(BaseReq baseReq) {

        }

        @Override
        public void onResp(BaseResp baseResp) {
            switch (baseResp.errCode) {
                case 0:
                    config.getCallBack().paySuccess();
                    break;
                default:
                    config.getCallBack().payFail(baseResp.errCode, baseResp.errStr);
                    break;
            }
            //销毁
            if (activity != null && config.getAutoFinish()) {
                activity.finish();
            }
            activity = null;
        }
    }
}
