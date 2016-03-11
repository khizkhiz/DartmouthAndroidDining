package com.dartmouth.dining;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/*

This class handles all database connections

*/

public class DatabaseIO extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBA.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String TABLE_NAME = "storage";
    public static final String _ID = "ID";
    public static final String COLUMN_USER = "USER";
    public static final String COLUMN_PASS = "PASS";
    
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + TABLE_NAME + " (" +
        _ID + " INTEGER PRIMARY KEY," +
        COLUMN_USER + TEXT_TYPE + COMMA_SEP +
       COLUMN_PASS + TEXT_TYPE +
        " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseIO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
  
 
    public boolean doRecordsExist(){
    	    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	String sql = "SELECT count(*) FROM " + TABLE_NAME;
    	
    	
    	SQLiteStatement statement = db.compileStatement(sql);
    	long count = statement.simpleQueryForLong();

    	
    	if (count > 0)
    		return true;
    	else
    		return false;
 	
    }
    
    
    public void putRecord(String a, String b){
    	
		   SQLiteDatabase db = this.getWritableDatabase();
		   String sql = "INSERT INTO " + DatabaseIO.TABLE_NAME + "(" + DatabaseIO._ID  + ", " + DatabaseIO.COLUMN_USER + "," + DatabaseIO.COLUMN_PASS + ") VALUES( 1, '" + a + "', '" + b + "');";
		   db.execSQL(sql);
		   db.close();
    	
    }
    
    
    public String readFirstRow(){
    	
    	SQLiteDatabase db = this.getWritableDatabase(); 
    	Cursor  cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    	
    		
    	if (cursor == null){
    		Log.i("Databae", "shit son");
    		db.close();
    		return null;
    	}
   
    	
    	if (cursor.moveToFirst()) {
    		String user = cursor.getString( cursor.getColumnIndex(COLUMN_USER) );
    		String pass = cursor.getString( cursor.getColumnIndex(COLUMN_PASS) );
    		db.close();
    		return user + " " + pass;
    	 } else{
    		 Log.i("Database","Shit son");
    		 db.close();
    		  return null;
    	 }
    	
    	
    }
    
    
    public void removeTable(){
    	SQLiteDatabase db = this.getWritableDatabase(); 
    	db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    
    
}