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

public class ChangePasswordActivity extends Activity implements View.OnClickListener
{
    EditText oldPasswordEditText,newPasswordEditText,confirmNewPasswordEditText;
    Button changeButton,cancelButton;
    String MyPREFERENCES = "MyPrefs" ;
    String emailPref = "nameKey";
    String passwordPref = "passwordKey";
    String loginPref = "loginKey";
    SharedPreferences sharedpreferences;
    String passwordGetString = "";
    DatabaseHelper database;
    Cursor resultSet;
    String oldPassword, newPassword, confirmNewPassword;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sharedpreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try
        {
            database = new DatabaseHelper(ChangePasswordActivity.this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        database.opendatabase();
        oldPasswordEditText = (EditText)findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = (EditText)findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = (EditText)findViewById(R.id.confirmNewPasswordEditText);
        changeButton = (Button)findViewById(R.id.changeButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        changeButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.changeButton:
                changePassword();
                break;
            case R.id.cancelButton:
                Intent forgotPasswordIntent = new Intent(getApplicationContext(),ManagingScreenActivity.class);
                startActivity(forgotPasswordIntent);
                finish();
                break;
        }
    }

    private void changePassword()
    {
        oldPassword = oldPasswordEditText.getText().toString().trim();
        newPassword = newPasswordEditText.getText().toString().trim();
        confirmNewPassword = confirmNewPasswordEditText.getText().toString().trim();
        System.out.println("Credentials: " +oldPassword+"--"+newPassword+"--"+confirmNewPassword);
        if (sharedpreferences.contains(emailPref))
        {
            if(sharedpreferences.contains(passwordPref))
            {
                System.out.println("changePassword : " + sharedpreferences.getString(emailPref, new String()) + "--" + sharedpreferences.getString(passwordPref, new String()));
                database.getReadableDatabase();
                resultSet = database.myDataBase.rawQuery("Select email,password from userTable where email='"+sharedpreferences.getString(emailPref, new String())+"' and password='"+sharedpreferences.getString(passwordPref, new String())+"'",null);
                resultSet.moveToFirst();
                if(resultSet.getCount() > 0)
                {
                    String p = resultSet.getString(1);
                    if(oldPassword.equals(p))
                    {
                            if(newPassword.equals(confirmNewPassword))
                            {
                                database.getWritableDatabase();
                                resultSet = database.myDataBase.rawQuery("Update userTable SET password='"+newPassword+"' where email='"+sharedpreferences.getString(emailPref, new String())+"'" ,null);
                                resultSet.moveToFirst();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.remove(passwordPref);
                                editor.putString(passwordPref, newPassword);
                                editor.remove(loginPref);
                                editor.putString(loginPref, "subsequent");
                                editor.commit();
                                database.close();
                                Log.w("passwordPref", sharedpreferences.getString(loginPref, "login")+"");
                                Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(ChangePasswordActivity.this,LoginScreenActivity.class);
                                startActivity(in);
                                finish();
                            }
                            else
                            {
                                confirmNewPasswordEditText.setError("Confirm Password should be same as New Password");
                            }
                        }
                    }
                    else
                    {
                        oldPasswordEditText.setError("Invalid Old Password");
                    }
                }
            }
            else
            {
                Log.w("P",passwordGetString);
            }
        }
}