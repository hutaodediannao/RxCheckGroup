package com.test.openandcloserecycleview;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.hutao.app.passkeyadapter.adapter.PassKeyRecyclerAdapter;
import com.hutao.app.passkeyadapter.viewHolder.PassKeyViewHolder;

import java.util.List;

/**
 * Created by hutao on 2018/4/4.
 */

public class ModelAdapter extends PassKeyRecyclerAdapter<Model> {

    public ModelAdapter(List<Model> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    public int getLayout() {
        return  R.layout.item_lay;
    }

    @Override
    public void bindHolder(PassKeyViewHolder holder, final Model model, final int position) {
        holder.setTextView(R.id.tvTitle, model.getTitle())
                .setTextView(R.id.tvContent, model.getContent())
                .setImageResource(R.id.iv, model.getResId())
                .setVisibity(R.id.viewContent, model.isOpen())
                .setCheckBox(R.id.cb, model.isOpen());

        CheckBox cb = holder.getView(R.id.cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCliclkListener != null) {
                    if (model.isOpen()) {
                        mList.get(position).setOpen(false);
                    } else {
                        mList.get(position).setOpen(true);
                    }
                    mCliclkListener.clickCheckBox(position, mList.get(position).isOpen());
                    notifyDataSetChanged();
                }
            }
        });
    }

    public interface CliclkListener{
        void clickCheckBox(int position, boolean isOpen);
    }

    private CliclkListener mCliclkListener;

    public void setCliclkListener(CliclkListener cliclkListener) {
        this.mCliclkListener = cliclkListener;
    }
}
