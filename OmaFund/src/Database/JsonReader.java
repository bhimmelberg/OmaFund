package Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {
  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static float getLongitude(String addrLine1, String addrLine2) throws IOException, JSONException
  {
	  String urlBegin = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	  String APIKey = "&key=AIzaSyBrxQ6cDHtbLklLr5z25xJxQAOHB0LqRe4";
	  String url = urlBegin + addrLine1.replace(" ", "%20") + "%20" + addrLine2.replace(" ", "%20") + APIKey;
	  JSONObject json = readJsonFromUrl(url);
	  JSONArray results = json.getJSONArray("results");
	  JSONObject addr = results.getJSONObject(0);
	  JSONObject geo = addr.getJSONObject("geometry");
	  JSONObject location = geo.getJSONObject("location");
	  float lng = location.getFloat("lng");
	  return lng;
  }
  
  public static float getLatitude(String addrLine1, String addrLine2) throws IOException, JSONException
  {
	  String urlBegin = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	  String APIKey = "&key=AIzaSyBrxQ6cDHtbLklLr5z25xJxQAOHB0LqRe4";
	  String url = urlBegin + addrLine1.replace(" ", "%20") + "%20" + addrLine2.replace(" ", "%20") + APIKey;
	  JSONObject json = readJsonFromUrl(url);
	  JSONArray results = json.getJSONArray("results");
	  JSONObject addr = results.getJSONObject(0);
	  JSONObject geo = addr.getJSONObject("geometry");
	  JSONObject location = geo.getJSONObject("location");
	  Float lat = location.getFloat("lat");
	  return lat;
  }
  
//  public static void main(String[] args) throws IOException, JSONException {
////    JSONObject json = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyBrxQ6cDHtbLklLr5z25xJxQAOHB0LqRe4");
////    JSONArray results = json.getJSONArray("results");
////    JSONObject addr = results.getJSONObject(0);
////    JSONObject geo = addr.getJSONObject("geometry");
////    JSONObject location = geo.getJSONObject("location");
////    float lng = location.getFloat("lng");
////    Float lat = location.getFloat("lat");
////    System.out.println("longitude: " + lng + "\nlatitude: " + lat);
//	  float lat = getLatitude("1347 South 52nd Ave", "Omaha NE");
//	  System.out.println(lat);
//  }
}