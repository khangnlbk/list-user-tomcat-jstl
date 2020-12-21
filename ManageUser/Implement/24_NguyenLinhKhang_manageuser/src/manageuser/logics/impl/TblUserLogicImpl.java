/**
 * Copyright(C) 2020 Luvina Software
 * TblUserLogicImpl.java, 15/07/2020, KhangNL
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.dao.TblDetailUserJapanDAO;
import manageuser.dao.TblUserDAO;
import manageuser.dao.impl.TblDetailUserJapanDAOImpl;
import manageuser.dao.impl.TblUserDAOImpl;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Lớp thực thi interface TblUserLogic
 *
 * @author KhangNL
 *
 */
public class TblUserLogicImpl implements TblUserLogic {
	// khởi tạo đối tượng tblUserDAO
	private TblUserDAO tblUserDAO = new TblUserDAOImpl();
	// khởi tạo đối tượng tblDetailUserJapanDAO
	private TblDetailUserJapanDAO tblDetailUserJapanDAO = new TblDetailUserJapanDAOImpl();

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#existLoginName(java.lang.String)
	 */
	public boolean checkExistLoginName(String loginName, String password)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// khởi tạo biến result
		boolean result = false;
		// khởi tạo đối tượng TblUserEntities
		TblUserEntities TblUserEntities = new TblUserEntities();
		// Lấy TblUserEntities
		TblUserEntities = tblUserDAO.getTblUserByLoginName(loginName);
		// Nếu tồn tại user, kiểm tra có trùng pass hay không
		if (TblUserEntities != null) {
			// encrypt pass nhập vào
			String passInput = Common.encryptPassword(password, TblUserEntities.getSalt());
			// so sánh với pass đã lưu trong DB
			result = Common.compareString(passInput, TblUserEntities.getPass());
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
		// Convert wildCard cho fullName
		fullName = Common.replaceWildCard(fullName);
		// Lấy tổng số total
		int total = tblUserDAO.getTotalUsers(groupId, fullName);
		return total;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getListUsers(int, int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException {
		// Convert wildCard cho fullName
		fullName = Common.replaceWildCard(fullName);
		// Khởi tạo whiteList
		String sortTypeWhiteList = "";
		// Nếu sortType = FULLNAME
		if (Constant.SORT_TYPE_FULLNAME.equals(sortType)) {
			// Gán giá trị biến whiteList = full_name
			sortTypeWhiteList = Constant.TBL_USER_FULL_NAME;
			// Nếu sortType = CODELEVEL
		} else if (Constant.SORT_TYPE_CODE_LEVEL.equals(sortType)) {
			// Gán giá trị biến whiteList = code_level
			sortTypeWhiteList = Constant.TBL_DETAIL_USER_JAPAN_CODE_LEVEL;
			// Nếu sortType = ENDDATE
		} else if (Constant.SORT_TYPE_END_DATE.equals(sortType)) {
			// Gán giá trị biến whiteList = end_date
			sortTypeWhiteList = Constant.TBL_DETAIL_USER_JAPAN_END_DATE;
		}
		// Nếu tên trường có trong db được phép sắp xếp
		if (Common.checkSortTypeWhiteList(sortTypeWhiteList)) {
			// Nếu sắp xếp fullName khác tăng dần và fullName khác giảm dần
			if (!Constant.SORT_ASC.equals(sortByFullName) && !Constant.SORT_DESC.equals(sortByFullName)) {
				// thì gán sort value = defaultValue
				sortByFullName = Constant.SORT_ASC;
			}
			// Nếu sắp xếp codeLevel khác tăng dần và codeLevel khác giảm dần
			if (!Constant.SORT_ASC.equals(sortByCodeLevel) && !Constant.SORT_DESC.equals(sortByCodeLevel)) {
				// thì gán sort value = defaultValue
				sortByCodeLevel = Constant.SORT_ASC;
			}
			// Nếu sắp xếp endDate khác tăng dần và endDate khác giảm dần
			if (!Constant.SORT_ASC.equals(sortByEndDate) && !Constant.SORT_DESC.equals(sortByEndDate)) {
				// thì gán sort value = defaultValue
				sortByEndDate = Constant.SORT_DESC;
			}
		} else {
			// nếu sortType không thuộc whiteList thì gán sortType = null
			sortType = null;
		}
		// Lấy về listUsers
		List<UserInforEntities> listUsers = tblUserDAO.getListUsers(offset, limit, groupId, fullName, sortType,
				sortByFullName, sortByCodeLevel, sortByEndDate);
		return listUsers;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.TblUserLogic#checkExistedLoginName(java.lang.String)
	 */
	@Override
	public boolean checkExistLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// Khai báo biến check
		boolean check = false;
		// nếu tồn tại loginName thì check = true
		check = tblUserDAO.getCheckExistedLoginName(loginName);
		// trả về biến chek
		return check;
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistEmail(java.lang.String)
	 */
	@Override
	public boolean checkExistEmail(String email, int userId) throws ClassNotFoundException, SQLException {
		// Khai báo biến check
		boolean check = false;
		// Kiểm tra và lấy về kết quả
		check = tblUserDAO.getCheckExistEmail(email, userId);
		// Trả về kết quả
		return check;
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#createUser(manageuser.entities.UserInforEntities)
	 */
	@Override
	public boolean createUser(UserInforEntities userInfor)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		// Khai báo biến check
		boolean result = false;
		try {
			// mở kết nối
			tblUserDAO.openConnection();
			// Tắt chế độ tự động commit
			tblUserDAO.disableAutoCommit();
			// Lấy salt
			String salt = Common.getSalt();
			// Khởi tạo đối tượng TblUser
			TblUserEntities tblUser = new TblUserEntities();
			tblUser.setUserId(userInfor.getUserId());
			tblUser.setGroupId(userInfor.getGroupId());
			tblUser.setLoginName(userInfor.getLoginName());
			// Mã hóa password
			tblUser.setPass(Common.encryptPassword(userInfor.getPassword(), salt));
			tblUser.setFullName(userInfor.getFullName());
			tblUser.setFullNameKana(userInfor.getFullNameKana());
			tblUser.setEmail(userInfor.getEmail());
			tblUser.setTel(userInfor.getTel());
			tblUser.setBirthday(userInfor.getBirthday());
			// Truyền rule là rule của user
			tblUser.setRule(Constant.RULE_USER);
			tblUser.setSalt(salt);
			// Thực hiện insert và lấy về userId sẽ được thêm
			int userId = tblUserDAO.insertUser(tblUser);
			// Nếu có chọn trình độ tiếng Nhật
			if (!Constant.CODE_LEVEL_DEFAULT.equals(userInfor.getCodeLevel())) {
				// Set giá trị cho biến conn bên tblDetailUserJapanDAO
				// trùng với TblUserDAO
				tblDetailUserJapanDAO.setConnection(tblUserDAO.getConnection());
				// Khởi tạo đối tượng TblUserDetailJapan
				TblDetailUserJapanEntities tblDetailUserJapan = new TblDetailUserJapanEntities();
				tblDetailUserJapan.setUserId(userId);
				tblDetailUserJapan.setStartDate(userInfor.getStartDate());
				tblDetailUserJapan.setEndDate(userInfor.getEndDate());
				tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
				tblDetailUserJapan.setTotal(Integer.parseInt(userInfor.getTotal()));
				// Insert data TblDetailUserJapan
				tblDetailUserJapanDAO.insertDetailUserJapan(tblDetailUserJapan);
			}
			// Commit dữ liệu
			tblUserDAO.commitToDB();
			result = true;
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// Nếu xảy ra Transaction thì rollback data
			tblUserDAO.rollbackData();
		} finally {
			// Đóng kết nối
			tblUserDAO.closeConnection();
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#checkExistUserByUserId(int)
	 */
	@Override
	public boolean checkExistUserById(int userId) throws ClassNotFoundException, SQLException {
		// Khai báo biến check
		boolean check = false;
		// Kiểm tra và lấy lại kết quả
		check = tblUserDAO.getCheckExistUserIdByUserId(userId);
		// trả về biến check
		return check;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserInforByUserId(int)
	 */
	@Override
	public UserInforEntities getUserInforByUserId(int userId) throws ClassNotFoundException, SQLException {
		// Khai báo và lấy về đối tượng UserInfor theo userId
		UserInforEntities userInfor = tblUserDAO.getUserInforByUserId(userId);
		// Trả về userInfor
		return userInfor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#editUser(manageuser.entities.
	 * UserInforEntities)
	 */
	@Override
	public boolean editUser(UserInforEntities userInfor)
			throws NumberFormatException, ClassNotFoundException, SQLException {
		// Khai báo biến check
		boolean result = false;
		try {
			// mở kết nối
			tblUserDAO.openConnection();
			// Tắt chế độ tự động commit
			tblUserDAO.disableAutoCommit();
			// Khởi tạo đối tượng TblUser để lấy về thông tin cho bảng tbl_user
			TblUserEntities tblUser = new TblUserEntities();
			tblUser.setUserId(userInfor.getUserId());
			tblUser.setGroupId(userInfor.getGroupId());
			tblUser.setFullName(userInfor.getFullName());
			tblUser.setFullNameKana(userInfor.getFullNameKana());
			tblUser.setEmail(userInfor.getEmail());
			tblUser.setTel(userInfor.getTel());
			tblUser.setBirthday(userInfor.getBirthday());
			// Thực hiện update bảng tbl_user
			tblUserDAO.updateUser(tblUser);
			// check = true nếu tbl_user không tồn tại trình độ tiếng nhật và
			// update cũng không có trình độ tiếng nhật
			boolean check = Constant.CODE_LEVEL_DEFAULT.equals(userInfor.getCodeLevel())
					&& !userInfor.isExistCodeLevel();
			// nếu ngược lại
			if (!check) {
				// Set giá trị cho biến conn bên tblDetailUserJapanDAO
				// trùng với TblUserDAO
				tblDetailUserJapanDAO.setConnection(tblUserDAO.getConnection());
				// Khai báo đối tượng tblDetailUserJapan
				TblDetailUserJapanEntities tblDetailUserJapan = null;
				// Nếu có chọn trình độ tiếng Nhật
				if (!Constant.CODE_LEVEL_DEFAULT.equals(userInfor.getCodeLevel())) {
					// Khởi tạo đối tượng TblUserDetailJapan
					tblDetailUserJapan = new TblDetailUserJapanEntities();
					tblDetailUserJapan.setUserId(userInfor.getUserId());
					tblDetailUserJapan.setStartDate(userInfor.getStartDate());
					tblDetailUserJapan.setEndDate(userInfor.getEndDate());
					tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
					tblDetailUserJapan.setTotal(Integer.parseInt(userInfor.getTotal()));
				}

				// Nếu không chọn trình độ tiếng nhật và DB có trình độ
				if (Constant.CODE_LEVEL_DEFAULT.equals(userInfor.getCodeLevel()) && userInfor.isExistCodeLevel()) {
					// Delete dữ liệu ở bảng tbl_detail_user_japan
					tblDetailUserJapanDAO.deleteDetailUserJapan(userInfor.getUserId());
					// Nếu chọn trình độ tiếng nhật
				} else {
					// Nếu DB có trình độ tiếng nhật
					if (userInfor.isExistCodeLevel()) {
						// Update bảng tbl_detail_user_japan
						tblDetailUserJapanDAO.updateDetailUserJapan(tblDetailUserJapan);
						// Nếu DB không có trình độ tiếng nhật
					} else {
						// Insert vào bảng tbl_detail_user_japan
						tblDetailUserJapanDAO.insertDetailUserJapan(tblDetailUserJapan);
					}
				}
			}
			// Commit dữ liệu
			tblUserDAO.commitToDB();
			result = true;
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// Nếu xảy ra Transaction thì rollback data
			tblUserDAO.rollbackData();
		} finally {
			// Đóng kết nối
			tblUserDAO.closeConnection();
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#getUserByUserId(int)
	 */
	@Override
	public TblUserEntities getUserByUserId(int userId) throws SQLException, NumberFormatException, ClassNotFoundException {
		// Khởi tạo đối tượng tblUserEntities
		TblUserEntities tblUserEntities = new TblUserEntities();
		try {
			// Lấy ra và gán vào tblUserEntities
			tblUserEntities = tblUserDAO.getUserByUserId(userId);
		} catch (SQLException | NumberFormatException | ClassNotFoundException e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
		}
		return tblUserEntities;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.TblUserLogic#deleteUser(int, boolean)
	 */
	@Override
	public boolean deleteUser(int userId, boolean existCodeLevel) throws ClassNotFoundException, SQLException {
		// Khai báo biến check
		boolean result = false;
		try {
			// mở kết nối
			tblUserDAO.openConnection();
			// Tắt chế độ tự động commit
			tblUserDAO.disableAutoCommit();
			if (existCodeLevel) {
				// Set giá trị cho biến conn bên TblUserDAO trùng với
				// tblDetailUserJapanDAO
				tblDetailUserJapanDAO.setConnection(tblUserDAO.getConnection());
				// Xóa thông tin về trình độ tiếng Nhật của user
				tblDetailUserJapanDAO.deleteDetailUserJapan(userId);
			}
			// Xóa thông tin của user
			tblUserDAO.deleteTblUser(userId);
			// Commit dữ liệu
			tblUserDAO.commitToDB();
			result = true;
		} catch (SQLException e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// Nếu xảy ra Transaction thì rollback data
			tblUserDAO.rollbackData();
		} finally {
			// Đóng kết nối
			tblUserDAO.closeConnection();
		}
		// trả về kết quả xóa
		return result;
	}
}
