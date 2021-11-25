package jp.go.kishou.adess.oswy61.distinction.rain.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.kishou.adess.oswy61.distinction.rain.bean.JudgeDataBean;

/**
 * index.jsp���烊�N�G�X�g���󂯁AJudgeDataBean���쐬����result.jsp�ɓn���B
 */
public class ProcessServlet extends HttpServlet {
    public ProcessServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;

		try {
			String abdir=new String(request.getParameter("abdir").getBytes("iso-8859-1"),"Shift_JIS");//������������
			JudgeDataBean bean = new JudgeDataBean();
			bean.init(abdir);
			request.setAttribute("judge", bean);
			rd = request.getRequestDispatcher("/result.jsp");
		}catch(Exception e){
			e.printStackTrace();
			rd = request.getRequestDispatcher("/error.jsp");
		}finally {
			rd.forward(request, response);
		}
	}

}
