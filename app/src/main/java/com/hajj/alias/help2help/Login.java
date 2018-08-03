package com.hajj.alias.help2help;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText pass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set items
        userName = findViewById(R.id.userNameTx);
        pass = findViewById(R.id.passTx);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TextView myMsg = findViewById(R.id.msgTx);


                String msg =dataSnapshot.getValue(String.class);
                myMsg.setText(msg);
                Button loginBtn = findViewById(R.id.loginBtn);
                if (msg.equals("on")){
                    loginBtn.setVisibility(View.VISIBLE);
                    userName.setVisibility(View.VISIBLE);
                    pass.setVisibility(View.VISIBLE);

                }else{
                    loginBtn.setVisibility(View.INVISIBLE);
                    userName.setVisibility(View.INVISIBLE);
                    pass.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        TextView myMsg = (TextView)findViewById(R.id.msgTx);



    }
    public void checkLogin(){
        userName = findViewById(R.id.userNameTx);
        pass = findViewById(R.id.passTx);
         FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        if(!userName.getText().toString().matches("")||!pass.getText().toString().matches(""))
        {
            // not null not empty
            ref.child("app_users").child(userName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    String passDB = dataSnapshot.child("pass").getValue(String.class);

                    if (passDB.matches(pass.getText().toString()))
                    {
                        goUserAct();
                    }else{
                        TextView ErrorTX = findViewById(R.id.errorMTx);
                        ErrorTX.setText("Wrong Password!!"+passDB);
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            //null or empty
        }

    }

    private void goUserAct() {
        Intent intent = new Intent(this, mUserAct.class);
        startActivity(intent);
    }

    public void goUserAct(View view) {
        checkLogin();
    }
}

