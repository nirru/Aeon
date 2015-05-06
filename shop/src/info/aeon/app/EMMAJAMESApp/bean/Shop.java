package info.aeon.app.EMMAJAMESApp.bean;

/**
 * 店铺区域
 */
public class Shop {
    private String sid;      // 店铺id  
    private String shopname; // 店铺名
    private String area;	 // 所属区域
    private String addtime;  // 添加时间
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "Shop [sid=" + sid + ", shopname=" + shopname + ", area=" + area + ", addtime=" + addtime + "]";
	}

}
