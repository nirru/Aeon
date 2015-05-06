package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 壁纸
 */
public class Top {
    private String img;    // 壁纸地址
    private String width;  // 宽
    private String height; // 高
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
	@Override
	public String toString() {
		return "Top [img=" + img + ", width=" + width + ", height=" + height + "]";
	}

}
