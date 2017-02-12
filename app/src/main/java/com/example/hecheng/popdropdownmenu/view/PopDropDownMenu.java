package com.example.hecheng.popdropdownmenu.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hecheng.popdropdownmenu.R;
import com.example.hecheng.popdropdownmenu.bean.DropDownBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chengbin He on 2016/12/5.
 */

public class PopDropDownMenu extends LinearLayout {
    /**
     * 显示全部筛选
     */
    public static final int ALL = 1;
    /**
     * 显示多级和单级筛选
     */
    public static final int LEFT_RIGHT = 2;
    /**
     * 显示两个单级筛选
     */
    public static final int MID_RIGHT = 3;

    /**
     * 左侧左List点击
     */
    public static final int LEFT_LEFT_CLICK = 11;

    /**
     * 左侧右List点击
     */
    public static final int LEFT_RIGHT_CLICK = 12;
    /**
     * 中间List点击
     */
    public static final int MID_CLICK = 20;
    /**
     * 左侧左List点击
     */
    public static final int RIGHT_CLICK = 30;


    private Context mContext;
    private LinearLayout tab;
    private PopupWindow mLeftPopupWindow;
    private PopupWindow mRightPopupWindow;
    private PopupWindow mMidPopupWindow;
    private ListView mMenuLeftList1;
    private ListView mMenuLeftList2;
    private ListView mMenuRightList;
    private ListView mMenuMidList;
    private RelativeLayout mid;//中间tab
    private RelativeLayout left;//左侧tab
    private RelativeLayout right;//右侧tab
    private TextView tvLeft;
    private TextView tvRigh;
    private TextView tvMid;
    private ImageView ivLeft;
    private ImageView ivMid;
    private ImageView ivRight;
    private int mRightRowSelected;//右侧被选中位置
    private int mLeftRowSelected1;//左侧左选中位置
    private int mLeftRowSelected2;//左侧右选中位置
    private int mMidRowSelected;//中间选中位置
    private int showType;//显示模式 ALL LEFT_RIGHT MID_RIGHT
    private RelativeLayout mLeftRlShadow;
    private RelativeLayout mRightRlShadow;
    private RelativeLayout mMidRlShadow;
    private List<DropDownBean> leftList1 = new ArrayList<>();//左侧左List
    private List<DropDownBean> leftList2 = new ArrayList<>();//左侧右List
    private List<DropDownBean> midList = new ArrayList<>();//中间list
    private List<DropDownBean> rightList = new ArrayList<>();//右侧list
    private DropDownMenuAdapter leftAdapter1;
    private DropDownMenuAdapter leftAdapter2;
    private DropDownMenuAdapter midAdapter;
    private DropDownMenuAdapter rightAdapter;
    private OnMenuSelectedListener mMenuSelectedListener;

    public PopDropDownMenu(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PopDropDownMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        tab = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.pop_dropdown_tab, null, false);
        addView(tab);

        left = (RelativeLayout) tab.findViewById(R.id.rl_left);
        left.setOnClickListener(onClickListener);
        right = (RelativeLayout) tab.findViewById(R.id.rl_right);
        right.setOnClickListener(onClickListener);
        mid = (RelativeLayout) tab.findViewById(R.id.rl_mid);
        mid.setOnClickListener(onClickListener);


        View leftPopWindows = LayoutInflater.from(mContext).inflate(R.layout.view_drop_down_doblelist, null);
        final View rightPopWindows = LayoutInflater.from(mContext).inflate(R.layout.view_drop_down_list, null);
        final View midPopWindows = LayoutInflater.from(mContext).inflate(R.layout.view_drop_down_list, null);

        //初始化popupwindow
        mLeftPopupWindow = new PopupWindow(leftPopWindows,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mRightPopupWindow = new PopupWindow(rightPopWindows,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mMidPopupWindow = new PopupWindow(midPopWindows,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mLeftPopupWindow.setTouchable(true);
        mLeftPopupWindow.setOutsideTouchable(false);
        mLeftPopupWindow.setFocusable(false);
        mLeftPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mLeftPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.i("hcb","mLeftPopupWindow onDismiss");
                left.setBackgroundColor(getResources().getColor(R.color.white));
                ivLeft.setImageResource(R.drawable.scheduledown);
                tvLeft.setTextColor(mContext.getResources().getColor(R.color.text_one));
            }
        });

