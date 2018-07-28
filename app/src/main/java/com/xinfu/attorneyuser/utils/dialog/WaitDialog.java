package com.xinfu.attorneyuser.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.xinfu.attorneyuser.R;

/**
 * 无进度的加载对话框
 */
public class WaitDialog extends Dialog {

	//单例
	private static WaitDialog mWaitDialog = null;

	private Context mContext;
	private ImageView icon;
	private String contentString = "律界通";
	private TextView content;
	private boolean isClose=true;

	public void setClose(boolean close) {
		isClose = close;
	}

	private Animation animation = null;

	public WaitDialog(Context context) {
		super(context, R.style.normal_dialog);
		this.mContext = context;
	}

	public static WaitDialog getInstace(Context context)
	{
		if(mWaitDialog == null)
		{
			mWaitDialog = new WaitDialog(context);
		}
		return mWaitDialog;
	}

	private void initWidget() {
		this.icon = ((ImageView) findViewById(R.id.dialog_wait_icon));
		this.content = ((TextView) findViewById(R.id.dialog_wait_content));
	}

	private void init() {
		animation = AnimationUtils.loadAnimation(mContext,
				R.anim.loading_animation);
	}

	private void setAnimation() {
		this.icon.startAnimation(this.animation);
	}

	public void setContent(String paramString) {
		this.contentString = paramString;
		if (this.content != null)
			this.content.setText(this.contentString);
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.dialog_wait);
		initWidget();
		init();
	}

	public void show() {
		super.show();
		setAnimation();
		this.content.setText(this.contentString);
	}

	@Override
	public void onBackPressed() {
		if(isClose) {
			super.onBackPressed();
		}
	}
}
