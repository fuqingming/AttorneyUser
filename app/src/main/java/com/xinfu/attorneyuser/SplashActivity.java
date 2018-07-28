package com.xinfu.attorneyuser;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

//import com.hyphenate.EMCallBack;
//import com.hyphenate.chat.EMClient;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;
import com.xinfu.attorneyuser.base.BaseAppCompatActivity;
import com.xinfu.attorneyuser.huanxin.DemoHelper;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.IMHelper;
import com.xinfu.attorneyuser.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class SplashActivity extends BaseAppCompatActivity {

    @BindView(R.id.guide_pages)
    ViewPager viewPager;
    @BindView(R.id.iv_indicator1)
    ImageView ivIndicator1;
    @BindView(R.id.iv_indicator2)
    ImageView ivIndicator2;
    @BindView(R.id.iv_indicator3)
    ImageView ivIndicator3;
    @BindView(R.id.iv_index)
    ImageView m_ivIndex;

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView()
    {
        DemoHelper.getInstance().initHandler(this.getMainLooper());
        if(!GlobalVariables.isFirstRun())
        {
            m_ivIndex.setVisibility(View.VISIBLE);
            if (!DemoHelper.getInstance().isLoggedIn())
            {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
            else
            {
                m_ivIndex.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },2000);
            }

            return;
        }

        LayoutInflater inflater = getLayoutInflater();
        View vPage1 = inflater.inflate(R.layout.activity_guide_page_1, null);
        View vPage2 = inflater.inflate(R.layout.activity_guide_page_2, null);
        View vPage3 = inflater.inflate(R.layout.activity_guide_page_3, null);

        ArrayList<View> pageViews = new ArrayList<>();
        pageViews.add(vPage1);
        pageViews.add(vPage2);
        pageViews.add(vPage3);

        viewPager.setAdapter(new GuidePagerAdapter(SplashActivity.this, pageViews));

        // 立即体验
        Button btnGoto = vPage3.findViewById(R.id.btn_goto);
        btnGoto.setOnClickListener(v -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        });

        // 页面切换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrollStateChanged(int arg0){}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2){}

            @Override
            public void onPageSelected(int arg0)
            {
                setCurSelectedIndicator(arg0);
            }

            private void setCurSelectedIndicator(int nIndex)
            {
                ivIndicator1.setImageResource(R.drawable.shape_circle_gray);
                ivIndicator2.setImageResource(R.drawable.shape_circle_gray);
                ivIndicator3.setImageResource(R.drawable.shape_circle_gray);
                switch (nIndex+1)
                {
                    case 1:
                        ivIndicator1.setImageResource(R.drawable.shape_circle_red);
                        break;

                    case 2:
                        ivIndicator2.setImageResource(R.drawable.shape_circle_red);
                        break;

                    case 3:
                        ivIndicator3.setImageResource(R.drawable.shape_circle_red);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private class GuidePagerAdapter extends PagerAdapter
    {
        private List<View> m_listView;

        public GuidePagerAdapter(Context context, List<View> listView)
        {
            this.m_listView = listView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object object)
        {
            (collection).removeView(m_listView.get(position));
        }

        @Override
        public int getCount()
        {
            return m_listView.size();
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position)
        {
            ( collection).addView(m_listView.get(position));
            return m_listView.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }

    // 按两次返回键退出程序
    private static final int WAIT_NEXT_KEY_BACK_DURATION = 1000 * 2;
    private Boolean m_bFistKeyBackPressed = false;
    private Boolean m_bIsWaitingNextKeyBack = false;
    private Timer m_timerWaitingNextKeyBack = new Timer();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!m_bFistKeyBackPressed)
            {
                m_bFistKeyBackPressed = true;

                Utils.showToast(this, "再按一次退出程序");

                if (!m_bIsWaitingNextKeyBack)
                {
                    m_bIsWaitingNextKeyBack = true;

                    m_timerWaitingNextKeyBack.schedule(new TimerTask()
                    {
                        public void run()
                        {
                            m_bFistKeyBackPressed = false;
                            m_bIsWaitingNextKeyBack = false;
                        }
                    }, WAIT_NEXT_KEY_BACK_DURATION);
                }
                return true;
            }
            else
            {
                finish();
            }
        }
        return false;
    }
}
