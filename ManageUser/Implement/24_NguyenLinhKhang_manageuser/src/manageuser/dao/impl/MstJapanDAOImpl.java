/**
 * Copyright(C) 2020 Luvina Software
 * MstJapanDAOImpl.java, 14/07/2020, KhangNL
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDAO;
import manageuser.entities.MstJapanEntities;
import manageuser.utils.Constant;

/**
 * Lớp thực thi interface MstJapanDAO
 *
 * @author KhangNL
 *
 */
public class MstJapanDAOImpl extends BaseDAOImpl implements MstJapanDAO {

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDAO#getAllMstjapan()
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws SQLException, ClassNotFoundException {
		// khởi tạo listMstJapanEntities
		List<MstJapanEntities> listMstJapanEntities = new ArrayList<MstJapanEntities>();
		try {
			// mở kết nối
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT code_level, name_level ");
			sql.append("FROM mst_japan ");
			sql.append("ORDER BY code_level ASC");
			// Tạo một đối tượng PreparedStatement để gửi các câu lệnh SQL đến
			// cơ sở dữ
			// liệu.
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			// Thực hiện câu truy vấn
			rs = statement.executeQuery();
			while (rs.next()) {
				MstJapanEntities mstJapanEntities = new MstJapanEntities();
				mstJapanEntities.setCodeLevel(rs.getString(Constant.MST_JAPAN_CODE_LEVEL));
				mstJapanEntities.setNameLevel(rs.getString(Constant.MST_JAPAN_NAME_LEVEL));
				// add mstJapanEntities vào mảng danh sách trình độ tiếng Nhật
				listMstJapanEntities.add(mstJapanEntities);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		return listMstJapanEntities;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstJapanDAO#getNameLevelByCodeLevel(java.lang.String)
	 */
	@Override
	public String getNameLevelByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		// khởi tạo biến nameLevel
		String nameLevel = null;
		try {
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT name_level ");
			sql.append("FROM mst_japan ");
			sql.append("WHERE code_level = ?");
			// PreparedStatement gửi các câu lệnh SQL đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setString(++i, codeLevel);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			while (rs.next()) {
				nameLevel = rs.getString(Constant.MST_JAPAN_NAME_LEVEL);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// trả về nameLevel
		return nameLevel;
	}
}
