package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import util.Pagination;
import dao.BanJiDao;
import dao.BjAndSubDao;
import dao.SubjectDao;
import entity.BanJi;
import entity.Subject;

public class BanJiServlet extends HttpServlet {
	BanJiDao bjDao = new BanJiDao();
	SubjectDao subDao=new SubjectDao();
	BjAndSubDao bsDao=new BjAndSubDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		if (type == null) {
			search(request, response);
		} else if (type.equals("showAdd")) {
			showAdd(request, response);
		} else if (type.equals("add")) {
			add(request, response);
		} else if (type.equals("showModify")) {
			showModify(request, response);
		} else if (type.equals("modify")) {
			modify(request, response);
		} else if (type.equals("delete")) {
			delete(request, response);
		} else if (type.equals("search")) {
			search(request, response);
		} else if (type.equals("manageSub")) {
			manageSub(request, response);
		} else if (type.equals("manageSub2")) {
			manageSub2(request, response);
		}else if (type.equals("addSub")) {
			addSub(request, response);
		}else if (type.equals("deleteSub")) {
			deleteSub(request, response);
		}else if (type.equals("searchSub")) {
			searchSub(request, response);
		}

	}

	private void manageSub2(HttpServletRequest request,
			HttpServletResponse response) {
		List<BanJi> bjs=bjDao.searchAll();
		int bjId =0;
		if(request.getParameter("selectId")==null){
			 bjId =bjs.get(0).getId();
		}else{
			 bjId = Integer.parseInt(request.getParameter("selectId"));
		}
		
		BanJi bj=bjDao.searchBjAndSubById(bjId);
		List<Subject> noSubs=subDao.searchNoSubByBjId(bjId);
		try {
			request.setAttribute("bj", bj);
			request.setAttribute("noSubs", noSubs);
			request.setAttribute("bjs", bjs);
			request.getRequestDispatcher("WEB-INF/banji/manageSub2.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void deleteSub(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		int bjId=Integer.parseInt(request.getParameter("bjId"));
		String[] subIds = request.getParameter("subIds").split(",");
		boolean flag=bsDao.deleteAll(bjId, subIds);
		try {
			PrintWriter out=response.getWriter();
			BanJi bj=bjDao.searchBjAndSubById(bjId);
			List<Subject> noSubs=subDao.searchNoSubByBjId(bjId);
			String str=JSONArray.fromObject(bj.getSubs()).toString();
			str+="-|-"+JSONArray.fromObject(noSubs).toString();
			out.print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addSub(HttpServletRequest request, HttpServletResponse response) {
		int bjId=Integer.parseInt(request.getParameter("bjId"));
		String[] subIds = request.getParameter("subIds").split(",");
		boolean flag=bsDao.addAll(bjId, subIds);
		try {
			PrintWriter out=response.getWriter();
			BanJi bj=bjDao.searchBjAndSubById(bjId);
			List<Subject> noSubs=subDao.searchNoSubByBjId(bjId);
			String str=JSONArray.fromObject(bj.getSubs()).toString();
			str+="-|-"+JSONArray.fromObject(noSubs).toString();
			out.print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private void searchSub(HttpServletRequest request, HttpServletResponse response) {
		int bjId=Integer.parseInt(request.getParameter("bjId"));
		try {
			PrintWriter out=response.getWriter();
			BanJi bj=bjDao.searchBjAndSubById(bjId);
			List<Subject> noSubs=subDao.searchNoSubByBjId(bjId);
			String str=JSONArray.fromObject(bj.getSubs()).toString();
			str+="-|-"+JSONArray.fromObject(noSubs).toString();
			out.print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void manageSub(HttpServletRequest request,
			HttpServletResponse response) {
		List<BanJi> bjs=bjDao.searchAll();
		int bjId =0;
		if(request.getParameter("selectId")==null){
			 bjId =bjs.get(0).getId();
		}else{
			 bjId = Integer.parseInt(request.getParameter("selectId"));
		}
		
		BanJi bj=bjDao.searchBjAndSubById(bjId);
		List<Subject> noSubs=subDao.searchNoSubByBjId(bjId);
		try {
			request.setAttribute("bj", bj);
			request.setAttribute("noSubs", noSubs);
			request.setAttribute("bjs", bjs);
			request.getRequestDispatcher("WEB-INF/banji/manageSub.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {
		BanJi condition = new BanJi();
		String name = request.getParameter("name");
		if (!"".equals(name)) {
			condition.setName(name);
		}

		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}
		int max = bjDao.searchCount(condition);
		int yeNum = 5;
		int yeMa = 5;
		Pagination pagination = new Pagination(ye, max, yeNum, yeMa);
		ye = pagination.getYe();
		int begin = (ye - 1) * yeNum;

		List<BanJi> list = bjDao.searchByCondition(condition, begin, yeNum);
		request.setAttribute("bjs", list);
		request.setAttribute("p", pagination);
		request.setAttribute("condition", condition);
		try {
			request.getRequestDispatcher("WEB-INF/banji/list.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("selectId"));
		boolean flag = bjDao.delete(id);
		if (flag) {
			try {
				response.sendRedirect("bj");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			BanJi bj = new BanJi();
			bj.setId(id);
			bj.setName(name);
			boolean flag = bjDao.modify(bj);
			if (flag) {
				response.sendRedirect("bj");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showModify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("selectId"));
			BanJi bj = bjDao.searchById(id);
			request.setAttribute("bj", bj);
			request.getRequestDispatcher("WEB-INF/banji/modify.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			BanJi bj = new BanJi();
			bj.setName(name);
			boolean flag = bjDao.add(bj);
			if (flag) {
				response.sendRedirect("bj");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showAdd(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/banji/add.jsp").forward(
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
