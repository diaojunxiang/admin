package servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.Pagination;
import dao.BanJiDao;
import dao.StudentDao;
import entity.BanJi;
import entity.Student;

public class StudentServlet extends HttpServlet {
	StudentDao stuDao = new StudentDao();
	BanJiDao bjDao = new BanJiDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		try {
			Method method = this.getClass().getDeclaredMethod(type,
					HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Method[] methods = this.getClass().getDeclaredMethods();
		// for (int i = 0; i < methods.length; i++) {
		//
		// try {
		// if (methods[i].getName().equals(type)) {
		// //methods[i].setAccessible(true);
		// methods[i].invoke(this, request, response);
		// }
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalArgumentException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	// if (type == null) {
	// search(request, response);
	// } else if (type.equals("showAdd")) {
	// showAdd(request, response);
	// } else if (type.equals("add")) {
	// add(request, response);
	// } else if (type.equals("showModify")) {
	// showModify(request, response);
	// } else if (type.equals("modify")) {
	// modify(request, response);
	// } else if (type.equals("delete")) {
	// delete(request, response);
	// } else if (type.equals("search")) {
	// search(request, response);
	// }

	// }

	private void search(HttpServletRequest request, HttpServletResponse response) {
		Student condition = new Student();
		String name = request.getParameter("name");
		if (!"".equals(name)) {
			condition.setName(name);
		}
		String sex = request.getParameter("sex");
		if ("男".equals(sex) || "女".equals(sex)) {
			condition.setSex(sex);
		}
		if (null == request.getParameter("age")
				|| "".equals(request.getParameter("age"))) {
			condition.setAge(-1);
		} else {
			int age = Integer.parseInt(request.getParameter("age"));
			condition.setAge(age);
		}
		int bj_id = 0;
		if (request.getParameter("bj") != null) {
			bj_id = Integer.parseInt(request.getParameter("bj"));
		}

		BanJi bj = new BanJi();
		bj.setId(bj_id);
		condition.setBj(bj);

		int ye = 1;
		if (request.getParameter("ye") != null) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}
		int max = stuDao.searchCount(condition);
		int yeNum = 5;
		int yeMa = 5;
		Pagination pagination = new Pagination(ye, max, yeNum, yeMa);
		ye = pagination.getYe();
		int begin = (ye - 1) * yeNum;

		List<Student> list = stuDao.searchByCondition(condition, begin, yeNum);
		List<BanJi> bjList = bjDao.searchAll();
		request.setAttribute("bjs", bjList);
		request.setAttribute("stus", list);
		request.setAttribute("p", pagination);
		request.setAttribute("condition", condition);
		try {
			request.getRequestDispatcher("WEB-INF/student/list.jsp").forward(
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
		boolean flag = stuDao.delete(id);
		if (flag) {
			try {
				response.sendRedirect("stu?type=search");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id=0;
			String name = "";
			String sex = "";
			int age = 0;
			int bjId = 0;
			String newFileName = "";
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个list中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String url = request.getServletContext().getRealPath("/")
					+ "/photos";
			for (int i = 0; i < items.size(); i++) {
				if ("id".equals(items.get(i).getFieldName())) {
					id = Integer.parseInt(items.get(i).getString());
				}else if ("name".equals(items.get(i).getFieldName())) {
					name = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("sex".equals(items.get(i).getFieldName())) {
					sex = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("age".equals(items.get(i).getFieldName())) {
					age = Integer.parseInt(items.get(i).getString());
				} else if ("bj".equals(items.get(i).getFieldName())) {
					bjId = Integer.parseInt(items.get(i).getString());
				} else if ("photo".equals(items.get(i).getFieldName())) {
					UUID uuid = UUID.randomUUID();
					String oldFileName = items.get(i).getName();
					String houzhui = oldFileName.substring(oldFileName
							.lastIndexOf("."));
					newFileName = uuid + houzhui;
					File file = new File(url + "/" + newFileName);
					items.get(i).write(file);
				}
			}

			Student stu = new Student();
			stu.setId(id);
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			BanJi bj = new BanJi();
			bj.setId(bjId);
			stu.setBj(bj);
			stu.setPhoto(newFileName);
			boolean flag = stuDao.modify(stu);
			if (flag) {
				response.sendRedirect("stu?type=search");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showModify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("selectId"));
			Student stu = stuDao.searchById(id);
			List<BanJi> bjList = bjDao.searchAll();
			request.setAttribute("bjs", bjList);
			request.setAttribute("stu", stu);
			request.getRequestDispatcher("WEB-INF/student/modify.jsp").forward(
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
			// String name = request.getParameter("name");
			// String sex = request.getParameter("sex");
			// int age = Integer.parseInt(request.getParameter("age"));
			// int bjId = Integer.parseInt(request.getParameter("bj"));
			String name = "";
			String sex = "";
			int age = 0;
			int bjId = 0;
			String newFileName = "";
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个list中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String url = request.getServletContext().getRealPath("/")
					+ "/photos";
			for (int i = 0; i < items.size(); i++) {
				if ("name".equals(items.get(i).getFieldName())) {
					name = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("sex".equals(items.get(i).getFieldName())) {
					sex = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("age".equals(items.get(i).getFieldName())) {
					age = Integer.parseInt(items.get(i).getString());
				} else if ("bj".equals(items.get(i).getFieldName())) {
					bjId = Integer.parseInt(items.get(i).getString());
				} else if ("photo".equals(items.get(i).getFieldName())) {
					UUID uuid = UUID.randomUUID();
					String oldFileName = items.get(i).getName();
					String houzhui = oldFileName.substring(oldFileName
							.lastIndexOf("."));
					newFileName = uuid + houzhui;
					File file = new File(url + "/" + newFileName);
					items.get(i).write(file);
				}
			}

			Student stu = new Student();
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			BanJi bj = new BanJi();
			bj.setId(bjId);
			stu.setBj(bj);
			stu.setPhoto(newFileName);
			boolean flag = stuDao.add(stu);
			if (flag) {
				response.sendRedirect("stu?type=search");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = 0;
			if(request.getParameter("age")!=null&&!"".equals(request.getParameter("age"))){
				age=Integer.parseInt(request.getParameter("age"));
			}
			int bjId = 0;
			if(request.getParameter("bj")!=null&&!"".equals(request.getParameter("bj"))){
				bjId=Integer.parseInt(request.getParameter("bj"));
			}
			String photo=request.getParameter("photo");
			

			Student stu = new Student();
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			BanJi bj = new BanJi();
			bj.setId(bjId);
			stu.setBj(bj);
			stu.setPhoto(photo);
			boolean flag = stuDao.add(stu);
			if (flag) {
				response.sendRedirect("stu?type=search");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String newFileName = "";
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个list中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String url = request.getServletContext().getRealPath("/")
					+ "/photos";
			for (int i = 0; i < items.size(); i++) {
				if ("photo".equals(items.get(i).getFieldName())) {
					UUID uuid = UUID.randomUUID();
					String oldFileName = items.get(i).getName();
					String houzhui = oldFileName.substring(oldFileName
							.lastIndexOf("."));
					newFileName = uuid + houzhui;
					File file = new File(url + "/" + newFileName);
					items.get(i).write(file);
				}
			}

				response.getWriter().print(newFileName);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void showAdd(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<BanJi> list = bjDao.searchAll();
			request.setAttribute("bjs", list);

			request.getRequestDispatcher("WEB-INF/student/add.jsp").forward(
					request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private void show(HttpServletRequest request, HttpServletResponse
	// response) {
	// try {
	//
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int max = stuDao.searchCount();
	// int yeNum = 5;
	// int yeMa = 5;
	// Pagination pagination = new Pagination(ye, max, yeNum, yeMa);
	// ye = pagination.getYe();
	// int begin = (ye - 1) * yeNum;
	// List<Student> list = stuDao.searchByBegin(begin, yeNum);
	// request.setAttribute("stus", list);
	// request.setAttribute("p", pagination);
	// request.getRequestDispatcher("WEB-INF/student/list.jsp").forward(
	// request, response);
	// } catch (ServletException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}
