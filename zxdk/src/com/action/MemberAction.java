/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

 
import com.bean.ComBean;
import com.util.Constant; 

/** 
 * MyEclipse Struts
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class MemberAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("gb2312");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}	
		String strr="";
		try{
			String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			String date2=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			String method=request.getParameter("method").trim();
			ComBean cBean = new ComBean();
			HttpSession session = request.getSession();   
			if(method.equals("mreg")){ //ѧ��ע��
				String username = request.getParameter("username"); 
				String password = request.getParameter("password"); 
				String realname = request.getParameter("realname"); 
				String sex = request.getParameter("sex"); 
				String age = request.getParameter("age"); 
				String address = request.getParameter("address"); 
				String email = request.getParameter("email");
				String str=cBean.getString("select id from member where username='"+username+"'");
				if(str==null){
					int flag=cBean.comUp("insert into member(username,password,realname,sex,age,address,email,regtime) " +
							"values('"+username+"','"+password+"','"+realname+"','"+sex+"','"+age+"','"+address+"','"+email+"','"+date+"')");
					if(flag == Constant.SUCCESS){ 
						request.setAttribute("message", "ע��ɹ����¼��");
						request.getRequestDispatcher("login.jsp").forward(request, response); 
					}
					else { 
						request.setAttribute("message", "����ʧ�ܣ�");
						request.getRequestDispatcher("reg.jsp").forward(request, response); 
					}
				}
				else{
					request.setAttribute("message", "���û����Ѵ��ڣ�");
					request.getRequestDispatcher("reg.jsp").forward(request, response); 
				} 
			}
			else if(method.equals("mupreg")){ //ѧ���޸�ע������
				String member=(String)session.getAttribute("member"); 
				String realname = request.getParameter("realname"); 
				String sex = request.getParameter("sex"); 
				String age = request.getParameter("age"); 
				String address = request.getParameter("address"); 
				String email = request.getParameter("email"); 
				int flag=cBean.comUp("update member set realname='"+realname+"',sex='"+sex+"',age='"+age+"'," +
						"address='"+address+"',email='"+email+"' where username='"+member+"'");
				if(flag == Constant.SUCCESS){ 
					request.setAttribute("message", "�����ɹ���");
					request.getRequestDispatcher("member/info/index.jsp").forward(request, response); 
				}
				else { 
					request.setAttribute("message", "����ʧ�ܣ�");
					request.getRequestDispatcher("member/info/index.jsp").forward(request, response); 
				}
			}
			else if(method.equals("mlogin")){//ѧ����¼
				String username = request.getParameter("username"); 
				String password = request.getParameter("password"); 
				String sf = request.getParameter("sf"); 
				if(sf.equals("ѧ��")){
					String str=cBean.getString("select id from member where username='"+username+"' and  password='"+password+"'  and ifuse='����'");
					if(str==null){
						request.setAttribute("message", "��¼��Ϣ����");
						request.getRequestDispatcher("login.jsp").forward(request, response); 
					}
					else{
						session.setAttribute("member", username); 
						request.getRequestDispatcher("member/index.jsp").forward(request, response); 
					}
				}
				else{
					String str=cBean.getString("select id from admin where username='"+username+"' and  password='"+password+"' and  sf='"+sf+"' ");
					if(str==null){
						request.setAttribute("message", "��¼��Ϣ����");
						request.getRequestDispatcher("login.jsp").forward(request, response); 
					}
					else{
						session.setAttribute("user", username); session.setAttribute("sf", sf); 
						request.getRequestDispatcher("admin/index.jsp").forward(request, response); 
					}
				}
				
			}
			else if(method.equals("lost")){ //��ʧ����
				request.setAttribute("message", "�������Ѿ�����ע�����䣡");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else if(method.equals("memberexit")){ //�˳���¼
				session.removeAttribute("member");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
			else if(method.equals("muppwd")){//ѧ���޸�����
				String member=(String)session.getAttribute("member"); 
				String oldpwd = request.getParameter("oldpwd"); 
				String newpwd = request.getParameter("newpwd"); 
				String str=cBean.getString("select id from member where username='"+member+"' and  password='"+oldpwd+"'");
				if(str==null){
					request.setAttribute("message", "ԭʼ������Ϣ����");
					request.getRequestDispatcher("member/info/editpwd.jsp").forward(request, response); 
				}
				else{
					int flag=cBean.comUp("update member set password='"+newpwd+"' where username='"+member+"'");
					if(flag == Constant.SUCCESS){ 
						request.setAttribute("message", "�����ɹ���");
						request.getRequestDispatcher("member/info/editpwd.jsp").forward(request, response); 
					}
					else { 
						request.setAttribute("message", "����ʧ�ܣ�");
						request.getRequestDispatcher("member/info/editpwd.jsp").forward(request, response); 
					}
				}
			}
			/////////////////////////////����Ա����
			else if(method.equals("delm")){//ɾ��ѧ��
				String id = request.getParameter("id");  
				int flag=cBean.comUp("delete from member where id='"+id+"'");
				if(flag == Constant.SUCCESS){
					request.setAttribute("message", "�����ɹ���");
					request.getRequestDispatcher("admin/member/index.jsp").forward(request, response); 
				}
				else{
					request.setAttribute("message", "����ʧ�ܣ�");
					request.getRequestDispatcher("admin/member/index.jsp").forward(request, response); 
				}
			}
			else if(method.equals("ifusem")){//ͣ��/���� ѧ��
				String id = request.getParameter("id");  
				String sql="";
				String str=cBean.getString("select ifuse from member where id='"+id+"'");
				if(str.equals("����")){
					sql="update member set ifuse='ͣ��' where id='"+id+"'";
				}
				else{
					sql="update member set ifuse='����' where id='"+id+"'";
				}
				int flag=cBean.comUp(sql);
				if(flag == Constant.SUCCESS){
					request.setAttribute("message", "�����ɹ���");
					request.getRequestDispatcher("admin/member/index.jsp").forward(request, response); 
				}
				else{
					request.setAttribute("message", "����ʧ�ܣ�");
					request.getRequestDispatcher("admin/member/index.jsp").forward(request, response); 
				}
			}
			else{//�޲�������ת������ҳ��
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return mapping.findForward("error.jsp"); 
		}
		return mapping.findForward(strr);
	} 
}