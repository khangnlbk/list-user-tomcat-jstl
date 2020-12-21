/**
 * Copyright(C) 2020 Luvina Software
 * MstGroupLogic.java, 15/07/2020, KhangNL
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * Description MstGroupLogic, chứa các hàm tính toán logic với bảng mst_group
 *
 * @author KhangNL
 *
 */
public interface MstGroupLogic {
	/**
	 * Hàm lấy danh sách tất cả group
	 * 
	 * @return danh sách tất cả group
	 * @throws SQLException lỗi không kết nối với db
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 */
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy GroupName theo groupId
	 * 
	 * @param groupId mã nhóm
	 * @retur groupName tên nhóm
	 * @throws SQLException lỗi khi không thao tác được với DB
	 * @throws ClassNotFoundException lỗi khi không tìm thấy class
	 */
	public String getGroupNameByGroupId(int groupId) throws ClassNotFoundException, SQLException;
}
