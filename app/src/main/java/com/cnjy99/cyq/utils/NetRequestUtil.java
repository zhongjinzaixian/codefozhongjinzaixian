package com.cnjy99.cyq.utils;

import android.app.Activity;
import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class NetRequestUtil {

    private NetRequestReturnInterface netRequestReturnInterface;
    private ResultInterface resultObject;

    private boolean isFirstRequest = true; //多个请求同时发来时判断是否为第一个请求
    public String result = ""; //获取结果的字符串
    private int count = 0;

    private Context mContet;
    private Activity activity;

    public NetRequestUtil(Context context){
        this.mContet = context;
    }

    public void setNetRequestReturnInterface(NetRequestReturnInterface netRequestReturnInterface) {
        this.netRequestReturnInterface = netRequestReturnInterface;
    }

    public void setResultInterface(ResultInterface resultObject) {
        this.resultObject = resultObject;
    }

    public void requestParams(HttpMethod httpMethod, String url) {
        requestParams(httpMethod,url,null);
    }

    public void requestParams(HttpMethod httpMethod, String url,
                              Map<String, String> map) {
        requestParams(httpMethod, url, map,1);
    }

    public void requestParams(HttpMethod httpMethod, String url,int type) {
        requestParams(httpMethod, url, null,type);
    }

    public void requestParams(HttpMethod httpMethod,String bodyUrl,Map<String,String> map,int type){

        final HttpUtils httpUtils = new HttpUtils(500000);
        httpUtils.configCurrentHttpCacheExpiry(0);
        httpUtils.configCookieStore(MyCookieStore.cookieStore);
        String url = null;
        url = bodyUrl;
        RequestParams requestParams = new RequestParams();

        if(map != null){
            Iterator it = map.keySet().iterator();
            while (it.hasNext()){
                String key;
                String value;
                key = it.next().toString();
                value = map.get(key);
                if(httpMethod.equals(HttpMethod.GET)){
                    requestParams.addQueryStringParameter(key,value);
                }else {
                    requestParams.addBodyParameter(key,value);
                }
            }
        }else {
            httpUtils.send(httpMethod, url, new RequestCallBack<String>() {

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    result = responseInfo.result;
                    DefaultHttpClient httpClient = (DefaultHttpClient)httpUtils.getHttpClient();
                    MyCookieStore.cookieStore = httpClient.getCookieStore();
                    CookieStore cookieStore = httpClient.getCookieStore();
                    List<Cookie> cookies = cookieStore.getCookies();
                    String aa = null;
                    for (int i = 0;i < cookies.size(); i++){
                        if("JSESSIONID".equals(cookies.get(i).getName())){
                            aa = cookies.get(i).getValue();
                            break;
                        }
                    }
                    resultObject.onResultSuccess(result);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    result = s;
                    resultObject.onResultFail(result);
                }

                @Override
                public void onCancelled() {
                    super.onCancelled();
                    resultObject.onResultCancelled();
                }
            });
        }
        requestParams = null;
    }
}
