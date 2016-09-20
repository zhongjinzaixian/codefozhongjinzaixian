package com.cnjy99.cyq.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 所有SharedPreferences操作的公告抽象类
 * Created by Administrator on 2016/9/20 0020.
 */
public abstract class SpBase {
    protected SharedPreferences sp = null;

    public SpBase(Context context, String spName) {
        // 创建对象 0 有这个文件名 为 spName 的文件 就覆盖 没有就新建文件名为 spName 的文件
        this.sp = context.getSharedPreferences(spName, context.MODE_PRIVATE);
    }

    public boolean clear() {
        return SpUtil.clear(this.sp);
    }

    public boolean contains(String key) {
        return SpUtil.contains(this.sp, key);
    }

    public Boolean getBoolean(String key) {
        return SpUtil.getBoolean(this.sp, key);
    }

    public Boolean getBoolean(String key, boolean defVal) {
        return SpUtil.getBoolean(this.sp, key, defVal);
    }

    public boolean save(String key, Object obj) {
        return SpUtil.save(this.sp, key, obj);
    }

    // TODO. 实现所有的方法调用
}
