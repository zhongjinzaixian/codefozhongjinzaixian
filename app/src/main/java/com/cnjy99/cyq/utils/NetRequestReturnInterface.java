package com.cnjy99.cyq.utils;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public interface NetRequestReturnInterface {
    public void onResultSuccess(String string, int i);
    public void onResultFail(String string, int i);
    public void onNetResultCancelled();
}
