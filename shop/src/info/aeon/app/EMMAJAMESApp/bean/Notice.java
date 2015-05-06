package info.aeon.app.EMMAJAMESApp.bean;
/**
 * 收藏店铺的通知
 */
public class Notice {
    private String content; // 通知内容
    private String addtime; // 通知时间
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "Notice [content=" + content + ", addtime=" + addtime + "]";
	}

}
