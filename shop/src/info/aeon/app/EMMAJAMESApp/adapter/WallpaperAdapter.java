package info.aeon.app.EMMAJAMESApp.adapter;

import java.util.List;

import info.aeon.app.EMMAJAMESApp.R;
import info.aeon.app.EMMAJAMESApp.bean.Paper;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader;
import info.aeon.app.EMMAJAMESApp.server.PreferenceHelper;
import info.aeon.app.EMMAJAMESApp.server.AsyncImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class WallpaperAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private AsyncImageLoader asyncImageLoader;
	private List<Paper> wallpapers;
	private ListView listView;
	private PreferenceHelper ph;
	
	public WallpaperAdapter(Context context,List<Paper> wallpapers,ListView listView) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.wallpapers = wallpapers;
		this.asyncImageLoader = new AsyncImageLoader(context);
		this.listView = listView;
		ph = new PreferenceHelper(context);
	}

	@Override
	public int getCount() {
		return wallpapers.size();
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
	public View getView(final int position, View convertView,final ViewGroup parent) {
		PaperHolder holder = null;
		if (convertView == null) {

			holder = new PaperHolder();

			convertView = mInflater.inflate(R.layout.wallpaper_item,null);
			holder.img = (ImageView) convertView.findViewById(R.id.wallpaper_album);
			holder.deciedBtn = convertView.findViewById(R.id.decide_btn);
			convertView.setTag(holder);

		} else {
			holder = (PaperHolder) convertView.getTag();
		}
		//holder.img.measure(0, 0);
		
		int width = holder.img.getWidth();
		int width1 = holder.img.getMeasuredWidth();
		Log.d(GV.TAG,"壁纸显示宽度为："+width+"    "+width1);
		
		final String imageUrl = GV.IMG_URL_BASE_PAPER + wallpapers.get(position).getImg();
		holder.img.setTag(imageUrl);
		//final View cv = convertView;
        final Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                	//cv.setVisibility(View.INVISIBLE);
                	if(imageDrawable == null){//下载图片失败
                		imageViewByTag.setImageResource(R.drawable.image_fail);
                		Toast.makeText(context, context.getResources().getString(R.string.img_download_failed), Toast.LENGTH_SHORT).show();
                	} else{
                		imageViewByTag.setScaleType(ScaleType.FIT_END);
                        imageViewByTag.setImageDrawable(imageDrawable);
                	}
                }
            }
        });
        if (cachedImage == null) {
        	//cv.setVisibility(View.INVISIBLE);
        	holder.img.setScaleType(ScaleType.CENTER);
        	holder.img.setImageResource(R.drawable.image_indicator);
        }else{
        	holder.img.setScaleType(ScaleType.FIT_END);
        	holder.img.setImageDrawable(cachedImage);
        }
		
		holder.deciedBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//GV.homeBgBitmap = ((BitmapDrawable)cachedImage).getBitmap();
				GV.homeBgDrawable = asyncImageLoader.loadDrawable(imageUrl,null);
				ph.setBrandWallpaper(GV.currentBrandSidValue, imageUrl);
				Toast.makeText(context, context.getResources().getString(R.string.wallpaper_set_successfully), Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	public final class PaperHolder {
		public ImageView img;
		public View deciedBtn;
	}

}