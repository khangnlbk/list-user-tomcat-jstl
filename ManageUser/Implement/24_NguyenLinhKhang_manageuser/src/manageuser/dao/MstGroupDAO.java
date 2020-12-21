/**
 * Copyright(C) 2020 Luvina Software
 * MstGroupDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * Interface MstGroupDAO, chứa hàm thao tác đến bảng mst_group
 *
 * @author KhangNL
 *
 */
public interface MstGroupDAO {
	/**
	 * Hàm lấy tất cả Group trong DB
	 * 
	 * @return danh sách các Group trong DB
	 * @throws SQLException lỗi khi không thao tác được với DB
	 * @throws ClassNotFoundException lỗi khi không tìm thấy Class
	 */
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException;
	
	/**
	 * Hàm lấy group theo groupId
	 * 
	 * @param groupId group_id
	 * @return groupName group_name
	 * @throws ClassNotFoundException lỗi không tìm thấy class
	 * @throws SQLException lỗi thao tác với db
	 */
	public String getGroupNameByGroupId(int groupId) throws ClassNotFoundException, SQLException;

}