        mRightPopupWindow.setTouchable(true);
        mRightPopupWindow.setOutsideTouchable(false);
        mRightPopupWindow.setFocusable(false);
        mRightPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mRightPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                right.setBackgroundColor(getResources().getColor(R.color.white));
                ivRight.setImageResource(R.drawable.scheduledown);
                tvRigh.setTextColor(mContext.getResources().getColor(R.color.text_one));
            }
        });

        mMidPopupWindow.setTouchable(true);
        mMidPopupWindow.setOutsideTouchable(false);
        mMidPopupWindow.setFocusable(false);
        mMidPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mMidPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mid.setBackgroundColor(getResources().getColor(R.color.white));
                ivMid.setImageResource(R.drawable.scheduledown);
                tvMid.setTextColor(mContext.getResources().getColor(R.color.text_one));
            }
        });


        mLeftRlShadow = (RelativeLayout) leftPopWindows.findViewById(R.id.rl_menu_leftshadow);
        mLeftRlShadow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftPopupWindow.dismiss();
            }
        });

        mRightRlShadow = (RelativeLayout) rightPopWindows.findViewById(R.id.rl_menu_rightshadow);
        mRightRlShadow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRightPopupWindow.dismiss();
            }
        });

        mMidRlShadow = (RelativeLayout) midPopWindows.findViewById(R.id.rl_menu_rightshadow);
        mMidRlShadow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMidPopupWindow.dismiss();
            }
        });

        //初始化tab text
        tvLeft = (TextView) tab.findViewById(R.id.tv_menu_title);
        tvRigh = (TextView) tab.findViewById(R.id.tv_menu_title2);
        tvMid = (TextView) tab.findViewById(R.id.tv_menu_title3);

        //初始化tab icon
        ivLeft = (ImageView) tab.findViewById(R.id.iv_menu_arrow);
        ivRight = (ImageView) tab.findViewById(R.id.iv_menu_arrow2);
        ivMid = (ImageView) tab.findViewById(R.id.iv_menu_arrow3);
        //初始化List
        mMenuRightList = (ListView) rightPopWindows.findViewById(R.id.lv_menu);
        mMenuMidList = (ListView) midPopWindows.findViewById(R.id.lv_menu);
        mMenuLeftList1 = (ListView) leftPopWindows.findViewById(R.id.lv_leftmenu);
//        mMenuLeftList1.setSelector(R.drawable.selector_dropdownmenu_left);
        mMenuLeftList1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mMenuLeftList2 = (ListView) leftPopWindows.findViewById(R.id.lv_rightmenu);

        //初始化Adapter
        leftAdapter1 = new DropDownMenuAdapter(mContext, leftList1);
        leftAdapter2 = new DropDownMenuAdapter(mContext, leftList2);
        midAdapter = new DropDownMenuAdapter(mContext, midList);
        rightAdapter = new DropDownMenuAdapter(mContext, rightList);


        //左侧左List点击事件
        mMenuLeftList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftRowSelected1 = position;
                mMenuSelectedListener.onSelected(view, mLeftRowSelected1, LEFT_LEFT_CLICK);
            }
        });

        //左侧右List点击事件
        mMenuLeftList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftRowSelected2 = position;
                tvLeft.setText(leftList2.get(position).getName());
                mMenuSelectedListener.onSelected(view, mLeftRowSelected2, LEFT_RIGHT_CLICK);
                mLeftPopupWindow.dismiss();
            }
        });

        //右List点击事件
        mMenuRightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRightPopupWindow.dismiss();
                mRightRowSelected = position;
//				rightAdapter.setShowCheck(RIGHTCLICK);
                tvRigh.setText(rightList.get(position).getName());
                mMenuSelectedListener.onSelected(view, mRightRowSelected, RIGHT_CLICK);
            }
        });

        //中间List点击事件
        mMenuMidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMidPopupWindow.dismiss();
                mMidRowSelected = position;
				midAdapter.setSelectIndex(mMidRowSelected);
                tvMid.setText(midList.get(position).getName());
                mMenuSelectedListener.onSelected(view, mMidRowSelected, MID_CLICK);
            }
        });


    }

    View.OnClickListener onClickListener = new OnClickListener() {


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_left:
                    mMenuLeftList1.setAdapter(leftAdapter1);
                    mMenuLeftList2.setAdapter(leftAdapter2);
//                    leftAdapter1.setShowCheck(LEFTCLICK);
                    leftAdapter1.setSelectIndex(mLeftRowSelected1);

//                    dismissAll();
                    if ( mLeftPopupWindow.isShowing()){
                        mLeftPopupWindow.dismiss();
//                        dissMissLeftMenu();
                    }else {
                        dismissAll();
                        if (android.os.Build.VERSION.SDK_INT ==24){
                            //7.0 PopWindow BUG 需要修改显示方式
                            int[] a = new int[2];
                            tab.getLocationInWindow(a);
                            mLeftPopupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                                    Gravity.NO_GRAVITY, 0 , a[1]+tab.getHeight());
                        }else {
                            mLeftPopupWindow.showAsDropDown(tab);
                        }

                        left.setBackgroundColor(getResources().getColor(R.color.white));
                        tvLeft.setTextColor(mContext.getResources().getColor(R.color.app_green));
                        ivLeft.setImageResource(R.drawable.scheduleup);
                    }

                    break;
                case R.id.rl_right:
