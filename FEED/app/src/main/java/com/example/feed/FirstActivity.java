package com.example.feed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";
    private FirebaseAuth mAuth;
    private EditText ETemail;
    private EditText ETpassword;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }

        mAuth = FirebaseAuth.getInstance();

        ETemail = (EditText)findViewById(R.id.editText);
        ETpassword = (EditText)findViewById(R.id.editText2);

        final Button signIn = (Button) findViewById(R.id.sign_in);
        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                signInIntoApp();
            }
        });

        Button signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view){
                    Intent intent = new Intent(FirstActivity.this, RegisterActivity.class);
                    startActivity(intent);
            }
        });

    }

    private void signInIntoApp(){
        String email = ETemail.getText().toString().trim();
        String password = ETpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this," Invalid Email ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this," Invalid Password ",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(FirstActivity.this," Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}
