package mindblossom.secure.rsa.secure_rsa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends ActionBarActivity{
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView name ;
    EditText publicK;
    EditText privateK;
    TextView modulus;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mindblossom.secure.rsa.secure_rsa.R.layout.activity_display_contact);
        name = (TextView) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.editTextName);
        publicK = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.editTextPublicK);
        privateK = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.editTextPrivateK);
        Button b2 = (Button)findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button);
        b2.setVisibility(View.INVISIBLE);
        mydb = new DBHelper(this);

        Intent intent = getIntent();
        if(intent !=null) {
            if (intent.hasExtra("privateK")) {
                String pprivateK = intent.getStringExtra("privateK");
                privateK.setText(pprivateK);
            }
            if (intent.hasExtra("publicK")) {
                String ppublicK = intent.getStringExtra("publicK");
                publicK.setText(ppublicK);
            }
        }
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                final String ppublicK = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PUBLIC_K));
                final String pprivateK = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PRIVATE_K));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = (Button)findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button1);
                b.setVisibility(View.INVISIBLE);
                b2 = (Button)findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button);
                b2.setVisibility(View.VISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                publicK.setText((CharSequence)ppublicK);
                publicK.setFocusable(false);
                publicK.setClickable(false);

                privateK.setText((CharSequence)pprivateK);
                privateK.setFocusable(false);
                privateK.setClickable(false);


                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("publicK", ppublicK);
                        intent.putExtra("privateK", pprivateK);
                        Toast.makeText(getApplicationContext(), "Profil Launched", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(mindblossom.secure.rsa.secure_rsa.R.menu.display_contact, menu);
            }

            else{
                getMenuInflater().inflate(mindblossom.secure.rsa.secure_rsa.R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case mindblossom.secure.rsa.secure_rsa.R.id.Edit_Contact:
                Button b = (Button)findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button1);
                b.setVisibility(View.VISIBLE);
                Button b2 = (Button)findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button);
                b2.setVisibility(View.INVISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                publicK.setEnabled(true);
                publicK.setFocusableInTouchMode(true);
                publicK.setClickable(true);

                privateK.setEnabled(true);
                privateK.setFocusableInTouchMode(true);
                privateK.setClickable(true);

                return true;
            case mindblossom.secure.rsa.secure_rsa.R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(mindblossom.secure.rsa.secure_rsa.R.string.deleteContact)
                        .setPositiveButton(mindblossom.secure.rsa.secure_rsa.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(mindblossom.secure.rsa.secure_rsa.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                b2 = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button);
                b2.setVisibility(View.VISIBLE);
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(), publicK.getText().toString(), privateK.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(!name.getText().toString().matches("") && !publicK.getText().toString().matches("") && !privateK.getText().toString().matches("")) {
                    if (mydb.insertContact(name.getText().toString(), publicK.getText().toString(), privateK.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
            }
        }
    }
}