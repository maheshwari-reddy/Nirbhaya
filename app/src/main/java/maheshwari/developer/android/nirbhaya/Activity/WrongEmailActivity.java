package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import maheshwari.developer.android.nirbhaya.R;

public class WrongEmailActivity extends Activity
{
    Button backButton;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_email);

        backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
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