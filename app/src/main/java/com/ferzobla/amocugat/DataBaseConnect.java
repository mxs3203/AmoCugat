package com.ferzobla.amocugat;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBaseConnect 
{
	private static final String DATABASE_NAME = "lists";
	private static SQLiteDatabase myData; 				
	private static Helper databaseOpenHelper;
	private String tableName;

	public void setHelper(Context context){
		databaseOpenHelper = new Helper(context, DATABASE_NAME, null, 1);
	}

	public boolean checkTableName(String tableName){
		Cursor cursor = myData.rawQuery("select DISTINCT tbl_name from lists where tbl_name = '"+tableName+"'", null);
		if(cursor!=null) {
			if(cursor.getCount()>0) {
				cursor.close();
				return true;
			}
			cursor.close();
		}
		return false;
	}
	
	public static void open() throws SQLException
	{
		
		myData = databaseOpenHelper.getWritableDatabase();
	} 
	
	public static void close() 
	{
		if(myData != null)
		{	
			myData.close();
		}
	} 
	
	public static String createTable(String name){
		return "CREATE TABLE " + name  + "(_id integer primary key autoincrement,cardNum integer, pravilo TEXT);";
	}
	
	public static void insertCard(String name, int cardNum, String pravilo)
	{
		ContentValues newCard = new ContentValues();
		newCard.put("cardNum", cardNum);
		newCard.put("pravilo", pravilo);
		
		open();
		myData.insert(name, null, newCard);
		close(); 
	} 
	
	public static Cursor getAllCards(String name)
	{
		return myData.query(name, new String[] { "_id","cardNum","pravilo"}, null,null, null, null, "cardNum ASC");
	} 
	
	public static Map<String,String> getAllValues(String name)
	{
	    Map<String,String> values = new HashMap<String,String>();
	    Cursor cursor = myData.query(name, new String[] { "_id","cardNum","pravilo"}, null, null, null, null, "cardNum ASC");
	    if (cursor.moveToFirst())
	    {
	        do
	        {
	            values.put(cursor.getString(1),cursor.getString(2));
	        }
	        while (cursor.moveToNext());
	    }
	    if (cursor != null && !cursor.isClosed()) 
	    {
	        cursor.close();
	    }

	    return values;
	}

	public static void deleteAll()
	{
		open();
		myData.delete("list" , null, null);
		close();
	}
	
	public void deleteDeck(String name, long id)
	{
		open(); // open the database
		myData.delete(name, "_id=" + id , null);
		close(); // close the databaseZ
	}
	
	public int getSize()
	{
		return databaseOpenHelper.getSize(myData, tableName);
	}
	
	private class Helper extends SQLiteOpenHelper 
	{
		
		public Helper(Context context, String name, CursorFactory factory, int version)
		{
			super(context, name, factory, version);
		} 
		// creates the contacts table when the database is created
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String query = createTable(tableName);
			db.execSQL(query); 
		} 
		
		public int getSize(SQLiteDatabase d, String tableName)
		{
			String cnt = "Select * from " + tableName;
			d = this.getReadableDatabase();
			Cursor c = d.rawQuery(cnt, null);
			return c.getCount();
		}
		
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			
		} 
	}

}
