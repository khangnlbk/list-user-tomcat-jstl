/**
 * Copyright(C) 2020 Luvina Software
 * AddUserInputController.java, [dd/mm/yyyy], KhangNL
 */
package manageuser.controllers;

import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUserInfor;

/**
 * Controller điều khiển chức năng thêm mới user màn hình ADM003
 *
 * @author KhangNL
 *
 */
public class AddUserInputController extends HttpServlet {

	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm thực hiện điều khiển hiển thị màn hình ADM003 chức năng add user
 	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServerException {
		try {
			// gọi hàm setDataLogic để set các giá trị lên selectBox
			Common.setDataLogic(req);
			// Khởi tạo, gán giá trị userInfor
			UserInforEntities userInfor = getDefaultValue(req);
			// Nếu userInfor != null 
			if (userInfor != null) {
				// set userInfor lên request
				req.setAttribute(Constant.ATTRIBUTE_USER_INFOR, userInfor);
				// Đưa dữ liệu lên màn hình ADM003
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
				dispatcher.forward(req, resp);
			// Nếu userInfor == null (trường hợp back về từ ADM004 rồi ấn F5)
			} else {
				// SendRedirect đến SystemErrorController
				resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
			}
		} catch (Exception e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến SystemErrorController
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}

	/**
	 * Controller điều khiển khi click button xác nhận tại màn ADM003 chức năng add
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServerException {
		try {
			// khởi tạo session
			HttpSession session = req.getSession();
			// Khởi tạo các biến
			ValidateUserInfor validateUserInfor = new ValidateUserInfor();
			// khởi tạo và gán giá trị cho đối tượng userInfor
			UserInforEntities userInfor = getDefaultValue(req);
			List<String> listErr = validateUserInfor.validateUserInfor(userInfor);
			// Nếu có lỗi
			if (listErr.size() > 0) {
				// Gọi hàm set giá trị của các selected box lên req
				Common.setDataLogic(req);
				// Set danh sách lỗi lên req
				req.setAttribute(Constant.LIST_ERR, listErr);
				// Set đối tượng userInfor lên req để giữ lại giá trị trên màn hình
				req.setAttribute(Constant.USER_INFOR, userInfor);
				// Đưa dữ liệu lên màn hình ADM003
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
				dispatcher.forward(req, resp);
			} else {
				// Nếu không có lỗi
				// Lấy về session hiện tại
				session = req.getSession();
				// Lấy về key của đối tượng UserInfor
				String key = Common.createKey();
				// Set key lên session
				session.setAttribute(key, userInfor);
				// set biến check di chuyển từ màn ADM003 sang ADM004 để
				// trường hợp nhập link trực tiếp
				session.setAttribute(Constant.PASSED, Constant.PASSED);
				// Gọi đến url AddUserConfim
				resp.sendRedirect(req.getContextPath() + Constant.URL_ADD_USER_CONFIRM + "?key=" + key);
			}
		} catch (Exception e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến SystemErrorController
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}

	/**
	 * Phương thức để set các giá trị default
	 * 
	 * @param req
	 *            request truyền vào
	 * @return userInfor đối tượng userInfor
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi kết nối db
	 */
	private UserInforEntities getDefaultValue(HttpServletRequest req) throws ClassNotFoundException, SQLException {
		// Khởi tạo UserInfor
		UserInforEntities userInfor = new UserInforEntities();
		// khởi tạo MstJapanLogic sử dụng để lấy name level của user
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		// khởi tạo mstGroupLogic để lấy GroupName
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();

		// Khởi tạo đối tượng action để kiểm tra action đến controller
		String action = req.getParameter(Constant.ACTION);
		// Trường hợp hiển thị ban đầu của màn hình ADM003
		// Khởi tạo loginName default = rỗng
		String loginName = Constant.EMPTY;
		// Khởi tạo groupId default = 0
		int groupId = Constant.GROUP_ID_DEFAULT;
		// Khởi tạo groupName default = rỗng
		String groupName = Constant.EMPTY;
		// Khởi tạo fullName default = rỗng
		String fullName = Constant.EMPTY;
		// Khởi tại fullNameKana default = rỗng
		String fullNameKana = Constant.EMPTY;
		// Lấy về ngày tháng năm hiện tại
		int year = Common.getYearNow();
		int month = Common.getMonthNow();
		int day = Common.getDayNow();
		// khởi tạo birthday
		String birthday = Common.convertToString(year, month, day);
		// Khởi tạo email default = rỗng
		String email = Constant.EMPTY;
		// Khởi tạo tel default = rỗng
		String tel = Constant.EMPTY;
		// Khởi tạo password default = rỗng
		String password = Constant.EMPTY;
		// Khởi tạo confirmPass default = rỗng
		String confirmPassword = Constant.EMPTY;
		// Khởi tạo codeLevel default = rỗng
		String codeLevel = Constant.EMPTY;
		// Khởi tạo nameLevel default = rỗng
		String nameLevel = Constant.EMPTY;
		// Khởi tạo startDateJapan
		String startDateJapan = Common.convertToString(year, month, day);
		// Khởi tạo endDateJapan
		String endDateJapan = Common.convertToString(year + 1, month, day);
		// Khởi tạo total default = rỗng
		String total = Constant.EMPTY;

		// Trường hợp click confirm
		if (Constant.VALIDATE_ACTION.equals(action)) {
			// lấy loginName từ request
			loginName = req.getParameter(Constant.LOGIN_NAME);
			// lấy groupId từ request
			groupId = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_GROUP_ID));
			// Lấy groupName theo groupId
			groupName = mstGroupLogic.getGroupNameByGroupId(groupId);
			// lấy fullName từ request
			fullName = req.getParameter(Constant.ATTRIBUTE_FULL_NAME);
			// lấy fullNameKana từ request
			fullNameKana = req.getParameter(Constant.ATTRIBUTE_FULL_NAME_KANA);
			// lấy year theo year birthday
			year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_BIRTHDAY));
			// lấy month theo month birthday
			month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_BIRTHDAY));
			// lấy day theo month birthday
			day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_BIRTHDAY));
			// convert và gán cho biến birthday
			birthday = Common.convertToString(year, month, day);
			// lấy email từ request
			email = req.getParameter(Constant.ATTRIBUTE_EMAIL);
			// lấy tel từ request
			tel = req.getParameter(Constant.ATTRIBUTE_TEL);
			// lấy password từ request
			password = req.getParameter(Constant.ATTRIBUTE_PASSWORD);
			// lấy confirmPassword từ request
			confirmPassword = req.getParameter(Constant.ATTRIBUTE_CONFIRM_PASSWORD);
			// lấy codeLevel từ request
			codeLevel = req.getParameter(Constant.ATTRIBUTE_CODE_LEVEL);
			// Nếu có chọn trình độ tiếng Nhật
			if (!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)) {
				// Lấy nameLevel theo codeLevel
				nameLevel = mstJapanLogic.getNameLevelByCodeLevel(codeLevel);
				// convert ngày bắt đầu theo năm tháng ngày
				year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_START_DATE));
				month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_START_DATE));
				day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_START_DATE));
				startDateJapan = Common.convertToString(year, month, day);
				// convert ngày kết thúc theo năm tháng ngày
				year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_END_DATE));
				month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_END_DATE));
				day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_END_DATE));
				endDateJapan = Common.convertToString(year, month, day);
				// lấy total từ request
				total = req.getParameter(Constant.ATTRIBUTE_TOTAL);
			}
		}
		// Trường hợp Back từ màn ADM004
		if (Constant.BACK_ACTION.equals(action)) {
			// khởi tạo session
			HttpSession session = req.getSession();
			// Lấy về key
			String key = req.getParameter(Constant.ATTRIBUTE_KEY);
			// Lấy thông tin UserInfor từ session
			userInfor = (UserInforEntities) session.getAttribute(key);
			// xoá key trên session
			session.removeAttribute(key);
		} else {
			// Nếu không phải trường hợp BACK thì set các giá trị vào userInfor
			// Set thông tin cho đối tượng UserInfor
			userInfor.setUserId(Constant.USER_ID_DEFAULT);
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			userInfor.setGroupName(groupName);
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);
			userInfor.setBirthday(birthday);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setPassword(password);
			userInfor.setConfirmPassword(confirmPassword);
			userInfor.setCodeLevel(codeLevel);
			userInfor.setNameLevel(nameLevel);
			userInfor.setStartDate(startDateJapan);
			userInfor.setEndDate(endDateJapan);
			userInfor.setTotal(total);
		}

		// Trả về đối tượng userInfor
		return userInfor;
	}
}
