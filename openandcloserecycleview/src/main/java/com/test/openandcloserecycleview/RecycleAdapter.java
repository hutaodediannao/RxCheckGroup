package com.test.openandcloserecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hutao on 2018/4/4.
 */

public abstract class RecycleAdapter<T> extends RecyclerView.Adapter<CommonHolder> {

    public List<T> mList;
    private Context mContext;

    public RecycleAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonHolder.newInstance(parent, mContext, getLaout());
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        bindHolder(holder, position, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    abstract int getLaout();

    abstract void bindHolder(CommonHolder holder, int position, T t);
}
