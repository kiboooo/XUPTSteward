package com.example.dickjampink.logintest.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickjampink.logintest.R;
import com.example.dickjampink.logintest.activity.PYJHActivity;
import com.example.dickjampink.logintest.bean.PYJHData;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.List;


/**
 * Created by Kiboooo on 2017/7/27.
 *
 */

public class TestStackAdapter extends StackAdapter<Integer> {

    public static String[] ItemTitle = new String[]{
            "第一学期",
            "第二学期",
            "第三学期",
            "第四学期",
            "第五学期",
            "第六学期",
            "第七学期",
            "第八学期",
    };


    public TestStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        ColorItemViewHolder h = (ColorItemViewHolder) holder;
//        Log.e("bindView", PYJHActivity.pList.size() + ""+PYJHActivity.pList.get(0).get(0).getClassName());
        h.onBind(data, position, PYJHActivity.pList.get(position));
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.cardstack_itemcard, parent, false);
        return new ColorItemViewHolder(view);
    }

    private static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        RecyclerView recyclerView;
        LinearLayoutManager layoutManager;

        ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            recyclerView = (RecyclerView) view.findViewById(R.id.pyjh_recycler);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        void onBind(Integer data, int position, List<PYJHData> pyjhList) {
            List<PYJHData> p = pyjhList;
            Log.e("bindView", p.size() + "");
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(ItemTitle[position]);
            PYJHAdapter pyjhAdapter = new PYJHAdapter(p);
            recyclerView.setAdapter(pyjhAdapter);
        }

    }
}
