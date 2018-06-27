package cn.edu.bjut.sipl.smarteye.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.bjut.sipl.smarteye.R;

/**
 * 界面显示基类
 * Created by gongzp
 * date    on 2018/5/25.
 */

public class BaseActivity extends ActionBarActivity {
	protected ActionBar actionBar;


	protected ImageView return_iv;                          //自定义的actionBar的返回按钮
	protected TextView  actionBar_tv;                       //自定义的actionBar的标题
	protected TextView  actionbar_right_tv;                 //自定义的actionBar右面的按钮
	protected ImageView actionbar_right_iv;                 //顶部右侧的actionbar图片
	protected int currentPage = 0;
	protected int pageSize = 15;
	protected int pageCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBar = this.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setHomeAsUpIndicator(R.mipmap.back_blue);
	}

	/**
	 * 设置状态栏样式统一
	 */
	public void setStatusBarStyle() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			//去除actionBar的阴影效果
			getSupportActionBar().setElevation(0);
		}

	}

	/**
	 * 加载自定义的ActionBar
	 * @param title
     */
	public void setCustomActionBar(String title){

		//加载自定义的actionBar视图
		View mActionBarView  = getLayoutInflater().inflate(R.layout.top_bar, null);
		ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
		actionBar.setCustomView(mActionBarView, layout);

		return_iv = (ImageView)mActionBarView.findViewById(R.id.return_iv);
		actionBar_tv = (TextView)mActionBarView.findViewById(R.id.actionbar_tv);
		actionbar_right_tv = (TextView)mActionBarView.findViewById(R.id.actionbar_right_tv);
		actionbar_right_iv = (ImageView)mActionBarView.findViewById(R.id.actionbar_right_iv);

		//设置自定义返回按钮的内边距
		return_iv.setPadding(10,10,10,10);
		return_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		actionBar_tv.setText(title);
		actionBar.setCustomView(mActionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case android.R.id.home:
			this.finish();
			break;
		default:
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * 获取InputMethodManager，隐藏软键盘
	 * @param token
	 */
	protected void hideKeyboard(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token,0);
		}
	}


	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	protected boolean isShouldHideKeyboard(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = {0, 0};
			v.getLocationInWindow(l);
			int left = l[0],
					top = l[1],
					bottom = top + v.getHeight(),
					right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
		return false;
	}
}
