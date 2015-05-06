package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.List;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Album;
import info.aeon.app.EMMAJAMESApp.frame.FrameActivity;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader.ImageCallback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CollectionAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private AsyncImageLoader asyncImageLoader;
	private List<Album> collections;
	private ListView listView;
	
	public CollectionAdapter(Context context,List<Album> collections,ListView listView) {
		this.mInflater = LayoutInflater.from(context);
		this.collections = collections;
		this.asyncImageLoader = new AsyncImageLoader(context);
		this.listView = listView;
	}

	@Override
	public int getCount() {
		return collections.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		NewsHolder holder = null;
		if (convertView == null) {

			holder = new NewsHolder();

			convertView = mInflater.inflate(R.layout.collection_item,null);
			holder.collectionName = (TextView)convertView.findViewById(R.id.collection_name);
			holder.collectionAlbum = (ImageView) convertView.findViewById(R.id.collection_album);
			convertView.setTag(holder);

		} else {
			holder = (NewsHolder) convertView.getTag();
		}
		
		holder.collectionName.setText(collections.get(position).getAlbumname());
		
		final String imageUrl = GV.IMG_URL_BASE_ALBUM+collections.get(position).getImg();
		holder.collectionAlbum.setTag(imageUrl);
		//final View cv = convertView;
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                	//cv.setVisibility(View.INVISIBLE);
                    imageViewByTag.setBackgroundDrawable(imageDrawable);
                    //imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage != null) {
        	holder.collectionAlbum.setBackgroundDrawable(cachedImage);
        	//holder.collectionAlbum.setImageDrawable(cachedImage);
        }else{
        	//cv.setVisibility(View.INVISIBLE);
        	//holder.collectionAlbum.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.image_indicator));
        	//holder.collectionAlbum.setImageResource(R.drawable.image_indicator);
            //Log.e(GV.TAG, "还没加载完图片的时候");
        }
		
        final int p = position;
		 final OnTouchListener TouchDark = new OnTouchListener() {
	         
		        public final float[] BT_SELECTED = new float[] {1,0,0,0,-50,0,1,0,0,-50,0,0,1,0,-50,0,0,0,1,0};
		        public final float[] BT_NOT_SELECTED = new float[] {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0};
		         
		        @SuppressWarnings("deprecation")
				@SuppressLint("ClickableViewAccessibility")
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
		                GV.webpageCurrentUrl = collections.get(p).getUrl();
		                FrameActivity.showTabActivity(GV.TAB_WEB);
		            }
		            return true;
		        }
		    };
		    
		    
		    holder.collectionAlbum.setOnTouchListener(TouchDark);
        
        
		return convertView;
	}

	public final class NewsHolder {
		public ImageView collectionAlbum;
		public TextView collectionName;
	}

}