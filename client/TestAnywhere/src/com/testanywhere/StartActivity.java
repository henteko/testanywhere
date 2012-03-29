package com.testanywhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.start);

		final Intent show = new Intent(this, ShowQActivity.class);
		Button button = (Button) findViewById(R.id.startbutton);
		// ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//次のActivityに
				show.setAction(show.ACTION_VIEW);
				startActivity(show);
				finish();
			}
		});

	}

}
