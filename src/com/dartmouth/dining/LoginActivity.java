package com.dartmouth.dining;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/*

Startup screen and login form

*/
public class LoginActivity extends Activity {
	
	static boolean failedLogin = false;
	static boolean noInternet = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final DatabaseIO db = new DatabaseIO(this);
		
		if (db.doRecordsExist() && db.doRecordsExist()){
			
			Intent intent = new Intent(this, WebViewActivity.class);
			startActivity(intent);
			return;
		} 
		
		
  	  	AbstractedLoginView abstractedLoginView = new AbstractedLoginView(this);
  	  	abstractedLoginView.loadLoginView();
  	  
	}

	@Override
	protected void onStart() {
	    super.onStart();  // Always call the superclass method first
	   
	    
	    	if(failedLogin){
	    		
	    		failedLogin = false;
	    		
	    		Toast.makeText(getApplicationContext(), "Invalid Username or Password =)",
						   Toast.LENGTH_LONG).show();
	    	}
	    	
	    	if(noInternet){
	    		noInternet = false;
	    		Toast.makeText(getApplicationContext(), "Check your internet connection",
						   Toast.LENGTH_LONG).show();
	    	}
	}
	
	
	@Override
	protected void onResume() {
	    super.onResume();  
	    
    	if(failedLogin){
    		
    		failedLogin = false;
    		
    		Toast.makeText(getApplicationContext(), "Invalid Username or Password =)",
					   Toast.LENGTH_LONG).show();
    	}
    	
    	if(noInternet){
    		noInternet = false;
    		Toast.makeText(getApplicationContext(), "Check your internet connection",
					   Toast.LENGTH_LONG).show();
    	}
	    
	}

}
