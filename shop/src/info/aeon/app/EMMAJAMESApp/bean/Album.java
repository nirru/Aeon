package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 特集
 * @author Administrator
 */
public class Album {
	private String albumname;    // 特集名
	private String img;          // 特集图片地址（完整地址待给出）
	private String width;        // 图片的宽
	private String height;       // 图片的高
	private String url;          // 点击图片的跳转链接
	private String asstime;      // 添加时间
	public String getAlbumname() {
		return albumname;
	}
	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAsstime() {
		return asstime;
	}
	public void setAsstime(String asstime) {
		this.asstime = asstime;
	}
	@Override
	public String toString() {
		return "Album [albumname=" + albumname + ", img=" + img + ", width=" + width + ", height=" + height + ", url=" + url + ", asstime=" + asstime + "]";
	}
	
	

}
