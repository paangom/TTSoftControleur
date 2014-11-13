package com.ttsoftcontroleur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Controler extends Activity implements OnClickListener {

	private Button scan;
	private TextView contentTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demarrer_controle);

		// formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView) findViewById(R.id.scan_content);

		scan = (Button) findViewById(R.id.demarrer);
		scan.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.demarrer) {

			// scan
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();

		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanningResult != null) {
			// we have a result
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			if (scanContent != null && scanFormat != null) {
				// formatTxt.setText("FORMAT: " + scanFormat);
				contentTxt.setText(scanContent);
			} else {
				contentTxt.setText("Aucune donnée recue!");
			}

		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Aucune donnée n'est ecrite!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fin_control, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.termiate_control) {
			Intent intent = new Intent(getApplicationContext(),
					ControleRapportTabs.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			onBackPressed();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {

		return;
	}
}
