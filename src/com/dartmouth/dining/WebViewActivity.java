package com.dartmouth.dining;


import java.util.Map;



import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;


/*

This class allows us to have different webview entities
for logging in and display of consumer DBA and swipes
*/
public class WebViewActivity extends Activity {

	
	
	
	WebView w; 
	WebView w2;
	public static String bullet = "nothing";
	public static String raw_file = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		
	    traditional();
	      
	}


	
	@SuppressLint("SetJavaScriptEnabled")
	public void traditional(){
		
		
		setContentView(R.layout.custom_webview);
		final WebView webView = (WebView) findViewById(R.id.webView2);
		webView.setVisibility(View.INVISIBLE);
		
      
//        WebView w = (WebView) findViewById(R.id.webView1);  
//        w.loadUrl("file:///android_asset/load.html");
		
		AbstractedWebView a = new AbstractedWebView(this);
		a.loadWebviewActivity(getIntent());

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	// TODO Auto-generated method stub
    	switch (item.getItemId()) {
		case R.id.action_refresh:
			Toast.makeText(this, "You selected the refresh", Toast.LENGTH_SHORT).show();
			//test();
			break;
		case R.id.action_settings:
			Toast.makeText(this, "asdfdasf", Toast.LENGTH_SHORT).show();

			break;
		case R.id.action_logout:
			Toast.makeText(this, "You selected the logout option", Toast.LENGTH_SHORT).show();
			

			
			break;
		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
    



    public static boolean setContent(Context c, WebView w){
	
	SharedPreferences s = c.getSharedPreferences("DartKid", 0);
	if (s == null || s.getAll().size() == 0){
		Log.i("saisi", "lmao nothing");
		return false;
	}
	
	if(raw_file == null){raw_file = Utilities.read_file(c);}
	String content_to_modify = raw_file;
	
	
	w.getSettings().setJavaScriptEnabled(true);
	w.getSettings().setLoadsImagesAutomatically(true);
	w.getSettings().setBlockNetworkImage(false);
		
		Map<String,?> keys = s.getAll();
	


		for(Map.Entry<String,?> entry : keys.entrySet()) {
		
			
			String key = entry.getKey();
			String value = entry.getValue().toString();
			String key_l = key.toLowerCase();

			

			
    	    if (key_l.contains("fines")){
    	    	content_to_modify = content_to_modify.replace("nne", value); 
    	    }
    	    
    	    else if (key_l.contains("discretionary")){
    	    	content_to_modify = content_to_modify.replace("sita", value); 
    	    }
    	    else if (key_l.contains("dds")){
    	    	
    	    	content_to_modify = content_to_modify.replace("tano", value); 
    	    }
    	    else if (key_l.equals("dba")){
    	    	content_to_modify = content_to_modify.replace("tatu", value); 
    	    }	    	    
    	    else if (key_l.contains("dining")){
    			content_to_modify = content_to_modify.replace("moja", value); 
    	    }
    	    else if (key_l.contains("swipes")){
    	    	content_to_modify = content_to_modify.replace("mbili", value); 
    	    }	    	    

		}
		
		
		String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"hi.css\" />" + content_to_modify;
    	
		htmlData = htmlData.replace("null", "");
		
    	w.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
    	
    	
    	
		return true;
		
	} 
	}
