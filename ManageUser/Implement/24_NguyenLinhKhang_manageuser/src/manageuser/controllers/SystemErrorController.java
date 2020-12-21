package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Controller điều khiển system_error
 *  
 * @author KhangNL
 */

public class SystemErrorController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm điều khiển hiển thị màn hình systemerror
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Lấy về tên câu thông báo lỗi
			// Nếu xảy ra lỗi với db thì messError default = null, 
			// còn các trường hợp khác (không được xóa admin, biên tập user không tồn tại) thì khác null
			String messError = req.getParameter(Constant.MESS_ERROR);
			// Nếu xảy ra lỗi với db
			if (messError == null) {
				// Set mã lỗi = ER015
				messError = Constant.ER015;
			}
			// Set nội dung câu thông báo lên req
			req.setAttribute(Constant.ERROR, MessageErrorProperties.getValueByKey(messError));
			// Di chuyển đến màn hình SystemError
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_SYSTEM_ERROR);
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			System.out.println(this.getClass().getName() + "-" + Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "-" + e.getMessage());
			// Set nội dung câu thông báo lên req
			req.setAttribute(Constant.ERROR, MessageErrorProperties.getValueByKey(Constant.ER015));
			// Hiển thị màn hình thông báo lỗi
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_SYSTEM_ERROR);
			dispatcher.forward(req, resp);
		}
	}
}
