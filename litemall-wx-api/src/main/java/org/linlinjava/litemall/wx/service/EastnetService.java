package org.linlinjava.litemall.wx.service;


import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.message.req.TextMessage;
import org.linlinjava.litemall.wx.util.MessageUtil;
import org.linlinjava.litemall.wx.util.OperatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 解耦，使控制层与业务逻辑层分离开来，主要处理请求，响应
 *
 * @author pengsong
 * @date 2016.01.19
 */
@Service
public class EastnetService {

    @Autowired
    private LitemallUserService litemallUserService;

    public String processRequest(HttpServletRequest request,HttpServletResponse response) {
        String respMessage = null;
        //默认返回的文本消息类容
        String respContent = "请求处理异常，请稍后尝试！";
        String fromUserName = "";
        String toUserName = "";
        String msgType = "";
        try {
            //xml请求解析
            Map<String, String> requestMap = MessageUtil.pareXml(request);

            //发送方账号（open_id）
            fromUserName = requestMap.get("FromUserName");
            //公众账号
            toUserName = requestMap.get("ToUserName");
            //消息类型
            msgType = requestMap.get("MsgType");

            System.out.println("fromUserName=" + fromUserName);
            System.out.println("toUserName=" + toUserName);
            System.out.println("msgType=" + msgType);
            //String eventType = requestMap.get("Event");
            String fromContent = requestMap.get("Content");
            System.out.println("fromContent=" + fromContent);
            String userName = "";
            if ((MessageUtil.REQ_MESSSAGE_TYPE_EVENT).equals(msgType)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                System.out.println("eventType=" + eventType);
                if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    System.out.println("eventKey=" + eventKey);
                    if ("31".equals(eventKey)) {//个人信息修改
                        respContent = "个人中心";
                    } else if ("32".equals(eventKey)) {
                        respContent = "我的订单";
                    } else if ("11".equals(eventKey)) {//蔬菜商城的跳转
                        List<LitemallUser> litemallUsers = litemallUserService.queryByOpenid(fromUserName);
                        if(litemallUsers.size()==0){//没有进行绑定
                            respContent = "您必须绑定后才可进入商城！   绑定账号:请回复  用户名绑定+手机号,例:用户名绑定12345678911";
                        }else if (litemallUsers.size()==1){
                            System.out.println("okokokokokokokok");
                            LitemallUser user = litemallUsers.get(0);
                            response.sendRedirect("fxjykx.natappfree.cc/#" + "/login/wxgzhLogin?openId=" + user.getWeixinOpenid());
                        }

                    } else if ("stuTravelAdd".equals(eventKey)) {//行程添加
                        //respContent=new OperatorUtil().addTravel(fromUserName);
                    } else if ("stuTravelEdit".equals(eventKey)) {//行程修改
                        respContent = new OperatorUtil().editTravel(fromUserName);
                    } else if ("help".equals(eventKey)) {//操作说明
                        respContent = "绑定账号:请回复  用户名绑定+用户名,例:用户名绑定fangw";
                    } else if ("callAdmin".equals(eventKey)) {//呼叫管理员
                        respContent = "已通知管理员，稍后管理员会与您联系";
                    } else if ("suggestions".equals(eventKey)) {//意见反馈
                        respContent = "意见反馈被点击";
                    } else {
                        respContent = "请求失败";
                    }
                }
            }
            //订阅
            String eventTypeSub = requestMap.get("Event");
            if ((MessageUtil.EVENT_TYPE_SUBSCRIBE).equals(eventTypeSub)) {
                respContent = "蔬菜商城欢迎迎您的到来！ \n 回复\"用户名绑定\"+登录手机  如:用户名绑定12345678911  可完成账号绑定！\n 只有绑定账号后才可以实现接下来的操作";
            }
            //event
//			if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
//				String EventKey=requestMap.get("EventKey");
//				if("stuInfoEdit".equals(EventKey)){
//					respContent=new OperatorUtil().editTravel(fromUserName);
////					respMessage=("<xml><ToUserName><![CDATA["+requestMap.get("FromUserName")+
////							"]]></ToUserName>"+"<FromUserName><![CDATA["+requestMap.get("ToUserName")
////							+"]]></FromUserName><CreateTime>"+System.currentTimeMillis()+"</CreateTime><MsgType><![CDATA[event]]></MsgType><Content><![CDATA["+respContent+"]]></Content></xml>");
////					return respMessage;
//				}
//			}
            if (fromContent.contains("用户名绑定")) {
                String mobile = fromContent.substring(5).trim();
                System.out.println();
                int i = litemallUserService.wxUpdateByMobile(mobile, fromUserName);
                if (i > 0) {
                    respContent = "用户名绑定成功";
                } else {
                    respContent = "用户名绑定失败";
                }
            }
            if (fromContent.contains("解除绑定")) {
                String mobile = fromContent.substring(4).trim();
                List<LitemallUser> litemallUsers = litemallUserService.queryByMobile(mobile);
                if (litemallUsers.size() == 0) {
                    respContent = "该手机没有注册，请去注册！";
                } else if (litemallUsers.size() == 1) {
                    LitemallUser user = litemallUsers.get(0);
                    String weixinOpenid = user.getWeixinOpenid();
                    if ("".equals(weixinOpenid)) {
                        respContent = "该手机没有绑定，无法解绑！";
                    } else {
                        if (weixinOpenid.equals(fromUserName)) {
                            int i = litemallUserService.wxUpdateByMobile(mobile, "");
                            if (i == 1) {
                                respContent = "解绑成功！";
                            } else if (i == 0) {
                                respContent = "解绑失败！";
                            }
                        } else {
                            respContent = "您不具备管理员权限";
                        }
                    }
                }
            }
            if ("行程查看".equals(fromContent)) {
                respContent = new OperatorUtil().viewTravel(fromUserName);
            }
            if ("行程添加".equals(fromContent)) {
                //respContent=new OperatorUtil().addTravel(fromUserName);
            }
            if ("行程修改".equals(fromContent)) {
                respContent = new OperatorUtil().editTravel(fromUserName);
            }
            if ("帮助".equals(fromContent)) {
                respContent = "绑定账号:请回复  用户名绑定+用户名,例:用户名绑定fangw\n行程查看:请回复  行程查看\n行程添加:请回复  行程添加\n行程修改:请回复  行程修改\n";
            }
            //回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(toUserName);
            textMessage.setFromUserName(fromUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSSAGE_TYPE_TEXT);
            //textMessage.setFuncFlag(0);
            StringBuffer sb = new StringBuffer();
            //文本消息
            if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_TEXT)) {
                //Connection conn=new DBCPConnection().getConnection();
//				if(conn==null){
//					respContent="连接数据库失败";
//				}else{
//					int count =0;
//					String sql="select * from crm_student_info";
//			        PreparedStatement ps=conn.prepareStatement(sql);
//			        ResultSet rs=ps.executeQuery();
//		            while (rs.next()) {
//		            	if(count>6){
//		            		break;
//		            	}
//		                String name=rs.getString("exam_time");
//		                sb.append(name).append("/n");
//		                count++;
//		            }
//				}
//				respContent=sb.toString();
                //respContent = new OpenDBConnection().selectData("select * from crm_student_info");
            }
            //图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            //地理位置
            else if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            //链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            //音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            //事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSSAGE_TYPE_EVENT)) {
                //事件类型
                String eventType = requestMap.get("Event");
                //订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢关注！";
                }
                //取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    //
                    System.out.println("取消订阅");
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    //自定义菜单消息处理
                    System.out.println("自定义菜单消息处理");
                }
            }
            textMessage.setContent(respContent);
            respMessage = ("<xml><ToUserName><![CDATA[" + requestMap.get("FromUserName") +
                    "]]></ToUserName>" + "<FromUserName><![CDATA[" + requestMap.get("ToUserName")
                    + "]]></FromUserName><CreateTime>" + System.currentTimeMillis() + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + respContent + "]]></Content></xml>");
            if ("".equals(respContent) || (respContent == null)) {
                respMessage = ("<xml><ToUserName><![CDATA[" + requestMap.get("FromUserName") +
                        "]]></ToUserName>" + "<FromUserName><![CDATA[" + requestMap.get("ToUserName")
                        + "]]></FromUserName><CreateTime>" + System.currentTimeMillis() + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[查无此人]]></Content></xml>");
            } else {
                respMessage = ("<xml><ToUserName><![CDATA[" + requestMap.get("FromUserName") +
                        "]]></ToUserName>" + "<FromUserName><![CDATA[" + requestMap.get("ToUserName")
                        + "]]></FromUserName><CreateTime>" + System.currentTimeMillis() + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + respContent + "]]></Content></xml>");
            }

            //respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            respMessage = ("<xml><ToUserName><![CDATA[" + fromUserName +
                    "]]></ToUserName>" + "<FromUserName><![CDATA[" + toUserName
                    + "]]></FromUserName><CreateTime>" + System.currentTimeMillis() + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + respContent + "]]></Content></xml>");
        }
        return respMessage;
    }

    public List<LitemallUser> findUserByOpenId(String openId) {
        List<LitemallUser> litemallUsers = litemallUserService.queryByOpenid(openId);

        return litemallUsers;
    }
}
