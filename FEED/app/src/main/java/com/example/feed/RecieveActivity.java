package com.example.feed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class RecieveActivity extends AppCompatActivity {

    private DatabaseReference reff;
    private ListView mListView;
    private ArrayAdapter adapter;
    private ArrayList<String> arrayList;
    private long N=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);

        Toast.makeText(RecieveActivity.this,"Please Wait.....",Toast.LENGTH_SHORT).show();

        mListView = (ListView) findViewById(R.id.listview);


        reff = FirebaseDatabase.getInstance().getReference().child("Child");
        arrayList = new ArrayList<String>();


        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                arrayList.clear();
                N=dataSnapshot.getChildrenCount();
                for( int i = 1 ; i <= N ; i++ ){
                    arrayList.add(dataSnapshot.child(String.valueOf(i)).child("foodName").getValue(String.class));
                }

                adapter = new ArrayAdapter(RecieveActivity.this, android.R.layout.simple_expandable_list_item_1,arrayList);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Cache c = new Cache();

                        i++;

                        c.setUserName(dataSnapshot.child(String.valueOf(i)).child("userName").getValue(String.class));
                        c.setFoodName(dataSnapshot.child(String.valueOf(i)).child("foodName").getValue(String.class));
                        c.setQuantity(dataSnapshot.child(String.valueOf(i)).child("quantity").getValue(String.class));
                        c.setNumber(dataSnapshot.child(String.valueOf(i)).child("number").getValue(String.class));
                        c.setX(dataSnapshot.child(String.valueOf(i)).child("x").getValue(Double.class));
                        c.setY(dataSnapshot.child(String.valueOf(i)).child("y").getValue(Double.class));


                        Intent intent = new Intent(RecieveActivity.this,DisplayActivity.class);

                        intent.putExtra("name",c.getUserName());
                        intent.putExtra("food",c.getFoodName());
                        intent.putExtra("quant",c.getQuantity());
                        intent.putExtra("num",c.getNumber());
                        intent.putExtra("lat",c.getX());
                        intent.putExtra("lng",c.getY());

                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
