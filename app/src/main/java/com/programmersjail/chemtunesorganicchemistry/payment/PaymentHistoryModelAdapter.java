package com.programmersjail.chemtunesorganicchemistry.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.programmersjail.chemtunesorganicchemistry.R;

import java.util.List;

public class PaymentHistoryModelAdapter extends RecyclerView.Adapter<PaymentHistoryModelAdapter.ViewHolder> {

    private Context mCtx;
    private List<PaymentHistoryModel> paymentHistoryModelList;

    public PaymentHistoryModelAdapter(Context mCtx, List<PaymentHistoryModel> paymentHistoryModelList) {
        this.mCtx = mCtx;
        this.paymentHistoryModelList = paymentHistoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_model, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final PaymentHistoryModel model = paymentHistoryModelList.get(position);

        holder.t1.setText(model.getUserName());
        holder.t2.setText(model.getPhoneNumber());
        holder.t3.setText(model.getCollageName());
        holder.t4.setText(model.getSubscriptionFee());
        holder.t5.setText(model.getMobBankingNo());
        holder.t6.setText(model.getTransID());
        holder.t7.setText(model.getAmount());
        holder.t8.setText(model.getDate());
        holder.t9.setText(model.getTime());
        holder.t10.setText(model.getPackageName());
       // holder.t11.setText(model.getMonthLimit());

    }

    @Override
    public int getItemCount() {
        return paymentHistoryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.p_name);
            t2 = (TextView) itemView.findViewById(R.id.p_phone_number);
            t3 = (TextView) itemView.findViewById(R.id.p_collage_name);
            t4 = (TextView) itemView.findViewById(R.id.p_subscription_fee);
            t5 = (TextView) itemView.findViewById(R.id.p_mob_banking_no);
            t6 = (TextView) itemView.findViewById(R.id.p_trans_id);
            t7 = (TextView) itemView.findViewById(R.id.p_amount);
            t8 = (TextView) itemView.findViewById(R.id.p_date);
            t9 = (TextView) itemView.findViewById(R.id.p_time);
            t10 = (TextView) itemView.findViewById(R.id.p_pakName);
            t11 = (TextView) itemView.findViewById(R.id.p_pakLimit);

        }
    }
}
