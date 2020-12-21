/**
 * Copyright(C) 2020 Luvina Software
 * TblDetailUserJapanEntities.java, 14/07/2020, KhangNL
 */
package manageuser.entities;

/**
 * Lớp Java bean định nghĩa đối tượng TblDetailUserJapan
 *
 * @author KhangNL
 *
 */
public class TblDetailUserJapanEntities {
	// Khởi tạo các thuộc tính của đối tượng TblDetailUserJapan
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private String startDate;
	private String endDate;
	private int total;
	
	/**
	 * Khởi tạo đối tượng không tham số
	 */
	public TblDetailUserJapanEntities() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the detailUserJapanId
	 */
	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}
	
	/**
	 * @param detailUserJapanId the detailUserJapanId to set
	 */
	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
}
