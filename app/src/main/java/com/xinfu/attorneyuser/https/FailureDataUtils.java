package com.xinfu.attorneyuser.https;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xinfu.attorneyuser.LoginActivity;
import com.xinfu.attorneyuser.R;
import com.xinfu.attorneyuser.bean.response.ResponseBaseBean;
import com.xinfu.attorneyuser.bean.response.ResponseFocusBean;
import com.xinfu.attorneyuser.utils.Utils;

public class FailureDataUtils{

    public static void showServerReturnErrorMessageFragment(Context context, ResponseBaseBean response, final View.OnClickListener clickListener)
    {
        if(response.getStatus() == HttpCode.HTTPCODE_ERROR)
        {
            if(response.getResult().equals("用户尚未登录"))
            {
                View.OnClickListener onOkClickListener = v -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);

                    if(clickListener != null)
                    {
                        clickListener.onClick(v);
                    }
                };

                FailureDataUtils.showConfirmDialog(context, "", "登录超时。请重新登录！", onOkClickListener, false);
            }
            else
            {
                Utils.showToast(context,response.getResult());
            }
        }
    }

    public static void showServerReturnErrorMessageFragment(Context context, ResponseBaseBean response, final int iIntentType)
    {
        if(response.getStatus() == HttpCode.HTTPCODE_ERROR)
        {
            if(response.getResult().equals("用户尚未登录"))
            {
                View.OnClickListener onOkClickListener = v -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("iLoginType", iIntentType);
                    context.startActivity(intent);
                };

                FailureDataUtils.showConfirmDialog(context, "", "登录超时。请重新登录！", onOkClickListener, false);
            }
        }
    }

    public static void showServerReturnErrorMessageFragment(Context context, ResponseFocusBean response, final int iIntentType)
    {
        if(response.getStatus() == HttpCode.HTTPCODE_ERROR)
        {
            if(response.getResult().equals("用户尚未登录"))
            {
                View.OnClickListener onOkClickListener = v -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("iLoginType", iIntentType);
                    context.startActivity(intent);
                };

                FailureDataUtils.showConfirmDialog(context, "", "登录超时。请重新登录！", onOkClickListener, false);
            }
        }
    }

    public static void showServerReturnShowErrorMessageFragment(Context context, ResponseBaseBean response, final int iIntentType)
    {
        if(response.getStatus() == HttpCode.HTTPCODE_ERROR)
        {
            if(response.getResult().equals("用户尚未登录"))
            {
                View.OnClickListener onOkClickListener = v -> {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("iLoginType", iIntentType);
                    context.startActivity(intent);
                };

                FailureDataUtils.showConfirmDialog(context, "", "登录超时。请重新登录！", onOkClickListener, false);
            }
            else
            {
                Utils.showToast(context,response.getResult());
            }
        }
    }

    public static void showServerReturnErrorMessageEx(Context context, ResponseBaseBean response, final View.OnClickListener clickListener)
    {
        if(response.getStatus() == HttpCode.HTTPCODE_ERROR)
        {
            if(response.getResult().equals("用户尚未登录"))
            {
                View.OnClickListener onOkClickListener = v -> {
                    if(clickListener != null)
                    {
                        clickListener.onClick(v);
                    }
                };

                FailureDataUtils.showConfirmDialog(context, "", "登录超时。请重新登录！", onOkClickListener, false);
            }
            else
            {
                Utils.showToast(context,response.getResult());
            }
        }
    }

    public static void showConfirmDialog(final Context context,
                                         final String strTitle,
                                         final String strMsg,
                                         final View.OnClickListener onOkClickListener,
                                         final Boolean bShowCancelButton)
    {
        View vContent = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
        final Dialog dlg = new Dialog(context, R.style.common_dialog);
        dlg.setContentView(vContent);

        dlg.setCanceledOnTouchOutside(false); // 点击窗口外区域不消失

        if(onOkClickListener != null)
        {
            dlg.setCancelable(false); // 返回键不消失
        }

        // 必须用代码调整dialog的大小
        android.view.WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        //int nScreenWidth = m_context.getResources().getDisplayMetrics().widthPixels;
        //int nScreenHeight = m_context.getResources().getDisplayMetrics().heightPixels;
        lp.width = (int) context.getResources().getDimension(R.dimen.dialog_width);
        dlg.getWindow().setAttributes(lp);

        String strRealTitle = strTitle;
        String strRealMsg = strMsg;
        if(strRealTitle == null || strRealTitle.isEmpty())
        {
            strRealTitle = strRealMsg;
            strRealMsg = "";
        }

        TextView tvTitle = (TextView) vContent.findViewById(R.id.tv_title);
        if (strRealTitle != null && !strRealTitle.isEmpty())
        {
            tvTitle.setText(strRealTitle);
        }
        else
        {
            tvTitle.setVisibility(View.GONE);
        }

        TextView tvMsg = (TextView) vContent.findViewById(R.id.tv_msg);
        if (strRealMsg != null && !strRealMsg.isEmpty())
        {
            tvMsg.setText(Html.fromHtml(strRealMsg));
        }
        else
        {
            vContent.findViewById(R.id.ll_msg_area).setVisibility(View.GONE);
        }

        Button btnConfirm = (Button) vContent.findViewById(R.id.btn_confirm);
        View vTwoButtonArea = vContent.findViewById(R.id.ll_two_button_area);

        if (bShowCancelButton)
        {
            btnConfirm.setVisibility(View.GONE);

            Button btnOk = (Button) vContent.findViewById(R.id.btn_ok);

            assert (onOkClickListener != null);
            //btnOk.setOnClickListener(onOkClickListener);
            btnOk.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    dlg.dismiss();
                    onOkClickListener.onClick(v);
                }
            });

            Button btnCancel = (Button) vContent.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(v -> dlg.dismiss());
        }
        else
        {
            vTwoButtonArea.setVisibility(View.GONE);

            btnConfirm.setOnClickListener(v -> {
                dlg.dismiss();

                if (onOkClickListener != null)
                {
                    onOkClickListener.onClick(v);
                }
            });
        }

        if(!((Activity) context).isFinishing())
        {
            dlg.show();
        }
    }
}
