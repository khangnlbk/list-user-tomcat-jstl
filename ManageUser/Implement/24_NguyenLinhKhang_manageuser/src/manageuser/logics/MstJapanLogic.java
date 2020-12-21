/**
 * Copyright(C) 2020 Luvina Software
 * MstJapanLogic.java, 15/07/2020, KhangNL
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstJapanEntities;

/**
 * Description MstJapanLogic, chứa các hàm tính toán logic với bảng mst_japan
 *
 * @author KhangNL
 *
 */
public interface MstJapanLogic {
	/**
	 * Hàm lấy danh sách tất cả tên trình độ tiếng Nhật
	 * 
	 * @return danh sách trình độ tiếng Nhật
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException Lỗi không kết nối được db
	 */
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy nameLevel theo codeLevel
	 * 
	 * @param codeLevel mã trình độ
	 * @return nameLevel tên tình độ tiếng Nhật
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác với db
	 */
	public String getNameLevelByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException;
	
}
