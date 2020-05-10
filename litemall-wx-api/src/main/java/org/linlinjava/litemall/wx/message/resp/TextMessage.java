package org.linlinjava.litemall.wx.message.resp;
/**
 * 响应文本消息
 * @author pengsong
 * @date 2016.01.19
 */
public class TextMessage extends BaseMessage{
	//回复的消息类容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
