package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.CreditCollectData;

import java.util.ArrayList;

/**
 * Created by Kiboooo on 2017/7/22.
 *
 */

public class CreditCollectAdapter extends RecyclerView.Adapter<CreditCollectAdapter.ViewHolder> {

    private ArrayList<CreditCollectData> CCD;

    public CreditCollectAdapter(ArrayList<CreditCollectData> CCD) {
        this.CCD = CCD;
    }

    @Override
    public CreditCollectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_collect, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CreditCollectAdapter.ViewHolder holder, int position) {
        CreditCollectData creditCollectData = CCD.get(position);
        Log.e("onBindViewHolder", creditCollectData.getClassname() + "  " + creditCollectData.getCreditNeed());
        holder.Class_Nature.setText(creditCollectData.getClassname());
        holder.Credit_Need.setText(creditCollectData.getCreditNeed());
        holder.Credit_Not.setText(creditCollectData.getCreditNot());
        holder.Credit_Require.setText(creditCollectData.getCreditRequire());
        holder.Credit_Get.setText(creditCollectData.getCreditGet());
    }

    @Override
    public int getItemCount() {
        return CCD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Credit_Get;
        TextView Credit_Need;
        TextView Class_Nature;
        TextView Credit_Not;
        TextView Credit_Require;

        public ViewHolder(View itemView) {
            super(itemView);
            Class_Nature = (TextView) itemView.findViewById(R.id.ClassNature);
            Credit_Need = (TextView) itemView.findViewById(R.id.Credit_Need);
            Credit_Not = (TextView) itemView.findViewById(R.id.Credit_Not);
            Credit_Require = (TextView) itemView.findViewById(R.id.Credit_Require);
            Credit_Get = (TextView) itemView.findViewById(R.id.Credit_Get);

        }
    }
}
