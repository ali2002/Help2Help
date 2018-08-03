package com.hajj.alias.help2help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderSent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sent);
        String FromMsg= getIntent().getStringExtra("whoSend");
        TextView ttx = findViewById(R.id.msgFromTx);
        ttx.setText(FromMsg);
    }
}
