package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 优惠券
 * @author Administrator
 */
public class Coupon {
	
    private String couponname; // 优惠券名
    private String img;        // 优惠券图片
    private String width;      // 宽
    private String height;     // 高
    private String addtime;    // 添加时间
	public String getCouponname() {
		return couponname;
	}
	public void setCouponname(String couponname) {
		this.couponname = couponname;
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
		return "Coupon [couponname=" + couponname + ", img=" + img + ", width=" + width + ", height=" + height + ", addtime=" + addtime + "]";
	}
    
    

}
