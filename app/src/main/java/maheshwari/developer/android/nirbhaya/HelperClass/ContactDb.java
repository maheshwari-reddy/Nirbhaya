package maheshwari.developer.android.nirbhaya.HelperClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDb extends SQLiteOpenHelper
{

	public ContactDb(Context context) 
	{
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	private static final String DB_NAME= "contact_db";
	private static final int DB_VERSION= 1;
	private static final String DB_TABLE= "contacts";
	
	private static final String COL_NAME= "u_name";
	private static final String COL_NUMBER= "u_number";
	private static final String ROW_ID= "row_id";
	
	String CREATE_TABLE= "create table contacts(row_id integer primary key autoincrement, u_name text not null, u_number text not null)";
	SQLiteDatabase mDb;
	
	public void init(){
		mDb= this.getWritableDatabase();
	}
	
	public void uninit(){
		if(mDb.isOpen()){
			mDb.close();
		}
	}
	
	public void insertValues(String name, String number){
		ContentValues cv= new ContentValues();
		cv.put("u_name", name);
		cv.put("u_number", number);
		mDb.insert(DB_TABLE, null, cv);
	}
	
	public Cursor getValue(){
		return mDb.rawQuery("select * from contacts", null);
	}
	
	public void deleteAll(){
		mDb.delete(DB_TABLE, null, null);
	}
	
	public void deleteOne(String row){
		mDb.delete(DB_TABLE, row+"="+ROW_ID, null);
	}
	
	public void updateRecord(String row, String name, String number){
		ContentValues cv= new ContentValues();
		cv.put("u_name", name);
		cv.put("u_number", number);
		
		mDb.update(DB_TABLE, cv, row+"="+ROW_ID, null);
	}

}
