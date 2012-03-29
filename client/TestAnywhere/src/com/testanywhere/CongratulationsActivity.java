package com.testanywhere;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CongratulationsActivity extends Activity {

	private Button submitButton;
	private Facebook facebook = null;
	private SharedPreferences facebookPrefs = null;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.congratulations);
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

		Button button = (Button) findViewById(R.id.submitButton);
		// ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("message", "test");
				bundle.putString("access_token", facebook.getAccessToken());
				facebook.dialog(CongratulationsActivity.this, "feed", bundle,
						new DialogListener() {

							public void onComplete(Bundle values) {

							}

							public void onFacebookError(FacebookError e) {

							}

							public void onError(DialogError e) {

							}

							public void onCancel() {

							}
						});
			}
		});

	}

}

/*
 * Bundle bundle = new Bundle(); bundle.putString("message", "test");
 * 
 * facebook.dialog(TestAnywhereActivity.this, "feed", bundle, new
 * DialogListener() {
 * 
 * public void onComplete(Bundle values) {
 * 
 * }
 * 
 * public void onFacebookError(FacebookError e) {
 * 
 * }
 * 
 * public void onError(DialogError e) {
 * 
 * }
 * 
 * public void onCancel() {
 * 
 * 
 * } });
 */
