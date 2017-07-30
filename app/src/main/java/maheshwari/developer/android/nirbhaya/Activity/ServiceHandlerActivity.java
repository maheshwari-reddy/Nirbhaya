package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import maheshwari.developer.android.nirbhaya.HelperClass.MyService;
import maheshwari.developer.android.nirbhaya.R;

public class ServiceHandlerActivity extends Activity implements View.OnClickListener
{
    Button activateButton,deactivateButton,backButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_handler);
        activateButton = (Button)findViewById(R.id.activateButton);
        deactivateButton = (Button)findViewById(R.id.deactivateButton);
        backButton = (Button)findViewById(R.id.backButton);
        activateButton.setOnClickListener(this);
        deactivateButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.activateButton:
                Intent activateIntent = new Intent(ServiceHandlerActivity.this, MyService.class);
                startService(activateIntent);
                break;
            case R.id.deactivateButton:
                Intent deactivateIntent = new Intent(ServiceHandlerActivity.this, MyService.class);
                stopService(deactivateIntent);
                break;
            case R.id.backButton:
                Intent backIntent = new Intent(ServiceHandlerActivity.this, ManagingScreenActivity.class);
                startActivity(backIntent);
                finish();
                break;
        }
    }
}