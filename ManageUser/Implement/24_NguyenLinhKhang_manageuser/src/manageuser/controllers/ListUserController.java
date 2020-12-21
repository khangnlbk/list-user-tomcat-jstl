/**
 * Copyright(C) 2020 Luvina Software
 * ListUserController.java, [dd/mm/yyyy], KhangNL
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

import manageuser.entities.MstGroupEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.ConfigProperties;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller điều khiển chức năng list user màn hình ADM002
 *
 * @author KhangNL
 *
 */
public class ListUserController extends HttpServlet {
	/**
	 * Biến tự khởi tạo serialVersionUID để đảm bảo servlet cùng một version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Hàm điều khiển hiển thị màn hình ADM002
	 * @param req request servlet
	 * @param resp response servlet
	 * @throws ServletException lỗi servlet
	 * @throws IOException lỗi không đọc được file
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// khởi tạo session
			HttpSession session = req.getSession();
			// Nếu login thành công
			// khởi tạo tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			// khởi tạo listUsers
			List<UserInforEntities> listUsers = new ArrayList<>();
			// khởi tạo mstGroupLogic thao tác với group
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			// list group
			List<MstGroupEntities> listMstGroup = mstGroupLogic.getAllMstGroup();
			// list pagging
			List<Integer> listPaging = new ArrayList<Integer>();

			// Khởi tạo các giá trị DEFAULT
			// khởi tạo currentPage trang hiện tại
			int currentPage = Constant.CURRENT_PAGE_DEFAULT;
			// khởi tạo groupId
			int groupId = Constant.GROUP_ID_DEFAULT;
			// khởi tạo fullName
			String fullName = Constant.EMPTY;
			// khởi tạo sortType biến ưu tiên sắp xếp
			String sortType = Constant.SORT_TYPE_FULLNAME;
			// khởi tạo sortByFullName
			String sortByFullName = Constant.SORT_ASC;
			// khởi tạo sortByEndDate
			String sortByCodeLevel = Constant.SORT_ASC;
			// khởi tạo sortByEndDate
			String sortByEndDate = Constant.SORT_DESC;
			// khởi tạo offset - biến lưu vị trí bắt đầu lấy dữ liệu
			int offset = Constant.OFFSET_DEFAULT;
			// khởi tạo limit - số giá trị hiển thị trên 1 page
			int limit = Common.getLimit();
			// set limit page lên request
			req.setAttribute(Constant.ATTRIBUTE_LIMIT_PAGE,
					Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE)));

			// Lấy về action để hiển thị của ADM002 : SEARCH, SORT, PAGING, BACK
			String action = req.getParameter(Constant.ACTION);

			// Nếu là trường hợp: search, sort, paging
			if (Constant.SEARCH_ACTION.equals(action) || Constant.SORT_ACTION.equals(action)
					|| Constant.PAGING_ACTION.equals(action)) {
				// lấy điều kiện tìm kiếm từ form
				fullName = req.getParameter(Constant.ATTRIBUTE_FULL_NAME);
				groupId = Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_GROUP_ID), Constant.GROUP_ID_DEFAULT);
				// Nếu là trường hợp sort, paging thì set thêm các giá trị lên
				// req
				if (Constant.SORT_ACTION.equals(action) || Constant.PAGING_ACTION.equals(action)) {
					// Lấy trang hiện tại
					currentPage =  Common.convertStringToInt(req.getParameter(Constant.ATTRIBUTE_CURRENT_PAGE), Constant.CURRENT_PAGE_DEFAULT);
					// Lấy về trường sắp xếp ưu tiên từ req
					sortType = req.getParameter(Constant.ATTRIBUTE_SORT_TYPE);
					if (Constant.SORT_TYPE_FULLNAME.equals(sortType)) {
						// nếu sortType là sắp xếp theo fullName
						// lấy về kiểu sắp xếp cho sortByFullName từ req
						sortByFullName = req.getParameter(Constant.ATTRIBUTE_SORT_BY_FULL_NAME);
					} else if (Constant.SORT_TYPE_CODE_LEVEL.equals(sortType)) {
						// nếu sortType là sắp xếp theo codeLevel
						// lấy về kiểu sắp xếp cho sortByCodeLevel từ req
						sortByCodeLevel = req.getParameter(Constant.ATTRIBUTE_SORT_BY_CODE_LEVEL);
					} else if (Constant.SORT_TYPE_END_DATE.equals(sortType)) {
						// nếu sortType là sắp xếp theo endDate
						// lấy về kiểu sắp xếp cho sortByEndDate từ req
						sortByEndDate = req.getParameter(Constant.ATTRIBUTE_SORT_BY_END_DATE);
					}
				}
				// Nếu là trường hợp BACK
			} else if (Constant.BACK_ACTION.equals(action)) {
				// Lấy các giá trị từ session về
				groupId = (int) session.getAttribute(Constant.ATTRIBUTE_GROUP_ID);
				fullName = (String) session.getAttribute(Constant.ATTRIBUTE_FULL_NAME);
				currentPage = (int) session.getAttribute(Constant.ATTRIBUTE_CURRENT_PAGE);
				sortType = (String) session.getAttribute(Constant.ATTRIBUTE_SORT_TYPE);
				sortByFullName = (String) session.getAttribute(Constant.ATTRIBUTE_SORT_BY_FULL_NAME);
				sortByCodeLevel = (String) session.getAttribute(Constant.ATTRIBUTE_SORT_BY_CODE_LEVEL);
				sortByEndDate = (String) session.getAttribute(Constant.ATTRIBUTE_SORT_BY_END_DATE);
			}

			// Tính tổng số user với điều kiện tìm kiếm
			int totalRecords = tblUserLogic.getTotalUsers(groupId, fullName);
			// Set total records lên req
			req.setAttribute(Constant.ATTRIBUTE_TOTAL_RECORDS, totalRecords);

			// Nếu tìm thấy user
			if (totalRecords > 0) {
				// Hàm lấy tổng số trang
				int totalPage = Common.getTotalPage(totalRecords, limit);
				// set total page lên request
				req.setAttribute(Constant.ATTRIBUTE_TOTAL_PAGE, totalPage);
				// Nếu trang hiện tại lớn hơn tổng số trang (ví dụ trường hợp:
				// có 11 bản ghi, currentPage=3, xóa 1 bản ghi: totalPage=2)
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				// Lấy vị trí cần lấy dữ liệu
				offset = Common.getOffset(currentPage, limit);
				// Lấy list paging
				listPaging = Common.getListPaging(totalRecords, limit, currentPage);
				// nếu có listPaging
				if (listPaging.size() > 0) {
					// set listPaging lên request
					req.setAttribute(Constant.ATTRIBUTE_LIST_PAGING, listPaging);
				}
				// Lấy danh sách user với điều kiện tìm kiếm đã nhập
				listUsers = tblUserLogic.getListUsers(offset, limit, groupId, fullName, sortType, sortByFullName,
						sortByCodeLevel, sortByEndDate);
				// Set danh sách lên req
				req.setAttribute(Constant.ATTRIBUTE_LIST_USERS, listUsers);
			} else {
				// Nếu không có user nào thì set câu thông báo MSG005 lên request
				req.setAttribute(Constant.MSG005, MessageProperties.getValueByKey(Constant.MSG005));
			}
			// Set listGroup lên req
			req.setAttribute(Constant.ATTRIBUTE_LIST_MST_GROUP, listMstGroup);

			// Set điều kiện tìm kiếm,sort,paging lên session để phục vụ trường hợp BACK
			session.setAttribute(Constant.ATTRIBUTE_FULL_NAME, fullName);
			session.setAttribute(Constant.ATTRIBUTE_GROUP_ID, groupId);
			session.setAttribute(Constant.ATTRIBUTE_CURRENT_PAGE, currentPage);
			session.setAttribute(Constant.ATTRIBUTE_SORT_TYPE, sortType);
			session.setAttribute(Constant.ATTRIBUTE_SORT_BY_FULL_NAME, sortByFullName);
			session.setAttribute(Constant.ATTRIBUTE_SORT_BY_CODE_LEVEL, sortByCodeLevel);
			session.setAttribute(Constant.ATTRIBUTE_SORT_BY_END_DATE, sortByEndDate);

			// Chuyển đến ADM002
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(Constant.JSP_ADM002);
			dispatcher.forward(req, resp);

		} catch (Exception e) {
			// Nếu bắt được exeption thì in ra màn hình console
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến màn hình system_error.jsp
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}
}
