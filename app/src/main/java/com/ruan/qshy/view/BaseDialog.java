package com.ruan.qshy.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ruan.qshy.R;


/**
 * Author: Guo Xiongcheng
 * Date:     2016/6/27 15:19
 * Description: //Dialog 基类
 * History: //修改记录
 * 修改人姓名             修改时间            版本号                  描述
 */
public class BaseDialog extends Dialog {
    public static boolean isCanBack = true;

    public BaseDialog(Context context) {
        super(context, R.style.dialog);
    }

    public BaseDialog(Context context, int style) {
        super(context, style);
    }

    Drawable drawable = new ColorDrawable() {
        Paint paint = new Paint();

        @Override
        public void draw(Canvas canvas) {
            Rect rect = canvas.getClipBounds();
            paint.setStyle(Paint.Style.STROKE);

            paint.setStrokeWidth(0.5f);
            paint.setColor(Color.argb(200, 0, 0, 40));
            canvas.drawRoundRect(new RectF(0, 0, rect.width(), rect.height()), 10, 10, paint);

            paint.setStrokeWidth(1.0f);
            paint.setColor(Color.argb(200, 200, 200, 200));
            for (int i = 1; i < 2; i++) {
                canvas.drawRoundRect(new RectF(i, i, rect.width() - i * 2, rect.height() - 1 * 2), 10, 10, paint);
            }

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(200, 0, 0, 40));

            canvas.drawRoundRect(new RectF(2, 2, rect.width() - 4, rect.height() - 4), 10, 10, paint);

            paint.setStyle(Paint.Style.STROKE);

            float height = rect.height() * 0.2f;

            for (int i = 1; i <= height; i++) {
                paint.setColor(Color.argb(255 - i * 3, 200, 200, 200));
                canvas.drawCircle(rect.width() / 2, i - rect.width() * 2, rect.width() * 2, paint);
            }

        }
    };

    public TextView tv_title;
    public TextView tv_desc;
    public Button btn_left;
    public Button btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.base_dialog);
    }

    /**
     * 获取对话框的ui容器
     *
     * @return
     */
    public FrameLayout getContentView() {
        View v = this.findViewById(R.id.base_dialog_layout);
        if (v != null) {
            return (FrameLayout) v;
        }
        return null;
    }

    /*
     * 设置对话框的ui
     * @see android.app.Dialog#setContentView(int)
     */
    @Override
    public void setContentView(int layoutResID) {
        /*
         * if (layoutResID != R.layout.base_dialog) {
		 * super.setContentView(R.layout.base_dialog); final FrameLayout
		 * contentView = getContentView(); contentView.removeAllViews();
		 * getLayoutInflater().from(mContext) .inflate(layoutResID,
		 * contentView); } else { super.setContentView(layoutResID); }
		 */
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        FrameLayout contentView = (FrameLayout) layoutInflater.inflate(
                R.layout.dialog_layout_base, null);

        //	contentView.setBackground(drawable);
        layoutInflater.inflate(layoutResID, contentView);
        super.setContentView(contentView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    /*
     * 点击对话框右上角可触发关闭对话框事件
     * @see android.app.Dialog#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getWindow().getDecorView();
            float disX = v.getWidth() - event.getX();
            float disY = event.getY();
            if ((disX > 0 && disX < v.getWidth() * 0.1)
                    && (disY > 0 && disY < v.getHeight() * 0.2)) {
                if (beforeDismiss()) {
                    this.dismiss();
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 点击对话框右上角时，是否关闭对话框，默认为不关闭
     *
     * @return
     */
    public boolean beforeDismiss() {
        return false;
    }

    protected long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isCanBack) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                if ((System.currentTimeMillis() - exitTime) > 2000) {
//                    Toast.makeText(getContext(), "再点击一次退出程序", Toast.LENGTH_SHORT).show();
//                    exitTime = System.currentTimeMillis();
//                } else {
//
//                    System.exit(0);
//                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
