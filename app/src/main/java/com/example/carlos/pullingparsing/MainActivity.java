package com.example.carlos.pullingparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    /** TextView will display the results & RequestQueue gives a list of valid requests **/
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        /** Should cause the instance of MainActivity to add a request to the queue**/
        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        /**Test URL from n2yo that should give an "info" object using a users key
         * n2yo test: https://www.n2yo.com/rest/v1/satellite/tle/25544&apiKey=7K5RG7-EPM5SQ-LEYHBJ-3X9X
         * https://www.n2yo.com/rest/v1/satellite/above/41.702/-76.014/0/70/18/&apiKey=7K5RG7-EPM5SQ-LEYHBJ-3X9X
         * **GENERAL FORMAT**
         * "https://www.n2yo.com/rest/v1/satellite/above/" + Observer Latitude + "/" + Observer Longitude + "/" + altitude + "/" + category_ID(18 seems good) + "/" + search radius(90) + "/&apiKey=7K5RG7-EPM5SQ-LEYHBJ-3X9X"
         * */
        String url = "https://www.n2yo.com/rest/v1/satellite/above/41.702/-76.014/0/70/18/&apiKey=7K5RG7-EPM5SQ-LEYHBJ-3X9X";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("info");
                            /**
                             * DONT KNOW WHERE TO IMPLEMENT satCount BUT ITS THE NUMBER OF satellites DETECTED
                             */
                            int satCount = jsonObject.getInt("satcount");

                            JSONArray jsonArray = response.getJSONArray("above");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject satellite = jsonArray.getJSONObject(i);
                                int satID = satellite.getInt("satid");
                                String satName = satellite.getString("satname");
                                int satLatitude = satellite.getInt("satlat");
                                int satLongitude = satellite.getInt("satlng");
                                int satAltitude = satellite.getInt("satalt");
                                mTextViewResult.append(satName + " ID:" + String.valueOf(satID) + " Latitude:" + String.valueOf(satLatitude) + " Longitude:" + String.valueOf(satLongitude) + " Altitude:" + String.valueOf(satAltitude));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
