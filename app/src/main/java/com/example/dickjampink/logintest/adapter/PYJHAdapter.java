package com.example.dickjampink.logintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.bean.PYJHData;

import java.util.List;

/**
 * Created by Kiboooo on 2017/7/27.
 */

public class PYJHAdapter extends RecyclerView.Adapter<PYJHAdapter.ViewHolder> {
    private List<PYJHData> PYJHDatas;

    public PYJHAdapter(List<PYJHData> datas) {
        this.PYJHDatas = datas;
    }

    @Override
    public PYJHAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pyjh_card, parent, false));
    }

    @Override
    public void onBindViewHolder(PYJHAdapter.ViewHolder holder, int position) {
        PYJHData pyjhData = PYJHDatas.get(position);
        Log.e("pyjhData", pyjhData.getClassName());
        Log.e("pyjhData", pyjhData.getClassNature());
        Log.e("pyjhData", pyjhData.getPyjh_credit());
        Log.e("pyjhData", pyjhData.getPyjh_test());
        Log.e("pyjhData", pyjhData.getPyjh_leaningWhere());
        Log.e("pyjhData", pyjhData.getPyjh_major());
        Log.e("pyjhData", pyjhData.getPyjh_starAndEnd());

        holder.pyjh_className.setText(pyjhData.getClassName());
        holder.pyjh_classNature.setText(pyjhData.getClassNature());
        holder.pyjh_credit_text.setText(pyjhData.getPyjh_credit());
        holder.pyjh_test_text.setText(pyjhData.getPyjh_test());
        holder.pyjh_leaningWhere_text.setText(pyjhData.getPyjh_leaningWhere());
        holder.pyjh_major_text.setText(pyjhData.getPyjh_major());
        holder.pyjh_starAndEnd_text.setText(pyjhData.getPyjh_starAndEnd());
    }

    @Override
    public int getItemCount() {
        return PYJHDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pyjh_className;
        TextView pyjh_classNature;
        TextView pyjh_credit_text;
        TextView pyjh_test_text;
        TextView pyjh_leaningWhere_text;
        TextView pyjh_major_text;
        TextView pyjh_starAndEnd_text;

        public ViewHolder(View itemView) {
            super(itemView);
            pyjh_className = (TextView) itemView.findViewById(R.id.pyjh_className);
            pyjh_classNature = (TextView) itemView.findViewById(R.id.pyjh_classNature);
            pyjh_credit_text = (TextView) itemView.findViewById(R.id.pyjh_credit_text);
            pyjh_test_text = (TextView) itemView.findViewById(R.id.pyjh_test_text);
            pyjh_leaningWhere_text = (TextView) itemView.findViewById(R.id.pyjh_leaningWhere_text);
            pyjh_major_text = (TextView) itemView.findViewById(R.id.pyjh_major_text);
            pyjh_starAndEnd_text = (TextView) itemView.findViewById(R.id.pyjh_starAndEnd_text);
        }
    }
}
