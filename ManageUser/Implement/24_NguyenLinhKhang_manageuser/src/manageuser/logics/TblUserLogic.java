/**
 * Copyright(C) 2020 Luvina Software
 * TblUserLogic.java, 15/07/2020, KhangNL
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;

/**
 * Description TblUserLogic, chứa các hàm tính toán logic với bảng tbl_user
 *
 * @author KhangNL
 *
 */
public interface TblUserLogic {
	/**
	 * Hàm kiểm tra tên đăng nhập và password có khớp trong db không
	 * 
	 * @param loginName tên đăng nhập cần kiểm tra
	 * @param password  mật khẩu cần kiểm tra
	 * @return nếu đúng trả về true, nếu sai trả về fail
	 * @throws SQLException lỗi không kết nối với db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws NoSuchAlgorithmException lỗi không tìm được thuật toán encrypt
	 */
	public boolean checkExistLoginName(String loginName, String pass) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;
	
	/**
	 * Hàm lấy tổng số user
	 * 
	 * @param groupId groupId của user cần lấy
	 * @param fullName fullName của user cần lấy
	 * @return tổng số user lấy được
	 * @throws SQLException lỗi không kết nối với db
	 * @throws ClassNotFoundException lỗi không tìm được class
	 */
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException;
	
	/**
	 * Hàm lấy ra danh sách user theo các điều kiệm tìm kiếm, sắp xếp
	 * 
	 * @param offset vị trí data cần lấy nào
	 * @param limit số lượng lấy
	 * @param groupId mã nhóm tìm kiếm
	 * @param fullName Tên tìm kiếm
	 * @param sortType Nhận biết xem cột nào được ưu tiên sắp xếp(full_name or end_date or code_level)
	 * @param sortByFullName  Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or DESC)
	 * @param sortByEndDate Giá trị sắp xếp của cột Ngày kết hạn(ASC or DESC)
	 * @return danh sách user phù hợp với điều kiện tìm kiếm
	 * @throws SQLException lỗi khi không kết nối db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws SQLException, ClassNotFoundException;
	
	/**
	 * Hàm kiểm tra tồn tại loginName
	 * 
	 * @param loginName giá trị kiểm tra
	 * @return true nếu đã tồn tại, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi khi không tìm thấy class
	 * @throws SQLException lỗi thao tác db
	 */
	public boolean checkExistLoginName(String loginName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm kiểm tra tồn tại email
	 * 
	 * @param email giá trị kiểm tra
	 * @param userId userId cần kiểm tra có email
	 * @return true nếu đã tồn tại, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm được class
	 * @throws SQLException lỗi thao tác với db
	 */
	public boolean checkExistEmail(String email, int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm thêm mới user
	 * 
	 * @param userInfor thông tin của user muốn thêm
	 * @return true nếu thêm mới thành công, ngược lại trả về false
	 * @throws SQLException lỗi thao tác với db
	 * @throws ClassNotFoundException lỗi không tìm được class
	 * @throws NoSuchAlgorithmException lỗi không tìm thấy thuật toán encrypt
	 */
	public boolean createUser(UserInforEntities userInfor)
			throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
	
	/**
	 * Hàm check userId tồn tại
	 * 
	 * @param userId id muốn check
	 * @return true nếu userId tồn tại và là user, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException  lỗi thao tác với db
	 */
	public boolean checkExistUserById(int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy user theo id
	 * 
	 * @param userId muốn tìm kiếm
	 * @return đối tượng UserInfor
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException  lỗi thao tác với db
	 */
	public UserInforEntities getUserInforByUserId(int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm update thông tin của 1 user
	 * @param userInfor thông tin của user
	 * @return true nếu edit thành công, ngược lại trả về false
	 * @throws NumberFormatException bắn lỗi khi ép kiểu dữ liệu về number
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác với db
	 */
	public boolean editUser(UserInforEntities userInfor) throws NumberFormatException, ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy TblUserEntites theo userId (không phân biệt user hay admin)
	 * 
	 * @param userId userId cần tìm kiếm
	 * @return đối tượng TblUserEntites
	 * @throws SQLException lỗi thao tác với db
	 * @throws NumberFormatException không tìm thấy thuật toán
	 * @throws ClassNotFoundException không tìm thấy class
	 */
	public TblUserEntities getUserByUserId(int userId) throws SQLException, NumberFormatException, ClassNotFoundException;
	
	/**
	 * Hàm xóa tất cả thông tin liên quan đến user
	 * @param userId id của user muốn xóa
	 * @param existCodeLevel có tồn tại trình độ tiếng nhật hay không
	 * @return true nếu xóa thành công, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác với db
	 */
	public boolean deleteUser(int userId, boolean existCodeLevel) throws ClassNotFoundException, SQLException;
}
