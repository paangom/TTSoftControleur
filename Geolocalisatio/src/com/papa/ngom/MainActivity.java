package com.papa.ngom;

/*import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity implements OnClickListener, LocationListener{
	private LocationManager lManager;
    private Location location;
    private String choix_source = "";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        //On sp�cifie que l'on va avoir besoin de g�rer l'affichage du cercle de chargement
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
 
        setContentView(R.layout.main);
 
        //On r�cup�re le service de localisation
        lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 
        //Initialisation de l'�cran
        reinitialisationEcran();
 
        //On affecte un �couteur d'�v�nement aux boutons
        findViewById(R.id.choix_source).setOnClickListener(this);
        findViewById(R.id.obtenir_position).setOnClickListener(this);
        findViewById(R.id.afficherAdresse).setOnClickListener(this);
    }
 
        //M�thode d�clencher au clique sur un bouton
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choix_source:
			choisirSource();
			break;
		case R.id.obtenir_position:
			obtenirPosition();
			break;
		case R.id.afficherAdresse:
			afficherAdresse();
			break;
		default:
			break;
		}
	}
 
	//R�initialisation de l'�cran
	private void reinitialisationEcran(){
		((TextView)findViewById(R.id.latitude)).setText("0.0");
		((TextView)findViewById(R.id.longitude)).setText("0.0");
		((TextView)findViewById(R.id.altitude)).setText("0.0");
		((TextView)findViewById(R.id.adresse)).setText("");
 
		findViewById(R.id.obtenir_position).setEnabled(false);
		findViewById(R.id.afficherAdresse).setEnabled(false);
	}
 
	private void choisirSource() {
		reinitialisationEcran();
 
		//On demande au service la liste des sources disponibles.
		List <String> providers = lManager.getProviders(true);
		final String[] sources = new String[providers.size()];
		int i =0;
		//on stock le nom de ces source dans un tableau de string
		for(String provider : providers)
			sources[i++] = provider;
 
		//On affiche la liste des sources dans une fen�tre de dialog
		//Pour plus d'infos sur AlertDialog, vous pouvez suivre le guide
		//http://developer.android.com/guide/topics/ui/dialogs.html
		new AlertDialog.Builder(MainActivity.this)
		.setItems(sources, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				findViewById(R.id.obtenir_position).setEnabled(true);
				//on stock le choix de la source choisi
				choix_source = sources[which];
				//on ajoute dans la barre de titre de l'application le nom de la source utilis�
				setTitle(String.format("%s - %s", getString(R.string.app_name),
						choix_source));
			}
		})
		.create().show();
	}
 
	private void obtenirPosition() {
		//on d�marre le cercle de chargement
		setProgressBarIndeterminateVisibility(true);
 
		//On demande au service de localisation de nous notifier tout changement de position
		//sur la source (le provider) choisie, toute les minutes (60000millisecondes).
		//Le param�tre this sp�cifie que notre classe impl�mente LocationListener et recevra
		//les notifications.
		lManager.requestLocationUpdates(choix_source, 60000, 0, this);
	}
 
	private void afficherLocation() {
		//On affiche les informations de la position a l'�cran
		((TextView)findViewById(R.id.latitude)).setText(String.valueOf(location.getLatitude()));
		((TextView)findViewById(R.id.longitude)).setText(String.valueOf(location.getLongitude()));
		((TextView)findViewById(R.id.altitude)).setText(String.valueOf(location.getAltitude()));
	}
 
	private void afficherAdresse() {
		setProgressBarIndeterminateVisibility(true);
 
		//Le geocoder permet de r�cup�rer ou chercher des adresses
		//gr�ce � un mot cl� ou une position
		Geocoder geo = new Geocoder(MainActivity.this);
		try {
			//Ici on r�cup�re la premiere adresse trouv� gr�ce � la position que l'on a r�cup�r�
			List
<Address> adresses = geo.getFromLocation(location.getLatitude(),
					location.getLongitude(),1);
 
			if(adresses != null && adresses.size() == 1){
				Address adresse = adresses.get(0);
				//Si le geocoder a trouver une adresse, alors on l'affiche
				((TextView)findViewById(R.id.adresse)).setText(String.format("%s, %s %s",
						adresse.getAddressLine(0),
						adresse.getPostalCode(),
						adresse.getLocality()));
			}
			else {
				//sinon on affiche un message d'erreur
				((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu �tre d�termin�e");
			}
		} catch (IOException e) {
			e.printStackTrace();
			((TextView)findViewById(R.id.adresse)).setText("L'adresse n'a pu �tre d�termin�e");
		}
		//on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 
	public void onLocationChanged(Location location) {
		//Lorsque la position change...
		Log.i("Tuto g�olocalisation", "La position a chang�.");
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
		//... on active le bouton pour afficher l'adresse
		findViewById(R.id.afficherAdresse).setEnabled(true);
		//... on sauvegarde la position
		this.location = location;
		//... on l'affiche
		afficherLocation();
		//... et on sp�cifie au service que l'on ne souhaite plus avoir de mise � jour
		lManager.removeUpdates(this);
	}
 
	public void onProviderDisabled(String provider) {
		//Lorsque la source (GSP ou r�seau GSM) est d�sactiv�
		Log.i("Tuto g�olocalisation", "La source a �t� d�sactiv�");
		//...on affiche un Toast pour le signaler � l'utilisateur
		Toast.makeText(MainActivity.this,
				String.format("La source \"%s\" a �t� d�sactiv�", provider),
				Toast.LENGTH_SHORT).show();
		//... et on sp�cifie au service que l'on ne souhaite plus avoir de mise � jour
		lManager.removeUpdates(this);
		//... on stop le cercle de chargement
		setProgressBarIndeterminateVisibility(false);
	}
 
	public void onProviderEnabled(String provider) {
		Log.i("Tuto g�olocalisation", "La source a �t� activ�.");
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("Tuto g�olocalisation", "Le statut de la source a chang�.");
	}
 
}
*/

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

import android.util.Log;

public class MainActivity extends Activity implements LocationListener {

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 20 * 1; // 1 minute
	protected LocationManager locationManager;
	protected Context context;
	protected boolean gps_enabled, network_enabled;
	TextView txtLat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		txtLat = (TextView) findViewById(R.id.textview1);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// getting GPS status
		gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if (gps_enabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		} else if (network_enabled) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		};
	}

	@Override
	public void onLocationChanged(Location location) {
		txtLat = (TextView) findViewById(R.id.textview1);
		txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude", "disable");
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude", "enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude", "status");
	}
}