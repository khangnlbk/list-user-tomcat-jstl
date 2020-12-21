/**
 * Copyright(C) 2020 Luvina Software
 * TblUserDAOImpl.java, 14/07/2020, KhangNL
 */
package manageuser.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import manageuser.dao.TblUserDAO;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.utils.Constant;

/**
 * Lớp thực thi interface TblUserDAO
 *
 * @author KhangNL
 *
 */
public class TblUserDAOImpl extends BaseDAOImpl implements TblUserDAO {
	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getTblUserByLoginName(java.lang.String)
	 */
	@Override
	public TblUserEntities getTblUserByLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// khởi tạo đối tượng tbluserEntities
		TblUserEntities tbluserEntities = null;
		try {
			// mở kết nối
			openConnection();
			// tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT password,salt ");
			sql.append(" FROM tbl_user ");
			sql.append(" WHERE login_name = ? and rule = ?;");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			int i = 0;
			ps.setString(++i, loginName);
			ps.setInt(++i, Constant.RULE_ADMIN);
			// thực thi câu truy vấn
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// Khởi tạo đối tượng TblUserEntities
				tbluserEntities = new TblUserEntities();
				// Lấy về giá trị của trường password
				String password = rs.getString(Constant.TBL_USER_PASSWORD);
				// Lấy về giá trị của trường salt
				String salt = rs.getString(Constant.TBL_USER_SALT);
				// Gán giá trị password cho đối tượng TblUserEntities
				tbluserEntities.setPass(password);
				// Gán giá trị salt cho đối tượng TblUserEntities
				tbluserEntities.setSalt(salt);
			}
			// Đóng kết nối
			ps.close();
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		return tbluserEntities;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getTotalUsers(int, java.lang.String)
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException {
		int totalUsers = 0;
		try {
			// Mở kết nối
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) ");
			sql.append("FROM tbl_user ");
			sql.append("WHERE rule = ? ");
			// Thêm các câu truy vấn khi có điều kiện SEARCH
			if (groupId > Constant.GROUP_ID_DEFAULT) {
				sql.append("AND group_id = ? ");
			}
			if (!Constant.EMPTY.equals(fullName)) {
				sql.append("AND full_name LIKE ? ");
			}
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, Constant.RULE_USER);
			if (groupId > Constant.GROUP_ID_DEFAULT) {
				ps.setInt(++i, groupId);
			}
			if (!Constant.EMPTY.equals(fullName)) {
				ps.setString(++i, fullName + "%");
			}
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			while (rs.next()) {
				totalUsers = rs.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// đóng kết nối
			closeConnection();
		}
		// Trả về số users tìm được
		return totalUsers;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getListUsers(int, int, int,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException {
		// Khởi tạo 1 list có KDL là UserInfoBean để chứa danh sách userInfo
		List<UserInforEntities> listUsers = new ArrayList<>();
		try {
			// Mở kết nối tới DB
			openConnection();
			// Khởi tạo 1 StringBuilder chứa câu lệnh SQL
			StringBuilder sql = new StringBuilder();
			// Thêm chuỗi câu lệnh SQL vào StringBuilder
			// Câu sql select id, họ tên, họ tên kana, ngày sinh, tên nhóm,
			// email, số điện thoại, trình độ tiếng nhật, điểm thi
			sql.append("SELECT tbl_user.user_id, tbl_user.full_name, tbl_user.full_name_kana, tbl_user.birthday,");
			sql.append(" mst_group.group_name, tbl_user.email, tbl_user.tel,");
			sql.append(" mst_japan.name_level, tbl_detail_user_japan.end_date,");
			sql.append(" tbl_detail_user_japan.total");
			sql.append(" FROM tbl_user");
			sql.append(" INNER JOIN mst_group");
			sql.append(" ON tbl_user.group_id=mst_group.group_id ");
			sql.append(" LEFT JOIN tbl_detail_user_japan");
			sql.append(" ON tbl_user.user_id=tbl_detail_user_japan.user_id ");
			sql.append(" LEFT JOIN mst_japan");
			sql.append(" ON tbl_detail_user_japan.code_level = mst_japan.code_level ");
			sql.append(" WHERE tbl_user.rule = ?");

			// append mệnh đề where vào câu sql
			if (groupId != Constant.GROUP_ID_DEFAULT) {
				// nếu có groupId thì append câu sql là điều kiện groupId
				sql.append(" AND mst_group.group_id = ?");
			}
			if (!Constant.FULL_NAME_DEFAULT.equals(fullName)) {
				// nếu có fullname thì append câu sql là điều kiện fullname
				sql.append(" AND tbl_user.full_name LIKE ?");
			}
			// append mệnh đề order by vào câu sql
			if (sortType != null) {
				if (Constant.SORT_TYPE_FULLNAME.equals(sortType)) {
					// nếu sortType default = SORT_TYPE_FULLNAME hoặc sortType =
					// SORT_TYPE_FULLNAME
					// fullname
					// thì append câu lệnh sql ORDER BY theo thứ tự fullname>
					// code_level > end_date
					sql.append(" ORDER BY tbl_user.full_name " + sortByFullName);
					sql.append(", mst_japan.code_level " + sortByCodeLevel);
					sql.append(", tbl_detail_user_japan.end_date " + sortByEndDate);
				} else if (Constant.SORT_TYPE_CODE_LEVEL.equals(sortType)) {
					// nếu = codelevel thì append câu lệnh sql ORDER BY theo thứ
					// tự
					// code_level > fullname > end_date
					sql.append(" ORDER BY mst_japan.code_level " + sortByCodeLevel);
					sql.append(", tbl_user.full_name " + sortByFullName);
					sql.append(", tbl_detail_user_japan.end_date " + sortByEndDate);
				} else if (Constant.SORT_TYPE_END_DATE.equals(sortType)) {
					// nếu = endDate thì append câu lệnh sql ORDER BY theo thứ
					// tự
					// end_date > fullname > code_level
					sql.append(" ORDER BY tbl_detail_user_japan.end_date " + sortByEndDate);
					sql.append(", tbl_user.full_name " + sortByFullName);
					sql.append(", mst_japan.code_level " + sortByCodeLevel);
				}
			}

			// append câu truy vấn limit để lấy số lượng bản ghi và offset để
			// lấy vị trí bắt đầu lấy bản ghi
			sql.append(" LIMIT ? OFFSET ? ");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB tránh
			// lỗi sql injection
			ps = connection.prepareStatement(sql.toString());

			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, Constant.RULE_USER);
			if (groupId > Constant.GROUP_ID_DEFAULT) {
				ps.setInt(++i, groupId);
			}
			if (!Constant.EMPTY.equals(fullName)) {
				ps.setString(++i, fullName + "%");
			}
			ps.setInt(++i, limit);
			ps.setInt(++i, offset);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			// Kiểm tra nếu resultSet còn dữ liệu thì thực hiện vòng lặp
			while (rs.next()) {
				// Set các giá trị vào userInfor
				UserInforEntities userInforEntities = new UserInforEntities();
				userInforEntities.setUserId(rs.getInt(Constant.TBL_USER_USER_ID));
				userInforEntities.setFullName(rs.getString(Constant.TBL_USER_FULL_NAME));
				userInforEntities.setBirthday(rs.getString(Constant.TBL_USER_BIRTHDAY));
				userInforEntities.setGroupName(rs.getString(Constant.MST_GROUP_GROUP_NAME));
				userInforEntities.setEmail(rs.getString(Constant.TBL_USER_EMAIL));
				userInforEntities.setTel(rs.getString(Constant.TBL_USER_TEL));
				userInforEntities.setNameLevel(rs.getString(Constant.MST_JAPAN_NAME_LEVEL));
				userInforEntities.setEndDate(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_END_DATE));
				userInforEntities.setTotal(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_TOTAL));
				listUsers.add(userInforEntities);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// đóng kết nối đến DB
			closeConnection();
		}
		// trả về danh sách userInfo
		return listUsers;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getWhiteList(java.lang.String)
	 */
	@Override
	public List<String> getColumnWhiteList(String dbName) throws ClassNotFoundException, SQLException {
		// Khai báo list lưu tên các cột trong DB
		List<String> whiteList = new ArrayList<String>();
		try {
			openConnection();
			// Nếu có kết nối
			if (connection != null) {
				// Tạo câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT COLUMN_NAME ");
				sql.append("FROM INFORMATION_SCHEMA.`COLUMNS` ");
				sql.append("WHERE TABLE_SCHEMA = ?");
				// prepare statement gửi câu truy vấn đã tham số hóa đến DB
				ps = connection.prepareStatement(sql.toString());
				// Set thêm giá trị cho câu truy vấn
				int i = 0;
				ps.setString(++i, dbName);
				// Thực hiện câu truy vấn
				rs = ps.executeQuery();
				while (rs.next()) {
					whiteList.add(rs.getString("COLUMN_NAME"));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// đóng kết nối đến DB
			closeConnection();
		}
		return whiteList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getExistedLoginName(java.lang.String)
	 */
	@Override
	public boolean getCheckExistedLoginName(String loginName) throws ClassNotFoundException, SQLException {
		// Khai báo biến checkExistedLoginName
		boolean checkExistedLoginName = false;
		try {
			// mở kết nối
			openConnection();
			// tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT password,salt ");
			sql.append(" FROM tbl_user ");
			sql.append(" WHERE login_name = ?");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			int i = 0;
			ps.setString(++i, loginName);
			// thực thi câu truy vấn
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				checkExistedLoginName = true;
			}
			ps.close();
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// đóng kết nối
			closeConnection();
		}
		return checkExistedLoginName;
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getCheckExistEmail(java.lang.String, int)
	 */
	@Override
	public boolean getCheckExistEmail(String email, int userId) throws ClassNotFoundException, SQLException {
		// Khai báo biến checkExistEmail
		boolean checkExistEmail = false;
		try {
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append("FROM tbl_user ");
			sql.append("WHERE ");
			sql.append("email = ? ");
			// Nếu là trường hợp edit
			if (userId > 0) {
				// Thêm điều kiện vào câu truy vấn
				sql.append("AND user_id <> ? ");
			}
			// Tạo một đối tượng PreparedStatement để gửi các câu lệnh SQL được
			// tham số hóa
			// đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setString(++i, email);
			// Nếu là trường hợp edit
			if (userId > 0) {
				ps.setInt(++i, userId);
			}
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			while (rs.next()) {
				checkExistEmail = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// Trả về kết quả existsUserId
		return checkExistEmail;
	}

	/**
	 * (non-JavaDoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#insertUser(manageuser.entities.TblUserEntities)
	 */
	@Override
	public Integer insertUser(TblUserEntities tblUser) throws SQLException {
		int userId = 0;
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tbl_user ");
			sql.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, tblUser.getUserId());
			ps.setInt(++i, tblUser.getGroupId());
			ps.setString(++i, tblUser.getLoginName());
			ps.setString(++i, tblUser.getPass());
			ps.setString(++i, tblUser.getFullName());
			ps.setString(++i, tblUser.getFullNameKana());
			ps.setString(++i, tblUser.getEmail());
			ps.setString(++i, tblUser.getTel());
			ps.setString(++i, tblUser.getBirthday());
			ps.setInt(++i, tblUser.getRule());
			ps.setString(++i, tblUser.getSalt());
			// Thực hiện câu truy vấn
			ps.execute();
			// Lấy giá trị UserId vừa thêm vào
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
		// Trả về userId
		return userId;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getCheckExistUserByUserId(int)
	 */
	@Override
	public boolean getCheckExistUserIdByUserId(int userId) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến checkExistsUserId
		boolean checkExistsUserId = false;
		try {
			// mở kết nối
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT user_id ");
			sql.append("FROM tbl_user ");
			sql.append("WHERE user_id = ? ");
			sql.append("AND rule = ? ");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			ps.setInt(++i, Constant.RULE_USER);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			// Nếu tồn tại user
			while (rs.next()) {
				checkExistsUserId = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// Trả về giá trị kiểm tra
		return checkExistsUserId;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getUserInforByUserId(int)
	 */
	@Override
	public UserInforEntities getUserInforByUserId(int userId) throws ClassNotFoundException, SQLException {
		// Khai báo đối tượng UserInfor
		UserInforEntities userInfor = null;
		try {
			// Mở kết nối
			openConnection();
			// Tạo câu query truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT u.user_id, u.login_name, g.group_id, u.full_name, u.full_name_kana, u.birthday, g.group_name, u.email, u.tel, ");
			sql.append("j.code_level, j.name_level, d.start_date, d.end_date, d.total ");
			sql.append("FROM tbl_user AS u INNER JOIN mst_group AS g ");
			sql.append("ON u.group_id = g.group_id ");		
			sql.append("LEFT JOIN  ");
			sql.append("(`tbl_detail_user_japan` AS d INNER JOIN `mst_japan` AS j ");
			sql.append("ON d.code_level = j.code_level) ");
			sql.append("ON u.user_id = d.user_id ");
			sql.append("WHERE u.rule = ? ");
			sql.append("AND u.user_id = ? ");
			// Tạo một đối tượng PreparedStatement để gửi các câu lệnh SQL được
			// tham số hóa
			// đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			// Truyền rule của user
			ps.setInt(++i, Constant.RULE_USER);
			ps.setInt(++i, userId);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			while (rs.next()) {
				// Khởi tạo đối tượng UserInfor
				userInfor = new UserInforEntities();
				// Set dữ liệu từ DB cho đối tượng
				userInfor.setUserId(rs.getInt(Constant.TBL_USER_USER_ID));
				userInfor.setLoginName(rs.getString(Constant.TBL_USER_LOGIN_NAME));
				userInfor.setGroupId(rs.getInt(Constant.TBL_USER_GROUP_ID));
				userInfor.setGroupName(rs.getString(Constant.MST_GROUP_GROUP_NAME));
				userInfor.setFullName(rs.getString(Constant.TBL_USER_FULL_NAME));
				userInfor.setFullNameKana(rs.getString(Constant.TBL_USER_FULL_NAME_KANA));
				userInfor.setBirthday(rs.getString(Constant.TBL_USER_BIRTHDAY));
				userInfor.setEmail(rs.getString(Constant.TBL_USER_EMAIL));
				userInfor.setTel(rs.getString(Constant.TBL_USER_TEL));
				userInfor.setCodeLevel(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_CODE_LEVEL));
				userInfor.setNameLevel(rs.getString(Constant.MST_JAPAN_NAME_LEVEL));
				userInfor.setStartDate(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_START_DATE));
				userInfor.setEndDate(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_END_DATE));
				userInfor.setTotal(rs.getString(Constant.TBL_DETAIL_USER_JAPAN_TOTAL));
			}

		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}

		return userInfor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.dao.TblUserDAO#updateUser(manageuser.entities.TblUserEntities)
	 */
	@Override
	public void updateUser(TblUserEntities tblUser) throws SQLException {
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tbl_user ");
			sql.append("SET group_id = ?, full_name = ?, full_name_kana = ?, email = ?, tel = ?, birthday = ? ");
			sql.append("WHERE user_id = ? ");
			sql.append("AND rule = ?");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, tblUser.getGroupId());
			ps.setString(++i, tblUser.getFullName());
			ps.setString(++i, tblUser.getFullNameKana());
			ps.setString(++i, tblUser.getEmail());
			ps.setString(++i, tblUser.getTel());
			ps.setString(++i, tblUser.getBirthday());
			ps.setInt(++i, tblUser.getUserId());
			ps.setInt(++i, Constant.RULE_USER);
			// Thực hiện câu truy vấn
			ps.execute();
		} catch (SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#getUserByUserId(int)
	 */
	@Override
	public TblUserEntities getUserByUserId(int userId) throws SQLException, ClassNotFoundException {
		// Khởi tạo tblUserEntites
		TblUserEntities tblUserEntities = new TblUserEntities();
		try {
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT tbl_user.login_name, tbl_user.rule ");
			sql.append("FROM tbl_user ");
			sql.append("WHERE user_id = ?");
			// prepqare statement gửi câu truy vấn được tham số
			ps = connection.prepareStatement(sql.toString());
			// set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			if (rs.next()) {
				tblUserEntities.setLoginName(rs.getString(Constant.TBL_USER_LOGIN_NAME));
				tblUserEntities.setRule(rs.getInt(Constant.TBL_USER_RULE));
			} else {
				tblUserEntities = null;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// đóng kết nối DB
			closeConnection();
		}
		return tblUserEntities;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.TblUserDAO#deleteTblUser(int)
	 */
	@Override
	public boolean deleteTblUser(int userId) throws SQLException {
		// Khởi tạo biến check = false
		boolean check = false;
		try {
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tbl_user ");
			sql.append("WHERE user_id = ? ");
			sql.append("AND rule = ?");
			// prepare statement gửi câu truy vấn đã tham số hóa đến DB
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			ps.setInt(++i, Constant.RULE_USER);
			// Thực hiện câu truy vấn
			ps.execute();
			check = false;
		} catch (SQLException e) {
			// Nếu bắt được exeption thì in ra màn hình console và throw lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		}
		// trả về giá trị check
		return check;
	}
}
