/**
 * Copyright(C) 2020 Luvina Software
 * MstJapanEntities.java, 14/07/2020, KhangNL
 */
package manageuser.entities;

/**
 * Lớp Java bean định nghĩa đối tượng MstJapan
 *
 * @author KhangNL
 *
 */
public class MstJapanEntities {
	// Khởi tạo các thuộc tính của đối tượng MstJapan
	private String codeLevel;
	private String nameLevel;
	
	/**
	 * Khởi tạo đối tượng không tham số
	 */
	public MstJapanEntities() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	
	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}
	
	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}	
}
