/**
 * Copyright(C) 2020 Luvina Software
 * TblUserDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;

/**
 * Interface TblUserDAO, chứa hàm thao tác đến bảng tbl_user
 *
 * @author KhangNL
 *
 */
public interface TblUserDAO extends BaseDAO {
	/**
	 * Hàm lấy ra user tương ứng với loginName
	 * 
	 * @param loginName là loginName của User muốn lấy
	 * @return nếu tìm thấy user trả về TblUserEntities, nếu không tìm thấy trả về null
	 * @throws SQLException lỗi không kết nối được db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public TblUserEntities getTblUserByLoginName(String loginName) throws ClassNotFoundException, SQLException;	
	
	/**
	 * Hàm lấy tổng số user theo tên và group
	 * 
	 * @param groupId groupId của user muốn lấy ra
	 * @param fullName fullName của user muốn lấy ra
	 * @return tổng số user tìm được
	 * @throws SQLException lỗi không kết nối được db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public int getTotalUsers (int groupId, String fullName) throws ClassNotFoundException, SQLException;
	
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
	 * Hàm lấy ra tên các trường trong db để tránh lỗi sql injection trong câu lệnh order by
	 * 
	 * @param dbName tên db
	 * @return danh sách các trường trong db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi kết nối db
	 */
	public List<String> getColumnWhiteList(String dbName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm kiểm tra loginName đã tồn tại chưa
	 * 
	 * @param loginName
	 * @return trả về true nếu tìm thấy loginName, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi kết nối db
	 */
	public boolean getCheckExistedLoginName(String loginName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm kiểm tra email tồn tại
	 * @param email email muốn kiểm tra
	 * @param userId userId kiểm tra có email
	 * @return trả về true nếu tìm thấy email, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác được với DB
	 */
	public boolean getCheckExistEmail(String email, int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm thêm mới đối tượng user vào DB
	 * @param tblUser đối tượng muốn thêm
	 * @return userId của đối tượng vừa thêm vào
	 * @throws SQLException lỗi thao tác được với DB
	 */
	public Integer insertUser(TblUserEntities tblUser) throws SQLException;
	
	/**
	 * Hàm trả về userId tồn tại và userId đó không phải là admin
	 * @param userId id muốn check
	 * @return trả về true nếu tìm thấy userId, ngược lại trả về false
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác được với DB
	 */
	public boolean getCheckExistUserIdByUserId(int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy user theo id
	 * @param userId muốn tìm kiếm
	 * @return đối tượng UserInfor
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác được với DB
	 */
	public UserInforEntities getUserInforByUserId(int userId) throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm update thông tin của đối tượng tblUser
	 * @param tblUser thông tin của đối tượng tblUser
	 * @throws SQLException lỗi thao tác được với DB
	 */
	public void updateUser(TblUserEntities tblUser) throws SQLException;
	
	/**
	 * Hàm lấy tblUserEntities theo userId (không phân biệt user hay admin)
	 * @param userId userId cần tìm kiếm
	 * @return đối tượng TblUserEntites
	 * @throws ClassNotFoundException không tìm thấy class
	 */
	public TblUserEntities getUserByUserId(int userId) throws SQLException, ClassNotFoundException;
	
	/**
	 * Hàm xóa thông tin của user
	 * @param userId id của user muốn xóa thông tin
	 * @return true nếu xoá được, false nếu không xoá được
	 * @throws SQLException bắn lỗi khi không thao tác được với DB
	 */
	public boolean deleteTblUser(int userId) throws SQLException;
}
