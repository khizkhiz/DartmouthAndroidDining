package com.dartmouth.dining;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

/*
 I hate how verbose it is to read files in Java. 
*/

public class Utilities {
	
	public static String read_file(Context context){
		String s = "";
		try {
		    BufferedReader reader = new BufferedReader(
		        new InputStreamReader(context.getAssets().open("test.html")));

		    // do reading, usually loop until end of file reading  
		    String mLine = reader.readLine();
		    while (mLine != null) {
		       mLine = reader.readLine(); 
		       s += mLine;
		    }

		    reader.close();
		} catch (IOException e) {
		    assert true;
		}
		
		return s;
	}

}
