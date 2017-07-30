package maheshwari.developer.android.nirbhaya.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import maheshwari.developer.android.nirbhaya.R;

public class ManagingScreenActivity extends ActionBarActivity implements View.OnClickListener
{
    Button addContactButton,viewContactButton,serviceButton,cancelButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_screen);

        addContactButton = (Button)findViewById(R.id.addContactButton);
        viewContactButton = (Button)findViewById(R.id.viewContactButton);
        serviceButton = (Button)findViewById(R.id.serviceButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        addContactButton.setOnClickListener(this);
        viewContactButton.setOnClickListener(this);
        serviceButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.addContactButton:
                Intent addIn = new Intent(this, AddContactActivity.class);
                startActivity(addIn);
                finish();
                break;
            case R.id.viewContactButton:
                Intent viewIn = new Intent(this, ViewContactActivity.class);
                startActivity(viewIn);
                finish();
                break;
            case R.id.serviceButton:
                Intent serviceIn = new Intent(this, ServiceHandlerActivity.class);
                startActivity(serviceIn );
                finish();
                break;
            case R.id.cancelButton:
                finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_managing_screen, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.change_password)
        {
            Intent changePasswordIn = new Intent(this, ChangePasswordActivity.class);
            startActivity(changePasswordIn );
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
