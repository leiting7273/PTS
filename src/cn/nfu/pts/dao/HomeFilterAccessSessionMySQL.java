package cn.nfu.pts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.nfu.pts.service.DbPoolConnection;

public class HomeFilterAccessSessionMySQL {

	public HomeFilterAccessSessionMySQL() {
	}

	/**
	 * @description:get user home filter from db
	 * @version:v1.0
	 * @param userName
	 * @return
	 */
	public String getHomeFilter(String userName)
	{
		PreparedStatement pstm = null;
		Connection conn = null;
		ResultSet rs = null;
		String filterIdStr = "";  
		
		try
		{
			conn = DbPoolConnection.getInstance().getReadConnection();
			String sql = "select filter_id from home_filter as A JOIN filter as B on A.filter_id = B.id where A.user_name= ? and B.is_valid = 1";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userName);
			rs = pstm.executeQuery();
			if(rs.next())
			{
				filterIdStr = rs.getString("filter_id");
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DbPoolConnection.getInstance().closeAll(rs, pstm, conn);
			return filterIdStr;
		}
	}

	/**
	 * @description:add user home filter
	 * @version:v1.0
	 * @param userName
	 * @param filterId
	 * @return
	 */
	public boolean addHomeFilter(String userName,String filterId)
	{
		PreparedStatement pstm = null;
		Connection conn = null;
		try
		{
			conn = DbPoolConnection.getInstance().getConnection();
			String sql = "insert into home_filter (user_name,filter_id) values (?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userName);
			pstm.setString(2, filterId);
			return pstm.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DbPoolConnection.getInstance().closeAll(pstm, conn);
		}
		return false;
	}

	/**
	 * @description:update user home filter
	 * @version:v1.0
	 * @param userName
	 * @param filterId
	 * @return
	 */
	public boolean updateHomeFilter(String userName,String filterId)
	{
		PreparedStatement pstm = null;
		Connection conn = null;
		try
		{
			conn = DbPoolConnection.getInstance().getConnection();
			String sql = "update home_filter set filter_id=?  where user_name=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(2, userName);
			pstm.setString(1, filterId);
			return pstm.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DbPoolConnection.getInstance().closeAll(pstm, conn);
		}
		return false;
	}

}