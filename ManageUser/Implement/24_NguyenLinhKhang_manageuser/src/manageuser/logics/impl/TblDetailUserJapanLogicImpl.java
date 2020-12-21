/**
 * Copyright(C) 2020 Luvina Software
 * TblDetailUserJapanImpl.java, 15/07/2020, KhangNL
 */
package manageuser.logics.impl;

import java.sql.SQLException;

import manageuser.dao.TblDetailUserJapanDAO;
import manageuser.dao.impl.TblDetailUserJapanDAOImpl;
import manageuser.logics.TblDetailUserJapanLogic;

/**
 * Lớp thực thi interface TblDetailUserJapan
 *
 * @author KhangNL
 *
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblDetailUserJapanLogic#
	 * checkExistDetailUserJapanByUserId(int)
	 */
	@Override
	public boolean checkExistDetailUserJapanByUserId(int userId) throws SQLException, ClassNotFoundException {
		// Khởi tạo tblDetailUserJapanDAO
		TblDetailUserJapanDAO tblDetailUserJapanDAO = new TblDetailUserJapanDAOImpl();
		// Khởi tạo biến lưu kết quả check
		boolean check = false;
		// Thực hiện check và lấy về kết quả
		check = tblDetailUserJapanDAO.getCheckExistDetailUserJapanByUserId(userId);
		return check;
	}
}
