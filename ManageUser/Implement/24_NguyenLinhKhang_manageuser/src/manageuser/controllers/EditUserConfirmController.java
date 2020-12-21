package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Controller điều khiển chức năng sửa user màn hình ADM004
 * 
 * @author KhangNL
 */

public class EditUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserConfirmController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Hàm điều khiển hiển thị màn hình ADM004 chức năng update user
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Khởi tại session
			HttpSession session = req.getSession();
			// Lấy biến check di chuyển từ session về
			String checkPassed = (String) session.getAttribute(Constant.PASSED);
			// Nếu đã di chuyển từ màn ADM003 sang ADM004
			if (Constant.PASSED.equals(checkPassed)) {
				// xoá biến checkPassed
				session.removeAttribute(checkPassed);
				// lấy về giá trị key của userInfor từ request
				String key = req.getParameter(Constant.ATTRIBUTE_KEY);
				// lấy về đối tượng UserInfor từ session theo key tương ứng
				UserInforEntities userInfor = (UserInforEntities) session.getAttribute(key);
				// set salt lên request để khi click button xác nhận có
				// thể lấy lại thông tin user từ session theo key
				req.setAttribute(Constant.ATTRIBUTE_KEY, key);
				// set userInfor lên request để hiển thị ở màn ADM004
				req.setAttribute(Constant.ATTRIBUTE_USER_INFOR, userInfor);
				// forward đến màn ADM004
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM004);
				dispatcher.forward(req, resp);
				// Nếu không phải di chuyển từ màn ADM003
			} else {
				// sendRedirect đến systemerror
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
	 * Controller điều khiển khi click button OK tại màn ADM004 chức năng update users
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// Khởi tại session
			HttpSession session = req.getSession();
			// Khởi tạo tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Khởi tạo tblDetailUserJapanLogic
			TblDetailUserJapanLogic tblDetailUserJapanLogic = new TblDetailUserJapanLogicImpl();
			// Lấy về session hiện tại
			session = req.getSession();
			// Lấy về key trên req
			String key = req.getParameter(Constant.ATTRIBUTE_KEY);
			// Lấy về đối tượng UserInfor
			UserInforEntities userInfor = (UserInforEntities) session.getAttribute(key);
			// Xóa đối tượng userInfor khỏi session
			session.removeAttribute(key);
			// Khai báo biến lưu kết quả thêm mới
			boolean result = false;
			// Khai báo biến lưu path
			String path = "";
			// Nếu userId tồn tại và là user
			if (tblUserLogic.checkExistUserById(userInfor.getUserId())) {
				// Khởi tạo biến check email tồn tại, trả về true nếu userId khác chứa email này
				boolean checkExistEmail = tblUserLogic.checkExistEmail(userInfor.getEmail(), userInfor.getUserId());
				// Nếu userId khác không chứa email này
				if (!checkExistEmail) {
					// Check tồn tại phần trình độ tiếng Nhật trong DB cho user
					boolean existCodeLevel = tblDetailUserJapanLogic
							.checkExistDetailUserJapanByUserId(userInfor.getUserId());
					// Set kết quả check cho đối tượng userInfor
					userInfor.setExistCodeLevel(existCodeLevel);
					// Thực hiện edit và lấy về kết quả edit
					result = tblUserLogic.editUser(userInfor);
				}
				// Nếu edit thành công
				if (result) {
					// Lưu path edit thành công với câu thông báo MSG002
					path = Constant.URL_SUCCESS + "?" + Constant.MESS_NOTI + "=" + Constant.MSG002;
				} else { // Nếu thất bại
					// Lưu path hệ thống có lỗi
					path = Constant.URL_SYSTEM_ERROR;
				}
			// nếu userId không phải là user, không tồn tại hoặc userId không chứa email
			} else {
				// Lưu path user không tồn tại với cấu thông báo ER013
				path = Constant.URL_SYSTEM_ERROR + "?" + Constant.MESS_ERROR + "=" + Constant.ER013;
			}
			
			// Di chuyển đến MH tương ứng
			resp.sendRedirect(req.getContextPath() + path);
		} catch (Exception e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến SystemErrorController
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}

}
