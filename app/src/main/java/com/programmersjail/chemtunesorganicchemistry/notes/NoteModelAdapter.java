package com.programmersjail.chemtunesorganicchemistry.notes;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import com.programmersjail.chemtunesorganicchemistry.PlayerActivity;
import com.programmersjail.chemtunesorganicchemistry.R;
import com.programmersjail.chemtunesorganicchemistry.helper.SharedPrefManager;
import com.programmersjail.chemtunesorganicchemistry.helper.SubscriptionFee;
import com.programmersjail.chemtunesorganicchemistry.helper.URls;
import com.programmersjail.chemtunesorganicchemistry.helper.VolleySingleton;
import com.programmersjail.chemtunesorganicchemistry.registration.User;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLecture;
import com.programmersjail.chemtunesorganicchemistry.videolec.VideoLectureModel;
import com.programmersjail.chemtunesorganicchemistry.videolec.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NoteModelAdapter extends RecyclerView.Adapter<NoteModelAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Activity mCtx;
    private List<NoteModel> noteModelList;


    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    private User user;

    public NoteModelAdapter(Activity mCtx, List<NoteModel> noteModelList) {
        this.mCtx = mCtx;
        this.noteModelList = noteModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final NoteModel model = noteModelList.get(position);
        holder.title.setText(model.getNoteTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(v.getContext(), PDFViewer.class);
                i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i3.putExtra("title",noteModelList.get(holder.getAdapterPosition()).getNoteTitle());
                i3.putExtra("url",noteModelList.get(holder.getAdapterPosition()).getNoteURL());
                v.getContext().startActivity(i3);
               // Environment.DIRECTORY_PODCASTS,"/"+folderName+"/"+model.getNoteTitle()+".pdf";
            }
        });

        user = SharedPrefManager.getInstance(mCtx).getUser();



        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String phn = user.getPhoneNumber();
                //if everything is fine
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URls.URL_SUBSCRIPTION_FEE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                try {


                                    //converting response to json object
                                    JSONObject obj = new JSONObject(response);

                                    //if no error in response
                                    if (!obj.getBoolean("error")) {
                                       // Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                        //getting the user from the response
                                        JSONObject userJson = obj.getJSONObject("tmu_students");

                                        //userJson.getString("user");

                                        //creating a new user object
                                        final SubscriptionFee due = new SubscriptionFee(
                                                userJson.getString("account_type"),
                                                userJson.getString("subscription_fee"),
                                                userJson.getString("package_name"),
                                                userJson.getString("package_limit")



                                        );

                                        //..................................................................................


                                        int i = 0;

                                        if(due.getSubscriptionFee().equals("paid")  ){

                                            checkPermission(
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    STORAGE_PERMISSION_CODE);
                                            Toast.makeText(mCtx, "again click to download pdf file", Toast.LENGTH_SHORT).show();

                                        }else  {
                                            Toast.makeText(mCtx, "get account premium", Toast.LENGTH_SHORT).show();
                                            ViewDialog alert = new ViewDialog();
                                            alert.showDialog(mCtx, "Get Premium Account");
                                        }




                                        //t7.setText(due.getDue());



                                    } else {
                                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    }

                                }catch (JSONException e){
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
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    message = "The server could not be found. Please try again after some time!!";
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                } else if (error instanceof AuthFailureError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    message = "Parsing error! Please try again after some time!!";
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    message = "Cannot connect to Internet...Please check your connection!";
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                } else if (error instanceof TimeoutError) {
                                    message = "Connection TimeOut! Please check your internet connection.";
                                    Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("phone_number", phn);
                        return params;
                    }
                };

                VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);





            }
        });

    }



     private void startDownloading() throws IOException {

        int i = 0;
        NoteModel model = noteModelList.get(i);

        String url = model.getNoteURL();
        final String folderName = "Chemtunes";


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download");
        request.setDescription("Downloading file......");

         request.allowScanningByMediaScanner();
         request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PODCASTS,"/"+folderName+"/"+model.getNoteTitle()+".pdf");
         String path = "/"+folderName+"/"+model.getNoteTitle()+".pdf";
         request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,path);
         DownloadManager manager = (DownloadManager) mCtx.getSystemService(mCtx.DOWNLOAD_SERVICE);
         manager.enqueue(request);

     }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.note_title);
            download = (ImageView) itemView.findViewById(R.id.download);
        }
    }


    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                mCtx,
                permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            mCtx,
                            new String[] { permission },
                            requestCode);
        }
        else {
           // Toast.makeText(mCtx, "Permission already granted", Toast.LENGTH_SHORT).show();

            try {
                startDownloading();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mCtx,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();

            }
            else {
                Toast.makeText(mCtx,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


}
