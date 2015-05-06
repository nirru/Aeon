package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 新着商品
 */
public class Goods {
    private String goodsname;  // 商品名
    private String url;        // 链接
    private String addtime;    // 添加时间
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "Goods [goodsname=" + goodsname + ", url=" + url + ", addtime=" + addtime + "]";
	}

}
