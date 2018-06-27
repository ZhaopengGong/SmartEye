package cn.edu.bjut.sipl.smarteye.utils

import android.Manifest
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.app.Activity

/**
 * 系统工具类
 * Created by gongzp
 * date    on 2018/5/25.
 */

class MyUtils {
    companion object {
        // 扫描本地相册状态码
        val SCAN_ALBUM_CODE = 100
        // 相机状态码
        val PHONE_CARAME_CODE = 101
        // 照片裁剪状态码
        val PHONE_CROP_CODE = 102
    }

    /**
     * 将像素密度转换为像素
     */
    fun dp2px(context:Context, dp : Int):Int{
        val scale = context.resources.displayMetrics.density
        return (dp*scale+0.5f).toInt()
    }


    // 配置android6.0动态权限申请
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}