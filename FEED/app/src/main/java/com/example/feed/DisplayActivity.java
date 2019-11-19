package com.example.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayActivity extends AppCompatActivity {

    private EditText uname, fname, quantity, number;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);



        uname = (EditText) findViewById(R.id.etname);
        fname = (EditText) findViewById(R.id.etfoodname);
        quantity = (EditText) findViewById(R.id.etquantity);
        number = (EditText) findViewById(R.id.etnum);

        Intent i = getIntent();

        String name = i.getStringExtra("name");
        String food = i.getStringExtra("food");
        String quant = i.getStringExtra("quant");
        String num = i.getStringExtra("num");
        Double x =i.getDoubleExtra("lat",0);
        Double y =i.getDoubleExtra("lng",0);

        uname.setText("Donor Name- "+name);
        fname.setText("Food Name- "+food);
        quantity.setText("Food Quantity- "+quant);
        number.setText("Donor Number- "+num);

        uname.setEnabled(false);
        fname.setEnabled(false);
        quantity.setEnabled(false);
        number.setEnabled(false);

        final String address = "geo:"+String.valueOf(x)+","+String.valueOf(y);

        btn = (Button) findViewById(R.id.btnloc);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse(address);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(address));
//                startActivity(intent);

            }
        });

    }
}
