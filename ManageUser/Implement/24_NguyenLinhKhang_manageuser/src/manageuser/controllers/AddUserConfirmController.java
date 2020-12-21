package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Controller điều khiển chức năng thêm mới user màn ADM004
 * 
 * @author KhangNL
 * 
 */

public class AddUserConfirmController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm thực hiện điều khiển hiển thị màn hình ADM004 chức năng add user
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
				// set key lên request để khi click button xác nhận có thể lấy
				// lại thông tin user từ session theo key
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
	 * Controller điều khiển khi click button OK tại màn ADM004 chức năng add
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Khởi tại session
			HttpSession session = req.getSession();
			// Lấy về key của userInfor trên req
			String key = req.getParameter(Constant.ATTRIBUTE_KEY);
			// Lấy về đối tượng UserInfor từ session theo key
			UserInforEntities userInfor = (UserInforEntities) session.getAttribute(key);
			// Xóa đối tượng userInfor khỏi session
			session.removeAttribute(key);
			// Khai báo biến lưu path
			String path = "";
			// Khai báo biến lưu kết quả thêm mới
			boolean result = false;
			// khởi tạo tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Nếu chưa tồn tại loginName và Email
			if (!tblUserLogic.checkExistLoginName(userInfor.getLoginName())
					&& !tblUserLogic.checkExistEmail(userInfor.getEmail(), Constant.USER_ID_DEFAULT)) {
				// Thực hiện thêm mới và lấy về kết quả trạng thái thêm mới
				result = tblUserLogic.createUser(userInfor);
				// Nếu đã tồn tại
			}						
			// Nếu thêm mới thành công
			if (result) {
				// Lưu url đến SuccessController
				path = Constant.URL_SUCCESS + "?" + Constant.MESS_NOTI + "=" + Constant.MSG001;
			// Nếu thất bại hoặc đã tồn tại loginName hoặc Email
			} else {
				// Lưu url đến SystemError
				path = Constant.URL_SYSTEM_ERROR;
			}
			// Gọi đến url tương ứng
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
