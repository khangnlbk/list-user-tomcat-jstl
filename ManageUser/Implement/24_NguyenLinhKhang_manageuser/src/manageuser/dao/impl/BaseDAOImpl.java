/**
 * Copyright(C) 2020 Luvina Software
 * BaseDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manageuser.dao.BaseDAO;
import manageuser.utils.Constant;
import manageuser.utils.DatabaseProperties;

/**
 * Lớp thực thi interface BaseDAO
 *
 * @author KhangNL
 *
 */
public class BaseDAOImpl implements BaseDAO {
	// Khởi tại các biến để kết nối cơ sở dữ liệu
	protected PreparedStatement ps = null;
	protected Connection connection = null;
	protected ResultSet rs = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDAO#openConnection()
	 */
	@Override
	public void openConnection() throws ClassNotFoundException, SQLException {
		// khởi tạo tham số Driver với dbURL lấy từ file properties
		String driver = DatabaseProperties.getValueByKey(Constant.DRIVER);
		// khởi tạo tham số dbURL với dbURL lấy từ file properties
		String dbURL = DatabaseProperties.getValueByKey(Constant.DB_URL);
		// khởi tạo tham số user với user lấy từ file properties
		String user = DatabaseProperties.getValueByKey(Constant.USER);
		// khởi tạo tham số pass với pass lấy từ file properties
		String pass = DatabaseProperties.getValueByKey(Constant.PASS);
		// Kết nối với cơ sở dữ liệu
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(dbURL, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDAO#closeConnection()
	 */
	@Override
	public void closeConnection() throws SQLException {
		try {
			// Nếu kết nối chưa được đóng và khác null thì đóng
			if (!connection.isClosed() && connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.dao.BaseDAO#getConnection()
	 */
	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// trả về kết nối tới DB
		return connection;
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.dao.BaseDAO#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * (non-JavaDoc)
	 * @see manageuser.dao.BaseDAO#disableAutoCommit()
	 */
	@Override
	public void disableAutoCommit() throws SQLException {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-JavaDoc)
	 * @see manageuser.dao.BaseDAO#commitToDB()
	 */
	@Override
	public void commitToDB() throws SQLException {
		try {
			connection.commit();
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-JavaDoc)
	 * @see manageuser.dao.BaseDAO#rollbackData()
	 */
	@Override
	public void rollbackData() throws SQLException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}
}
