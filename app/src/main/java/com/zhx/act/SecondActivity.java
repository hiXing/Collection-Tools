package com.zhx.act;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhx.R;
import com.zhx.views.ToggleButton;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SecondActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImageLoader();
		setContentView(R.layout.second_activity);
	}
	private void initImageLoader(){
		DisplayImageOptions.Builder dioBuilder = new DisplayImageOptions.Builder();
		dioBuilder.cacheInMemory(true);
		dioBuilder.cacheOnDisk(true);
		dioBuilder.showImageOnLoading(android.R.drawable.btn_star_big_on);
		dioBuilder.showImageOnFail(android.R.drawable.btn_star_big_off);
		dioBuilder.cacheInMemory(true);
		dioBuilder.cacheOnDisk(true);
		dioBuilder.bitmapConfig(Bitmap.Config.RGB_565);// default 设置图片的解码类型
		
		File cacheDir = StorageUtils.getCacheDirectory(this);  //缓存文件夹路径
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
		        .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个 
//		        .taskExecutor(...)
//		        .taskExecutorForCachedImages(...)
		        .threadPoolSize(3) // default  线程池内加载的数量
		        .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
		        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
		        .denyCacheImageMultipleSizesInMemory()
		        .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
		        .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
		        .memoryCacheSizePercentage(13) // default
				.diskCache(new UnlimitedDiskCache(cacheDir))// default 可以自定义缓存路径
		        .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
		        .diskCacheFileCount(100)  // 可以缓存的文件数量 
		        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
		        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) 
		        .imageDownloader(new BaseImageDownloader(this)) // default
		        .imageDecoder(new BaseImageDecoder(false)) // default
		        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
		        .writeDebugLogs() // 打印debug log
		        .build(); //开始构建 
		ImageLoader.getInstance().init(config);
	}

	private void setToggenButton(){
		ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.setting_notice_toggleBtn);;
		//切换开关
		toggleBtn.toggle();
		//切换无动画
		toggleBtn.toggle(false);
		//开关切换事件
		toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged(){
			@Override
			public void onToggle(boolean on) {

			}
		});

		toggleBtn.setToggleOn();
		toggleBtn.setToggleOff();
		//无动画切换
//	    toggleBtn.setToggleOn(false);
//	    toggleBtn.setToggleOff(false);

		//禁用动画
//	    toggleBtn.setAnimate(false);
	}

@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
