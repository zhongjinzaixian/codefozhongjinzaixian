package com.cnjy99.cyq.utils;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public interface ResultInterface {
    public void onResultSuccess(String string);
    public void onResultFail(String string);
    public void onResultCancelled();
}
