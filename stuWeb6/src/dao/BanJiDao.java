package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Subject;

public class BanJiDao extends BaseDao {

	public int searchCount() {
		int count = 0;
		try {
			getStatement();
			String sql = "select count(id) from banji";
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

	public List<BanJi> searchByBegin(int begin, int num) {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			getStatement();
			// Statement stat = conn.createStatement();
			String sql = "select * from banji limit " + begin + "," + num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
				list.add(bj);
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

	public List<BanJi> searchAll() {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			getStatement();
			String sql = "select * from banji";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
				list.add(bj);
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

	public boolean add(BanJi bj) {
		boolean flag = false;
		try {
			String sql = "insert into banji (name,stuNums) values (?,?)";
			getPreparedStatement(sql);
			pstat.setString(1, bj.getName());
			pstat.setInt(2, bj.getStuNums());
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

	public BanJi searchById(int id) {
		BanJi bj = null;
		try {
			getStatement();
			String sql = "select * from banji where id=" + id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
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

		return bj;
	}

	public BanJi searchBjAndSubById(int id) {
		BanJi bj = new BanJi();
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from v_bj_sub where bjId=" + id;
			rs = stat.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				if (i == 0) {
					bj.setId(rs.getInt("bjId"));
					bj.setName(rs.getString("bjName"));
					bj.setStuNums(rs.getInt("stuNums"));
				}
				Subject sub = new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				if (sub.getId() != 0) {
					list.add(sub);
				}
				i++;
			}
			bj.setSubs(list);
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

		return bj;
	}

	public boolean modify(BanJi bj) {
		boolean flag = false;
		try {
			String sql = "update banji set name=?,stuNums=? where id=?";
			getPreparedStatement(sql);
			pstat.setString(1, bj.getName());
			pstat.setInt(2, bj.getStuNums());
			pstat.setInt(3, bj.getId());
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
			String sql = "delete from banji where id=?";
			getConnection();
			conn.setAutoCommit(false);
			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			} else {
				return flag;
			}

			sql = "update student set bj_id=null where bj_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
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

	public List<BanJi> searchByCondition(BanJi condition, int begin, int yeNum) {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			getStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null) {
				where += " and name like '%" + condition.getName() + "%'";
			}
			String sql = "select * from banji " + where + " limit " + begin
					+ "," + yeNum;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
				list.add(bj);
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

	public int searchCount(BanJi condition) {
		int count = 0;
		try {
			getStatement();
			String where = "where 1=1 ";
			if (condition.getName() != null) {
				where += " and name like '%" + condition.getName() + "%'";
			}
			String sql = "select count(id) from banji " + where;
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

}