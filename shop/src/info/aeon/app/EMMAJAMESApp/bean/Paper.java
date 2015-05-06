package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 壁纸
 */
public class Paper {
    private String papername; // 壁纸名
    private String img;       // 壁纸
    private String width;     // 宽
    private String height;    // 高
    private String addtime;   // 添加时间
    
	public String getPapername() {
		return papername;
	}
	public void setPapername(String papername) {
		this.papername = papername;
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
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "Paper [papername=" + papername + ", img=" + img + ", width=" + width + ", height=" + height + ", addtime=" + addtime + "]";
	}

}
