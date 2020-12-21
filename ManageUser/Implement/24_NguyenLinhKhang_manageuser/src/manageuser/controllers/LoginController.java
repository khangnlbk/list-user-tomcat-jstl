/**
 * Copyright(C) 2020 Luvina Software
 * LoginController.java, 15/07/2020, KhangNL
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 *Controller điều khiển chức năng Login
 *
 * @author KhangNL
 *
 */
public class LoginController extends HttpServlet {

	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm điều khiển hiển thị màn hình ADM001
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// forward đến trang ADM001
		RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM001);
		dispatcher.forward(req, resp);
	}

	/**
	 * Controller điều khiển khi click button OK tại màn ADM001
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Khai báo và khởi tạo đối tượng ValidateTblUser
			ValidateUser validateTblUser = new ValidateUser();
			// Khởi tạo danh sách lỗi
			List<String> listErr = new ArrayList<String>();
			// Lấy về loginName trên request
			String loginName = req.getParameter(Constant.LOGIN_NAME);
			// Lấy về password trên request
			String password = req.getParameter(Constant.PASSWORD);
			// gọi đến validateLogin và gán lỗi vào listErr nếu có lỗi
			listErr = validateTblUser.validateLogin(loginName, password);
			// Nếu không có lỗi
			if (listErr.size() == 0) {
				// Lấy về session hiện tại
				HttpSession session = req.getSession();
				// Set loginName lên session để giữ lại phiên đăng nhập, sử dụng cho loginFilter
				session.setAttribute(Constant.LOGIN_NAME, loginName);
				// senRedirect đến url_list_User
				resp.sendRedirect(req.getContextPath() + Constant.URL_LIST_USER);
			} else { 
				// Nếu có lỗi
				// Set loginName và danh sách lỗi lên req
				req.setAttribute(Constant.LOGIN_NAME, loginName);
				req.setAttribute(Constant.LIST_ERR, listErr);
				// Hiển thị màn hình ADM001
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM001);
				dispatcher.forward(req, resp);
			}
		} catch (Exception e) {
			// Nếu bắt được exeption thì in ra màn hình console 
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến màn hình system_error.jsp
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}
}