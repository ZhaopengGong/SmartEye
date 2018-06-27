package cn.edu.bjut.sipl.smarteye.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cn.edu.bjut.sipl.smarteye.data.IdentifyData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_query_data.*
import java.lang.reflect.Type
import android.widget.Toast
import cn.edu.bjut.sipl.smarteye.R
import cn.edu.bjut.sipl.smarteye.data.CreditData
import cn.edu.bjut.sipl.smarteye.interfaces.MyCallBack
import cn.edu.bjut.sipl.smarteye.utils.UrlUtils
import cn.edu.bjut.sipl.smarteye.utils.XUtils
import com.bumptech.glide.Glide
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 识别结果页
 * Created by gongzp
 * date    on 2018/5/26.
 */

class QueryDataShowActivity : BaseActivity() {

    //文件的路径
    var filePath :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_data)
        initUI()
        initData()
    }

    /**
     * 初始化UI
     */
    fun initUI(){
        setCustomActionBar("识别结果")
        actionbar_right_tv.text = "一键存证"
        actionbar_right_tv.setOnClickListener(View.OnClickListener {
            val md5 : String? = getMd5(File(filePath))
            val params = mutableMapOf<String, Any>()
            XUtils.Get(UrlUtils.Companion.save_credit + md5, params, object : MyCallBack<String>() {
                override fun onSuccess(myResult: String) {
                    super.onSuccess(myResult)
                    // 数据解析
                    val type : Type = object : TypeToken<CreditData>(){}.type
                    val data : CreditData = Gson().fromJson<CreditData>(myResult,type)
                    Toast.makeText(this@QueryDataShowActivity,"区块链已存证交易hash为"+data.result,Toast.LENGTH_LONG).show()

                }

                override fun onError(ex: Throwable, isOnCallback: Boolean) {
                    super.onError(ex, isOnCallback)
                    Toast.makeText(applicationContext, "请求失败", Toast.LENGTH_SHORT).show()
                }
            });
        })
    }

    /**
     * 初始化数据
     */
    fun initData(){
        val myResult : String = intent.getStringExtra("data")
        filePath = intent.getStringExtra("filePath")
        Glide.with(this).load(filePath).into(original_iv)
        // 界面前面的查询数据
        val type : Type = object : TypeToken<IdentifyData>(){}.type
        val data : IdentifyData = Gson().fromJson<IdentifyData>(myResult,type)
        val result_bean = data.result
        identify_tv.text = data.name
        //判断查询结果，如果没有隐藏对应的条目
        if (result_bean != null){
            //纤维素判断
            if (TextUtils.isEmpty(result_bean.fibrinous)){
                fibrinous_ll.visibility = View.GONE
            }else{
                fibrinous_ll.visibility = View.VISIBLE
                fibrinous_tv.text = result_bean.fibrinous
            }
            //蛋白质判断
            if (TextUtils.isEmpty(result_bean.protein)){
                protein_ll.visibility = View.GONE
            }else{
                protein_ll.visibility = View.VISIBLE
                protein_tv.text = result_bean.protein
            }
            //脂肪判断
            if (TextUtils.isEmpty(result_bean.fat)){
                fat_ll.visibility = View.GONE
            }else{
                fat_ll.visibility = View.VISIBLE
                fat_tv.text = result_bean.fat
            }
            //碳水化合物判断
            if (TextUtils.isEmpty(result_bean.carbo)){
                carbo_ll.visibility = View.GONE
            }else{
                carbo_ll.visibility = View.VISIBLE
                carbo_tv.text = result_bean.carbo
            }
            //热量判断
            if (TextUtils.isEmpty(result_bean.heat)){
                heat_ll.visibility = View.GONE
            }else{
                heat_ll.visibility = View.VISIBLE
                heat_tv.text = result_bean.heat
            }
            //评价
            if (TextUtils.isEmpty(result_bean.comment)){
                comment_ll.visibility = View.GONE
            }else{
                comment_ll.visibility = View.VISIBLE
                comment_tv.text = result_bean.comment
            }
        }else{
            fibrinous_ll.visibility = View.GONE
            protein_ll.visibility = View.GONE
            fat_ll.visibility = View.GONE
            carbo_ll.visibility = View.GONE
            heat_ll.visibility = View.GONE
            comment_ll.visibility = View.GONE
        }
    }

    /**
     * 获取文件的md5值
     */
    fun getMd5 (file : File) : String{
        try {
            val fis = FileInputStream(file)
            val md = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(1024)
            var length = -1
            while (true) {
                length = fis.read(buffer, 0, 1024)
                if (length<0)
                    break
                md.update(buffer, 0, length)
            }
            val bigInt = BigInteger(1, md.digest())
            System.out.println("文件md5值：" + bigInt.toString(16))
            //生成的MD5码，有时会是31位，需要补全为32位
            var str = bigInt.toString(16)
            val strlength = str.length
            if (strlength > 0 && strlength != 32) {
                var `val` = ""
                for (i in strlength..31) {
                    `val` = "0" + `val`
                }
                str = `val` + str
            }
            return str
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }
}