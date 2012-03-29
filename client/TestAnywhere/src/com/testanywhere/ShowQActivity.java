package com.testanywhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ShowQActivity extends Activity {
	
	private Button submitButton;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.showq);
		
		final Intent con = new Intent(this, CongratulationsActivity.class);
		submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				con.setAction(con.ACTION_VIEW);
				startActivity(con);
			}
		});
	}

}
