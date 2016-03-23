package com.zhx.act;

import com.zhx.R;
import com.zhx.download.DownloadActivity;
import com.zhx.views.SwitchButton;
import com.zhx.views.SwitchButton2;
import com.zhx.views.ToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private Button switchBtn;
	SwitchButton2 mySwitchBtn2;
	SwitchButton mySwitchBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupView();
		setToggenButton();
		setSwtichButton2();
		setSwtichButton();
	}

	private void setupView() {
		switchBtn = $(R.id.switch_btn);
		Toolbar toolbar = $(R.id.main_toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(R.string.app_name);
	}
	private void setSwtichButton() {
		mySwitchBtn = (SwitchButton)$(R.id.mySwitchBtn);
		mySwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Toast.makeText(MainActivity.this, "open push", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this, "colsed push", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void setSwtichButton2() {
		mySwitchBtn2 = (SwitchButton2) findViewById(R.id.mySwitchBtn2);
		mySwitchBtn2.setImageResource(R.mipmap.switch_bkg_switch,R.mipmap.switch_btn_slip);
		mySwitchBtn2.setOnSwitchStateListener(new SwitchButton2.OnSwitchListener() {
			@Override
			public void onSwitched(boolean isSwitchOn) {
				if(isSwitchOn){
					Toast.makeText(MainActivity.this, "推送开启", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this, "推送关闭", Toast.LENGTH_SHORT).show();
				}
			}
		});
		switchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isOn = mySwitchBtn2.getSwitchState();
				mySwitchBtn2.setSwitchState(!isOn);
			}
		});
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
			Intent intent = new Intent(MainActivity.this,SecondActivity.class);
			startActivity(intent);
			return true;
		}else if (id == R.id.action_download) {
			Intent intent = new Intent(MainActivity.this,DownloadActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
