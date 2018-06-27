package cn.edu.bjut.sipl.smarteye.activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import cn.edu.bjut.sipl.smarteye.utils.XUtils
import kotlinx.android.synthetic.main.activity_query.*
import android.widget.Toast
import cn.edu.bjut.sipl.smarteye.R
import cn.edu.bjut.sipl.smarteye.interfaces.MyCallBack
import cn.edu.bjut.sipl.smarteye.utils.UrlUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.File

/**
 * 查询界面动画
 * Created by gongzp
 * date    on 2018/5/25.
 */

class QueryActivity : BaseActivity() {

    // 处理的handler
    var handler : Handler = Handler()
    // 是否旋转
    var isRotate : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)
        initUI()
        initData()
    }

    /**
     * 初始化UI
     */
    fun initUI(){
        setCustomActionBar("慧眼识菜")
        return_iv.visibility = View.GONE
        query()
    }

    /**
     * 初始化数据
     */
    fun initData(){
        val circleData = sickworm.com.misportsconnectview.SportsData()
        circleData.progress = 100
        loading_view.setSportsData(circleData)
    }


    /**
     * 调用查询接口
     */
    fun query(){
        var myUri = Uri.parse( intent.getStringExtra("uri"))
        val params = mutableMapOf<String, Any>()
        params.put("photo", File(myUri.path))
        XUtils.UpLoadFile(UrlUtils.Companion.post_photo, params, object : MyCallBack<String>() {
            override fun onSuccess(myResult: String) {
                super.onSuccess(myResult)
                Log.i("Query", "info")
                val resultObj: JSONObject?
                try {
                    resultObj = JSONObject(myResult)
                    if (null != resultObj && 0 == resultObj.getInt("error")) {
                        //延时0.5秒中跳转到显示结果界面
                        handler?.postDelayed(Runnable {
                            var intent = Intent(this@QueryActivity, QueryDataShowActivity::class.java)
                            intent.putExtra("data", myResult)
                            intent.putExtra("filePath",myUri.path)
                            startActivity(intent)
                            this@QueryActivity.finish()
                        }, 500)
                    } else {
                        return_iv.visibility = View.VISIBLE
                        Toast.makeText(applicationContext,"请求失败", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onError(ex: Throwable, isOnCallback: Boolean) {
                super.onError(ex, isOnCallback)
                return_iv.visibility = View.VISIBLE
                Toast.makeText(applicationContext, "请求失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}