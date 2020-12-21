/**
 * Copyright(C) 2020 Luvina Software
 * MstGroupEntities.java, 14/07/2020, KhangNL
 */
package manageuser.entities;

/**
 * Lớp Java bean định nghĩa đối tượng MstGroup
 *
 * @author KhangNL
 *
 */
public class MstGroupEntities {
	// Khởi tạo các thuộc tính của đối tượng MstGroup
	private int groupId;
	private String groupName;
	
	/**
	 * Khởi tạo đối tượng không tham số
	 */
	public MstGroupEntities() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
}
