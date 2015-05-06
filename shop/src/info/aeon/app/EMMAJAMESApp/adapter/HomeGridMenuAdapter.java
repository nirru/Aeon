package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.List;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.interfaces.GridViewItemTouchHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HomeGridMenuAdapter extends BaseAdapter {
	private LayoutInflater myInflater;
	private List<Integer> menuIconResIds;
	private GridViewItemTouchHelper callback;
	
	public HomeGridMenuAdapter(Context context, List<Integer> menuIconResIds,GridViewItemTouchHelper callback){
		myInflater = LayoutInflater.from(context);
		this.callback = callback;
		this.menuIconResIds = menuIconResIds;
	}

	@Override
	public int getCount() {
		return menuIconResIds.size();
	}

	@Override
	public Object getItem(int position) {
		return menuIconResIds.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconButton iconButton = new IconButton();
		if (convertView == null) {
			iconButton = new IconButton();
			convertView = myInflater.inflate(R.layout.home_gridmenu_item, null);
			iconButton.iconImage = (ImageView) convertView.findViewById(R.id.home_gridmenu_icon);
			convertView.setTag(iconButton);
		} else {
			iconButton = (IconButton) convertView.getTag();
		}
		iconButton.iconImage.setBackgroundResource(menuIconResIds.get(position));
		final int p = position;
		iconButton.iconImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callback.onIconGridItemTouch(v,  p);				
			}
		});
		
		/*final int p = position;
		 final OnTouchListener touchLight = new OnTouchListener() {
	         
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
		            	callback.onIconGridItemTouch(v,  p);
		                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
		                v.setBackgroundDrawable(v.getBackground());
		            }
		            return false;
		        }
		    };
		    
		    iconButton.iconImage.setOnTouchListener(touchLight);
		*/
		return convertView;
	}
	
	private class IconButton {

		public ImageView iconImage;
	}
}
