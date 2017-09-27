package servlet;

import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CreateMD5;
import util.RandomNumber;
import util.ValidateCode;
import dao.UserDao;
import entity.User;

public class UserServlet extends HttpServlet {
	UserDao userDao = new UserDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		if (type == null) {
			showRegister(request, response);
		} else if (type.equals("showRegister")) {
			showRegister(request, response);
		} else if (type.equals("doRegister")) {
			doRegister(request, response);
		} else if (type.equals("showLogin")) {
			showLogin(request, response);
		} else if (type.equals("doLogin")) {
			doLogin(request, response);
		} else if (type.equals("randomImage")) {
			randomImage(request, response);
		}

	}

	private void doLogin(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String rand = request.getParameter("random");
			if (rand.equals(request.getSession().getAttribute("rand"))) {
				String name = request.getParameter("username");
				String password = request.getParameter("password");
				User user = new User();
				user.setUsername(name);
				User u = userDao.searchByName(name);

				user.setPassword(CreateMD5.getMd5(password + u.getTime()));
				user = userDao.searchByUsernameAndPassword(user);
				if (user != null) {
					request.getSession().setAttribute("user", user);
					response.sendRedirect("index");
				} else {
					response.sendRedirect("ur?type=showLogin");
				}
			} else {
				request.setAttribute("mes", "验证码失败");

				request.getRequestDispatcher("ur?type=showRegister").forward(
						request, response);

			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showLogin(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doRegister(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String rand = request.getParameter("random");
			if (rand.equals(request.getSession().getAttribute("rand"))) {
				String name = request.getParameter("username");
				String password = request.getParameter("password");
				// password MD5加密
				// password=CreateMD5.getMd5(password);
				User user = new User();
				user.setUsername(name);
				// user.setPassword(password);
				Date date = new Date();
				java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
				user.setTime(time);

				User u = userDao.add(user);
				u = userDao.searchByName(u.getUsername());
				password = CreateMD5.getMd5(password, u.getTime().toString());
				u.setPassword(password);
				u = userDao.update(u);
				System.out.println(u.getId() + " " + u.getUsername());
				request.getSession().setAttribute("user", user);
				response.sendRedirect("index");

			} else {
				request.setAttribute("mes", "验证码失败");
				request.getRequestDispatcher("ur?type=showRegister").forward(
						request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showRegister(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void randomImage(HttpServletRequest request,
			HttpServletResponse response) {

		RandomNumber rn = new RandomNumber();
		try {
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			ValidateCode vc = rn.generateImage();
			ServletOutputStream outStream = response.getOutputStream();
			// 输出图象到页面
			ImageIO.write(vc.getImage(), "JPEG", outStream);
			outStream.close();
			request.getSession().setAttribute("rand", vc.getRand());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
