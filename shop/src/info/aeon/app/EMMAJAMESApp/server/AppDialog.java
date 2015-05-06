package info.aeon.app.EMMAJAMESApp.server;

import info.aeon.app.EMMAJAMESApp.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class AppDialog extends Dialog {

	public final static int NO_DIALOG_TITLE = -1;

	public AppDialog(Context context, int dialogLayout,	Boolean ifOutsideCancle, float dialogAlph, float dimAmount) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(dialogLayout); // 最好放在这两句中间
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, titleLayout);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 获取圆角对话框布局View，背景设为圆角
		final View dialogView = inflater.inflate(dialogLayout, null, false);
		dialogView.setBackgroundResource(R.drawable.diglog_back_drawable);

		setCanceledOnTouchOutside(ifOutsideCancle);// 点击对话框外部取消对话框显示
		LayoutParams lp = getWindow().getAttributes();

		getWindow().addFlags(LayoutParams.FLAG_BLUR_BEHIND);// 添加模糊效果

		// 设置透明度，对话框透明(包括对话框中的内容)alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明
		lp.alpha = dialogAlph;
		lp.dimAmount = dimAmount;// 设置对话框显示时的黑暗度，0.0f和1.0f之间，在我这里设置成0.0f会出现黑屏状态

		getWindow().setAttributes(lp);

		setOwnerActivity((Activity) context);// )把对话框附着到一个Activity上
	}

}
