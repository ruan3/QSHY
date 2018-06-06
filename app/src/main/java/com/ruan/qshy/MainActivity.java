package com.ruan.qshy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.ruan.qshy.R;
import com.ruan.qshy.view.UpdataProgressDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends Activity {

    Button btn_download;

    Context context;

    String tag = "Com";

    ProgressDialog mProgressDialog;

    String totalM;
    String progressM;

    String path;

    String mDownloadUrl = "http://apk.fangame.com.tw/lsg_ad2.apk";//请求地址

    UpdataProgressDialog updataProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_download = (Button) findViewById(R.id.btn_download);
        context = this;
        btn_download.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                downloadUpdateApk();
            }
        });

    }



    /**
     * 下载服务器端更新后最新的apk
     */
    private void downloadUpdateApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            mProgressDialog = new ProgressDialog(context);
            updataProgressDialog = new UpdataProgressDialog(context);
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk/download/"+"lsg_ad2.apk";
            Log.e(tag, "路径---->"+path);
            // mDownloadUrl为JSON从服务器端解析出来的下载地址
            RequestParams requestParams = new RequestParams(mDownloadUrl);
            // 为RequestParams设置文件下载后的保存路径
            requestParams.setSaveFilePath(path);
            // 下载完成后自动为文件命名
            requestParams.setAutoRename(false);
            x.http().get(requestParams, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                    Log.i(tag, "下载成功");
//                    mProgressDialog.dismiss();
                    updataProgressDialog.dismiss();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.i(tag, "下载失败--->"+ex.toString());
//                    mProgressDialog.dismiss();
                    updataProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.i(tag, "取消下载");
//                    mProgressDialog.dismiss();
                    updataProgressDialog.dismiss();
                }

                @Override
                public void onFinished() {
                    Log.i(tag, "结束下载");
//                    mProgressDialog.dismiss();
                    updataProgressDialog.dismiss();
                    updataProgressDialog = null;
                    Log.e("Com", "path---->"+path);
                    installAPK(new File(path));
                }

                @Override
                public void onWaiting() {
                    // 网络请求开始的时候调用
                    Log.i(tag, "等待下载");
                }

                @Override
                public void onStarted() {
                    // 下载的时候不断回调的方法
                    Log.i(tag, "开始下载");
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // 当前的下载进度和文件总大小
                    Log.i(tag, "正在下载中......");
//                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    mProgressDialog.setMessage("正在下载中......");
//                    mProgressDialog.show();
//                    mProgressDialog.setMax((int)total);
//                    mProgressDialog.setProgress((int) current);
                    updataProgressDialog.getData((int) current, (int)total);
                }
            });
        }
    }


    //下载到本地后执行安装
    protected void installAPK(File file) {
        //判读版本是否在7.0以上
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= 24){
            Uri apkUri = FileProvider.getUriForFile(context, "com.hw.qshy", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{

            Log.e(tag, "flie--->"+file.getAbsolutePath()+"---"+file.getName());
            if (!file.exists()) return;

            Uri uri = Uri.parse("file://" + file.toString());
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            //在服务中开启activity必须设置flag,后面解释
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }
}
