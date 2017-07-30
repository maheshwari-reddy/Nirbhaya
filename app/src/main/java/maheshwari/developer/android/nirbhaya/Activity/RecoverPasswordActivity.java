package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maheshwari.developer.android.nirbhaya.R;

public class RecoverPasswordActivity extends Activity
{
    Button loginButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent in=new Intent(v.getContext(),LoginScreenActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
