package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import maheshwari.developer.android.nirbhaya.R;


public class InstructionScreenActivity extends Activity
{
    Button okButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_screen);

        okButton = (Button)findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent instructionsIntent = new Intent(getApplicationContext(),HomeScreenActivity.class);
                startActivity(instructionsIntent);
                finish();
            }
        });
    }
}
