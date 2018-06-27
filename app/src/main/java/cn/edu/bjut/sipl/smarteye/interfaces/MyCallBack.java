package cn.edu.bjut.sipl.smarteye.interfaces;

import org.xutils.common.Callback;

/**
 * 回调接口
 * Created by gongzp
 * date    on 2018/5/25.
 */

public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType>{

    @Override
    public void onSuccess(ResultType result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(Callback.CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


}