package com.ttsoftcontroleur;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ControleRapportTabs extends TabActivity {

	@SuppressWarnings({ "unused" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources ressources = getResources();
		TabHost tabHost = getTabHost();

		// Cotronle tab
		Intent intentControle = new Intent().setClass(this,
				ControleActivity.class);
		TabSpec tabSpecControle = tabHost.newTabSpec("Contrôle")
				.setIndicator("Contrôle").setContent(intentControle);

		// Rapport tab
		Intent intentRapports = new Intent().setClass(this,
				RapportActivity.class);
		TabSpec tabSpecRapports = tabHost.newTabSpec("Rapports")
				.setIndicator("Rapports").setContent(intentRapports);

		// add all tabs
		tabHost.addTab(tabSpecControle);
		tabHost.addTab(tabSpecRapports);

		// set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fin_service, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.termiate_service) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
