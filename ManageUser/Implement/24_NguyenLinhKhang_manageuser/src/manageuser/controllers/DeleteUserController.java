package manageuser.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.TblUserEntities;
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Controller điều khiển chức năng xóa user màn hình ADM005
 */

public class DeleteUserController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller điều khiển khi click button OK tại alert delete tại màn ADM005 chức năng delete
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Khởi tạo tblDetailUserJapanLogic
			TblDetailUserJapanLogic tblDetailUserJapanLogic = new TblDetailUserJapanLogicImpl();
			// Lấy userId từ request về
			int userId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_USER_ID),
					Constant.USER_ID_DEFAULT);
			// Khai báo biến path đường dẫn
			String path = "";
			// Khởi tạo biến tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// Lấy ra tblUserEntities theo userId
			TblUserEntities tblUserEntities = tblUserLogic.getUserByUserId(userId);
			// Nếu tìm thấy user
			if (tblUserEntities != null) {
				// nếu rule = RULE_USER thì gọi hàm xóa
				if (tblUserEntities.getRule() == Constant.RULE_USER) {
					// kiểm tra tồn tại của trình độ tiếng nhật
					boolean existCodeLevel = tblDetailUserJapanLogic.checkExistDetailUserJapanByUserId(userId);
					// Thực hiện xóa
					if (tblUserLogic.deleteUser(userId, existCodeLevel)) {
						// Nếu xóa thành công, gán path đến màn hình ADM006 với
						// câu thông báo MSG003
						path = Constant.URL_SUCCESS + "?" + Constant.MESS_NOTI + "=" + Constant.MSG003;
					} else {
						// Nếu không thành công, di chuyển đến SystemError
						path = Constant.URL_SYSTEM_ERROR;
					}
				// Nếu user có rule khác RULE_USER
				} else {
					// Lưu path lỗi xóa admin với câu thông báo ER020
					path = Constant.URL_SYSTEM_ERROR + "?" + Constant.MESS_ERROR + "=" + Constant.ER020;
				}
			// Nếu không tìm thấy user
			} else {
				// Lưu path user không tồn tại với cấu thông báo ER013
				path = Constant.URL_SYSTEM_ERROR + "?" + Constant.MESS_ERROR + "=" + Constant.ER013;
			}
			// SendRedirect đến màn hình tương ứng
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
