/**
 * Copyright(C) 2020 Luvina Software
 * LogoutController.java, [dd/mm/yyyy], KhangNL
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * Controller điều khiển chức năng logout
 *
 * @author KhangNL
 *
 */
public class LogoutController extends HttpServlet {
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
		try {
			// Lấy session hiện tại
			HttpSession session = req.getSession();
			// Xóa session
			session.invalidate();
			// sendRedirect đến url_login
			resp.sendRedirect(req.getContextPath() + Constant.URL_LOGIN);
		} catch (Exception e) {
			// Nếu bắt được exeption thì in ra màn hình console
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến màn hình system_error.jsp
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}
}
