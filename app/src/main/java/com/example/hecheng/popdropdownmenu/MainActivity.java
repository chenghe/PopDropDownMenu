package com.example.hecheng.popdropdownmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.hecheng.popdropdownmenu.bean.DropDownBean;
import com.example.hecheng.popdropdownmenu.view.PopDropDownMenu;

import java.util.ArrayList;

/**
 * Created by HeCheng on 2017/2/12.
 */

public class MainActivity extends Activity{

    private PopDropDownMenu popDropDownMenu;
    private ArrayList<DropDownBean> list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list.add(new DropDownBean(1,"张三"));
        list.add(new DropDownBean(1,"李四"));
        list.add(new DropDownBean(1,"王五"));
        list.add(new DropDownBean(1,"张三"));
        list.add(new DropDownBean(1,"李四"));
        list.add(new DropDownBean(1,"王五"));
        list.add(new DropDownBean(1,"张三"));
        list.add(new DropDownBean(1,"李四"));

        popDropDownMenu = (PopDropDownMenu) findViewById(R.id.dropdownmenu);
        popDropDownMenu.setShowType(PopDropDownMenu.ALL);
        popDropDownMenu.setmMenuMidList(list);
        popDropDownMenu.setmMenuRightList(list);
        popDropDownMenu.setmMenuLeftList1(list);
        popDropDownMenu.setmMenuLeftList2(list);
        popDropDownMenu.setMenuSelectedListener(new PopDropDownMenu.OnMenuSelectedListener() {
            @Override
            public void onSelected(View listview, int position, int type) {

            }
        });
    }
}
