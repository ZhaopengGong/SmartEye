package cn.edu.bjut.sipl.smarteye;

import android.app.Application;

import org.xutils.x;

/**
 * Created by gongzp
 * date    on 2018/5/25.
 */

public class MyApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
