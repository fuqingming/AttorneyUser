package com.xinfu.attorneyuser.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.backhandler.OnTaskSuccessComplete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    // 设置通用Title
    public static void initCommonTitle(final Activity activity, final String strTitle)
    {
        initCommonTitle(activity, strTitle, false);
    }

    // 设置通用Title
    public static void initCommonTitle(final Activity activity, final String strTitle, Boolean bShowReturnButton)
    {
        initCommonTitle(activity, strTitle, bShowReturnButton, R.mipmap.back_03);
    }

    // 设置通用Title
    public static void initCommonTitle(	final Activity activity,
                                           final String strTitle,
                                           Boolean bShowReturnButton,
                                           final int strButtonIcon)
    {
        initCommonTitle(activity, null, strTitle, bShowReturnButton, strButtonIcon);
    }

    // 设置通用Title
    public static void initCommonTitle(final Activity activity,
                                       final String strTitle,
                                       Boolean bShowReturnButton, String right,
                                       final int strButtonIcon)
    {
        initCommonTitle(activity, null, strTitle, bShowReturnButton,right, strButtonIcon);
    }

    // 设置通用Title
    public static void initCommonTitle(final View vContent, final String strTitle, final int ivIcon)
    {
        initCommonTitle(null,vContent, strTitle,true,ivIcon);
    }

    // 设置通用Title
    public static void initCommonTitle(final View vContent, final String strTitle)
    {
        initCommonTitle(null,vContent, strTitle,false,0);
    }

    // 设置通用Title
    public static void initCommonTitle(	final Activity activity,
                                           final View vContent,
                                           final String strTitle,
                                           Boolean bShowReturnButton,
                                           final int strButtonIcon)
    {
        TextView tvTitle = null;
        if(vContent == null)
        {
            tvTitle = activity.findViewById(R.id.tv_title_text);
        }
        else
        {
            tvTitle =  vContent.findViewById(R.id.tv_title_text);
        }

        // Title
        tvTitle.setText(strTitle);

        // 返回按钮
        if(bShowReturnButton)
        {
            ImageView ivBack = null;
            if(vContent == null)
            {
                ivBack = activity.findViewById(R.id.iv_title_back);
                ivBack.setImageDrawable(activity.getResources().getDrawable(strButtonIcon));
            }
            else
            {
                ivBack = vContent.findViewById(R.id.iv_title_back);
                ivBack.setImageDrawable(vContent.getResources().getDrawable(strButtonIcon));
            }


            ivBack.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    activity.finish();
                }
            });
        }
    }

    // 设置通用Title
    public static void initCommonTitle(final Activity activity, final String strTitle, String strLeft, String strRight)
    {
        initCommonTitle(activity, null, strTitle, strLeft, strRight);
    }

