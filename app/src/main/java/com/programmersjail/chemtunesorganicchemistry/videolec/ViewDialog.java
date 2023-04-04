package com.programmersjail.chemtunesorganicchemistry.videolec;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmersjail.chemtunesorganicchemistry.chempak.ChemPackageName;
import com.programmersjail.chemtunesorganicchemistry.R;

public class ViewDialog {

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.dialog_msg);
        text.setText(msg);

        ImageView dialogButtonC = (ImageView) dialog.findViewById(R.id.cancel);
        dialogButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView dialogButtonD = (ImageView) dialog.findViewById(R.id.done);
        dialogButtonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(v.getContext(), ChemPackageName.class);
                i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
               // i2.putExtra("title",studyLayoutModelList.get(holder.getAdapterPosition()).getStudyTitle());
                v.getContext().startActivity(i2);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
