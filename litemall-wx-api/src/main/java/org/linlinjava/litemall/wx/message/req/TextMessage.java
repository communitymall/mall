package org.linlinjava.litemall.wx.message.req;
/**
 * 文本消息
 * @author pengsong
 * @date 2016.01.19
 */
public class TextMessage extends BaseMessage{
	//消息内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
