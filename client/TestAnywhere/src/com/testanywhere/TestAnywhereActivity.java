package com.testanywhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class TestAnywhereActivity extends Activity {
	private boolean isTimerRun = true;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(isTimerRun) {
			final Intent test = new Intent(this, TestAnywhereActivity.class);
			test.setAction(test.ACTION_VIEW);
			startActivity(test);
			finish();
		}
		isTimerRun = false;
		return true;
	}
	
	public void onStop() {
		super.onStop();
		isTimerRun = false;
	}
	
	public void onStart() {
		super.onStart();
		isTimerRun = true;
		
		final Intent test = new Intent(this, TestAnywhereActivity.class);
		setContentView(R.layout.main);
		Handler timerHandler = new Handler();
		Runnable timerRunnable = new Runnable() {
			public void run() {
				if(isTimerRun) {
					test.setAction(test.ACTION_VIEW);
					startActivity(test);
					finish();
				}
			}
		};
		timerHandler.postDelayed(timerRunnable, 3000);
		
	}
}