package com.dartmouth.dining;

import java.util.HashMap;
import java.util.Set;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*

	This class is in charge of loading webviews,
	injecting into webivews and parsing webviews

*/

@SuppressLint("DefaultLocale")
public class AbstractedWebView {
	
	public WebViewActivity activity = null; 
	public HashMap<String, Double> landing = new HashMap<String, Double>();
	
	
	static boolean finished_parse = false;
	public boolean false_positive = false;
	
	
	public static String title = "";
	public static String value = "";
	int loadCount = 0;
	
	public AbstractedWebView(WebViewActivity n){
		
		activity = n;
	}
	

	
	@SuppressLint("SetJavaScriptEnabled")
	public void loadWebviewActivity(Intent i){
		
		final WebView webView = (WebView) activity.findViewById(R.id.webView2);
		
		webView.setVisibility(View.INVISIBLE);
		final Context context = activity.getBaseContext();
		final DatabaseIO db = new DatabaseIO(context);


		String credentials = "";
		
		activity.setTitle("Welcome!");
		
		String u = "";
		String p = "";
		
		if (db.doRecordsExist() && db.doRecordsExist()){
			credentials = db.readFirstRow();
			u = credentials.split(" ")[0];
			p = credentials.split(" ")[1];
		} else{		
			u = i.getStringExtra("user");
			p = i.getStringExtra("pass");
		}
		
		final String user = u;
		final String pass = p;
		
		Log.i("saisi", "just before settings");
				
		SharedPreferences settings = context.getSharedPreferences("DartKid", 0);
		if (settings != null){
				//first time login
				// load default
			
				Log.i("saisi", "outside da club");
				if (loadCount == 0){
					WebView w = (WebView) activity.findViewById(R.id.webView1);
					Log.i("saisi", "in da club");
					Log.i("saisi", "loadCount " + Integer.toString(loadCount)); 
					
					if (!(WebViewActivity.setContent(context, w))){
						for(int a = 0; a < 10; a++){
							Log.i("saisi", "fuck");
						}
						
					} 
					loadCount++;
					
					
					//return;
				} else{
					//  later
					Log.i("saisi", "can't get in da club");
				}	
		}
	
		
		Log.i("saisi", "after settings");


		webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadsImagesAutomatically(false);
		webView.getSettings().setBlockNetworkImage(true);
		webView.addJavascriptInterface(new jsInterface(this), "HTMLOUT");
		
//        String postData = "user=" + user + "&pwd=" + pass;
//        Log.i("Saisi post", postData);      
//        webView.postUrl(url, EncodingUtils.getBytes(postData, "base64"));

		
		count = 0;
		loadAndLogin(user, pass, webView);
	}
	
	int count = 0;
	
	@SuppressLint("SetJavaScriptEnabled")
	public void loadAndLogin(final String user, final String pass, final WebView webView){
		webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setLoadsImagesAutomatically(false);
		webView.getSettings().setBlockNetworkImage(true);
		
		

		
		webView.setWebViewClient(new WebViewClient() {
		    @SuppressLint("DefaultLocale")
			public void onPageFinished(WebView view, String url) {
		    	
		    	
	        	String webUrl = webView.getUrl().toLowerCase();
	        	
	        	
	        	if (webUrl.contains("err=")){
	        		LoginActivity.failedLogin = true;
	                activity.finish();
	        		return;
	        	} else if(webUrl.contains("about")){
	        		LoginActivity.noInternet = true;
	        		activity.finish();
	        		return;
	        	}
		    	

				webView.setWebViewClient(new WebViewClient() {
				    public void onPageFinished(WebView view, String url) {
				    	
			        	String webUrl = webView.getUrl().toLowerCase();
			        	
			        	
			        	if (webUrl.contains("err=")){
			        		LoginActivity.failedLogin = true;
			                activity.finish();
			        		return;
			        	} else if(webUrl.contains("about")){
			        		LoginActivity.noInternet = true;
			        		activity.finish();
			        		return;
			        	}
				    	
				    	
				    	 Log.i("saisi", "starting parse");
				    	parseWebpageData(user, pass, webView);
				    	Log.i("saisi", "done parse");
				    }});
					

		    		
	            webView.loadUrl(
		                "javascript:document.getElementById('txtUserName').value = '" + user + "';"+
		                "javascript:document.getElementById('txtPassword').value = '" + pass + "';"+
		                "javascript:document.getElementsByTagName('input')[2].click();"
		         ); 
		            
		    }
		});
		String url = "https://dartmouth.managemyid.com/student/login.php";
		 webView.loadUrl(url);
		 
		 
	}
	
	public void parseWebpageData(final String user, final String pass, final WebView webView){
		webView.addJavascriptInterface(new jsInterface(this), "HTMLOUT");
		webView.evaluateJavascript("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
            	
        		final Context context = activity.getBaseContext();
        		final DatabaseIO db = new DatabaseIO(context);
            	
            	Log.i("saisi", "done evaluating javascript");
            	
	    		if (landing == null){
	    			Log.i("saisi","Problem getting Dictionary from landing");
	    			return;
	    		}

	    		Log.i("saisi", "still here");
	    		
	    		if ((!db.doRecordsExist())) {
	    			db.putRecord(user, pass);
	    		}


    			SharedPreferences settings = context.getSharedPreferences("DartKid", 0);
    			SharedPreferences.Editor editor = settings.edit();

    			
    			Set<String> setsOfKeys = landing.keySet();
    			Log.i("saisi size", Integer.toString(setsOfKeys.size()));
    			
	    		for(String key: setsOfKeys){	
	    			
	    			
	    			Double val = landing.get(key);
	    			String value = "$" + Double.toString(val);
	    			
	    			
	    			
	    			if (key.contains("fine")){
			    	    editor.putString("Fines", value);
	    			} else if (key.contains("discre")){
			    	    editor.putString("Discretionary",value);
	    			}else if (key.contains("chargi")){
			    	    editor.putString("DDS Charges",value);
	    			}else if (key.contains("choice")){
	    				value = value.replace("$", "");
			    	    editor.putString("Swipes",value);
	    			}else if (key.contains("dining")){
			    	    editor.putString("Dining DBA",value);
	    			}else if (key.trim().equals("dba")){
			    	    editor.putString("DBA",value);
	    			}
	  
	    		}
	    		
	    		editor.commit();
	    		
	    		WebView w = (WebView) activity.findViewById(R.id.webView1);
	    		WebViewActivity.setContent(context, w);
	    		Log.i("saisi", "DONE EVERYTHING. EXIT SCENE");

            }
        });
	}
	

}
