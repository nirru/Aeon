package info.aeon.app.EMMAJAMESApp.view;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.frame.GV;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


public class DarkImageButton extends ImageView {

    public DarkImageButton(Context context) {
        super(context);
        setFocusable(false);
        init(context, null);
    }

    public DarkImageButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.imageButtonStyle);
        init(context, attrs);
        setFocusable(false);
    }

    public DarkImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        setFocusable(false);
    }

    private void init(Context context, AttributeSet attrs) {
        setBackgroundDrawable(createStateDrawable(context, getBackground()));//布局里设置background
       // setImageDrawable(createStateDrawable(context, getBackground()));//布局里设置Src
        setFocusable(true);
    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        return false;
    }

    public StateListDrawable createStateDrawable(Context context,
            Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(View.PRESSED_ENABLED_STATE_SET,
                createPressDrawable(normal));
        drawable.addState(View.ENABLED_STATE_SET, normal);
        drawable.addState(View.EMPTY_STATE_SET, normal);
        return drawable;
    }
    
   /* public Drawable createPressDrawable(Drawable d){
    	Bitmap bitmap = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true);
    	Bitmap bitmap2 = bitmap.extractAlpha();
    	Paint paint = new Paint();
        paint.setColor(0x60000000);
    	new Canvas(bitmap).drawBitmap(bitmap2, 0, 0, paint);
    	return new BitmapDrawable(bitmap);
    }*/
    
    
    public Drawable createPressDrawable(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int len = w*h;
        int[] pixels = new int[len];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);
        for(int i=0; i<len;i++){
        	if(pixels[i] != 0x00000000)
        		pixels[i] = getResources().getColor(R.color.tran_dark_color);;
        }
        new Canvas(bitmap).drawBitmap(pixels, 0, w, 0, 0, w, h, true, null);
       return new BitmapDrawable(bitmap);
    }
    
    
    
    
    
    
    
    
    
    //----------------------------------------------------------------------
    // 另一种实现不用自己过滤透明区域
    //----------------------------------------------------------------------

	/* public static final OnTouchListener TouchLight = new OnTouchListener() {
		 
	        public final float[] BT_SELECTED = new float[] {1,0,0,0,50,0,1,0,0,50,0,0,1,0,50,0,0,0,1,0};
	        public final float[] BT_NOT_SELECTED = new float[] {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0};
	 
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
	            	Log.d("chuchuyajun","TouchLight:ACTION_DOWN");
	                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
	                v.setBackgroundDrawable(v.getBackground());
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	Log.d("chuchuyajun","TouchLight:ACTION_UP");
	                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
	                v.setBackgroundDrawable(v.getBackground());
	            }
	            return false;
	        }
	    };
	     
	    public static final OnTouchListener TouchDark = new OnTouchListener() {
	         
	        public final float[] BT_SELECTED = new float[] {1,0,0,0,-50,0,1,0,0,-50,0,0,1,0,-50,0,0,0,1,0};
	        public final float[] BT_NOT_SELECTED = new float[] {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0};
	         
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
	            	Log.d("chuchuyajun","TouchDark:ACTION_DOWN");
	                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
	                v.setBackgroundDrawable(v.getBackground());
	            } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            	Log.d("chuchuyajun","TouchDark:ACTION_UP");
	                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
	                v.setBackgroundDrawable(v.getBackground());
	            }
	            return true;
	        }
	    };*/
}