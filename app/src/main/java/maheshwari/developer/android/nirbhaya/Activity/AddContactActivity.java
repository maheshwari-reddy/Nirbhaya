package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import maheshwari.developer.android.nirbhaya.HelperClass.ContactDb;
import maheshwari.developer.android.nirbhaya.R;


public class AddContactActivity extends Activity
{
    EditText contactNameEditText,contactNumberEditText;
    Button saveButton,backButton;
    ContactDb mDb;
    String name, number;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mDb = new ContactDb(getApplicationContext());
        mDb.init();
        contactNameEditText = (EditText)findViewById(R.id.contactNameEditText);
        contactNumberEditText = (EditText)findViewById(R.id.contactNumberEditText);
        saveButton = (Button)findViewById(R.id.saveButton);
        backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent in = new Intent(AddContactActivity.this,ManagingScreenActivity.class);
                startActivity(in);
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                name = contactNameEditText.getText().toString().trim();
                number = contactNumberEditText.getText().toString().trim();

                Cursor c = mDb.getValue();
                if (c.getCount() == 5)
                {
                    Toast.makeText(getApplicationContext(), "Contacts list is full", Toast.LENGTH_SHORT).show();
                }
                else if(name.isEmpty() || number.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Please enter all the details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mDb.insertValues(name, number);
                    Toast.makeText(getApplicationContext(), "Contact saved successfully",Toast.LENGTH_SHORT).show();
                    contactNameEditText.setText("");
                    contactNumberEditText.setText("");
                }
            }
        });
    }
}
