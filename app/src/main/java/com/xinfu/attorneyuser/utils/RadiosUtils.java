package com.xinfu.attorneyuser.utils;

import android.content.Context;
import android.widget.Toast;

import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by asus on 2018/2/7.
 */

public class RadiosUtils
{

    public static void openRadios(final Context context, List<MediaBean> m_arrMediaBean, int size, final OnTaskSuccessComplete onTaskSuccessComplete)
    {
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(context)
                .image()
                .multiple();
        if (m_arrMediaBean != null && !m_arrMediaBean.isEmpty())
        {
            rxGalleryFinal.selected(m_arrMediaBean);
        }
        rxGalleryFinal.maxSize(size)
                .imageLoader(ImageLoaderType.FRESCO)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>()
                {

                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception
                    {
                        onTaskSuccessComplete.onSuccess(imageMultipleResultEvent.getResult());
                    }

                    @Override
                    public void onComplete()
                    {
                        super.onComplete();
                        Toast.makeText(context, "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }
}
