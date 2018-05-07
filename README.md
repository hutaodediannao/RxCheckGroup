# RxCheckGroup
多选，单选，tab选择器

![效果展示](https://github.com/hutaodediannao/RxCheckGroup/blob/master/Screenshot_2018-05-07-19-49-17-349_com.test.rxche.png)

使用：
compile 'com.hutao:RxCheckGroup:1.0.0'

布局中引入：
    <com.hutao.rxcheckgroup.CheckGroup
        android:id="@+id/checkGroup"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:ableCancleChecked="true"
        app:checkMode="single"
        app:columnWeight="3"
        app:tabLeftMargin="5dp"
        app:tabRightMargin="5dp"
        app:tabTopMargin="5dp"
        app:firstChecked="true"
        app:tabHeight="40dp"
        app:tabTextSize="14"
        app:tabBackgroundDrawable="@drawable/check_background_selector"
        app:tabTextColorSelector="@drawable/check_text_selector"/>
        
        
activity中：
   checkGroup = findViewById(R.id.checkGroup);
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        checkGroup.updateUI(list);