//    public static void initCommonTitle(	final Activity activity,
//                                           final String strTitle,
//                                           Boolean bShowReturnButton,String right,
//                                           final int strButtonIcon)
    // 设置通用Title
    public static void initCommonTitle(final Activity activity, final String strTitle, boolean bShowReturnButton, String strRight)
    {
        initCommonTitle(activity, strTitle, bShowReturnButton, strRight, R.mipmap.back_03);
    }

    // 设置通用Title
    public static void initCommonTitle(	final Activity activity,
                                           final String strTitle,
                                           final String strLeft)
    {
        initCommonTitle(activity, null, strTitle, strLeft, "");
    }

    // 设置通用Title
    public static void initCommonTitle(final View vContent, final String strTitle, String strLeft, String strRight)
    {
        initCommonTitle(null,vContent, strTitle,strLeft,strRight);
    }

    // 设置通用Title
    public static void initCommonTitle(final View vContent, final String strTitle, String strLeft)
    {
        initCommonTitle(null,vContent, strTitle,strLeft,"");
    }

    // 设置通用Title
    public static void initCommonTitle(	final Activity activity,
                                           final View vContent,
                                           final String strTitle,
                                           String strLeft,
                                           String strRight)
    {
        TextView tvTitle = null;
        TextView tvLeft = null;
        TextView tvRight = null;
        if(vContent == null)
        {
            tvTitle = activity.findViewById(R.id.tv_title_text);
            tvLeft = activity.findViewById(R.id.tv_title_back);
            tvRight = activity.findViewById(R.id.tv_title_right);
        }
        else
        {
            tvTitle =  vContent.findViewById(R.id.tv_title_text);
            tvLeft =  vContent.findViewById(R.id.tv_title_back);
            tvRight =  vContent.findViewById(R.id.tv_title_right);
        }

        tvTitle.setText(strTitle);
        tvLeft.setText(strLeft);
        tvRight.setText(strRight);

        tvLeft.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                activity.finish();
            }
        });
    }

    // 设置通用Title
    public static void initCommonTitle(	final Activity activity,
                                        final View vContent,
                                        final String strTitle,
                                        boolean bShowReturnButton,
                                        String strRight,
                                        final int strButtonIcon)
    {
        TextView tvTitle = null;
        ImageView ivBack = null;
        TextView tvRight = null;
        if(vContent == null)
        {
            tvTitle = activity.findViewById(R.id.tv_title_text);
            ivBack = activity.findViewById(R.id.iv_title_back);
            ivBack.setImageDrawable(activity.getResources().getDrawable(strButtonIcon));
            tvRight = activity.findViewById(R.id.tv_title_right);
        }
        else
        {
            tvTitle =  vContent.findViewById(R.id.tv_title_text);
            ivBack = vContent.findViewById(R.id.iv_title_back);
            ivBack.setImageDrawable(vContent.getResources().getDrawable(strButtonIcon));
            tvRight =  vContent.findViewById(R.id.tv_title_right);
        }

        tvTitle.setText(strTitle);
        tvRight.setText(strRight);
        ivBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                activity.finish();
            }
        });
    }

    /**
     * convert px to its equivalent dp
     *
     * 将px转换为与之相等的dp
     */
    public static int px2dp(Context context, float pxValue)
    {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * convert dp to its equivalent px
     *
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 打开软键盘
     * @param context
     */
    public static void showKeyboard(final Context context)
    {
        new Timer().schedule(new TimerTask()
        {
            public void run()
            {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        },300);

    }

    /**
     * 关闭软键盘
     * @param mContext
     */
    public static void hintKeyboard(final Context mContext, final EditText mEditText)
    {
        new Timer().schedule(new TimerTask()
        {
            public void run()
            {
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            }
        },300);
    }

    // 点击EditText外的地方隐藏软键盘
    public static void setOnTouchEditTextOutSideHideIM(final Activity activity, final View rootViewInsideScrollView)
    {
        View vContent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        vContent.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                View view = activity.getCurrentFocus();
                if(view != null)
                {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    return imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
                else
                {
                    return true;
                }
            }
        });
        rootViewInsideScrollView.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

                boolean bRet = false;
                try
                {
                    bRet = imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return false;
            }
        });
    }

    // 点击EditText外的地方隐藏软键盘
    public static void setOnTouchEditTextOutSideHideIM(final Activity activity)
    {
        View vContent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        vContent.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                View view = activity.getCurrentFocus();
                if(view != null)
                {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    return imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
                else
                {
                    return true;
                }
            }
        });
    }

    // 点击EditText外的地方隐藏软键盘
    public static void setOnTouchEditTextOutSideHideIM(final Fragment fragment)
    {
        final Activity activity = fragment.getActivity();
        View vContent = fragment.getView();
        vContent.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = activity.getCurrentFocus();
                if(v != null)
                {
                    IBinder ib = v.getWindowToken();
                    return imm.hideSoftInputFromWindow(ib, 0);
                }
                else
                {
                    return false;
                }
            }
        });
    }

    // 点击EditText外的地方隐藏软键盘
    public static void setOnTouchEditTextOutSideHideIM(final Fragment fragment, final View rootViewInsideScrollView)
    {
        final Activity activity = fragment.getActivity();
        rootViewInsideScrollView.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent event)
            {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

                boolean bRet = false;
                try
                {
                    bRet = imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                return false;
            }
        });
    }

    public static Dialog showDialogWifi(final Activity activity)
    {
        View.OnClickListener onLeftButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        };

        View.OnClickListener onRightButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
            }
        };

        return showCommonDialog(activity,
                "网络状态提示",
                "当前没有可以使用的网络，请设置网络！",
                "取消",
                onLeftButtonClickListener,
                "立即前往",
                onRightButtonClickListener);
    }

    public static Dialog showLogOutDialog(final Context context, final OnTaskSuccessComplete onTaskSuccess)
    {
        View.OnClickListener onLeftButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        };

        View.OnClickListener onRightButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTaskSuccess != null)
                {
                    onTaskSuccess.onSuccess(null);
                }
            }
        };

        return showCommonDialog(context,
                "退出登陆",
                "是否退出登陆。",
                "取消",
                onLeftButtonClickListener,
                "确认",
                onRightButtonClickListener);
    }

    public static Dialog showCancleOrderDialog(final Context context, final OnTaskSuccessComplete onTaskSuccess)
    {
        View.OnClickListener onLeftButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        };

        View.OnClickListener onRightButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onTaskSuccess != null)
                {
                    onTaskSuccess.onSuccess(null);
                }
            }
        };

        return showCommonDialog(context,
                "提示",
                "是否取消通知律师。",
                "取消",
                onLeftButtonClickListener,
                "确认",
                onRightButtonClickListener);
    }

    public static Dialog showCommonDialog(final Context context,
                                          final String strTitle,
                                          final String strMsg,
                                          final String strLeftButtonText,
                                          final View.OnClickListener onLeftButtonClickListener,
                                          final String strRightButtonText,
                                          final View.OnClickListener onRightButtonClickListener)
    {
        View vContent = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        final Dialog dlg = new Dialog(context, R.style.common_dialog);
        dlg.setContentView(vContent);
        dlg.setCanceledOnTouchOutside(false); // 点击窗口外区域不消失
        dlg.setCancelable(false); // 返回键不消失

        // 必须用代码调整dialog的大小
        android.view.WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        //lp.width = (int) (MyApplication.s_nScreenWidth * 0.95);
        //lp.height = (int) (MyApplication.m_nScreenHeight * 0.5);
        lp.width = (int) context.getResources().getDimension(R.dimen.dialog_width);
        dlg.getWindow().setAttributes(lp);

        // title
        TextView tvTitle = vContent.findViewById(R.id.tv_title);
        if (strTitle != null && !strTitle.isEmpty())
        {
            tvTitle.setText(strTitle);
        }
        else
        {
            tvTitle.setVisibility(View.GONE);
        }

        // msg
        TextView tvMsg =  vContent.findViewById(R.id.tv_msg);
        tvMsg.setText(strMsg);

        // left button
        Button btnLeft =  vContent.findViewById(R.id.btn_left);
        btnLeft.setText(strLeftButtonText);
        btnLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onLeftButtonClickListener != null)
                {
                    onLeftButtonClickListener.onClick(v);
                }
            }
        });

        // right button
        Button btnRight = vContent.findViewById(R.id.btn_right);
        btnRight.setText(strRightButtonText);
        btnRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onRightButtonClickListener != null)
                {
                    onRightButtonClickListener.onClick(v);
                }
            }
        });

        dlg.show();

        return dlg;
    }

    public static Dialog showDialog(final Activity activity, int layout, final OnTaskSuccessComplete onTaskSuccessComplete)
    {
        View.OnClickListener onLeftButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        };

        View.OnClickListener onRightButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onTaskSuccessComplete.onSuccess(null);
            }
        };

        return showCommonDialog(activity,
                layout,
                onLeftButtonClickListener,
                onRightButtonClickListener);
    }

    public static Dialog showCommonDialog(final Context context,
                                          final String strTitle,
                                          final String strLeftButtonText,
                                          final View.OnClickListener onLeftButtonClickListener,
                                          final String strRightButtonText,
                                          final View.OnClickListener onRightButtonClickListener,
                                          final int layout)
    {
        View vContent = LayoutInflater.from(context).inflate(layout, null);
        final Dialog dlg = new Dialog(context, R.style.common_dialog);
        dlg.setContentView(vContent);
        dlg.setCanceledOnTouchOutside(false); // 点击窗口外区域不消失
        dlg.setCancelable(false); // 返回键不消失

        // 必须用代码调整dialog的大小
        android.view.WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        //lp.width = (int) (MyApplication.s_nScreenWidth * 0.95);
        //lp.height = (int) (MyApplication.m_nScreenHeight * 0.5);
        lp.width = (int) context.getResources().getDimension(R.dimen.dialog_width);
        dlg.getWindow().setAttributes(lp);

        // title
        TextView tvTitle = vContent.findViewById(R.id.dialog_text);
        if (strTitle != null && !strTitle.isEmpty())
        {
            tvTitle.setText(strTitle);
        }
        else
        {
            tvTitle.setVisibility(View.GONE);
        }

        // left button
        TextView tvLeft =  vContent.findViewById(R.id.dialog_cancel);
        tvLeft.setText(strLeftButtonText);
        tvLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onLeftButtonClickListener != null)
                {
                    onLeftButtonClickListener.onClick(v);
                }
            }
        });

        // right button
        TextView tvRight = vContent.findViewById(R.id.dialog_sure);
        tvRight.setText(strRightButtonText);
        tvRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onRightButtonClickListener != null)
                {
                    onRightButtonClickListener.onClick(v);
                }
            }
        });

        dlg.show();

        return dlg;
    }

    public static Dialog showCommonDialog(final Context context,
                                          final int layout,
                                          final View.OnClickListener onLeftButtonClickListener,
                                          final View.OnClickListener onRightButtonClickListener)
    {
        View vContent = LayoutInflater.from(context).inflate(layout, null);
        final Dialog dlg = new Dialog(context, R.style.common_dialog);
        dlg.setContentView(vContent);
        dlg.setCanceledOnTouchOutside(false); // 点击窗口外区域不消失
        dlg.setCancelable(false); // 返回键不消失

        // 必须用代码调整dialog的大小
        android.view.WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        //lp.width = (int) (MyApplication.s_nScreenWidth * 0.95);
        //lp.height = (int) (MyApplication.m_nScreenHeight * 0.5);
        lp.width = (int) context.getResources().getDimension(R.dimen.dialog_width);
        dlg.getWindow().setAttributes(lp);

        // left button
        TextView tvLeft =  vContent.findViewById(R.id.dialog_cancel);
        tvLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onLeftButtonClickListener != null)
                {
                    onLeftButtonClickListener.onClick(v);
                }
            }
        });

        // right button
        TextView tvRight = vContent.findViewById(R.id.dialog_sure);
        tvRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dlg.dismiss();
                if (onRightButtonClickListener != null)
                {
                    onRightButtonClickListener.onClick(v);
                }
            }
        });

        dlg.show();

        return dlg;
    }

    public static void showToast(Context context, String text)
    {
        Toast.makeText(context,text, Toast.LENGTH_LONG).show();
    }

    public static Uri getMediaUriFromPath(Context context, String path)
    {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[] {path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if(cursor.moveToFirst())
        {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
    }


    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";
    public static String delHTMLTag(String htmlStr)
    {
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");

        Pattern a_space = Pattern.compile("&nbsp;", Pattern.CASE_INSENSITIVE);
        Matcher b_space = a_space.matcher(htmlStr);
        htmlStr = b_space.replaceAll("");

        return htmlStr.trim(); // 返回文本字符串
    }

    public static int readPictureDegree(String path)
    {
        int degree  = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate)
    {
        if(bitmap == null)
            return null ;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * @param	bitmap      原图
     * @return  缩放Bitmap截取Bitmap最大中间部位的正方形。（根据Bitmap长宽判断截取）
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, Context context)
    {
        int m_nBitmapLength = 0; //希望得到的正方形部分的边长
        int m_nBitmapWidth = bitmap.getWidth();
        int m_nBitmapHeight = bitmap.getHeight();
        float m_fScale = context.getResources().getDisplayMetrics().density;
        int m_nScreenWidth = (int) (m_nBitmapWidth / m_fScale + 0.5f); // 屏幕宽度（dp）
        int m_nScreenHeight = (int) (m_nBitmapHeight / m_fScale + 0.5f); // 屏幕高度（dp）
        if (m_nScreenWidth > m_nScreenHeight)
        {
            m_nBitmapLength = m_nScreenHeight;
        }
        else
        {
            m_nBitmapLength = m_nScreenWidth;
        }
        if (null == bitmap || m_nBitmapLength <= 0)
        {
            return null;
        }

        Bitmap m_bitmapResult = bitmap;

        if (m_nBitmapWidth > m_nBitmapLength && m_nBitmapHeight > m_nBitmapLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (m_nBitmapLength * Math.max(m_nBitmapWidth, m_nBitmapHeight) / Math.min(m_nBitmapWidth, m_nBitmapHeight));
            int scaledWidth = m_nBitmapWidth > m_nBitmapHeight ? longerEdge : m_nBitmapLength;
            int scaledHeight = m_nBitmapWidth > m_nBitmapHeight ? m_nBitmapLength : longerEdge;
            Bitmap m_bitmapScaled;

            try
            {
                m_bitmapScaled = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch (Exception e)
            {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int m_nBitmapX = (scaledWidth - m_nBitmapLength) / 2;
            int m_nBitmapY = (scaledHeight - m_nBitmapLength) / 2;

            try
            {
                m_bitmapResult = Bitmap.createBitmap(m_bitmapScaled, m_nBitmapX, m_nBitmapY, m_nBitmapLength, m_nBitmapLength);
                m_bitmapScaled.recycle();
            }
            catch (Exception e)
            {
                return null;
            }
        }

        return m_bitmapResult;
    }

    public static boolean saveBitmap(Bitmap bitmap, String strDir, String strFileName)
    {
        File file = new File(strDir, strFileName);
        if (file.exists())
        {
            file.delete();
        }

        try
        {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static Fragment getTopVisibleFragment(FragmentManager fragmentManager)
    {
        Fragment fragment = getVisibleFragment(fragmentManager);

        if(fragment != null)
        {
            // 检查是否还有子Fragment
            FragmentManager fm = fragment.getChildFragmentManager();
            Fragment subFragment = getVisibleFragment(fm);
            if(subFragment != null)
            {
                fragment = subFragment;
            }
        }

        return fragment;
    }

    public static Fragment getVisibleFragment(FragmentManager fragmentManager)
    {
        List<Fragment> fragments = fragmentManager.getFragments();

        if(fragments == null)
        {
            return null;
        }

        for (Fragment fragment : fragments)
        {
            if (fragment != null && fragment.isVisible())
            {
                return fragment;
            }
        }

        return null;
    }

    public static String getAppVersionName(Context context)
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        }
        catch (Exception e)
        {
            Log.e("getAppVersion", "Exception", e);
            return "";
        }
    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static String getVersionCode(Context mContext)
    {
        String mVersionName = "";
        PackageManager packageManager = mContext.getPackageManager();
        try
        {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            mVersionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return "V" + mVersionName;
    }

//    public static void enlargeImage(Context context,final ImageView m_ivIcon,List<String> images,int position){
//        ImageTrans.with(context)
//                .setImageList(images)
//                .setSourceImageView(new SourceImageViewGet() {
//                    @Override
//                    public ImageView getImageView(int pos) {
//                        return m_ivIcon;
//                    }
//                })
//                .setImageLoad(new MyImageLoad())
//                .setNowIndex(position)
//                .setProgressBar(new MyProgressBarGet())
//                .setAdapter(new MyImageTransAdapter())
//                .show();
//    }

    /**
     *
     * @param v view
     * @param l Left
     * @param t Top
     * @param r Right
     * @param b Bottom
     */
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
