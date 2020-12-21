/**
 * Copyright(C) 2020 Luvina Software
 * TblDetailUserJapanDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao;

import java.sql.SQLException;

import manageuser.entities.TblDetailUserJapanEntities;

/**
 * Interface TblDetailUserJapanDAO, chứa hàm thao tác đến bảng tbl_detail_user_japan
 *
 * @author KhangNL
 *
 */
public interface TblDetailUserJapanDAO extends BaseDAO {
	/**
	 * Hàm thêm mới 1 detail user vào DB
	 * @param tblDetailUserJapan đối tượng muốn thêm
	 * @throws SQLException lỗi thao tác với db
	 */
	public void insertDetailUserJapan(TblDetailUserJapanEntities tblDetailUserJapan) throws SQLException;
	
	/**
	 * Hàm check tồn tại phần trình độ tiếng Nhật của user
	 * @param userId id của user cần check
	 * @return true nếu đã tồn tại, ngược lại trả về false
	 * @throws SQLException lỗi thao tác với db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public boolean getCheckExistDetailUserJapanByUserId(int userId) throws SQLException, ClassNotFoundException;
	
	/**
	 * Hàm xóa trình độ tiếng Nhật của user
	 * @param userId id của user muốn xóa phần trình độ tiếng Nhật
	 * @return nếu xoá được trả về true, ngược lại trả về false
	 * @throws SQLException lỗi thao tác với db
	 */
	public boolean deleteDetailUserJapan(int userId) throws SQLException;
	
	/**
	 * Hàm update vùng trình độ tiếng Nhật cho user
	 * @param tblDetailUserJapan đối tượng lưu trình độ tiếng Nhật của user
	 * @throws SQLException lỗi thao tác với db
	 */
	public void updateDetailUserJapan(TblDetailUserJapanEntities tblDetailUserJapan) throws SQLException;
}
