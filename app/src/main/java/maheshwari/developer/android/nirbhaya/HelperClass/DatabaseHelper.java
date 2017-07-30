package maheshwari.developer.android.nirbhaya.HelperClass;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper 
{
	private Context mycontext;
	   String DB_PATH=null;
	  
	   private static String DB_NAME = "nirbhayaDb.sqlite"; //the extension may be .sqlite or .db
	   public SQLiteDatabase myDataBase;
	   
	   @SuppressLint("SdCardPath")
	public DatabaseHelper(Context context) throws IOException 
	   {
		   super(context, DB_NAME, null,1);
		   mycontext=context;

	       DB_PATH = "/data/data/" + mycontext.getApplicationContext().getPackageName()+ "/databases/";
	       boolean dbexist = checkdatabase();
	       if (!dbexist) 
	       {
	        	System.out.println("Database doesn't exist");
	            createdatabase();
	            opendatabase(); 
	       }
           else
           {
	        	opendatabase();  
	       }
	}

	 public void createdatabase() throws IOException 
	 {
	     boolean dbexist = checkdatabase();
	        if(dbexist)
            {
	            System.out.println(" Database exists.");
	        }
            else
            {
	            this.getReadableDatabase();
	            try
                {
	                copydatabase();
	            }
                catch(IOException e)
                {
	                throw new Error("Error copying database");
	            }
	        }
	    }   

	    private boolean checkdatabase()
        {
	        boolean checkdb = false;
	        try
            {
	            String myPath = DB_PATH + DB_NAME;
	            File dbfile = new File(myPath);
	            checkdb = dbfile.exists();
	        }
            catch(SQLiteException e)
            {
	            System.out.println("Database doesn't exist");
	        }
	        return checkdb;
	    }

  public boolean checktable(String st) {
	boolean b=false;
	try
    {
		String s1;
		Cursor c;
		c=myDataBase.rawQuery("select name from sqlite_master where type='table' and name='"+st+"'",null);
		if(c.moveToNext())
		{
			s1=c.getString(0);
			if(s1=="")
			{
				b=false;
			}
			else
				b=true;
		}
	}
    catch (Exception e)
    {
		e.printStackTrace();
	}
	return b;
}
	    private void copydatabase() throws IOException
        {
	        InputStream myinput = mycontext.getAssets().open(DB_NAME);
	        String outfilename = DB_PATH + DB_NAME;
	        OutputStream myoutput = new FileOutputStream(outfilename);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = myinput.read(buffer))>0)
            {
	            myoutput.write(buffer,0,length);
	        }
	        myoutput.flush();
	        myoutput.close();
	        myinput.close();
	    }

	    public void opendatabase() throws SQLException
        {
	        String mypath = DB_PATH + DB_NAME;
	        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	    }

	    public synchronized void close()
        {
	        if(myDataBase != null)
            {
	            myDataBase.close();
	        }
	        super.close();
	    }

	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{

	}

	public void onCreate(SQLiteDatabase arg0)
    {
		
	}

}
