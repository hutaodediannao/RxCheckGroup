package com.hutao.rxcheckgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 满足，单选，多选，不选等功能的复合选择框
 * Created by hutao on 2018/5/7.
 */

public class CheckGroup extends FrameLayout {

    /**
     * 选择模式，单选或者多选
     */
    public enum CHECK_MODE {
        SINGLE_CHECK_MODE(1),//单选
        MUILT_CHECK_MODE(2);//多选

        private int value;

        CHECK_MODE(int value) {
        }

        public int getValue() {
            return value;
        }
    }

    private GridLayout mGridLayout;//容器
    private List<Integer> selectIndexList = new ArrayList<>();//选中的下标位置集合

    public CheckGroup(@NonNull Context context) {
        this(context, null);
    }

    public CheckGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CheckGroup);
        //单选或者多选
        int checkModeStr = typedArray.getInt(R.styleable.CheckGroup_checkMode, 1);
        switch (checkModeStr){
            case 1:
                mCheckMode = CHECK_MODE.SINGLE_CHECK_MODE;
                break;
            case 2:
                mCheckMode = CHECK_MODE.MUILT_CHECK_MODE;
                break;
        }

        ableCancleChecked = typedArray.getBoolean(R.styleable.CheckGroup_ableCancleChecked, false);
        defacultTabHeightDpValue = typedArray.getDimension(R.styleable.CheckGroup_tabHeight, R.dimen._30dp);
        defacultColumeCount = typedArray.getInt(R.styleable.CheckGroup_columnWeight, 3);
        defacultTabLeftMargin = typedArray.getDimension(R.styleable.CheckGroup_tabLeftMargin, R.dimen._5dp);
        defacultTabRightMargin = typedArray.getDimension(R.styleable.CheckGroup_tabRightMargin, R.dimen._5dp);
        defacultTabTOPMargin = typedArray.getDimension(R.styleable.CheckGroup_tabTopMargin, R.dimen._5dp);
        tabBackgroundDrawable = typedArray.getResourceId(R.styleable.CheckGroup_tabBackgroundDrawable, R.drawable.check_background_selector);
        tabTextColorSelector = typedArray.getResourceId(R.styleable.CheckGroup_tabTextColorSelector, R.drawable.check_text_selector);
        tabTextSize = typedArray.getInteger(R.styleable.CheckGroup_tabTextSize, 14);
        defacultFirstChecked = typedArray.getBoolean(R.styleable.CheckGroup_firstChecked, false);
        typedArray.recycle();

        initView();
    }

    private void initView() {
        mGridLayout = (GridLayout) LayoutInflater.from(getContext()).inflate(R.layout.rx_check_group, null);
        mGridLayout.setColumnCount(defacultColumeCount);
        this.addView(mGridLayout);
    }

    private boolean defacultFirstChecked = false;//默认第一个item不选中
    private CHECK_MODE mCheckMode = CHECK_MODE.SINGLE_CHECK_MODE;//默认为单选
    private float defacultTabHeightDpValue = 30;//默认tab高度
    private int defacultColumeCount = 3;//列的个数
    private float defacultTabLeftMargin = 5;
    private float defacultTabRightMargin = 5;
    private float defacultTabTOPMargin = 5;
    private int tabBackgroundDrawable;//tab的背景选择器
    private int tabTextColorSelector;//tab的文字颜色选择器
    private float tabTextSize;//tab的文字大小
    private boolean ableCancleChecked = false;//是否支持取消选中

    private int mTotabTabCount = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateUI(List<String> titleList) {
        if (titleList == null) {
            mTotabTabCount = 0;
            return;
        }
        mTotabTabCount = titleList.size();

        mGridLayout.removeAllViews();
        for (int i = 0; i < mTotabTabCount; i++) {
            CheckBox cb = (CheckBox) LayoutInflater.from(getContext()).inflate(R.layout.rx_checkbox, null);
            cb.setChecked(false);//设置开始都不选择
            cb.setText(titleList.get(i));//设置显示文字
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize);//设置文字大小
            cb.setBackgroundResource(tabBackgroundDrawable);//设置背景点击选择的效果
//            cb.setTextColor(getResources().getColor(tabBackgroundDrawable));//设置选中文字的颜色
            if (i == 0) cb.setChecked(defacultFirstChecked);//第一个是否选中

            //开始排位
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = 0;
            layoutParams.height = (int) defacultTabHeightDpValue;
            //使用Spec定义子控件的位置和比重
            GridLayout.Spec columnSpec = GridLayout.spec(i % defacultColumeCount, 1f);
            layoutParams.columnSpec = columnSpec;
            layoutParams.leftMargin = (int) defacultTabLeftMargin;
            layoutParams.rightMargin = (int) defacultTabRightMargin;
            layoutParams.topMargin = (int) defacultTabTOPMargin;

            mGridLayout.addView(cb, layoutParams);
        }

        setCheckListener();
    }

    /**
     * 遍历获取单选的下标位
     *
     * @return
     */
    private List<Integer> getSelectIndexList() {
        selectIndexList.clear();
        int childCount = mGridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CheckBox checkBox = (CheckBox) mGridLayout.getChildAt(i);
            if (checkBox.isChecked()) selectIndexList.add(i);
        }
        return selectIndexList;
    }

    private void setCheckListener() {
        for (int i = 0; i < mTotabTabCount; i++) {
            final int finalI = i;
            final CheckBox cb = (CheckBox) mGridLayout.getChildAt(i);
            cb.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是单选，多选，是否能够不选
                    switch (mCheckMode) {
                        case SINGLE_CHECK_MODE:
                            if (ableCancleChecked) {//支持取消选中
                                if (cb.isChecked()) {
                                    cancleAllCheckBox();
                                    cb.setChecked(true);
                                } else {
                                    cancleAllCheckBox();
                                    cb.setChecked(false);
                                }
                            } else {
                                cancleAllCheckBox();
                                cb.setChecked(true);
                            }
                            break;
                        case MUILT_CHECK_MODE:
                            //无需处理
                            break;
                    }
                }
            });
        }
    }

    private void cancleAllCheckBox() {
        for (int i = 0; i < mTotabTabCount; i++) {
            CheckBox checkBox = (CheckBox) mGridLayout.getChildAt(i);
            checkBox.setChecked(false);
        }
    }

}
