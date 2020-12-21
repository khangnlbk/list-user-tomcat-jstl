/**
 * Copyright(C) 2020 Luvina Software
 * TblDetailUserJapanLogic.java, 15/07/2020, KhangNL
 */
package manageuser.logics;

import java.sql.SQLException;

/**
 * Description TblDetailUserJapanLogic, chứa các hàm tính toán logic với bảng tbl_detail_user_japan
 *
 * @author KhangNL
 *
 */
public interface TblDetailUserJapanLogic {

	/**
	 * Hàm check tồn tại phần trình độ tiếng Nhật của user
	 * @param userId id của user cần check
	 * @return true nếu đã tồn tại, ngược lại trả về false
	 * @throws SQLException lỗi thao tác với db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public boolean checkExistDetailUserJapanByUserId(int userId) throws SQLException, ClassNotFoundException;
}
