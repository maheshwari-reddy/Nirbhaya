package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import maheshwari.developer.android.nirbhaya.HelperClass.DatabaseHelper;
import maheshwari.developer.android.nirbhaya.R;

public class LoginScreenActivity extends Activity implements View.OnClickListener
{
    EditText emailEditText,passwordEditText;
    Button loginButton,forgotPasswordButton,cancelButton;
    String email = "", password = "";
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String emailPref = "nameKey";
    String passwordPref = "passwordKey";
    String loginPref = "loginKey";
    SharedPreferences.Editor editor;
    DatabaseHelper database;
    Cursor resultSet;
    String table = "userTable";
    boolean exist;
    String login;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        try
        {
            database = new DatabaseHelper(LoginScreenActivity.this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        forgotPasswordButton = (Button)findViewById(R.id.forgotPasswordButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        loginButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.loginButton:
                login();
                break;
            case R.id.forgotPasswordButton:
                Intent forgotPasswordIntent = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
                finish();
                break;
            case R.id.cancelButton:
                Intent cancelIntent = new Intent(getApplicationContext(),HomeScreenActivity.class);
                startActivity(cancelIntent);
                finish();
        }
    }

    public void login()
    {
        database.opendatabase();
        database.getReadableDatabase();
        Cursor cursor = database.myDataBase.rawQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name = '" + table + "'", null);
        if (cursor != null)
        {
            if (cursor.getCount() > 0)
            {
                exist = true;
                Log.w("Switch", "" + " = " + cursor.getColumnCount());
                cursor.close();
            }
            else
            {
                exist = false;
            }
            cursor.close();
        }
        else
        {
            exist = false;
        }
        Log.w("Switch", "" + " = " + exist);
        if (exist)
        {
            email = emailEditText.getText().toString().trim();
            password = passwordEditText.getText().toString().trim();
            Log.w("Credentials", "" + " = " + email + "  " + password);
            resultSet = database.myDataBase.rawQuery("Select email,password from userTable where email='" + email + "' and password='" + password + "'", null);
            resultSet.moveToFirst();
            Log.w("SwitchCount", "" + " = " + resultSet.getCount());
            if (resultSet.getCount() > 0)
            {
                editor = sharedpreferences.edit();
                editor.putString(emailPref, email);
                editor.putString(passwordPref, password);
                editor.commit();
                Log.w("Switch", "" + " = " + sharedpreferences.contains(loginPref));
                if (sharedpreferences.contains(loginPref))
                {
                    login = sharedpreferences.getString(loginPref, "login");
                    Log.e("login is :", login);
                    if ((login.equalsIgnoreCase("first")))
                    {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(LoginScreenActivity.this, ManagingScreenActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else if ((login.equalsIgnoreCase("subsequent")))
                    {
                        Intent in = new Intent(LoginScreenActivity.this, ManagingScreenActivity.class);
                        in.putExtra("tab", 1);
                        startActivity(in);
                        finish();
                    }
                }
            }
            else
            {
                if(email.equals("") || password.equals(""))
                {
                    Toast.makeText(LoginScreenActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
            }

            database.close();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Register first", Toast.LENGTH_LONG).show();
        }
    }
}
