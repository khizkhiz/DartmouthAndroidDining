package com.dartmouth.dining;

import java.util.Locale;




import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*
    Handles the login view
    Requests username and password
*/
public class AbstractedLoginView {
	
	public LoginActivity activity;
	static boolean falsePositive = true;
	
	public AbstractedLoginView (LoginActivity loginActivity){
		activity = loginActivity;
	}
	
	
	public void loadLoginView(){
		
		final Context context = activity.getBaseContext();
		final EditText user = (EditText)activity.findViewById(R.id.usernameTextBox);
		final EditText pass = (EditText)activity.findViewById(R.id.passwordTextBox);
			
		Button button = (Button) activity.findViewById(R.id.loginButton);
		
		activity.setTitle("Please Login");
		
		
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	String email = user.getText().toString().toLowerCase(Locale.ENGLISH);
            	if (!email.contains("@dartmouth.edu")){
            		
    	    		Toast.makeText(context, "Please enter a valid Dartmouth ID",
 						   Toast.LENGTH_LONG).show();
    	    		
    	    		return;
            		
            	}
            	
               
            	Intent messenger = new Intent(context, WebViewActivity.class);
            	messenger.putExtra("user", user.getText().toString());
            	messenger.putExtra("pass", pass.getText().toString());
            	activity.startActivity(messenger);
            	
//            	activity.setUser(user.toString());
//            	activity.setPass(pass.toString());
//        		activity.startActivity(new Intent(context, WebViewActivity.class));
            }
        });
        
      
       
       user.setOnFocusChangeListener(new OnFocusChangeListener() {
    	   @Override
    	   public void onFocusChange(View v, boolean hasFocus) {
    	       if(hasFocus){
    	    	   
    	    	   if(!falsePositive){
    	    		   user.setText("@dartmouth.edu");  
    	    	   }
    	       }
    	      }
    	   });

       falsePositive = false;
       
	}

}
