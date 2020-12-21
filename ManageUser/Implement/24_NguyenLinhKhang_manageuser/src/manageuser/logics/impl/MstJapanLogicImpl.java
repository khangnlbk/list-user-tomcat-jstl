/**
 * Copyright(C) 2020 Luvina Software
 * MstJapanLogicImpl.java, 15/07/2020, KhangNL
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.List;

import manageuser.dao.MstJapanDAO;
import manageuser.dao.impl.MstJapanDAOImpl;
import manageuser.entities.MstJapanEntities;
import manageuser.logics.MstJapanLogic;

/**
 * Lớp thực thi interface MstJapanLogic
 *
 * @author KhangNL
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {
	// Khởi tạo biến mstJapanDAO thao tác với db
	private MstJapanDAO mstJapanDAO = new MstJapanDAOImpl();

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.logics.MstJapanLogic#getAllMstJapan()
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException {
		// Lấy về danh sách listMstJapan
		List<MstJapanEntities> listMstJapan = mstJapanDAO.getAllMstJapan();
		// Trả về danh sách trình độ tiếng Nhật
		return listMstJapan;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * manageuser.logics.MstJapanLogic#getNameLevelByCodeLevel(java.lang.String)
	 */
	@Override
	public String getNameLevelByCodeLevel(String codeLevel) throws ClassNotFoundException, SQLException {
		// Lấy về đối tượng mstJapan theo codeLevel
		String nameLevel = mstJapanDAO.getNameLevelByCodeLevel(codeLevel);
		return nameLevel;
	}
}
