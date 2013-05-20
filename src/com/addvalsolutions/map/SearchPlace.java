/*package com.addvalsolutions.map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


public class SearchPlace 
{
	String path;
	private String origin;
	private String destination;
	private String temp = null;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private Object mMap;
	public SearchPlace(Context context,String org, String dest)
	{
		this.origin = org;
		this.destination = dest;
		jsonArray = new JSONArray();
		jsonObject = new JSONObject();
	
	}
		
	public void getRouteData()
	{
		try
		{
			path = "http://unscrewed-beta.addvalsolutions.com/mobile/nearby";
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(new HttpGet(path));
			jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
	       jsonArray = jsonObject.getJSONArray("results");
	       Log.d("json", jsonObject.toString());
	       int len = jsonArray.length();
	       MainActivity.retailers = new Retailer[len];
	       for(int i=0; i < len; i++)
	       {
	    	   JSONObject object = jsonArray.getJSONObject(i);
	    	   Retailer ret = new Retailer();
	    	   ret.setVenue_id(object.getInt("venue_id"));
	    	   ret.setTitle(object.getString("title"));
	    	   ret.setAddress(object.getString("address"));
	    	   ret.setCategory(object.getString("category"));
	    	   ret.setDistance(Float.valueOf(Double.toString(object.getDouble("distance"))));
	    	   ret.setLat(object.getString("lat"));
	    	   ret.setLng(object.getString("long"));
	    	   MainActivity.retailers[i] = ret;
	    	   
           }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			MainActivity.searchResponse = "Network error";
		}
	}
	
	
}	*/