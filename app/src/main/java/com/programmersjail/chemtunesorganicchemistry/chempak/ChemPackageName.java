 package com.programmersjail.chemtunesorganicchemistry.chempak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageModel;
import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.programmersjail.chemtunesorganicchemistry.R.drawable.logo_four;

 public class ChemPackageName extends AppCompatActivity {

    private RecyclerView recyclerViewTwo;
    private GridLayoutManager gridLayoutManager;

    private List<ChemPackageModel> chemPackageModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_package_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        getSupportActionBar().setLogo(getResources().getDrawable(logo_four));
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        recyclerViewTwo = (RecyclerView) findViewById(R.id.chem_package_rv);
        recyclerViewTwo.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerViewTwo.setLayoutManager(gridLayoutManager);
        loadChemPackage();
        chemPackageModelList = new ArrayList<>();


    }


     private void loadChemPackage(){

         //if everything is fine
         StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_CHEM_PACKAGE,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {


                         try {



                             JSONArray array = new JSONArray(response);


                             if(chemPackageModelList != null){
                                 chemPackageModelList.clear();
                             }


                             for (int i = 0; i < array.length(); i++) {


                                 JSONObject room = array.getJSONObject(i);
                                 chemPackageModelList.add(new ChemPackageModel(

                                         room.getString("package_title"),
                                         room.getString("package_s_dis"),
                                         room.getString("package_dis"),
                                         room.getString("package_img"),
                                         room.getString("package_duration"),
                                         room.getString("package_fee"),
                                         room.getString("package_limit")








                                 ));
                             }

                             ChemPackageModelAdapter adapter = new ChemPackageModelAdapter(getApplicationContext(),chemPackageModelList);
                             // adapter.notifyDataSetChanged();
                             recyclerViewTwo.setAdapter(adapter);





                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         String message = null;
                         if (error instanceof NetworkError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         } else if (error instanceof ServerError) {
                             message = "The server could not be found. Please try again after some time!!";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         } else if (error instanceof AuthFailureError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         } else if (error instanceof ParseError) {
                             message = "Parsing error! Please try again after some time!!";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         } else if (error instanceof NoConnectionError) {
                             message = "Cannot connect to Internet...Please check your connection!";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         } else if (error instanceof TimeoutError) {
                             message = "Connection TimeOut! Please check your internet connection.";
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                         }
                     }
                 }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 //  params.put("type", categoryName);
                 return params;
             }
         };

         VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                // Intent intent = new Intent(NewsDiaplayActivity.this, MainActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                //startActivity(intent);

                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}