package com.zhx.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhx.R;

public class DownloadActivity extends AppCompatActivity {
    private TextView textView;
    private String url = "http://p.gdown.baidu.com/4611141b43bed6c82093102d7e6c0d3ea29cd03be1a3a011b482bb498b66c382be47cff913900861d589cfab9523e914590e6ecd4518e0d62645481a615862f70885581d6ce075166c1c91f57c1fb8ce5065b699aedab85ac2d14137eb6b074b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        textView = (TextView) findViewById(R.id.download_text);
        findViewById(R.id.download_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWithAction(DownloadActivity.this,url);
            }
        });
        findViewById(R.id.download_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDownloadId(DownloadActivity.this,url);
            }
        });
    }

    public static void downloadWithAction(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    public long getDownloadId(Context context,String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request  request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalPublicDir("Baochun","报春资讯app.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);//在通知栏中显示
        return downloadManager.enqueue(request);
    }
}
