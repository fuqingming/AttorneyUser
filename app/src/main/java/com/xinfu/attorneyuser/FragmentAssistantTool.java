package com.xinfu.attorneyuser;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.adapter.ModuleSelectionAdapter;
import com.xinfu.attorneyuser.base.BaseFragment;
import com.xinfu.attorneyuser.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class FragmentAssistantTool extends BaseFragment
{
    @BindView(R.id.gridview_functions)
    GridView m_gridView;

    private List<Map<String, Object>> m_listData;
    private ModuleSelectionAdapter m_adapter;

    private int[] m_arrIcon = { R.mipmap.ca_01, R.mipmap.ca_02, R.mipmap.ca_03,
                                R.mipmap.ca_04, R.mipmap.ca_05, R.mipmap.ca_06,
                                R.mipmap.ca_07, R.mipmap.ca_08, R.mipmap.ca_09,
                                R.mipmap.ca_10, R.mipmap.ca_11, R.mipmap.ca_12 };

    private String[] m_arrText = {  "诉讼费", "交通赔偿", "司法鉴定",
                                    "律师费", "个人所得税", "医保计算器",
                                    "拍卖佣金", "违约金", "利息",
                                    "公证费", "工伤", "天数计算" };

    private String[] m_arrUrl = {   "http://tool.faniuwenda.com/litigation.html",
                                    "http://tool.faniuwenda.com/traffic.html",
                                    "http://tool.faniuwenda.com/attorneys.html",
                                    "http://tool.faniuwenda.com/legalfee.html",
                                    "http://tool.faniuwenda.com/individual.html",
                                    "http://tool.faniuwenda.com/Medical.html",
                                    "http://tool.faniuwenda.com/commission.html",
                                    "http://tool.faniuwenda.com/breach.html",
                                    "http://tool.faniuwenda.com/interest.html",
                                    "http://tool.faniuwenda.com/notarial.html",
                                    "http://tool.faniuwenda.com/employment.html",
                                    "http://tool.faniuwenda.com/daycalculate.html"};

    @Override
    protected int setLayoutResourceId()
    {
        return R.layout.fragment_assistant_tool;
    }

    @Override
    protected void initView()
    {
        Utils.initCommonTitle(getContentView(),"便民助手");
    }

    @Override
    protected void initData()
    {
        // 换算gridview中item高度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int nWidth = (dm.widthPixels - 2)/3;

        // 获取数据
        m_listData = getListData(m_arrIcon, m_arrText);
        m_adapter = new ModuleSelectionAdapter(getMContext(), m_listData, nWidth);

        m_gridView.setAdapter(m_adapter);

        m_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        // 点击一项
        m_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent it = new Intent(getMContext(),WebViewToolActivity.class);
                it.putExtra("webViewUrl",m_arrUrl[position]);
                it.putExtra("strTitle",m_arrText[position]);
                startActivity(it);
            }
        });
    }

    private List<Map<String, Object>> getListData(int[] m_arrIcon,String[] m_arrText)
    {
        ArrayList<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();

        for(int i = 0; i < m_arrIcon.length; i ++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", m_arrIcon[i]);
            map.put("text", m_arrText[i]);
            listData.add(map);
        }

        return listData;
    }
}
