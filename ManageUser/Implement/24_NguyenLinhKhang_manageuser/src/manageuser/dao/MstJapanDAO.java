/**
 * Copyright(C) 2020 Luvina Software
 * MstJapanDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapanEntities;

/**
 * Interface MstJapanDAO, chứa hàm thao tác đến bảng mst_japan
 *
 * @author KhangNL
 *
 */
public interface MstJapanDAO {
	/**
	 * Phương thức lấy danh sách trình độ tiếng Nhật từ db
	 * 
	 * @return danh sách trình độ tiếng Nhật
	 * @throws SQLException lỗi kết nối db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public List<MstJapanEntities> getAllMstJapan() throws SQLException, ClassNotFoundException;
	
	/**
	 * Phương thức lấy name_level theo code_Level
	 * 
	 * @param codeLevel mã trình độ
	 * @return nameLevel tên trình độ
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác với db
	 */
	public String getNameLevelByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException;
}
