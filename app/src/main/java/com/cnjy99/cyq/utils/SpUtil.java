package com.cnjy99.cyq.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * SharedPreferences工具类
 * Created by Administrator on 2016/9/20 0020.
 */
public class SpUtil {

    private static final String TAG = SpUtil.class.getSimpleName();

    /**
     * 清除方法
     * @param preferences
     * @return
     */
    public static boolean clear(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 判断是否包含 key 的 value
     * @param preferences
     * @param key
     * @return
     */
    public static boolean contains(SharedPreferences preferences, String key){
        return preferences.contains(key);
    }

    /**
     * 获取boolean 类型 如果取不到值就 返回 false
     * @param preferences
     * @param key
     * @return
     */
    public static boolean getBoolean(SharedPreferences preferences,String key){
        return preferences.getBoolean(key,false);
    }

    /**
     * 获取boolean 如果取不到值 返回自定义的值 defVal
     * @param preferences
     * @param key
     * @param defVal
     * @return
     */
    public static Boolean getBoolean(SharedPreferences preferences,String key,boolean defVal){
        return preferences.getBoolean(key,defVal);
    }

    /**
     * 获取int 如果取不到值 就返回-1
     * @param preferences
     * @param key
     * @return
     */
    public static Integer getInt(SharedPreferences preferences,String key){
        return preferences.getInt(key,-1);
    }

    /**
     * 获取int 如果取不到值 放回自定义的值 delVal
     * @param preferences
     * @param key
     * @param defVal
     * @return
     */
    public static Integer getInt(SharedPreferences preferences,String key,Integer defVal){
        return preferences.getInt(key,defVal);
    }

    /**
     * 获取Long 如果取不到值 就返回-1L
     * @param preferences
     * @param key
     * @return
     */
    public static Long getLong(SharedPreferences preferences,String key){
        return preferences.getLong(key,-1L);
    }

    /**
     * 获取Long 如果取不到值 就返回自定的值 delVal
     * @param preferences
     * @param key
     * @param delVal
     * @return
     */
    public static Long getLong(SharedPreferences preferences,String key,Long delVal){
        return preferences.getLong(key,delVal);
    }

    /**
     * 获取Float 如果取不到值 就返回-1f
     * @param preferences
     * @param key
     * @return
     */
    public static Float getFloat(SharedPreferences preferences,String key){
        return preferences.getFloat(key,-1f);
    }

    /**
     * 获取Float 如果取不到值 就返回自定义的值 delVal
     * @param preferences
     * @param key
     * @param delVal
     * @return
     */
    public static Float getFloat(SharedPreferences preferences,String key,Float delVal){
        return preferences.getFloat(key,delVal);
    }

    /**
     * 取字符串
     * @param preferences
     * @param key
     * @return
     */
    public static String getString(SharedPreferences preferences, String key) {
        return preferences.getString(key, "");
    }

    /**
     * 取字符串，带默认值
     * @param preferences
     * @param key
     * @param defVal
     * @return
     */
    public static String getString(SharedPreferences preferences, String key, String defVal) {
        return preferences.getString(key, defVal);
    }

    /**
     * 获取序列化对象
     * @param preferences
     * @param key
     * @return
     */
    public static Serializable getSerializable(SharedPreferences preferences,String key){

        String str = getString(preferences,key);
        if(TextUtils.isEmpty(str)){
            return null;
        }
        //解析对象字符串
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(str.getBytes(),0));
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            //将字符串对象反序列化成实体对象
            Serializable obj = (Serializable) ois.readObject();
            ois.close();
            bais.close();
            return obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 移除对象
     * @param preferences
     * @param key
     * @return
     */
    public static boolean remove(SharedPreferences preferences,String key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 移除多个对象
     * @param preferences
     * @param keys
     * @return
     */
    public static boolean removeKeys(SharedPreferences preferences,String[] keys){
        SharedPreferences.Editor editor = preferences.edit();
        for (String key : keys){
            editor.remove(key);
        }
        return editor.commit();
    }

    /**
     * 保存boolean
     * @param preferences
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences preferences,String key,Boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        return  editor.commit();
    }

    /**
     * 保存Integer
     * @param preferences
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences preferences,String key,Integer value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        return  editor.commit();
    }

    /**
     * 保存Long
     * @param preferences
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences preferences, String key, Long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 保存Float
     * @param preferences
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences preferences, String key, Float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 保存String
     * @param preferences
     * @param key
     * @param value
     * @return
     */
    public static boolean save(SharedPreferences preferences,String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        return  editor.commit();
    }

    /**
     * 保存序列化对象
     * @param preferences
     * @param key
     * @param obj
     * @return
     */
    public static boolean save(SharedPreferences preferences,String key,Object obj,Boolean flag){
        SharedPreferences.Editor editor = preferences.edit();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            String bytesToHexString =bytesToHexString(baos.toByteArray());
            editor.putString(key,bytesToHexString);
            oos.close();
            baos.close();
            return editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static boolean save(SharedPreferences preferences, String key, Object value) {
        if(value instanceof Integer)
            return save(preferences, key, (Integer)value);
        if(value instanceof Long)
            return save(preferences, key, (Long)value);
        if(value instanceof Float)
            return save(preferences, key, (Float)value);
        if(value instanceof Boolean)
            return save(preferences, key, (Boolean)value);
        if(value instanceof String)
            return save(preferences, key, (String)value);
//      if(value instanceof Serializable)
//            return save(sharedPreferences, key, (Serializable)value);
        //    Log.d(TAG, "值对象为：" + value);
        return false;
    }
}
