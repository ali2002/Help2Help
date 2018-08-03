package com.hajj.alias.help2help;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HelpActivity extends AppCompatActivity {

    private String TAG = HelpActivity.class.getSimpleName();
    String img,addr,UName,uId,blood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        String UHname= getIntent().getStringExtra("name");
         uId= getIntent().getStringExtra("id");
        TextView namTX=findViewById(R.id.hajj_user_nameTX);
        namTX.setText(UHname);
        new HelpActivity.GetContacts().execute();
    }




    private static String url = "https://api.myjson.com/bins/qgeag";

    public void helthBtn(View view) {
        Intent intent = new Intent(this, OrderSent.class);
        intent.putExtra("whoSend","for helth");
        startActivity(intent);
    }

    public void HelpBtn(View view) {
        Intent intent = new Intent(this, OrderSent.class);
        intent.putExtra("whoSend","for help");
        startActivity(intent);
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.v(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray jsonHaj = jsonObj.getJSONArray("hajj_prs");

                    // looping through All Contacts
                    for (int i = 0; i < jsonHaj.length(); i++) {
                        JSONObject c = jsonHaj.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String photo = c.getString("photo");
                        String adrs = c.getString("address");
                        String bld = c.getString("blood");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);


                        TextView  nName ;
                        TextView nMobil ;


                        if (id.matches(uId) ){
                            //find user
                            Log.v(TAG,"----------YES------>"+id);

                            img = photo;
                            addr=adrs;
                            blood =bld;
                            UName = name;
                            uId = id;
                        }else{

                            Log.v(TAG,"----------No------>"+id);
                        }
                        // adding contact to contact list

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TextView blooodTx = findViewById(R.id.blodTX);
            TextView addrTX = findViewById(R.id.addrTX);
            ImageView pic = findViewById(R.id.UHimg);
            blooodTx.setText(blood);
            addrTX.setText(addr);
            //set hajjUser image
            Picasso.get().load(img).resize(50, 50).transform(new CropCircleTransformation()).into(pic);

        }

    }
}
