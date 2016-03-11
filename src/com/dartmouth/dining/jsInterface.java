package com.dartmouth.dining;


import java.util.HashMap;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import android.util.Log;
import android.webkit.JavascriptInterface;


/*

Provides the js interface for webview modification 
*/
class jsInterface{
	
	AbstractedWebView absView = null;

	
	public jsInterface(AbstractedWebView abstractedWebView) {
		absView = abstractedWebView;
	}


	@JavascriptInterface
    public void processHTML(String html){
		absView.landing = null;
        
		Document doc = Jsoup.parse(html);
		String text = doc.body().text();
	
	
		if (text.toLowerCase(Locale.ENGLISH).contains("invalid username or password")){
			absView.landing.put("Invalid Username or Password", 2.0);
			Log.i("saisi", "wrong pass bro");
			return;
		}
		
		HashMap<String, Double> s = parseLanding(text);
		
		Log.i("saisi", "jsinterface");
		
		
//		for(String key : s.keySet()){
//			Log.i("Saisi keeeuuuuy", key);
//		}
//		
//		Log.i("saisi size of key set", Integer.toString(s.keySet().size()));
		
		absView.landing = s;
		
	
		AbstractedWebView.finished_parse = true;
	
    }
	
	
public static HashMap<String, Double> parseLanding(String text){
		
		HashMap<String, Double> dictionary = new HashMap<String, Double>();
		String s = text.toLowerCase(Locale.ENGLISH).trim();
		

		s = s.replaceAll("\\s+", "\n");
		s = s.replace("account\nbalance", "__delimiter__");
		s = s.replace("$", "");
		String[] array = s.split("\n");
		
		boolean enabled = false;

		String accumulate = "";
		
		for (String item: array){
			
			String current = item.trim();
			
			if(current.contains("__delimiter__")){
				enabled = true;
				continue;
			} else if(current.contains("click")){
				break;	
			} 
			
			
			if(enabled){
				try{
					Double a = Double.parseDouble(current);
					dictionary.put(accumulate, a);
					accumulate = "";
					
				}
				catch (Exception e){
					accumulate += " " + current;
				}	
			}	
		}

		return dictionary;
		
	}

}