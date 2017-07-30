package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import maheshwari.developer.android.nirbhaya.HelperClass.DatabaseHelper;
import maheshwari.developer.android.nirbhaya.R;

public class RegisterScreenActivity extends Activity implements View.OnClickListener
{
    EditText firstNameEditText,lastNameEditText,emailEditText,passwordEditText;
    Button registerButton,cancelButton;
    String firstName,lastName,email,password;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String loginPref = "loginKey";
    SharedPreferences.Editor editor;
    DatabaseHelper database;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        try
        {
            database = new DatabaseHelper(RegisterScreenActivity.this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        database.opendatabase();

        firstNameEditText = (EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText)findViewById(R.id.lastNameEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        registerButton = (Button)findViewById(R.id.registerButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.registerButton:
                registerUser();
                break;
            case R.id.cancelButton:
                Intent cancelButtonIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(cancelButtonIntent);
                finish();
        }
    }

    public void registerUser()
    {
        firstName= firstNameEditText.getText().toString().trim();
        lastName= lastNameEditText.getText().toString().trim();
        email= emailEditText.getText().toString().trim();
        password= passwordEditText.getText().toString().trim();
        if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals(""))
        {
            Toast.makeText(RegisterScreenActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            database.getWritableDatabase();
            database.myDataBase.execSQL("INSERT INTO userTable VALUES('" + firstName + "','" + lastName + "','" + email + "','" + password + "');");
            if(sharedpreferences.contains(loginPref))
            {
                Log.w("show", sharedpreferences.contains(loginPref) + "");
                editor = sharedpreferences.edit();
                editor.remove(loginPref);
                editor.putString(loginPref, "subsequent");
                editor.commit();
            }
            else
            {
                Log.w("show",sharedpreferences.contains(loginPref) + "  aaa");
                editor = sharedpreferences.edit();
                editor.putString(loginPref, "first");
                editor.commit();
                Log.w("show",sharedpreferences.contains(loginPref) + "");
            }
            Log.e("Ok", "Ok");
            Toast.makeText(RegisterScreenActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
            database.close();
            Intent i2 = new Intent(getApplicationContext(), ManagingScreenActivity.class);
            startActivity(i2);
            finish();
        }
    }
}