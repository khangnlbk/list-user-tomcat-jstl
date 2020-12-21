/**
 * Copyright(C) 2020 Luvina Software
 * TblUserEntities.java, 14/07/2020, KhangNL
 */
package manageuser.entities;

/**
 * Lớp Java bean định nghĩa đối tượng TblUser
 *
 * @author KhangNL
 *
 */
public class TblUserEntities {
	// Khởi tạo các thuộc tính của đối tượng TblUser
	private int userId;
	private int groupId;
	private String loginName;
	private String pass;
	private String fullName;
	private String fullNameKana;
	private String email;
	private String tel;
	private String birthday;
	private int rule;
	private String salt;
	
	/**
	 *  Khởi tạo đối tượng không tham số
	 */
	public TblUserEntities() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}
	
	/**
	 * @param fullNameKana the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	/**
	 * @return the rule
	 */
	public int getRule() {
		return rule;
	}
	
	/**
	 * @param rule the rule to set
	 */
	public void setRule(int rule) {
		this.rule = rule;
	}
	
	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}
	
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}	
}
