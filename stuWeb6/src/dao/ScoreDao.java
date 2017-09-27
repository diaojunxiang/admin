package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreDao extends BaseDao {
	public List<Score> searchByCondition(Score condition, int begin, int num) {
		List<Score> list = new ArrayList<Score>();
		try {
			getStatement();
			String where = " where 1=1 ";
			if (!"".equals(condition.getStu().getName())) {
				where += " and stuName like'%" + condition.getStu().getName()
						+ "%' ";
			}
			if (condition.getStu().getBj().getId() != 0) {
				where += " and bjId =" + condition.getStu().getBj().getId();
			}
			if (condition.getSub().getId() != 0) {
				where += " and subId =" + condition.getSub().getId();
			}

			String sql = "select * from v_stu_bj_sub_score" + where + " limit "
					+ begin + "," + num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score sc = new Score();
				sc.setId(rs.getInt("scId"));
				sc.setScore((Integer) rs.getObject("score"));
				sc.setGrade(rs.getString("grade"));

				Student stu = new Student();
				stu.setId(rs.getInt("stuId"));
				stu.setName(rs.getString("stuName"));

				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBj(bj);
				sc.setStu(stu);

				Subject sub = new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				sc.setSub(sub);
				list.add(sc);
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

	public int searchCount(Score condition) {
		int result = 0;
		try {
			getStatement();
			String where = " where 1=1 ";
			if (!"".equals(condition.getStu().getName())) {
				where += " and stuName like'%" + condition.getStu().getName()
						+ "%'";
			}
			if (condition.getStu().getBj().getId() != 0) {
				where += " and bjId =" + condition.getStu().getBj().getId();
			}
			if (condition.getSub().getId() != 0) {
				where += " and subId =" + condition.getSub().getId();
			}

			String sql = "select count(*) from v_stu_bj_sub_score" + where;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}

	public boolean add(Score sc) {
		boolean flag = false;
		try {
			getStatement();
			String sql = "insert into score (stu_id,sub_id,score) values("
					+ sc.getStu().getId() + "," + sc.getSub().getId() + ","
					+ sc.getScore() + ")";
			int result = stat.executeUpdate(sql);
			if (result > 0) {
				flag = true;
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
		return flag;
	}

	public boolean update(Score sc) {
		boolean flag = false;
		try {
			getStatement();
			String sql = "update score set score=" + sc.getScore()
					+ " where id=" + sc.getId();
			int result = stat.executeUpdate(sql);
			if (result > 0) {
				flag = true;
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
		return flag;
	}

	public Score searchScore(Score sc) {
		Score score = null;
		try {
			getStatement();

			String sql = "select * from score where stu_id="
					+ sc.getStu().getId() + " and sub_id="
					+ sc.getSub().getId();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				score = new Score();
				score.setId(rs.getInt("id"));
				score.setScore((Integer) rs.getObject("score"));
				score.setGrade(rs.getString("grade"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return score;
	}
}
