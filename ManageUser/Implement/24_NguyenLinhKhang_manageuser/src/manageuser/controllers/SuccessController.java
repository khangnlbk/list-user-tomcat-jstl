package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller điều khiển chức năng hiển thị các câu thông báo thành công màn hình ADM006
 * 
 * 
 * @author KhangNL
 * 
 */
public class SuccessController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Hàm điều khiển hiển thị màn hình ADM006
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Lấy về tên câu thông báo lỗi
			String messNoti = req.getParameter(Constant.MESS_NOTI);
			// Set nội dung câu thông báo lên req
			req.setAttribute(Constant.MSG, MessageProperties.getValueByKey(messNoti));
			// Di chuyển đến màn hình ADM006
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM006);
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-" + Thread.currentThread().getStackTrace()[1].getMethodName()
					+ "-" + e.getMessage());
			// SendRedirect đến SystemErrorController
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}
}
