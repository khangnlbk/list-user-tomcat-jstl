/**
 * Copyright(C) 2020 Luvina Software
 * BaseDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface BaseDao, chứa hàm kết nối database
 *
 * @author KhangNL
 *
 */
public interface BaseDAO {

	/**
	 * Hàm mở kết nối tới DB
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi không kết nối được SQL
	 */
	public void openConnection() throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm đóng connection
	 * @throws SQLException lỗi khi không thể đóng kết nối
	 */
	public void closeConnection () throws SQLException;
	
	/**
	 * Hàm lấy giá trị của biến connection
	 * @return giá trị của biến connection
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm set giá trị cho biến connection
	 * @param connnection đối tượng Connection
	 */
	public void setConnection(Connection connection);
	
	/**
	 * Hàm vô hiệu hóa chế độ tự động commit
	 * @throws SQLException bắn lỗi khi không thực hiện được lệnh setAutoCommit
	 */
	public void disableAutoCommit() throws SQLException;
	
	/**
	 * Hàm thực hiện commit các thao tác với dữ liệu đến DB
	 * @throws SQLException bắn lỗi khi không thực hiện được lệnh commit
	 */
	public void commitToDB() throws SQLException;
	
	/**
	 * Hàm rollback lại data nếu xảy ra lỗi trong trường hợp transaction
	 * @throws SQLException bắn lỗi khi không thực hiện được lệnh rollback
	 */
	public void rollbackData() throws SQLException;
}
