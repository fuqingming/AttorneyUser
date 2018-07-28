package com.xinfu.attorneyuser.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BitmapUtils {
    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ArrayList<String> bitmapsToBase64Array(List<MediaBean> m_arrMediaBean)
    {
        ArrayList<String> images = new ArrayList<>();
        for(int i = 0 ; i < m_arrMediaBean.size() ; i ++)
        {
            Bitmap bt =  BitmapFactory.decodeFile(m_arrMediaBean.get(i).getThumbnailSmallPath());
            String strBase = "\"data:image/jpeg;base64,"+bitmapToBase64(bt)+"\"";
            images.add(strBase);
        }

        return images;
    }

    public static String bitmapsToBase64Array(String strPath)
    {
        Bitmap bt =  BitmapFactory.decodeFile(strPath);

        return "data:image/jpeg;base64,"+bitmapToBase64(bt);
    }

}
