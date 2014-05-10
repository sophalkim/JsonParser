package ssk.project.JSON_Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String x = readTwitterFeed();
        String y = writeJSON();
        TextView tv = (TextView) findViewById(R.id.textview1);
        try {
        	JSONArray jsonArray = new JSONArray(x);
        	Log.i(MainActivity.class.getName(),
        			"Number of entries " + jsonArray.length());
        	for (int i=0; i<jsonArray.length(); i++) {
        		JSONObject jsonObject = jsonArray.getJSONObject(i);
        		y = y + jsonObject;
        	}
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        tv.setText(y);
    }
    
    public String readTwitterFeed() {
    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet("http://twitter.com/users/show/vogella.json");
    	try {
    		HttpResponse response = client.execute(httpGet);
    		StatusLine statusLine = response.getStatusLine();
    		int statusCode = statusLine.getStatusCode();
    		if (statusCode == 200) {
    			HttpEntity entity = response.getEntity();
    			InputStream content = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
    			String line;
    			while ((line = reader.readLine()) !=null) {
    				builder.append(line);
    			}
    		} else { 
    			Log.e(MainActivity.class.toString(), "Failed to download file");
    		}
    	} catch (ClientProtocolException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return builder.toString();
    }
    
    public String writeJSON() {
    	JSONObject object = new JSONObject();
    	try {
    		object.put("name", "Jack Hack");
    		object.put("score", new Integer(200));
    	} catch (JSONException e) {
    		e.printStackTrace();
    	}
    	return object.toString();
    }
}