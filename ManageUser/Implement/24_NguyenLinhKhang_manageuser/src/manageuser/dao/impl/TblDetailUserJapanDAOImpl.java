/**
 * Copyright(C) 2020 Luvina Software
 * TblDetailUserJapanDAOImpl.java, 14/07/2020, KhangNL
 */
package manageuser.dao.impl;

import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDAO;
import manageuser.entities.TblDetailUserJapanEntities;

/**
 * Lớp thực thi interface TblDetailUserJapanDAO
 *
 * @author KhangNL
 *
 */
public class TblDetailUserJapanDAOImpl extends BaseDAOImpl implements TblDetailUserJapanDAO {

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDAO#insertDetailUserJapan(manageuser.entities.TblDetailUserJapanEntities)
	 */
	@Override
	public void insertDetailUserJapan(TblDetailUserJapanEntities tblDetailUserJapan) throws SQLException {
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tbl_detail_user_japan ");
			sql.append("VALUES (?,?,?,?,?,?)");
			// PreparedStatement gửi các câu lệnh SQL được tham số hóa đến cơ sở
			// dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, tblDetailUserJapan.getDetailUserJapanId());
			ps.setInt(++i, tblDetailUserJapan.getUserId());
			ps.setString(++i, tblDetailUserJapan.getCodeLevel());
			ps.setString(++i, tblDetailUserJapan.getStartDate());
			ps.setString(++i, tblDetailUserJapan.getEndDate());
			ps.setInt(++i, tblDetailUserJapan.getTotal());
			// Thực hiện câu truy vấn
			ps.execute();
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDAO#getCheckExistDetailUserJapanByUserId(
	 *      int)
	 */
	@Override
	public boolean getCheckExistDetailUserJapanByUserId(int userId) throws SQLException, ClassNotFoundException {
		// Khởi tạo biến check
		boolean check = false;
		try {
			// mở kết nối
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT user_id ");
			sql.append("FROM tbl_detail_user_japan ");
			sql.append("WHERE user_id = ? ");
			// PreparedStatement để gửi các câu lệnh SQL được tham số hóa
			// đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			// Nếu tồn tại user
			while (rs.next()) {
				// Gán biến check = true
				check = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi ra
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
		return check;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblDetailUserJapanDAO#deleteDetailUserJapan(int)
	 */
	@Override
	public boolean deleteDetailUserJapan(int userId) throws SQLException {
		// khởi tạo biến check
		boolean check = false;
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tbl_detail_user_japan ");
			sql.append("WHERE user_id = ?");
			// Tạo một đối tượng PreparedStatement để gửi các câu lệnh SQL được
			// tham số hóa
			// đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			// Thực hiện câu truy vấn
			ps.execute();
			check = true;
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
		// trả giá trị biến check
		return check;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.dao.TblDetailUserJapanDAO#updateDetailUserJapan(manageuser.
	 * entities.TblDetailUserJapanEntities)
	 */
	@Override
	public void updateDetailUserJapan(TblDetailUserJapanEntities tblDetailUserJapan) throws SQLException {
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tbl_detail_user_japan ");
			sql.append("SET code_level = ?, start_date = ?, end_date = ?, total = ? ");
			sql.append("WHERE user_id = ?");
			// PreparedStatement để gửi các câu lệnh SQL được tham số hóa
			// đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setString(++i, tblDetailUserJapan.getCodeLevel());
			ps.setString(++i, tblDetailUserJapan.getStartDate());
			ps.setString(++i, tblDetailUserJapan.getEndDate());
			ps.setInt(++i, tblDetailUserJapan.getTotal());
			ps.setInt(++i, tblDetailUserJapan.getUserId());
			// Thực hiện câu truy vấn
			ps.execute();
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
	}

}
