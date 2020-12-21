package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller điều khiển chức năng xem thông tin chi tiết user màn ADM005
 * 
 * 
 * @author KhangNL
 * 
 */

public class ViewDetailUserController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm điều khiển hiển thị màn hình ADM005
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Kiểm tra checklogin nếu thành công thì thực hiện các phương thức
			// Lấy về userId từ req
			int userId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_USER_ID),
					Constant.USER_ID_DEFAULT);
			// khởi tạo đối tượng tblUserLogic để thao tác với tblUser
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Nếu check userId lấy về từ req là số và check userId tồn tại, là
			// user
			if ((userId != Constant.USER_ID_DEFAULT) && (tblUserLogic.checkExistUserById(userId))) {
				// Tìm kiếm đối tượng UserInfor theo userId
				UserInforEntities userInfor = tblUserLogic.getUserInforByUserId(userId);
				// Set đối tượng UserInfor vừa lấy được lên request
				req.setAttribute(Constant.USER_INFOR, userInfor);
				// Set câu MSG004 lên req
				req.setAttribute(Constant.MSG004, MessageProperties.getValueByKey(Constant.MSG004));
				// Chuyển đến màn ADM005 với dữ liệu của userInfor vừa tìm
				// được
				RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM005);
				dispatcher.forward(req, resp);
			// Nếu userId lấy về không là số, không tồn tại trong db, không là user
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
}
