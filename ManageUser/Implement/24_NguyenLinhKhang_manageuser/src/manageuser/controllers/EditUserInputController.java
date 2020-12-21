package manageuser.controllers;

import java.io.IOException;
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
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUserInfor;

/**
 * Controller điều khiển chức năng update user màn hình ADM003
 * 
 * @author KhangNL
 */
public class EditUserInputController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm điều khiển hiển thị màn hình ADM003 chức năng update user
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Khởi tạo biến tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Lấy userId từ req
			int userId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_USER_ID),
					Constant.USER_ID_DEFAULT);
			// Khai báo đối tượng userInfor
			UserInforEntities userInfor = null;
			// Nếu userId tồn tại trong DB và đó là id của user
			if (tblUserLogic.checkExistUserById(userId)) {
				// Lấy đối tượng userInfor
				userInfor = getDefaultValue(req);
			}
			// Nếu tồn tại thông tin của user trong DB
			if (userInfor != null) {
				// Set data logic cho các selectbox
				Common.setDataLogic(req);
				// Set đối tượng UserInfor vừa lấy được lên request
				req.setAttribute(Constant.USER_INFOR, userInfor);
				// Chuyển đến màn ADM003 với dữ liệu của userInfor vừa tìm được
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
				dispatcher.forward(req, resp);
			} else {
				// SendRedirect đến SystemErrorController với mã lỗi ER013
				resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR + "?" + Constant.MESS_ERROR + "="
						+ Constant.ER013);
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
	 * Controller điều khiển khi click button xác nhận tại màn ADM003 chức năng update user
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file 
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// Khai báo session
			HttpSession session;
			// Khởi tạo tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Lấy userId từ req
			int userId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_USER_ID), Constant.USER_ID_DEFAULT);
			// Khai báo đối tượng userInfor
			UserInforEntities userInfor = null;
			// Nếu userId tồn tại trong DB và đó là id của user
			if (tblUserLogic.checkExistUserById(userId)) {
				// Lấy đối tượng userInfor
				userInfor = getDefaultValue(req);
				// khởi tạo đối tượng validateUserInfor
				ValidateUserInfor validateUserInfor = new ValidateUserInfor();
				// Thực hiện validate trên màn ADM003
				List<String> listErr = validateUserInfor.validateUserInfor(userInfor);
				// Nếu có lỗi
				if (listErr.size() > 0) {
					// Gọi hàm set giá trị của các selected box lên req
					Common.setDataLogic(req);
					// Set danh sách lỗi lên request
					req.setAttribute(Constant.LIST_ERR, listErr);
					// Set đối tượng userInfor lên req để giữ lại giá trị trên màn hình
					req.setAttribute(Constant.USER_INFOR, userInfor);
					// Hiển thị lên màn hình ADM003
					RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM003);
					dispatcher.forward(req, resp);
				// Nếu không có lỗi
				} else {
					// Lấy về session hiện tại
					session = req.getSession();
					// Lấy về keySession của đối tượng UserInfor
					String key = Common.createKey();
					// Set thông tin lên session
					session.setAttribute(key, userInfor);
					// set biến PASSED lên session để kiểm tra trường hợp nhập link trực tiếp của màn ADM004
					session.setAttribute(Constant.PASSED, Constant.PASSED);
					// Gọi đến url EditUserConfim với key
					resp.sendRedirect(req.getContextPath() + Constant.URL_EDIT_USER_CONFIRM + "?key=" + key);
				}
			} else {
				// SendRedirect đến SystemErrorController với câu thông báo ER013
				resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR + "?" + Constant.MESS_ERROR + "="
						+ Constant.ER013);
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
	 * Hàm trả về đối tượng UserInfor
	 * 
	 * @param req
	 *            request
	 * @return userInfor đối tượng UserInfor
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi thao tác với db
	 */
	private UserInforEntities getDefaultValue(HttpServletRequest req) throws ClassNotFoundException, SQLException {
		// Khai tao session
		HttpSession session;
		// Khai bao bien tblUserLogic
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		// Khai báo và khởi tạo đối tượng userInfor
		UserInforEntities userInfor = null;
		// khởi tạo MstJapanLogic sử dụng để lấy name level của user
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		// khởi tạo MstGroupLogic sử dụng để lấy name group của user
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		// Lấy userId từ req
		int userId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_USER_ID), Constant.USER_ID_DEFAULT);
		// Lấy về trạng thái đến ADM003
		String action = req.getParameter(Constant.ACTION);
		// Nếu là trường hợp validate
		if (Constant.VALIDATE_ACTION.equals(action)) {
			userInfor = new UserInforEntities();
			// Lấy thông tin của user trong trường hợp edit từ req
			String loginName = req.getParameter(Constant.LOGIN_NAME);
			// lấy groupId từ req
			int groupId = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_GROUP_ID));
			// Lấy groupName theo groupId
			String groupName = mstGroupLogic.getGroupNameByGroupId(groupId);
			// lấy fullName từ req
			String fullName = req.getParameter(Constant.ATTRIBUTE_FULL_NAME);
			// lấy fullNameKana từ req
			String fullNameKana = req.getParameter(Constant.ATTRIBUTE_FULL_NAME_KANA);
			// lấy year, month, day birhtday từ req
			int year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_BIRTHDAY));
			int month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_BIRTHDAY));
			int day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_BIRTHDAY));
			// convert và gán cho biến birthday
			String birthday = Common.convertToString(year, month, day);
			// lấy email từ req
			String email = req.getParameter(Constant.ATTRIBUTE_EMAIL);
			// lấy tel từ req
			String tel = req.getParameter(Constant.ATTRIBUTE_TEL);
			// lấy codeLevel từ req
			String codeLevel = req.getParameter(Constant.ATTRIBUTE_CODE_LEVEL);
			// Nếu có chọn trình độ tiếng Nhật
			if (!Constant.CODE_LEVEL_DEFAULT.equals(codeLevel)) {
				// Lấy nameLevel theo codeLevel
				String nameLevel = mstJapanLogic.getNameLevelByCodeLevel(codeLevel);
				// khởi tạo ngày bắt đầu theo năm tháng ngày lấy từ req
				year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_START_DATE));
				month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_START_DATE));
				day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_START_DATE));
				String startDate = Common.convertToString(year, month, day);
				// khởi tạo ngày kết thúc theo năm tháng ngày lấy từ req
				year = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_YEAR_END_DATE));
				month = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_MONTH_END_DATE));
				day = Integer.parseInt(req.getParameter(Constant.ATTRIBUTE_DAY_END_DATE));
				String endDate = Common.convertToString(year, month, day);
				// lấy total từ req
				String total = req.getParameter(Constant.ATTRIBUTE_TOTAL);
				// set thông tin trình độ tiếng nhật cho userInfor
				userInfor.setNameLevel(nameLevel);
				userInfor.setStartDate(startDate);
				userInfor.setEndDate(endDate);
				userInfor.setTotal(total);
			}
			// Set thông tin cho userInfor
			userInfor.setUserId(userId);
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			userInfor.setGroupName(groupName);
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);
			userInfor.setBirthday(birthday);
			userInfor.setEmail(email);
			userInfor.setTel(tel);
			userInfor.setCodeLevel(codeLevel);
		// Trường hợp back từ màn ADM004
		} else if (Constant.BACK_ACTION.equals(action)) { 
			// Lấy về session hiện tại
			session = req.getSession();
			// Lấy về key
			String key = req.getParameter(Constant.ATTRIBUTE_KEY);
			// Lấy thông tin UserInfor từ session
			userInfor = (UserInforEntities) session.getAttribute(key);
			// xoá key trên session
			session.removeAttribute(key);
		// Nếu là trường hợp default
		} else { 
			// Tìm kiếm đối tượng UserInfor theo userId trong DB
			userInfor = tblUserLogic.getUserInforByUserId(userId);
			// Nếu không có trình độ tiếng Nhật
			if (userInfor.getCodeLevel() == null) {
				// lấy năm hiện tại
				int year = Common.getYearNow();
				// lấy tháng hiện tại
				int month = Common.getMonthNow();
				// lấy ngày hiện tại
				int day = Common.getDayNow();
				// Set cho startDate
				userInfor.setStartDate(Common.convertToString(year, month, day));
				// Set cho endDate
				userInfor.setEndDate(Common.convertToString(year + 1, month, day));
			}
		}
		// Trả về đối tượng UserInfor
		return userInfor;
	}
}
