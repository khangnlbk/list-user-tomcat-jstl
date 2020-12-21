/**
 * Copyright(C) 2020 Luvina Software
 * Validates.java, 15/07/2020, KhangNL
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Lớp validate các hạng mục của chương trình
 *
 * @author KhangNL
 *
 */
public class ValidateUser {
	/**
	 * Hàm validate login
	 * 
	 * @param loginName tên đăng nhập
	 * @param password  password đã nhập
	 * @return nếu có lỗi thì trả về danh sách các lỗi size khác 0, nếu không có lỗi size = 0
	 * @throws ClassNotFoundException không tìm thấy được class
	 * @throws SQLException lỗi thao tác với db
	 * @throws NoSuchAlgorithmException không tìm thấy thuật toán SHA trong hàm checkExistLoginName
	 */
	public List<String> validateLogin(String loginName, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// Khai báo list lưu danh sách lỗi
		List<String> listErr = new ArrayList<String>();
		try {
			// Nếu chưa nhập loginName
			if (Common.checkEmpty(loginName)) {
				// Thêm câu thông báo lỗi vào list
				listErr.add(MessageErrorProperties.getValueByKey(Constant.ER001_LOGIN_NAME));
			}
			// Nếu chưa nhập password
			if (Common.checkEmpty(password)) {
				// Thêm câu thông báo lỗi vào list
				listErr.add(MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD));
			}
			// Nếu đã nhập loginName, password thì kiểm tra loginName và password có trùng không
			if (listErr.size() == 0) {
				// tạo đối tượng tblUserLogic
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// Kiểm tra tồn tại loginName và pass tương ứng
				if (!tblUserLogic.checkExistLoginName(loginName, password)) {
					// Nếu không tồn tại user, thêm câu thông báo lỗi
					listErr.add(MessageErrorProperties.getValueByKey(Constant.ER016));
				}
			} 
		} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e ) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
		// trả về danh sách lỗi
		return listErr;
	}
}
