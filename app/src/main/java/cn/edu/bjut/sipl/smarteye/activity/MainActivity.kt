package cn.edu.bjut.sipl.smarteye.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import cn.edu.bjut.sipl.smarteye.utils.MyUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.os.Build
import android.graphics.Bitmap
import android.os.Environment
import cn.edu.bjut.sipl.smarteye.R
import java.io.FileNotFoundException
import java.io.IOException

/**
 * 主界面包含相机和相册按钮
 * Created by gongzp
 * date    on 2018/5/25.
 */

class MainActivity : Activity() {

    // 裁剪图片的路径
    var mCropPictureUri: Uri? = null
    // 生成的文件的名字
    var filename : String = ""
    // 标志位是否是相机图片
    var isCaremaPic : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    /**
     * 初始化UI
     */
    fun initUI(){
        val btnOnclickListener = BtnOnclickListener()
        //为两个按钮设置点击事件
        camera_iv.setOnClickListener(btnOnclickListener)
        album_iv.setOnClickListener(btnOnclickListener)
        val myUtils = MyUtils()
        //适配android6.0的权限认证
        myUtils.verifyStoragePermissions(this)
    }

    /**
     * 打开相册
     */
    fun openAlbum(){
        //实际开发采用
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image")
        startActivityForResult(intent,MyUtils.Companion.SCAN_ALBUM_CODE)
        //由于模拟器图库的刷新问题，采用如下打开方式
//        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, MyUtils.Companion.SCAN_ALBUM_CODE)
    }

    /**
     * 打开相机
     */
    fun openCamera(){
        filename= getPictureFilename() +".png"
        val outputFile = File(Environment.getExternalStorageDirectory(), "SmartEyePic/"+filename)
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdir()
        }
        val imageuri: Uri
        if (Build.VERSION.SDK_INT >= 24) {
            imageuri = FileProvider.getUriForFile(this,
                    "cn.edu.bjut.sipl",
                    outputFile)
        } else {
            imageuri = Uri.fromFile(outputFile)
        }
        //启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
        startActivityForResult(intent, MyUtils.PHONE_CARAME_CODE)
    }

    /**
     * 获取年月日时分秒作为图片的名称
     */
    fun getPictureFilename(): String{
        val dt = Date()
        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        return sdf.format(dt)
    }

    /**
     * 回调函数
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                MyUtils.SCAN_ALBUM_CODE //从相册图片后返回的uri
                ->{
                    isCaremaPic = false
                    //启动裁剪
                    startActivityForResult(CutForPhoto(data?.data), MyUtils.PHONE_CROP_CODE)
                }
                MyUtils.PHONE_CARAME_CODE //相机返回的 uri
                -> {
                    isCaremaPic = true
                    //启动裁剪
                    startActivityForResult(CutForCamera(), MyUtils.PHONE_CROP_CODE)
                }
                MyUtils.PHONE_CROP_CODE ->
                    try {
                    /*//获取裁剪后的图片，并显示出来
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mCropPictureUri))*/
                    if(isCaremaPic)
                        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mCropPictureUri))
                    val intent  = Intent(this, QueryActivity::class.java)
                    intent.putExtra("uri", mCropPictureUri.toString())
                    startActivity(intent)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 裁剪本地相册的图片
     */
    private fun CutForPhoto(uri: Uri?): Intent? {
        try {
            //直接裁剪
            val intent = Intent("com.android.camera.action.CROP")
            //设置裁剪之后的图片路径文件
            val cutfile = File(Environment.getExternalStorageDirectory(), "SmartEyePic/"+getPictureFilename()+".png") //随便命名一个
            if (!cutfile.getParentFile().exists()) {
                cutfile.getParentFile().mkdir()
            }
            cutfile.createNewFile()
            //初始化 uri
            var outputUri: Uri? = null //真实的 uri
            outputUri = Uri.fromFile(cutfile)
            mCropPictureUri = outputUri
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true)
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            val myUtils = MyUtils()
            //设置要裁剪的宽高
            intent.putExtra("outputX", myUtils.dp2px(this, 200)) //200dp
            intent.putExtra("outputY", myUtils.dp2px(this, 200))
            intent.putExtra("scale", true)
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false)
            if (uri != null) {
                intent.setDataAndType(uri, "image/*")
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            }
            intent.putExtra("noFaceDetection", true)
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            return intent
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 裁剪相机的照片
     */
    private fun CutForCamera(): Intent? {
        try {

            //设置裁剪之后的图片路径文件
            val cutfile = File(Environment.getExternalStorageDirectory(), "SmartEyePic/"+getPictureFilename()+".png") //随便命名一个
            cutfile.createNewFile()
            //初始化 uri
            var imageUri: Uri? = null //返回来的 uri
            var outputUri: Uri? = null //真实的 uri
            val intent = Intent("com.android.camera.action.CROP")
            //拍照留下的图片
            val camerafile = File(Environment.getExternalStorageDirectory(),"SmartEyePic/"+filename)
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imageUri = FileProvider.getUriForFile(this,
                        "cn.edu.bjut.sipl",
                        camerafile)
            } else {
                imageUri = Uri.fromFile(camerafile)
            }
            outputUri = Uri.fromFile(cutfile)
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCropPictureUri = outputUri
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true)
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            val myUtils = MyUtils()
            //设置要裁剪的宽高
            intent.putExtra("outputX", myUtils.dp2px(this, 200 ))
            intent.putExtra("outputY", myUtils.dp2px(this, 200))
            intent.putExtra("scale", true)
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false)
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*")
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            }
            intent.putExtra("noFaceDetection", true)
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            return intent
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return intent
    }


    /**
     * 定义一个内部类实现按钮的点击监听
     */
    inner class BtnOnclickListener : View.OnClickListener{

        override fun onClick(view: View?) {
            when(view?.id){
                R.id.camera_iv ->
                    //打开相机
                    this@MainActivity.openCamera()
                R.id.album_iv ->
                    //打开相册
                    this@MainActivity.openAlbum()
            }
        }
    }
}
