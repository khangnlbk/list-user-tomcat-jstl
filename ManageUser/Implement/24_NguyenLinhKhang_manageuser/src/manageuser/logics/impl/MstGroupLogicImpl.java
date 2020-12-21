/**
 * Copyright(C) 2020 Luvina Software
 * MstGroupLogicImpl.java, 15/07/2020, KhangNL
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstGroupDAO;
import manageuser.dao.impl.MstGroupDAOImpl;
import manageuser.entities.MstGroupEntities;
import manageuser.logics.MstGroupLogic;

/**
 * Lớp thực thi interface MstGroupLogic
 *
 * @author KhangNL
 *
 */
public class MstGroupLogicImpl implements MstGroupLogic {
	// Khởi tạo đối tượng mstGroupDAO
	private MstGroupDAO mstGroupDAO = new MstGroupDAOImpl();

	/**
	 *  (non-Javadoc)
	 * @see manageuser.logics.MstGroupLogic#getAllMstGroup()
	 */
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException {
		// Lấy danh sách mstGroup
		List<MstGroupEntities> listMstGroup = mstGroupDAO.getAllMstGroup();
		// Trả về danh sách mstGroup
		return listMstGroup;
	}

	/**
	 *  (non-Javadoc)
	 * @see manageuser.logics.MstGroupLogic#getGroupNameByGroupId(int)
	 */
	@Override
	public String getGroupNameByGroupId(int groupId) throws ClassNotFoundException, SQLException {
		// khởi tạo đối tượng groupName để thao tác với db
		String groupName = mstGroupDAO.getGroupNameByGroupId(groupId);
		// trả về groupName
		return groupName;
	}
}
