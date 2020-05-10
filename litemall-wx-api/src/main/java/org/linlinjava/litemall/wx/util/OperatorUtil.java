package org.linlinjava.litemall.wx.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OperatorUtil {
    /**
     * 获取账号绑定状态
     * @param fromUserName
     * @return
     */
	public String getBindStatus(String fromUserName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		//conn=new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					return "用户名已绑定";
				}else{
					return "请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
	}
	public List<String> getAllUser(){
		Connection conn=null;
		List<String> list=new ArrayList<String>();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		//conn=new DBCPConnection().getConnection();
		String selectSql="select * from qx_users ";
		if(conn==null){
			return null;
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				while(rs.next()){
					list.add(rs.getString("USERNAME"));
				}
			} catch (SQLException e) {
				return null;
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
		return list;
	}
	/**
	 * 绑定账号
	 * @param fromUserName
	 * @param userName
	 * @return
	 */
	public String bindAccount(String fromUserName,String userName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String result=getBindStatus(fromUserName);
		if("连接数据库失败".equals(result)||("查询数据库失败".equals(result))){
			return "连接数据库失败";
		}else if("用户名已绑定".equals(result)){
			return "用户名已绑定，无需重复绑定";
		}
		//conn = new DBCPConnection().getConnection();
		List<String> list=getAllUser();
		if(!list.contains(userName)){
			return "用户名不存在,绑定失败";
		}
	    int i = 0;
	    String sql = "insert into crm_stu_wechat (open_id,user_name) values(?,?)";
	    try {
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, fromUserName);
	    	ps.setString(2, userName);
	        i = ps.executeUpdate();
	        if(i>0){
	        	return "用户名绑定成功";
	        }else{
	        	return "用户名绑定失败";
	        }
	    } catch (SQLException e) {
	    	return "用户名绑定失败，未知错误";
	    }finally{
	    	closeConnection(conn,ps,rs);
	    }
	}
	/**
	 * 解除账号绑定
	 * @
	 * @param userName
	 * @return
	 */
	public String unBindAccount(String userName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		//conn = new DBCPConnection().getConnection();
	    int i = 0;
	    String sql=" delete from crm_stu_wechat where user_name= ?";
	    try {
	    	ps = conn.prepareStatement(sql);
	    	ps.setString(1, userName);
	        i = ps.executeUpdate();
	        if(i>0){
	        	return "用户名解除绑定成功";
	        }else{
	        	return "该用户名不存在";
	        }
	    } catch (SQLException e) {
	    	return "用户名解除绑定失败，未知错误";
	    }finally{
	    	closeConnection(conn,ps,rs);
	    }
	}
	/**
	 * 行程添加
	 * @param fromUserName
	 * @return
	 */
//	public String addTravel(String fromUserName){
//		Connection conn=null;
//	    PreparedStatement ps=null;
//	    ResultSet rs=null;
//		String sbStr="";
//		//conn = new DBCPConnection().getConnection();
//		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
//		if(conn==null){
//			return "连接数据库失败";
//		}else{
//			try {
//				ps=conn.prepareStatement(selectSql);
//				rs=ps.executeQuery();
//				if(rs.next()){
//					String userName=rs.getString("user_name");
//					String selectTravelSql="select * from crm_student where user_id='"+userName+"'";
//					//Connection connTravel= new DBCPConnection().getConnection();
//					//PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
//					//ResultSet rsTravel=psTravel.executeQuery();
////					if(rsTravel.next()){
////						String name=rsTravel.getString("id");
////						sbStr="点击添加行程";
////						return sbStr;
////					}else{
////						return "您未注册，请在电脑端完成注册";
////					}
//				}else{
//					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
//				}
//			} catch (SQLException e) {
//				return "查询数据库失败";
//			}finally{
//				closeConnection(conn,ps,rs);
//			}
//		}
//	}
	/**
	 * 行程查看
	 * @param fromUserName
	 * @return
	 */
	public String viewTravel(String fromUserName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		//conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectTravelSql="select * from crm_student_info where user_id='"+userName+"'";
					Connection connTravel= null; //new DBCPConnection().getConnection();
					PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
					ResultSet rsTravel=psTravel.executeQuery();
					while(rsTravel.next()){
						sb.append("考试时间：").append(rsTravel.getString("exam_time")).append("\n");
						sb.append("考试院校：").append(rsTravel.getString("exam_school")).append("\n");
						sb.append("考试地点：").append(rsTravel.getString("exam_area")).append("\n");
						sb.append("居住城市：").append(rsTravel.getString("live_city")).append("\n");
						sb.append("\n");  
						sb.append("---------------------------------");
						sb.append("\n");  
					}
					if((sb.toString()==null||("".equals(sb.toString())))){
						return "暂无您的行程";
					}else{
						return sb.toString();
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
	}
	/**
	 * 查看个人信息
	 * @return
	 */
	public String viewStuInfo(String fromUserName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		//conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu=null; //new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						sb.append("用户名:").append(rsStu.getString("user_id")).append("\t").append("\n");
						sb.append("姓名:").append(rsStu.getString("stu_name")).append("\t").append("\n");
						if("0".equals(rsStu.getString("stu_sex"))){
							sb.append("性别:").append("男").append("\t").append("\n");
						}else{
							sb.append("性别:").append("女").append("\t").append("\n");
						}
						sb.append("电话:").append(rsStu.getString("tel")).append("\t").append("\n");
						sb.append("生源地:").append(rsStu.getString("origin_area")).append("\t").append("\n");
						sb.append("父母电话:").append(rsStu.getString("parent_tel")).append("\t").append("\n");
						sb.append("身份证号:").append(rsStu.getString("idno")).append("\t").append("\n");
						sb.append("准考证号:").append(rsStu.getString("ticket_num")).append("\t").append("\n");
						if("1".equals(rsStu.getString("is_company"))){
							sb.append("是否父母陪同:").append("是").append("\n");
						}else{
							sb.append("是否父母陪同:").append("否").append("\n");
						}
						return sb.toString();
					}else{
						return "您未注册，请在电脑端完成注册";
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
	}
	/**
	 * 修改学生信息页面
	 * @param fromUserName
	 * @return
	 */
	public String editStuInfo(String fromUserName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String sbStr="";
		//conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu= null;//new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						String name=rsStu.getString("id");
						sbStr="点击修改个人信息";  
						return sbStr;
					}else{
						return "您未注册，请在电脑端完成注册";
					}
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
	}
	/**
	 * 修改行程
	 * @param fromUserName
	 * @return
	 */
	public String editTravel(String fromUserName){
		Connection conn=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
		String sbStr="";
		//conn = new DBCPConnection().getConnection();
		String selectSql="select * from crm_stu_wechat where open_id='"+fromUserName+"'";
		if(conn==null){
			return "连接数据库失败";
		}else{
			try {
				ps=conn.prepareStatement(selectSql);
				rs=ps.executeQuery();
				if(rs.next()){
					String userName=rs.getString("user_name");  
					String selectStuSql="select * from crm_student where user_id='"+userName+"'";
					Connection connStu= null;// new DBCPConnection().getConnection();
					PreparedStatement psStu=connStu.prepareStatement(selectStuSql);
					ResultSet rsStu=psStu.executeQuery();
					if(rsStu.next()){
						String selectTravelSql="select * from crm_student_info where user_id='"+userName+"'";
						Connection connTravel= null;//new DBCPConnection().getConnection();
						PreparedStatement psTravel=connTravel.prepareStatement(selectTravelSql);
						ResultSet rsTravel=psTravel.executeQuery();
						String name=rsStu.getString("id");
						if(!rsTravel.next()){
							sbStr="点击新增行程";  
							return sbStr;
						}else{
							sbStr="点击修改行程";  
							return sbStr;
						}
					}else{
						return "您未注册，请在电脑端完成注册";
					}
					
				}else{
					return "用户名未绑定,请输入\"用户名绑定\"+登录用户名  如:用户名绑定fangw";
				}
			} catch (SQLException e) {
				return "查询数据库失败";
			}finally{
				closeConnection(conn,ps,rs);
			}
		}
	}
	public void closeConnection(Connection conn,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                rs=null;
            }
        }
        
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                ps=null;
            }
        }
        
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                conn=null;
            }
        }
	}
}
