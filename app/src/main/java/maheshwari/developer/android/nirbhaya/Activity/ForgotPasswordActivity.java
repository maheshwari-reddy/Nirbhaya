package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import maheshwari.developer.android.nirbhaya.HelperClass.DatabaseHelper;
import maheshwari.developer.android.nirbhaya.R;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener
{
    EditText emailEditText;
    Button getPasswordButton,cancelButton;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "MyPrefs" ;
    String loginPref = "loginKey";
    SharedPreferences.Editor editor;
    DatabaseHelper database;
    Cursor resultSet;
    private static final String NUM  = "0123456789";
    String email;
    int noOfDigits = 8;
    int len = 8;
    static Pattern emailNamePtrn;
    static boolean failed, exist;
    AlertDialog alert;
    String temp_pass;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        getPasswordButton = (Button)findViewById(R.id.getPasswordButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        getPasswordButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        try
        {
            database = new DatabaseHelper(ForgotPasswordActivity.this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        emailNamePtrn = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.getPasswordButton:
                generatePassword();
                break;
            case R.id.cancelButton:
                Intent forgotPasswordIntent = new Intent(getApplicationContext(),LoginScreenActivity.class);
                startActivity(forgotPasswordIntent);
                finish();
                break;
        }
    }

    private void generatePassword()
    {
        email = emailEditText.getText().toString();
        boolean check = validateEmailAddress(email);
        Log.w("forgot", check + "  c");
        if (check)
        {
            database.opendatabase();
            database.getReadableDatabase();
            resultSet = database.myDataBase.rawQuery("SELECT * FROM userTable WHERE Email='"+email+"'", null);
            resultSet.moveToFirst();
            if (resultSet.getCount() > 0)
            {
                exist = true;
                Log.w("forgot", resultSet.getCount() + " " + resultSet.getString(0));
            }
            else
            {
                exist = false;
            }
            database.close();
            if (!exist)
            {
                alert = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
                alert.setMessage("This email address is not registered.\nPlease enter registered email address.");
                alert.setButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.e("Ok clicked", "okk..");
                    }
                });
                alert.show();
            }
            else
            {
                char[] pswd = generatePswd(len, noOfDigits);
                temp_pass = new String(pswd);
                String array[] = {email, temp_pass};
                new MyTask().execute(array);
            }
        }
        else
        {
            emailEditText.setError("Please enter a valid Email Address");
        }
    }
    private class MyTask extends AsyncTask<String , Void, Void >
    {
        private Exception exception;

        protected Void doInBackground(String... params)
        {
            sendEmail(params);
            return null;
        }
        protected void onPostExecute(Void feed)
        {
            Log.w("flag", failed + " flag");
            if(failed)
            {
                Intent i2 = new Intent(getApplicationContext(), WrongEmailActivity.class);
                startActivity(i2);
                finish();
            }
            else
            {
                database.opendatabase();
                database.getWritableDatabase();
                resultSet = database.myDataBase.rawQuery("Update userTable SET password='"+temp_pass+"' where email='"+email+"'" ,null);
                resultSet.moveToFirst();
                if(sharedpreferences.contains(loginPref))
                {
                    editor = sharedpreferences.edit();
                    editor.remove(loginPref);
                    editor.putString(loginPref, "first");
                    editor.commit();
                }
                database.close();
                Intent i2 = new Intent(getApplicationContext(), RecoverPasswordActivity.class);
                startActivity(i2);
                finish();
            }
        }
    }
    public static char[] generatePswd(int len, int noOfDigits)
    {
        Random rnd = new Random();
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfDigits; i++)
        {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        return pswd;
    }
    private static int getNextIndex(Random rnd, int len, char[] pswd)
    {
        int index = rnd.nextInt(len);
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }
    public static void sendEmail(String... params)
    {
        String result;
        // Recipient's email ID needs to be mentioned.
        String to = params[0];
        String password = params[1];
        // Sender's email ID needs to be mentioned
        String from = "mahi.reddy1612@yahoo.com";
        // Get system properties object
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("mahi.reddy1612@yahoo.com", "(102p1a0550)leo");
            }
        });
        try
        {
            // Instantiatee a message
            Message msg = new MimeMessage(session);
            //Set message attributes
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Welcome to Nirbhaya : Be Fearless");
            msg.setSentDate(new Date());
            msg.setContent("<p style=\"font-family: \"Times New Roman\", Times, serif;\">Welcome to Nirbhaya : Be Fearless , an android emergency application." +
                    "<br/>This is a temporary password for your registered email account." +
                    "<br/>Temporary Password: <span style=\"font-family: Courier New, Courier, monospace;color:#000000;font-weight:bold;\">"+ password +"</span>" +
                    "<br/>Please Login again with this temporary password on the login page.</p>","text/html");
            System.out.println("temp_pass : " +password);
            //Send the message
            try
            {
                Transport.send(msg);
                failed = false;
            }
            catch (SendFailedException e)
            {
                failed = true;
                System.out.println("SendFailedException : " +e);
            }
        }
        catch (MessagingException mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
    }
    public static boolean validateEmailAddress(String emailString)
    {
        try
        {
            InternetAddress emailAddr = new InternetAddress(emailString);
            emailAddr.validate();
            return true;
        }
        catch (AddressException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}