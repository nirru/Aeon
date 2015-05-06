package info.aeon.app.EMMAJAMESApp.server;


import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
/**
 * densityDpi ÿӢ����ٸ����ص�,
 * density =  densityDpi /160
 * APP���densityDpi�Ĵ�С���������ĸ��ļ����µ�ͼƬ����ϵ���£�
 * drawable-ldpi ���ܶȣ�ͨ����ָ120
 * drawable-mdpi �е��ܶȣ�ͨ����ָ160
 * drawable-xhdpi �����ܶȣ�ͨ����ָ320
 * px = dip*density 
 * @author Administrator
 *
 */
public class DisplayUtils {
	//==============================================================
	//          ��ȡ��Ļ�Ŀ�Ⱥ͸߶�--�Ѿ�������
	//==============================================================
	/**
	 * �õ���Ļ����ʾ���,��λΪpixel�����Ƽ�ʹ��
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getDisplayWidth(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return display.getWidth();
	}
	/**
	 * �õ���Ļ����ʾ�߶�,��λΪpixel�����Ƽ�ʹ��
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getDisplayHeight(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return display.getHeight();
	}
	
	
	//==============================================================
	//          ��ȡ��Ļ�Ŀ�ȡ��߶��Լ���ʾ�ܶ�
	//==============================================================
	/**
	 * �õ���Ļ����ʾ���,��λΪpixel
	 * @param context
	 * @return
	 */
	public static int getDisplayMetricsWidth(Context context){
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	/**
	 * �õ���Ļ����ʾ�߶�,��λΪpixel
	 * @param context
	 * @return
	 */
	public static int getDisplayMetricsHeight(Context context){
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	/**
	 * �õ���Ļ����ʾ�ܶ�
	 * @param context
	 * @return
	 */
	public static int getDisplayMetricsdensityDpi(Context context){
		return context.getResources().getDisplayMetrics().densityDpi;
	}
	
	//==============================================================
	//          dip��pix֮���ת��
	//==============================================================
	 /**
     * ��pxֵת��Ϊdip��dpֵ����֤�ߴ��С����
     * 
     * @param pxValue
     * @param scale ��DisplayMetrics��������density��
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����
     * 
     * @param dipValue
     * @param scale ��DisplayMetrics��������density��
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) { 
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     * 
     * @param pxValue
     * @param fontScale ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * ��spֵת��Ϊpxֵ����֤���ִ�С����
     * 
     * @param spValue
     * @param fontScale ��DisplayMetrics��������scaledDensity��
     * @return
     */ 
    public static int sp2px(Context context, float spValue) { 
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
}
