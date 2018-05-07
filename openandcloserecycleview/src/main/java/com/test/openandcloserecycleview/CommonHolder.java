package com.test.openandcloserecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hutao on 2018/4/4.
 */

public class CommonHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray;
    private View mItemView;

    public static CommonHolder newInstance(ViewGroup parentView, Context context, int layout) {
        View itemView = LayoutInflater.from(context).inflate(layout, parentView, false);
        return new CommonHolder(itemView);
    }

    public CommonHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        this.mViewSparseArray = new SparseArray<>();
    }

    public  <T>T getView(int viewId) {
        View v = mViewSparseArray.get(viewId);
        if (v == null) {
            v = mItemView.findViewById(viewId);
            mViewSparseArray.put(viewId, v);
        }
        return (T) v;
    }

    public CommonHolder setTetxView(int textViewId, String content) {
        TextView tv = getView(textViewId);
        tv.setText(content);
        return this;
    }

    public CommonHolder setImageView(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public CommonHolder setChecked(int viewId, boolean isChecked) {
        CheckBox cb = getView(viewId);
        cb.setChecked(isChecked);
        return this;
    }

    public CommonHolder setVisibity(int viewId, boolean isVIsibity) {
        View v = getView(viewId);
        if (isVIsibity) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.GONE);
        }
        return this;
    }

}
