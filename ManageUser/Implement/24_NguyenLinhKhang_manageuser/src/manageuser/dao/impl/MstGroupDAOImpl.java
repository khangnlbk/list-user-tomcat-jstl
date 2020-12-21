/**
 * Copyright(C) 2020 Luvina Software
 * MstGroupDAO.java, 14/07/2020, KhangNL
 */
package manageuser.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDAO;
import manageuser.entities.MstGroupEntities;
import manageuser.utils.Constant;

/**
 * Lớp thực thi interface MstGroupDAO
 *
 * @author KhangNL
 *
 */
public class MstGroupDAOImpl extends BaseDAOImpl implements MstGroupDAO {

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDAO#getAllMstGroup()
	 */
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException {
		// khởi tạo listMstGroup
		List<MstGroupEntities> listMstGroup = new ArrayList<MstGroupEntities>();
		try {
			// mở kết nối
			openConnection();
			// Nếu có kết nối đến DB
			if (connection != null) {
				// Tạo câu truy vấn
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT group_id, group_name ");
				sql.append("FROM mst_group ");
				sql.append("ORDER BY group_id ASC");
				// PreparedStatement để gửi các câu lệnh SQL đến cơ sở dữ liệu
				ps = connection.prepareStatement(sql.toString());
				// Thực hiện câu truy vấn
				rs = ps.executeQuery();
				while (rs.next()) {
					// khởi tạo đối tượng mstGroup
					MstGroupEntities mstGroup = new MstGroupEntities();
					// set các giá trị cho đối tượng mstGroup
					mstGroup.setGroupId(rs.getInt(Constant.MST_GROUP_GROUP_ID));
					mstGroup.setGroupName(rs.getString(Constant.MST_GROUP_GROUP_NAME));
					// add đối tượng mstGroup vào listMstGroup
					listMstGroup.add(mstGroup);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// trả về listMstGroup
		return listMstGroup;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.MstGroupDAO#getGroupNameByGroupId(int)
	 */
	@Override
	public String getGroupNameByGroupId(int groupId) throws ClassNotFoundException, SQLException {
		// khởi tạo biến groupName
		String groupName = null;
		try {
			// mở kết nối
			openConnection();
			// Tạo câu truy vấn
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT group_name ");
			sql.append("FROM mst_group ");
			sql.append("WHERE group_id = ?");
			// PreparedStatement để gửi các câu lệnh SQL đến cơ sở dữ liệu.
			ps = connection.prepareStatement(sql.toString());
			// Set thêm giá trị cho câu truy vấn
			int i = 0;
			ps.setInt(++i, groupId);
			// Thực hiện câu truy vấn
			rs = ps.executeQuery();
			while (rs.next()) {
				// set giá trị cho biến groupName
				groupName = rs.getString(Constant.MST_GROUP_GROUP_NAME);
			}

		} catch (ClassNotFoundException | SQLException e) {
			// nếu bắt được exeption thì in ra log và ném lỗi
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			throw e;
		} finally {
			// Đóng kết nối
			closeConnection();
		}
		// trả về groupName
		return groupName;
	}

}
