package com.ttsoftcontroleur;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ResultControleMain extends TabActivity{

	@SuppressWarnings({ "unused" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		
		// Cotronle tab
		Intent intentControle = new Intent().setClass(this, ResultControle.class);
		TabSpec tabSpecControle = tabHost
			.newTabSpec("Contrôle")
			.setIndicator("Contrôle")
			.setContent(intentControle);

		// Rapport tab
		Intent intentRapports = new Intent().setClass(this, RapportActivity.class);
		TabSpec tabSpecRapports = tabHost
			.newTabSpec("Rapports")
			.setIndicator("Rapports")
			.setContent(intentRapports);
		
		
		// add all tabs 
		tabHost.addTab(tabSpecControle);
		tabHost.addTab(tabSpecRapports);
		
		
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}
	
}
