package info.aeon.app.EMMAJAMESApp.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import info.aeon.app.EMMAJAMESApp.frame.GV;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncImageLoader {

     private HashMap<String, SoftReference<Drawable>> imageCache;
     private Context context;
      
     public AsyncImageLoader(Context context) {
    	 	this.context = context;
             imageCache = new HashMap<String, SoftReference<Drawable>>();
         }
      
     /**
      * 先根据传入的imageUrl查询是否已经缓存了这张图片，如果缓存了则取出缓存的Drawable返回，如果没有缓存
      * 就返回null，并启动一个异步加载这张网络图片。在加载成功后执行回调函数imageCallback.imageLoaded
      * 函数中的参数imageDrawable即为下载完成后的图片。
      * @param imageUrl
      * @param imageCallback
      * @return
      */
     public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {
    	 	//先尝试从内存缓存中读取这个url的图片
             if (imageCache.containsKey(imageUrl)) {
                 SoftReference<Drawable> softReference = imageCache.get(imageUrl);
                 Drawable drawable = softReference.get();
                 if (drawable != null) {
                     return drawable;
                 }
             }
             //再尝试从内存缓存中读取这个url的图片
             final String picName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
             String cachedPicPath = getCacheDir()+File.separator+picName;
             File file = new File(cachedPicPath);
     		 if (file.exists()) {
     			Bitmap bitmap = BitmapFactory.decodeFile(cachedPicPath);
                return new BitmapDrawable(bitmap);
     		 } 
            
             //最后不得已才从网络下载这个url的图片
             final Handler handler = new Handler() {
                 public void handleMessage(Message message) {
                	 Drawable drawable = (Drawable) message.obj;
                     imageCallback.imageLoaded(drawable, imageUrl);
                     Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                     saveExternalPic(bitmap,picName);
                 }
             };
             new Thread() {
                 @Override
                 public void run() {
                	//第一种下载方法，貌似比较慢
                    Drawable drawable = loadImageFromUrl(imageUrl);
                	 
                	 //第二种基于http的，速度要快一点
                     //Drawable drawable = downloadImageFromUrl(imageUrl);
                     imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                     Message message = handler.obtainMessage(0, drawable);
                     handler.sendMessage(message);
                 }
             }.start();
             return null;
         }
      
     
     //第一种下载方法，貌似比较慢
    public Drawable loadImageFromUrl(String url) {
            URL m;
            InputStream i = null;
            try {
                m = new URL(url);
                i = (InputStream) m.getContent();
            } catch (MalformedURLException e1) {
            	//Toast.makeText(context, context.getResources().getString(R.string.protocol_error), Toast.LENGTH_SHORT).show();
                //e1.printStackTrace();
            	//协议或者路径有问题
            } catch (IOException e) {
            	//Toast.makeText(context, context.getResources().getString(R.string.img_download_failed), Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            	//下载失败，可能是网络问题
            }
            Drawable d = (i != null)?Drawable.createFromStream(i, null):null;
            return d;
        }
  //第二种下载方法，貌似比较慢
    public Drawable downloadImageFromUrl(String url){
    	return new BitmapDrawable(ImageGetFromHttp.downloadBitmap(url));
    }
    /**
	 * 保存图片的方法 保存到sdcard
	 * 
	 * @throws IOException
	 */
	public String saveExternalPic(Bitmap b, String strFileName) {

		String cachePath = getCacheDir();
		FileOutputStream fos = null;
		File file = new File(cachePath, strFileName);
		if (file.exists()) {
			Log.i(strFileName, strFileName+"is exist!");
			return cachePath + "/" + strFileName;
		} 
		try {
			fos = new FileOutputStream(file);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				return cachePath+"/"+strFileName;
			}
		} catch (FileNotFoundException e) {
			Log.e("chuchuyajun","error ocurred when compress and save to png!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
    
    
    
    
    
    
    /** 获得缓存目录 **/
    private String getCacheDir() {
        String dir = getSDPath() + GV.CACHE_PIC_DIR;
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
        return dir;
    }
    
    /** 取SD卡路径 **/
    public String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		} else {
			Log.e("ERROR", "没有内存卡");
			return context.getFilesDir().getAbsolutePath();
		}
		return sdDir.toString();

	}
    
    
    public interface ImageCallback {
             public void imageLoaded(Drawable imageDrawable, String imageUrl);
         }
    
}