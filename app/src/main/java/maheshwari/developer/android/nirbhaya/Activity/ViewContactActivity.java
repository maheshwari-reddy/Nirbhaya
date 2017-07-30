package maheshwari.developer.android.nirbhaya.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import maheshwari.developer.android.nirbhaya.HelperClass.ContactDb;
import maheshwari.developer.android.nirbhaya.R;

public class ViewContactActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener {
    ListView listView;
    ContactDb mDb;
    Cursor c;
    int clickedPos;
    public ArrayList<String> nameList= new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        mDb = new ContactDb(getApplicationContext());
        mDb.init();
        listView = (ListView)findViewById(R.id.listView);
        updateList();
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(this);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.menu_view_contact, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.delet_all:
                mDb.deleteAll();
                updateList();
                break;

            case R.id.delete_one:
                c.moveToPosition(clickedPos);
                String rowId= c.getString(0);
                mDb.deleteOne(rowId);
                updateList();
                break;

            case R.id.update:
                putInput("update");
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class ContactAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return c.getCount();
        }

        public Object getItem(int position)
        {
            return null;
        }

        public long getItemId(int position)
        {
            return 0;
        }

        public View getView(int position, View v, ViewGroup parent)
        {
            c.moveToPosition(position);
            v = getLayoutInflater().inflate(R.layout.contact_row, null);
            TextView contactNameTextView= (TextView)v.findViewById(R.id.contactNameTextView);
            TextView contactNumberTextView =  (TextView)v.findViewById(R.id.contactNumberTextView);
            contactNameTextView.setText(c.getString(1));
            contactNumberTextView.setText(c.getString(2));
            return v;
        }

    }

    public void updateList()
    {
        c = mDb.getValue();
        ContactAdapter ca= new ContactAdapter();
        listView.setAdapter(ca);
    }

    public void putInput(String requestCode)
    {
        final String req_code= requestCode;
        final Dialog d= new Dialog(ViewContactActivity.this);
        d.setTitle("Create Contact");
        d.setContentView(R.layout.update);
        d.show();

        final EditText name = (EditText)d.findViewById(R.id.u_name);
        final EditText number = (EditText)d.findViewById(R.id.u_number);
        if(req_code.equalsIgnoreCase("update"))
        {
            c.moveToPosition(clickedPos);
            String n1= c.getString(1);
            String n2= c.getString(2);
            name.setText(n1);
            number.setText(n2);
        }
        Button ok= (Button)d.findViewById(R.id.uOk_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(req_code.equalsIgnoreCase("update"))
                {
                    //mDb.init();
                    c.moveToPosition(clickedPos);
                    String rowId1= c.getString(0);
                    String nameStr= name.getText().toString();
                    String numberStr= number.getText().toString();
                    mDb.updateRecord(rowId1, nameStr, numberStr);
                }
                else
                {
                    String nameS= name.getText().toString();
                    String numberS= number.getText().toString();
                    mDb.insertValues(nameS, numberS);
                }
                d.cancel();
                updateList();
            }
        });
    }

    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos,long arg3)
    {
        clickedPos= pos;
        return false;
    }

    protected void onPause()
    {
        super.onPause();
        mDb.uninit();
    }

    public void onBackPressed()
    {
        Intent in = new Intent(ViewContactActivity.this,ManagingScreenActivity.class);
        startActivity(in);
        finish();
    }
}
