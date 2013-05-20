package com.addvalsolutions.map;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

 
public class MainActivity extends FragmentActivity implements LocationListener {
 
 
  	// public static GeoPoint point;
	public static String searchResponse;
	public static List<LatLng> point;
	//public static Route[] steps;
	GoogleMap googleMap;
	TextView  lblfrom;
	TextView  lblto;
	private EditText to, from;
	int requestCode = 2;
	 public static Search[] response;
	 Location location;
   
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        
        
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
 
            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
            location = locationManager.getLastKnownLocation(provider);
            
            if(location!=null){
            	drawMarker(location);
            	
            }
            
                onLocationChanged(location);
                locationManager.requestLocationUpdates(provider, 20000, 0, this);
                
            }   
    }
    
    @Override
    protected void onActivityResult(int req, int res, final Intent data) {
    	if(req == 1001){
    		if(res == RESULT_OK){
    			 
            	super.onActivityResult(req, res, data);
            	new AsyncTask<Void, Void, Void>(){
            		
            		private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            		List<LatLng> latLngs;
            		
            		protected void onPreExecute() {
            			dialog.setMessage("Loading...");
            			dialog.show();
            		};

					
					protected Void doInBackground(Void... params) {
						FindRoute findRoute = new FindRoute();
						latLngs =  findRoute.getRouteData(data.getExtras().getString("origin"), data.getExtras().getString("dest"));
						publishProgress();
						return null;
					}
					
					protected void onProgressUpdate(Void...values) {
						PolylineOptions options = new PolylineOptions();
						options.addAll(latLngs);
						options.width(5).color(Color.BLUE).geodesic(true);
						googleMap.addPolyline(options);
					};
					
					protected void onPostExecute(Void result) {
						dialog.dismiss();
					};
            		
            	}.execute();
    		}    	      	                
    	}else{
        	  if(req == 3){
        		  if(res == RESULT_OK){
    			 
            	super.onActivityResult(req, res, data);
            	new AsyncTask<Void, Void, Void>(){
            		
            		private ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            		Search[] latLngs;
            		
            		protected void onPreExecute() {
            			dialog.setMessage("Please wait...");
            			dialog.show();
            		}

					@Override
					protected Void doInBackground(Void... params) {
						SearchAround find = new SearchAround();
						latLngs =  find.getPlace(Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude()),500,"ATM");
						publishProgress();
						return null;
					}

					
                    protected void onProgressUpdate(Void...values) {
                    	if(latLngs != null){
                    		
    						for(int p=0; p<latLngs.length; p++ ){
    							googleMap.addMarker(new MarkerOptions()
    							        .position(new LatLng(latLngs[p].getLatitude(), latLngs[p].getLongitude()))
    							        .title(latLngs[p].getName()));
    							}
                    	}
                    	
					};		
					protected void onPostExecute(Void result) {
						dialog.dismiss();
					}
					   		
            	}.execute();
    		}
          }
          } 	  
           
	
    	
    		
}
    	    		
    	
   
    	
    	
    
      private void elseif(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void RouteMap(View v){
    	 Intent intentRouteMap=new Intent(this,ShowRoute.class);
    	 startActivityForResult(intentRouteMap, 1001);
//    	 startAcivityForResult(intentRouteMap, 2);
    }
      public void SearchPlace(View v){
     	 Intent intentFindPlace=new Intent(this,FindPlace.class);
     	 startActivityForResult(intentFindPlace, 3);
      }
        
       /* Button routes = (Button) findViewById(R.id.routes);
        routes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	 Intent intent = new Intent(MainActivity.this, ShowRoute.class);
                 startActivityForResult(intent, 2);
            }
       });
*/
        
   
private void drawMarker(Location location){
//googleMap.clear();
//LatLng currentPosition = new LatLng(location.getLatitude(),
//location.getLongitude());
//googleMap.addMarker(new MarkerOptions().position(currentPosition).title("hii, I am here"));
//googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

}
    @Override
    public void  onLocationChanged(Location location) {
 
//         TextView tvLocation = (TextView) findViewById(R.id.tv_location);
 
        // Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
 
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 
        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
 
        // Setting latitude and longitude in the TextView tv_location
       // tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
 
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
/*    public void RouteMap(View view){
    	
    }
*/    

}