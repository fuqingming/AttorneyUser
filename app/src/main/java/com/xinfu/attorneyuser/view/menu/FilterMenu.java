package com.xinfu.attorneyuser.view.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

/**
 * 筛选菜单
 * @author zhengjun1
 */
@SuppressLint("AppCompatCustomView")
public class FilterMenu extends Button implements PopupWindow.OnDismissListener {
    private int normalBg;//正常状态下的背景
    private int pressBg;//按下状态下的背景
    private int normalIcon;//正常状态下的图标
    private int pressIcon;//按下状态下的图标
    private PopupWindow popupWindow;
    private Context context;
    private int screenWidth;
    private int screenHeight;
    private int paddingTop;
    private int paddingLeft;
    private int paddingRight;
    private int paddingBottom;
    private FilterMenuListener listener;

    public FilterMenu(Context context) {
        super(context);
        this.context = context;
        initAttrs(context);
    }

    public FilterMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        initBtn(context);
    }

    public FilterMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    //初始化各种自定义参数
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.popupbtn);

        normalBg = typedArray.getResourceId(R.styleable.popupbtn_normalBg, -1);
        pressBg = typedArray.getResourceId(R.styleable.popupbtn_pressBg, -1);
        normalIcon = typedArray.getResourceId(R.styleable.popupbtn_normalIcon, -1);
        pressIcon = typedArray.getResourceId(R.styleable.popupbtn_pressIcon, -1);
    }
    
    private void initAttrs(Context context) {
        normalBg = R.drawable.tab_bkg_line;
        normalIcon = R.mipmap.arrow_down_shop;
        pressBg = R.drawable.tab_bkg_selected;
        pressIcon = R.mipmap.arrow_up_shop;
    }

    /**
     * 初始话各种按钮样式
     */
    private void initBtn(final Context context) {
        paddingTop = this.getPaddingTop();
        paddingLeft = this.getPaddingLeft();
        paddingRight = this.getPaddingRight();
        paddingBottom = this.getPaddingBottom();
        setNormal();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();


    }

    /**
     * 隐藏弹出框
     */
    public void hidePopup(){
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置自定义接口
     * @param listener
     */
    public void setListener(FilterMenuListener listener) {
        this.listener = listener;
    }

    /**
     * 设置popupwindow的view
     */
    public void setPopupView(OnTaskSuccessComplete onTaskSuccessComplete) {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTaskSuccessComplete.onSuccess(null);
            }
        });
    }

    public void showPopUpWindow(final View view,final View vBottom) {
		if(popupWindow == null) {
            LinearLayout layout = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.6));
            view.setLayoutParams(params);
            layout.addView(view);
            layout.setBackgroundColor(Color.argb(60, 0, 0, 0));
            popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(FilterMenu.this);
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
        if(listener != null) {
            listener.onShow();
        }
        setPress();

        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            vBottom.getGlobalVisibleRect(rect);
            int h = vBottom.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }

        popupWindow.showAsDropDown(vBottom);
	}
    
    /**
     * 设置选中时候的按钮状态
     */
    private void setPress() {
        if (pressBg != -1) {
            this.setBackgroundResource(pressBg);
            this.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        }
        if (pressIcon != -1) {
            Drawable drawable = getResources().getDrawable(pressIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置正常模式下的按钮状态
     */
    private void setNormal() {
        if (normalBg != -1) {
            this.setBackgroundResource(normalBg);
            this.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        }
        if (normalIcon != -1) {
            Drawable drawable = getResources().getDrawable(normalIcon);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @Override
    public void onDismiss() {
        setNormal();
        if(listener != null) {
            listener.onHide();
        }
    }
}
