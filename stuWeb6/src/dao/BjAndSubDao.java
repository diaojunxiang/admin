package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import entity.BanJi;
import entity.Subject;

public class BjAndSubDao extends BaseDao {
	public boolean addAll(int bjId, String[] subIds) {
		boolean flag = true;
		try {
			

			Connection conn = getConnection();
conn.setAutoCommit(false);
			for (int i = 0; i < subIds.length; i++) {
				boolean f=add(conn,bjId, Integer.parseInt(subIds[i]));
				if(f==false){
					flag=false;
					break;
				}
			}
			conn.commit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean add(Connection conn,int bjId, int subId) {
		boolean flag = false;
		try {
			String sql = "insert into m_bj_sub (bj_id,sub_id) values (?,?)";
			PreparedStatement pstat=conn.prepareStatement(sql);
			pstat.setInt(1, bjId);
			pstat.setInt(2, subId);
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return flag;
	}
	
	
	public boolean deleteAll(int bjId, String[] subIds) {
		boolean flag = true;
		try {
			

			Connection conn = getConnection();
conn.setAutoCommit(false);
			for (int i = 0; i < subIds.length; i++) {
				boolean f=delete(conn,bjId, Integer.parseInt(subIds[i]));
				if(f==false){
					flag=false;
					break;
				}
			}
			conn.commit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean delete(Connection conn,int bjId, int subId) {
		boolean flag = false;
		try {
			String sql = "delete from m_bj_sub where bj_id=? and sub_id=?";
			conn.setAutoCommit(false);
			pstat = conn.prepareStatement(sql);

			pstat.setInt(1, bjId);
			pstat.setInt(2, subId);
			int rs = pstat.executeUpdate();
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return flag;
	}

}