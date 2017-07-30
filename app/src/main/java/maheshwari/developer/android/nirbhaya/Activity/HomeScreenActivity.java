package maheshwari.developer.android.nirbhaya.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import maheshwari.developer.android.nirbhaya.R;


public class HomeScreenActivity extends ActionBarActivity implements View.OnClickListener
{
    Button registerButton,instructionsButton,loginButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        registerButton = (Button)findViewById(R.id.registerButton);
        instructionsButton = (Button)findViewById(R.id.instructionsButton);
        loginButton = (Button)findViewById(R.id.loginButton);

        registerButton.setOnClickListener(this);
        instructionsButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.registerButton:
                Intent registerIntent = new Intent(getApplicationContext(),RegisterScreenActivity.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.instructionsButton:
                Intent instructionsIntent = new Intent(getApplicationContext(),InstructionScreenActivity.class);
                startActivity(instructionsIntent);
                finish();
                break;
            case R.id.loginButton:
                Intent loginIntent = new Intent(getApplicationContext(),LoginScreenActivity.class);
                startActivity(loginIntent);
                finish();
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.about_us)
        {
            AlertDialog.Builder b= new AlertDialog.Builder(this);
            b.setTitle("Welcome !");
            b.setMessage("Nirbhaya: Be Fearless is an android emergency application, which can send a distress call or emergency message to a specified contact or group in an emergency situation faced by a woman or any other individual in general. Correct Location, Information and Communication, with and from the app is dependent upon the basic hardware/software requirements, like - Active Data plan, SMS plan and active GPS functionality.");
            b.setPositiveButton("Ok", null);
            AlertDialog alert= b.create();
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
