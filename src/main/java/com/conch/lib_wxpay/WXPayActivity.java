package com.conch.lib_wxpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WXPayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXPayApi.getInstance(this).handleIntent(this, getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WXPayApi.getInstance(this).handleIntent(this, intent);
    }
}
