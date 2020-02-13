package com.example.a10_02_20;

import android.os.Handler;

public class ValidationWorker implements Runnable {
    public static final int VALIDATION_START = 0x01;
    public static final int VALIDATION_SUCCESS = 0x02;
    public static final int VALIDATION_FAILURE = 0x03;
    Handler handler;
    StoreProvider provider;

    public ValidationWorker(Handler handler,StoreProvider provider) {
        this.handler = handler;
        this.provider = provider;
    }

    @Override
    public void run() {
        handler.sendEmptyMessage(VALIDATION_START);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(provider.getToken() != null){
            handler.sendEmptyMessage(VALIDATION_SUCCESS);
        }else{
            handler.sendEmptyMessage(VALIDATION_FAILURE);
        }
    }
}
