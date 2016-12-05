package mindblossom.secure.rsa.secure_rsa;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    EditText messageIn;
    EditText mod;
    EditText kPrivate;
    EditText kPublic;
    EditText N;
    Button c3;
    Button b3;
    Button bCalcul;
    Button bCalcul2;
    Button bCalcul3;
    EditText input;
    EditText output;

    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(mindblossom.secure.rsa.secure_rsa.R.layout.activity_main);

        kPrivate = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pQ);
        kPublic = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pK);
        input = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.mc);

        final ClipboardManager clipboard;
        clipboard = (ClipboardManager)getSystemService( this.CLIPBOARD_SERVICE );

        c3 = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.c3);

        c3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText( "Texte copied",
                        input.getText().toString() );
                clipboard.setPrimaryClip( clip );
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("privateK")) {
            String privateK = intent.getStringExtra("privateK");
            kPrivate.setText(String.valueOf(privateK));
        }
        if (intent.hasExtra("publicK")) {
            String publicK = intent.getStringExtra("publicK");
            kPublic.setText(String.valueOf(publicK));
        }

        b3 = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.button3);
        bCalcul = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.cript);
        bCalcul2 = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.uncript);
        bCalcul3 = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.generate);
        Button startBtn = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.sms);
        final Button loginButton = (Button) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.contact);

        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });

        b3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!kPublic.getText().toString().matches("") && !kPrivate.getText().toString().matches("") ) {
                    final String ppublicK = kPublic.getText().toString() ;
                    final String pprivateK = kPrivate.getText().toString();
                    kPublic.setText((CharSequence)ppublicK);
                    kPrivate.setText((CharSequence)ppublicK);

                    Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                    intent.putExtra("publicK", ppublicK);
                    intent.putExtra("privateK", pprivateK);
                    Toast.makeText(getApplicationContext(), "New Contact Launched", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Click on Generate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        bCalcul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                messageIn = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.msg);
                input = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.mc);
                kPublic = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pK);

                String s = messageIn.getText().toString();

                try {
                    if(s != null && s.length() > 0 && s.length() < 125 && !s.matches("")){

                        byte[] bytes = s.getBytes();
                        BigInteger message = new BigInteger(bytes);

                        String getPq = kPublic.getText().toString();
                        BigInteger cPq = new BigInteger(getPq);

                        if (getPq != null){
                            //RSA(1000);
                            //BigInteger encrypt = encrypt(message, cPq);
                            //input.setText(String.valueOf(encrypt));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Insert a Public Key !", Toast.LENGTH_SHORT).show();}
                    }
                    else {Toast.makeText(getApplicationContext(),"Insert a Message (125 length max)!", Toast.LENGTH_SHORT).show();}
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), kPublic.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bCalcul2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                kPublic = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pK);
                kPrivate = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pQ);
                input = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.mc);
                output = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.md);
                String Message = input.getText().toString();

                try {
                    if(Message != null && Message.length() > 0  && !Message.matches("")){

                        BigInteger cMessage = new BigInteger(Message);
                        String getMod = kPublic.getText().toString();
                        BigInteger cMod = new BigInteger(getMod);

                        String getPk = kPrivate.getText().toString();
                        BigInteger cPk = new BigInteger(getPk);

                        if (getPk != null && getMod != null){
                            //RSA(1000);
                            //BigInteger decrypt = decrypt(cMessage,cPk,cMod);
                            //String sDecrypt = new String(decrypt.toByteArray());
                            //output.setText(String.valueOf(sDecrypt));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Insert a Private Key and a Public Key !", Toast.LENGTH_SHORT).show();}
                    }
                    else {Toast.makeText(getApplicationContext(),"Insert a Crypted Message !", Toast.LENGTH_SHORT).show();}
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Insert a Private Key, a Plublic Key and a Crypted Message !", Toast.LENGTH_SHORT).show();
                }



            }
        });

        //Button GENERATE
        bCalcul3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //EncryptionUtil app = new EncryptionUtil();

                kPrivate = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pQ);
                kPublic = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.pK);

                //BigInteger getPrivateKey = getPrivateKey();
                //kPrivate.setText(String.valueOf(getPrivateKey));

                //BigInteger getModulus = getModulus();
                //kPublic.setText(String.valueOf(getModulus));


            }
        });
    }

    protected void sendSMS() {

        input = (EditText) findViewById(mindblossom.secure.rsa.secure_rsa.R.id.mc);
        String inputM = input.getText().toString();
        if(inputM != null && inputM.length() > 0 && !inputM.matches("")) {

            Log.i("Send SMS", "");
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);

            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", new String(""));
            smsIntent.putExtra("sms_body", inputM);

            try {
                startActivity(smsIntent);
                finish();
                Log.i("Finished sending SMS...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Insert a Crypted Message !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
