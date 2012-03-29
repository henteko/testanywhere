package com.testanywhere;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class TestAnywhereActivity extends Activity {
	private Facebook facebook = null;
	private SharedPreferences facebookPrefs = null;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
		facebook = new Facebook(Constants.FACEBOOK_APP_ID);
		facebookPrefs = getPreferences(MODE_PRIVATE);
		String facebookAccessToken = facebookPrefs.getString(
				Constants.FACEBOOK_ACCESS_TOKEN, null);

		long facebookAcessExpires = facebookPrefs.getLong(
				Constants.FACEBOOK_ACCESS_EXPIRES, 0);
		if (facebookAccessToken != null) {
			facebook.setAccessToken(facebookAccessToken);
		}
		if (facebookAcessExpires != 0) {
			facebook.setAccessExpires(facebookAcessExpires);
		}

		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {

			facebook.authorize(this, new String[] {}, new DialogListener() {
				@Override
				public void onComplete(Bundle values) {
					SharedPreferences.Editor editor = facebookPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
				}

				@Override
				public void onFacebookError(FacebookError error) {
					System.out.println("facebook error");
				}

				@Override
				public void onError(DialogError e) {
					System.out.println("app error");
				}

				@Override
				public void onCancel() {
					System.out.println("cancel");
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onResult");
		facebook.authorizeCallback(requestCode, resultCode, data);

		final Intent serach = new Intent(this, StartActivity.class);
		Handler timerHandler = new Handler();
		Runnable timerRunnable = new Runnable() {
			public void run() {
				serach.setAction(serach.ACTION_VIEW);
				startActivity(serach);
				finish();
			}
		};
		timerHandler.postDelayed(timerRunnable, 3000);

	}

	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
}