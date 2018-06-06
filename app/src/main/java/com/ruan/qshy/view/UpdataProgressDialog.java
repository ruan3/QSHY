package com.ruan.qshy.view;

import android.content.Context;

import com.ruan.qshy.R;

public class UpdataProgressDialog extends BaseDialog{

	public BaseDialog dialog;
	Context context;
	LineProgressBar line_progresbar;
	public UpdataProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initView();
	}

	public void initView(){
		dialog = new BaseDialog(context,
				R.style.dialog);
		dialog.setContentView(R.layout.dialog_progress_bar);

		line_progresbar = (LineProgressBar) dialog.findViewById(R.id.line_progresbar);
		line_progresbar.setProgressDesc("剩余");
		dialog.show();
		dialog.setCancelable(false);
	}

	public void getData(int current,int max){

		line_progresbar.setMaxProgress(max);
		line_progresbar.setCurProgress(current);

	}

}
