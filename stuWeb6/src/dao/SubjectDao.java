package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Subject;

public class SubjectDao extends BaseDao {

	public int searchCount() {
		int count = 0;
		try {
			getStatement();
			String sql = "select count(id) from subject";
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<Subject> searchByBegin(int begin, int num) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			// Statement stat = conn.createStatement();
			String sql = "select * from subject limit " + begin + "," + num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<Subject> searchAll() {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from subject";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public List<Subject> searchNoSubByBjId(int bjId) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from subject where id not in (select subId from v_bj_sub where bjId="
					+ bjId + " and subId is not null )";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public boolean add(Subject sub) {
		boolean flag = false;
		try {
			String sql = "insert into subject (name) values (?)";
			getPreparedStatement(sql);
			pstat.setString(1, sub.getName());
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public Subject searchById(int id) {
		Subject sub = null;
		try {
			getStatement();
			String sql = "select * from subject where id=" + id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sub;
	}

	public boolean modify(Subject sub) {
		boolean flag = false;
		try {
			String sql = "update subject set name=? where id=?";
			getPreparedStatement(sql);
			pstat.setString(1, sub.getName());
			pstat.setInt(2, sub.getId());
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean delete(int id) {
		boolean flag = false;
		try {
			String sql = "delete from subject where id=?";
			getConnection();
			conn.setAutoCommit(false);
			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			// if (rs > 0) {
			// flag = true;
			// }else{
			// return flag;
			// }
			//
			// sql="update student set bj_id=null where bj_id=?";
			// pstat=conn.prepareStatement(sql);
			// pstat.setInt(1, id);
			// rs = pstat.executeUpdate();
			conn.commit();

			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public List<Subject> searchByCondition(Subject condition, int begin,
			int yeNum) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null) {
				where += " and name like '%" + condition.getName() + "%'";
			}
			String sql = "select * from subject " + where + " limit " + begin
					+ "," + yeNum;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public int searchCount(Subject condition) {
		int count = 0;
		try {
			getStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null) {
				where += " and name like '%" + condition.getName() + "%'";
			}
			String sql = "select count(id) from subject " + where;
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<Subject> searchByBjId(int bjId) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "";
			if (bjId == 0) {
				sql = "select id as subId,name as subName from subject";
			} else {
				sql = "select sub.id as subId,sub.name as subName"
						+ " from subject as sub inner join m_bj_sub as m on"
						+ " m.sub_id=sub.id inner join banji as bj"
						+ " on bj.id=m.bj_id where bj_id=" + bjId;
			}
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				list.add(sub);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}
}