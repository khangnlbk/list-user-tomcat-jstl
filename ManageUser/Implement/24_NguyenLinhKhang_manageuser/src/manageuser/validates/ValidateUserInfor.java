/**
 * Copyright(C) 2020 Luvina Software
 * ValidateUserInfor.java, [dd/mm/yyyy] Nguyễn Linh Khang
 */
package manageuser.validates;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Class thực hiện validate userInfor
 * 
 * @author KhangNL
 */
public class ValidateUserInfor {

	/**
	 * Hàm check hạng mục LoginName mã lỗi: ER001, ER019, ER007, ER003
	 * 
	 * @param loginName
	 *            giá trị nhập vào
	 * @return câu thông báo lỗi nếu có
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi thao tác db
	 */
	private String validateLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// khởi tạo message
		String message = "";
		// khởi tạo biến để thao tác với db
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		// Check không nhập
		if (Common.checkEmpty(loginName)) {
			// Lấy thông báo lỗi không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_LOGIN_NAME);
			// Sai format
		} else if (!Common.checkFormat(loginName, Constant.FORMAT_LOGIN_NAME)) {
			// Lấy thông báo lỗi sai format
			message = MessageErrorProperties.getValueByKey(Constant.ER019_LOGIN_NAME);
			// check độ dài trong khoảng
		} else if (!Common.checkLength(loginName, Constant.MIN_LENGTH_LOGIN_NAME, Constant.MAX_LENGTH_LOGIN_NAME)) {
			// lấy thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER007_LOGIN_NAME);
			// check tồn tại
		} else if (tblUserLogic.checkExistLoginName(loginName)) {
			// Lấy thông báo lỗi user đã tồn tại
			message = MessageErrorProperties.getValueByKey(Constant.ER003_LOGIN_NAME);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục group mã lỗi: ER002, ER004
	 * 
	 * @param groupId
	 *            mã nhóm đã chọn
	 * @return câu thông báo lỗi nếu có
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi thao tác với db
	 */
	private String validateGroup(int groupId) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến lỗi
		String message = "";
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		// Check không chọn group
		if (Common.checkNotSelectedSelectBox(groupId)) {
			// Thêm câu thông báo lỗi
			message = MessageErrorProperties.getValueByKey(Constant.ER002_GROUP);
			// Check group không tồn tại
		} else if (mstGroupLogic.getGroupNameByGroupId(groupId) == null) {
			// Thêm câu thông báo lỗi group ko tồn tại
			message = MessageErrorProperties.getValueByKey(Constant.ER004_GROUP);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục fullName mã lỗi: ER001, ER006
	 * 
	 * @param fullName
	 *            - fullName đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateFullName(String fullName) {
		// Khởi tạo biến lỗi
		String message = "";
		// Không nhập
		if (Common.checkEmpty(fullName)) {
			// thêm câu thông báo lỗi không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_FULL_NAME);
			// Check length
		} else if (!Common.checkLength(fullName, Constant.MIN_LENGTH_0, Constant.MAX_LENGTH_255)) {
			// Thêm câu thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER006_FULL_NAME);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục fullNameKana mã lỗi: ER009, ER006
	 * 
	 * @param fullNameKana
	 *            giá trị đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateFullNameKana(String fullNameKana) {
		// Khởi tạo biến lỗi
		String message = "";
		// check là ký tự kana
		if (!Common.checkFormat(fullNameKana, Constant.FORMAT_FULLNAMEKANA)) {
			// Thêm câu thông báo lỗi không phải là chữ kana
			message = MessageErrorProperties.getValueByKey(Constant.ER009_FULL_NAME_KANA);
			// Check length
		} else if (!Common.checkLength(fullNameKana, Constant.MIN_LENGTH_0, Constant.MAX_LENGTH_255)) {
			// Thêm câu thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER006_FULL_NAME_KANA);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục birthday mã lỗi: ER011
	 * 
	 * @param birthday
	 *            birthday cần check
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateBirthday(String birthday) {
		// khởi tạo biến lỗi
		String message = "";
		// Check ngày hợp lệ
		if (!Common.checkDate(birthday)) {
			// Thêm câu thông báo ngày không hợp lệ
			message = MessageErrorProperties.getValueByKey(Constant.ER011_BIRTH_DAY);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục email mã lỗi: ER001, ER006, ER005, ER003
	 * 
	 * @param email
	 *            giá trị đã nhập
	 * @param userId
	 *            id của user không muốn check
	 * @return câu thông báo lỗi nếu có
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi không thao được với db
	 */
	private String validateEmail(String email, int userId) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến lỗi
		String message = "";
		// Khai báo đối tượng tblLogic
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		// Không nhập
		if (Common.checkEmpty(email)) {
			// Thêm câu thông báo lỗi không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_EMAIL);
			// Check length
		} else if (!Common.checkLength(email, Constant.MIN_LENGTH_EMAIL, Constant.MAX_LENGTH_EMAIL)) {
			// Thêm câu thông báo độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER006_EMAIL);
			// Check format
		} else if (!Common.checkFormat(email, Constant.FORMAT_EMAIL)) {
			// Thêm câu thông báo lỗi format
			message = MessageErrorProperties.getValueByKey(Constant.ER005_EMAIL);
			// Check đã tồn tại
		} else if (tblUserLogic.checkExistEmail(email, userId)) {
			// Thêm câu thông báo đã tồn tại
			message = MessageErrorProperties.getValueByKey(Constant.ER003_EMAIL);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục tel mã lỗi: ER001, ER006, ER005
	 * 
	 * @param tel
	 *            giá trị đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateTel(String tel) {
		// Khởi tạo biến lỗi
		String message = "";
		// Không nhập
		if (Common.checkEmpty(tel)) {
			// Thêm câu thông báo không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_TEL);
			// Check length
		} else if (!Common.checkLength(tel, Constant.MIN_LENGTH_TEL, Constant.MAX_LENGTH_TEL)) {
			// Thêm câu thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER006_TEL);
			// Check format
		} else if (!Common.checkFormat(tel, Constant.FORMAT_TEL)) {
			// Thêm câu thông báo lỗi format
			message = MessageErrorProperties.getValueByKey(Constant.ER005_TEL);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục password mã lỗi: ER001, ER008, ER007
	 * 
	 * @param password
	 *            giá trị đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validatePassword(String password) {
		// Khởi tạo biến lỗi
		String message = "";
		// Không nhập
		if (Common.checkEmpty(password)) {
			// Thêm câu thông báo không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD);
			// Check kí tự 1 byte
		} else if (!Common.isOneByteCharater(password)) {
			// Thêm câu thông báo lỗi ký tự không phải 1 byte
			message = MessageErrorProperties.getValueByKey(Constant.ER008_PASSWORD);
			// Check length
		} else if (!Common.checkLength(password, Constant.MIN_LENGTH_PASSWORD, Constant.MAX_LENGTH_PASSWORD)) {
			// Thêm câu thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER007_PASSWORD);
		}
		return message;
	}

	/**
	 * Hàm check hạng mục confirmPassword mã lỗi: ER017
	 * 
	 * @param password
	 *            mật khẩu đã nhập
	 * @param confirmPassword
	 *            mật khẩu xác nhận đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateConfirmPassword(String password, String confirmPassword) {
		// Khơi tạo biến lỗi
		String message = "";
		// check giống pass
		if (!Common.compareString(password, confirmPassword)) {
			// Thêm câu thông báo lỗi không giống pass
			message = MessageErrorProperties.getValueByKey(Constant.ER017_PASSWORD_CONFIRM);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục codeLevel mã lỗi: ER004
	 * 
	 * @param codeLevel
	 *            giá trị đã chọn
	 * @return câu thông báo lỗi nếu có
	 * @throws ClassNotFoundException
	 *             lỗi không tìm được class
	 * @throws SQLException
	 *             lỗi thao tác db
	 */
	private String validateCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến lỗi
		String message = "";
		// Khởi tạo biến mstJapanLogic
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		// Check không tồn tại
		if (mstJapanLogic.getNameLevelByCodeLevel(codeLevel) == null) {
			// Thêm câu thông báo lỗi đã tồn tại
			message = MessageErrorProperties.getValueByKey(Constant.ER004_CODE_LEVEL);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục startDate mã lỗi: ER011
	 * 
	 * @param startDate
	 *            giát trị đã chọn
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateStartDate(String startDate) {
		// Khởi tạo biến lỗi
		String message = "";
		// Check ngày không hợp lệ
		if (!Common.checkDate(startDate)) {
			// Thêm câu thông lỗi ngày không hơp lệ
			message = MessageErrorProperties.getValueByKey(Constant.ER011_START_DATE);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục endDate mã lỗi: ER011, ER012
	 * 
	 * @param startDate
	 *            ngày bắt đầu
	 * @param endDate
	 *            ngày kết thúc
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateEndDate(String startDate, String endDate) {
		// Khởi tạo biến lỗi
		String message = "";
		// Check ngày không hợp lệ
		if (!Common.checkDate(endDate)) {
			// Thêm câu thông lỗi ngày không hơp lệ
			message = MessageErrorProperties.getValueByKey(Constant.ER011_END_DATE);
			// Ngày kết thúc < ngày hết hạn
		} else if (!Common.checkEndDate(startDate, endDate)) {
			// Thêm câu thông báo lỗi startDate < endDate
			message = MessageErrorProperties.getValueByKey(Constant.ER012_END_DATE);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm check hạng mục total mã lỗi: ER001, ER018, ER021
	 * 
	 * @param total
	 *            giá trị đã nhập
	 * @return câu thông báo lỗi nếu có
	 */
	private String validateTotal(String total) {
		// Khởi tạo biến lỗi
		String message = "";
		// check không nhập
		if (Common.checkEmpty(total)) {
			// Thêm câu thông báo lỗi không nhập
			message = MessageErrorProperties.getValueByKey(Constant.ER001_TOTAL);
			// check số halfsize
		} else if (!Common.checkFormat(total, Constant.FORMAT_TOTAL)) {
			// Thêm câu thông báo lỗi số halfsize
			message = MessageErrorProperties.getValueByKey(Constant.ER018_TOTAL);
			// Check điểm không quá 180
		} else if (Common.convertStringToInt(total, Constant.OVER_MAX_TOTAL) > Constant.MAX_TOTAL) {
			// Thêm câu thông báo lỗi độ dài trong khoảng
			message = MessageErrorProperties.getValueByKey(Constant.ER021_TOTAL);
		}
		// Trả về thông báo lỗi
		return message;
	}

	/**
	 * Hàm validate và add lỗi
	 * 
	 * @param userInfor
	 *            userInfor cần validate
	 * @return listErr danh sách lỗi
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi thao tác vs db
	 */
	public List<String> validateUserInfor(UserInforEntities userInfor) throws ClassNotFoundException, SQLException {
		// Khởi tạo listErr
		List<String> listErr = new ArrayList<String>();

		// Nếu là trường hợp ADD thì check thêm loginName
		if (Constant.USER_ID_DEFAULT == userInfor.getUserId()) {
			// Check hạng mục loginName và thêm thông báo lỗi hạng mục loginName
			Common.addErr(listErr,  validateLoginName(userInfor.getLoginName()));
		}

		// Check hạng mục loginName và thêm thông báo lỗi hạng mục group
		Common.addErr(listErr, validateGroup(userInfor.getGroupId()));
		// Check hạng mục fullName và thêm thông báo lỗi hạng mục fullName
		Common.addErr(listErr, validateFullName(userInfor.getFullName()));
		// Check hạng mục fullNameKana và thêm câu thông báo lỗi nếu có lỗi
		Common.addErr(listErr, validateFullNameKana(userInfor.getFullNameKana()));
		// Check hạng mục birthday và thêm câu thông báo lỗi nếu có lỗi
		Common.addErr(listErr, validateBirthday(userInfor.getBirthday()));
		// Check hạng mục email và thêm câu thông báo lỗi nếu có lỗi
		Common.addErr(listErr, validateEmail(userInfor.getEmail(), userInfor.getUserId()));
		// Check hạng mục tel và thêm câu thông báo lỗi nếu có lỗi
		Common.addErr(listErr, validateTel(userInfor.getTel()));

		// Nếu là trường hợp ADD thì check hạng mục password và confirmPassword
		if (Constant.USER_ID_DEFAULT == userInfor.getUserId()) {
			// Check hạng mục password và thêm câu thông báo lỗi nếu có lỗi
			Common.addErr(listErr, validatePassword(userInfor.getPassword()));
			// Nếu có nhập trường password
			if (!Common.checkEmpty(userInfor.getPassword())) {
				// Thì validate confirmPassword
				Common.addErr(listErr, validateConfirmPassword(userInfor.getPassword(), userInfor.getConfirmPassword()));
			}
		}

		// Nếu đã chọn trình độ tiếng Nhật
		if (!Constant.CODE_LEVEL_DEFAULT.equals(userInfor.getCodeLevel())) {
			// Check hạng mục codeLevel và thêm câu thông báo lỗi nếu có lỗi
			Common.addErr(listErr, validateCodeLevel(userInfor.getCodeLevel()));
			// Check hạng mục startDate và thêm câu thông báo lỗi nếu có lỗi
			Common.addErr(listErr, validateStartDate(userInfor.getStartDate()));
			// Check hạng mục endDate và thêm câu thông báo lỗi nếu có lỗi
			Common.addErr(listErr, validateEndDate(userInfor.getStartDate(), userInfor.getEndDate()));
			// Check hạng mục total và thêm câu thông báo lỗi nếu có lỗi
			Common.addErr(listErr, validateTotal(userInfor.getTotal()));
		}
		// Trả về danh sách lỗi
		return listErr;
	}
}
