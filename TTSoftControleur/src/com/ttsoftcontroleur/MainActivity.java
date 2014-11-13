package com.ttsoftcontroleur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	final Context context = this;
	private EditText username, password;
	private Button sign;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connexion);
		username = (EditText) findViewById(R.id.login);
		password = (EditText) findViewById(R.id.password);
		sign = (Button) findViewById(R.id.btnLogin);

		sign.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btnLogin) {
			if (username.getText().toString().equals("admin")
					&& password.getText().toString().equals("admin")) {
				Intent intent = new Intent(context, ControleRapportTabs.class);
				startActivity(intent);
			} else {
				Toast.makeText(
						context,
						username.getText().toString() + " et "
								+ password.getText().toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}
		
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
	    intent.setAction(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    startActivity(intent);
	}
}
