package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import util.Pagination;
import dao.BanJiDao;
import dao.ScoreDao;
import dao.SubjectDao;
import entity.BanJi;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreServlet extends HttpServlet {
	ScoreDao scDao = new ScoreDao();
	BanJiDao bjDao = new BanJiDao();
	SubjectDao subDao = new SubjectDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type == null || type.equals("search")) {
			search(request, response);
		} else if (type.equals("manage")) {
			manage(request, response);
		} else if (type.equals("input")) {
			input(request, response);
		}
	}

	private void input(HttpServletRequest request, HttpServletResponse response) {
		try {
		boolean flag=false;
		int scId = Integer.parseInt(request.getParameter("scId"));
		int score = Integer.parseInt(request.getParameter("score"));
		Score sc=new Score();
		sc.setScore(score);
		int stuId = Integer.parseInt(request.getParameter("stuId"));
		int subId = Integer.parseInt(request.getParameter("subId"));
		
		Student stu=new Student();
		stu.setId(stuId);
		Subject sub=new Subject();
		sub.setId(subId);
		sc.setStu(stu);
		sc.setSub(sub);
		sc.setId(scId);
		if(scId==0){
			
			flag=scDao.add(sc);
		}else{
			
			flag=scDao.update(sc);
		}
		PrintWriter out =response.getWriter();
		
		if(flag){
			 sc=scDao.searchScore(sc);
			 JSONObject json=JSONObject.fromObject(sc);
			 out.print(json);
		}else{
			out.print(flag);
		}
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		common(request, response);
		try {
			request.getRequestDispatcher("WEB-INF/score/manage.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void common(HttpServletRequest request, HttpServletResponse response) {
		String stuName = request.getParameter("stuName");
		if (stuName == null) {
			stuName = "";
		}
		int bjId = 0;
		if (request.getParameter("bj") != null) {
			bjId = Integer.parseInt(request.getParameter("bj"));
		}
		int subId = 0;
		if (request.getParameter("sub") != null) {
			subId = Integer.parseInt(request.getParameter("sub"));
		}
		Score condition = new Score();
		Student stu = new Student();

		stu.setName(stuName);
		BanJi bj = new BanJi();
		bj.setId(bjId);
		stu.setBj(bj);
		condition.setStu(stu);
		Subject sub = new Subject();
		sub.setId(subId);
		condition.setSub(sub);

		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}
		int max = scDao.searchCount(condition);
		int yeNum = 5;
		int yeMa = 5;
		Pagination p = new Pagination(ye, max, yeNum, yeMa);
		ye = p.getYe();
		int begin = (ye - 1) * yeNum;
		List<Score> list = scDao.searchByCondition(condition, begin, yeNum);
		List<BanJi> bjList = bjDao.searchAll();
		List<Subject> subList = subDao.searchAll();
		request.setAttribute("scs", list);
		request.setAttribute("p", p);
		request.setAttribute("condition", condition);
		request.setAttribute("bjs", bjList);
		request.setAttribute("subs", subList);
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		common(request, response);
		try {
			request.getRequestDispatcher("WEB-INF/score/list.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