//                    rightAdapter.setShowCheck(RIGHTCLICK);
                    rightAdapter.setSelectIndex(mRightRowSelected);
                    mMenuRightList.setAdapter(rightAdapter);
                    if (mRightPopupWindow.isShowing()){
                        mRightPopupWindow.dismiss();
                    }else {
                        dismissAll();
                        if (android.os.Build.VERSION.SDK_INT ==24){
                            //7.0 PopWindow BUG 需要修改显示方式
                            int[] a = new int[2];
                            tab.getLocationInWindow(a);
                            mRightPopupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                                    Gravity.NO_GRAVITY, 0 , a[1]+tab.getHeight());
                        }else {
                            mRightPopupWindow.showAsDropDown(tab);
                        }


                        right.setBackgroundColor(getResources().getColor(R.color.white));
                        tvRigh.setTextColor(mContext.getResources().getColor(R.color.app_green));
                        ivRight.setImageResource(R.drawable.scheduleup);
                    }
                    break;
                case R.id.rl_mid:
                    midAdapter.setSelectIndex(mMidRowSelected);
                    mMenuMidList.setAdapter(midAdapter);
                    if (mMidPopupWindow.isShowing()){
                        mMidPopupWindow.dismiss();
                    }else {
                        dismissAll();
                        if (android.os.Build.VERSION.SDK_INT ==24){
                            //7.0 PopWindow BUG 需要修改显示方式
                            int[] a = new int[2];
                            tab.getLocationInWindow(a);
                            mMidPopupWindow.showAtLocation(((Activity) mContext).getWindow().getDecorView(),
                                    Gravity.NO_GRAVITY, 0 , a[1]+tab.getHeight());
                        }else {
                            mMidPopupWindow.showAsDropDown(tab);
                        }


                        mid.setBackgroundColor(getResources().getColor(R.color.white));
                        tvMid.setTextColor(mContext.getResources().getColor(R.color.app_green));
                        ivMid.setImageResource(R.drawable.scheduleup);
                    }
                    break;

            }

        }
    };

    private void dismissAll(){
        mLeftPopupWindow.dismiss();
        mRightPopupWindow.dismiss();
        mMidPopupWindow.dismiss();
    }



    public void setShowType(int showType) {
        this.showType = showType;
        switch (showType) {
            case ALL:
                left.setVisibility(VISIBLE);
                mid.setVisibility(VISIBLE);
                right.setVisibility(VISIBLE);
                break;
            case LEFT_RIGHT:
                left.setVisibility(VISIBLE);
                mid.setVisibility(GONE);
                right.setVisibility(VISIBLE);
                break;
            case MID_RIGHT:
                left.setVisibility(GONE);
                mid.setVisibility(VISIBLE);
                right.setVisibility(VISIBLE);
                break;
            default:
                left.setVisibility(VISIBLE);
                mid.setVisibility(VISIBLE);
                right.setVisibility(VISIBLE);
                break;
        }
    }

    public void setMenuSelectedListener(OnMenuSelectedListener mMenuSelectedListener) {
        this.mMenuSelectedListener = mMenuSelectedListener;
    }

    public void setLeftTitle(String name) {
        tvLeft.setText(name);
    }

    public void setRightTitle(String name) {
        tvRigh.setText(name);
    }

    public void setMidTitle(String name) {
        tvMid.setText(name);
    }

    public void setmMenuLeftList1(List<DropDownBean> list) {
        leftList1.clear();
        leftList1.addAll(list);
        leftAdapter1.notifyData(leftList1);
    }

    public void setmMenuLeftList2(List<DropDownBean> list) {
        leftList2.clear();
        leftList2.addAll(list);
        leftAdapter2.notifyData(leftList2);
    }

    public void setmMenuMidList(List<DropDownBean> list) {
        midList = list;
        midAdapter.notifyData(midList);
    }

    public void setmMenuRightList(List<DropDownBean> list) {
        rightList = list;
        rightAdapter.notifyData(rightList);
    }

    public int getmRightRowSelected() {
        return mRightRowSelected;
    }

    public int getmLeftRowSelected1() {
        return mLeftRowSelected1;
    }

    public int getmLeftRowSelected2() {
        return mLeftRowSelected2;
    }

    public int getmMidRowSelected() {
        return mMidRowSelected;
    }

    /**
     * 收起左侧菜单
     */
    public void dissMissLeftMenu(){
        mLeftPopupWindow.dismiss();
    }

    public interface OnMenuSelectedListener {
        /**
         * @param listview
         * @param position 点击位置
         * @param type     11 左侧左List点击
         *                 12 左侧右List点击
         *                 20 中间List点击
         *                 21 右侧List点击
         */
        void onSelected(View listview, int position, int type);

    }

}
