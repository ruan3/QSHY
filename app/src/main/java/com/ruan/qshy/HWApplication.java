package com.ruan.qshy;

import org.xutils.x;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

public class HWApplication extends Application {

	Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
		x.Ext.init(this);//注册xUtils
	}

	/**
	 * 实现单例，任何一个页面都能拿到这个类的数据和对象
	 */
	public HWApplication(){}

	private static class HWApplicationHelper{

		public static HWApplication application = new HWApplication();

	}

	public static HWApplication getInstance() {
		return HWApplicationHelper.application;
	}

}
