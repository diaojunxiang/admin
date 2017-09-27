package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Pagination;
import dao.StudentDao;
import entity.Student;

public class IndexServlet extends HttpServlet {
	StudentDao stuDao = new StudentDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		try {
			if (type == null) {

				request.getRequestDispatcher("WEB-INF/index/index.jsp")
						.forward(request, response);

			} else if (type.equals("header")) {
				request.getRequestDispatcher("WEB-INF/index/header.jsp")
						.forward(request, response);
			} else if (type.equals("left")) {
				request.getRequestDispatcher("WEB-INF/index/left.jsp")
						.forward(request, response);
			} else if (type.equals("footer")) {
				request.getRequestDispatcher("WEB-INF/index/footer.jsp")
						.forward(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
