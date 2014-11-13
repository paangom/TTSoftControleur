package com.ttsoftcontroleur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ControleActivity extends Activity implements OnClickListener {

	private EditText ligne, matricule;
	private Button btnEnvoyer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controle);

		ligne = (EditText) findViewById(R.id.ligne);
		matricule = (EditText) findViewById(R.id.matricule);
		btnEnvoyer = (Button) findViewById(R.id.envoyer);
		btnEnvoyer.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.envoyer) {

			if (ligne.getText().toString().equalsIgnoreCase("227")
					&& matricule.getText().toString().equalsIgnoreCase("8081")) {
				Intent intent = new Intent(getApplicationContext(),
						DemarrerControle.class);
				startActivity(intent);
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Les informations que vous avez saisi sont incorrectes.",
						Toast.LENGTH_LONG).show();
			}
		}
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
