package com.xinfu.attorneyuser.utils.imagetrans;

import android.content.Context;
import android.view.ViewGroup;

import com.xinfu.attorneyuser.base.BaseApplication;
import com.xinfu.attorneyuser.utils.imagetrans.view.RingLoadingView;

import it.liuting.imagetrans.listener.ProgressViewGet;

/**
 * Created by liuting on 18/3/19.
 */

public class MyProgressBarGet implements ProgressViewGet<RingLoadingView>
{
    @Override
    public RingLoadingView getProgress(Context context)
    {
        RingLoadingView view = new RingLoadingView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(BaseApplication.dpToPx(50), BaseApplication.dpToPx(50)));
        return view;
    }

    @Override
    public void onProgressChange(RingLoadingView view, float progress)
    {
        view.setProgress(progress);
    }
}
