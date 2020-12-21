/**
 * Copyright(C) 2020 Luvina Software
 * Constant.java, 15/07/2020, KhangNL
 */
package manageuser.utils;

/**
 * Lớp chứa các biến constant của chương trình
 *
 * @author KhangNL
 *
 */
public class Constant {
	// Các constant chứa thông số kết nối với database
	public static final String DRIVER = "Driver";
	public static final String DB_URL = "dbURL";
	public static final String USER = "user";
	public static final String PASS = "pass";
	public static final String DB_NAME = "dbName";
	
	// Biến giá trị của file config.properties
	// Giá trị hiện thị tối đa số page của 1 chuỗi paging
	public static final String LIMIT_PAGE = "LIMIT_PAGE";
	// Giá trị hiển thị số record tối đa trên 1 page
	public static final String LIMIT_RECORD = "LIMIT_RECORD";
	
	// Biến giá trị của file messgae_error.properties
	// Hạng mục LoginName
	// Lỗi loginName không nhập
	public static final String ER001_LOGIN_NAME = "ER001_LOGIN_NAME";
	// Check format
	public static final String ER019_LOGIN_NAME = "ER019_LOGIN_NAME";
	// Check length
	public static final String ER007_LOGIN_NAME = "ER007_LOGIN_NAME";
	// LoginName đã tồn tại
	public static final String ER003_LOGIN_NAME = "ER003_LOGIN_NAME";
	// Hạng mục Group
	// Hãy chọn group
	public static final String ER002_GROUP = "ER002_GROUP";
	// Check không tồn tại
	public static final String ER004_GROUP = "ER004_GROUP";
	// Hạng mục fullName
	// Hãy nhập fullName
	public static final String ER001_FULL_NAME = "ER001_FULL_NAME";
	// Check length
	public static final String ER006_FULL_NAME = "ER006_FULL_NAME";
	// Hạng mục fullNameKana
	// Check phải là kana
	public static final String ER009_FULL_NAME_KANA = "ER009_FULL_NAME_KANA";
	// Check length
	public static final String ER006_FULL_NAME_KANA = "ER006_FULL_NAME_KANA";
	// Hạng mục birthday
	// Ngày không hợp lệ
	public static final String ER011_BIRTH_DAY = "ER011_BIRTHDAY";
	// Hạng mục email
	// Hãy nhập email
	public static final String ER001_EMAIL = "ER001_EMAIL";
	// Check length
	public static final String ER006_EMAIL = "ER006_EMAIL";
	// Check format
	public static final String ER005_EMAIL = "ER005_EMAIL";
	// Email đã tồn tại
	public static final String ER003_EMAIL = "ER003_EMAIL";
	// Hạng mục tel
	// Hãy nhập tel
	public static final String ER001_TEL = "ER001_TEL";
	// Check length
	public static final String ER006_TEL = "ER006_TEL";
	// Check format
	public static final String ER005_TEL = "ER005_TEL";
	// Hạng mục password
	// Lỗi password không nhập
	public static final String ER001_PASSWORD = "ER001_PASSWORD";
	// Check 1 byte
	public static final String ER008_PASSWORD = "ER008_PASSWORD";
	// Check length
	public static final String ER007_PASSWORD = "ER007_PASSWORD";
	// Hạng mục confirmPassword
	// Check confirmPass
	public static final String ER017_PASSWORD_CONFIRM = "ER017_PASSWORD_CONFIRM";
	// Hạng mục codeJapan
	// Check không tồn tại
	public static final String ER004_CODE_LEVEL = "ER004_CODE_LEVEL";
	// Hạng mục startDate
	// Check ngày hợp lệ
	public static final String ER011_START_DATE = "ER011_START_DATE";
	// Hạng mục endDate
	// Check ngày hợp lệ
	public static final String ER011_END_DATE = "ER011_END_DATE";
	// Ngày hết hạn < ngày bắt đầu
	public static final String ER012_END_DATE = "ER012_END_DATE";
	// Hạng mục total
	// Hãy nhập total
	public static final String ER001_TOTAL = "ER001_TOTAL";
	// Check số haflsize
	public static final String ER018_TOTAL = "ER018_TOTAL";
	// Check length
	public static final String ER006_TOTAL = "ER006_TOTAL";
	// Điểm không quá 180
	public static final String ER021_TOTAL = "ER021_TOTAL";
	
	// Các lỗi khác
	// Lỗi nhập sai tên đăng nhập hoặc password
	public static final String ER016 = "ER016";
	// Lỗi thao tác với db
	public static final String ER015 = "ER015";
	// lỗi user không tồn tại
	public static final String ER013 = "ER013";
	// Lỗi kiểm tra user admin
	public static final String ER020 = "ER020";
	
	// List lỗi
	public static final String LIST_ERR = "listErr";
	// tên biến userInfor
	public static final String USER_INFOR = "userInfor";
	
	// Format các hạng mục
	// Format loginName
	public static final String FORMAT_LOGIN_NAME = "^[a-zA-Z_][a-zA-Z0-9_]*";
	public static final int MIN_LENGTH_LOGIN_NAME = 4;
	public static final int MAX_LENGTH_LOGIN_NAME = 15;
	// Format FullName
	public static final int MIN_LENGTH_0 = 0;
	public static final int MAX_LENGTH_255 = 255;
	// Format FullNameKana
	public static final String FORMAT_FULLNAMEKANA = "[ァ-ンｧ-ﾝﾞﾟ・]{0,}";
	public static final int MIN_LENGTH_FULLNAMEKANA = 0;
	public static final int MAX_LENGTH_FULLNAMEKANA = 255;
	// Format Email
	// start fix bug ID 319- KhangNL 2020/08/2020
	public static final String FORMAT_EMAIL = "[^.@]{1,}@{1}[^@.]{1,}[.]{1}[^@.]{1,}";
	// end fix bug ID 319- KhangNL 2020/08/2020
	
	public static final int MIN_LENGTH_EMAIL = 0;
	public static final int MAX_LENGTH_EMAIL = 100;
	// Format Tel
	public static final String FORMAT_TEL = "[0-9]{1,4}[-]{1}[0-9]{1,4}[-]{1}[0-9]{1,4}";
	public static final int MIN_LENGTH_TEL = 0;
	public static final int MAX_LENGTH_TEL = 14;
	// Format Password
	public static final int MIN_LENGTH_PASSWORD = 5;
	public static final int MAX_LENGTH_PASSWORD = 15;
	// Format total
	public static final String FORMAT_TOTAL = "[0-9]*";
	public static final int MAX_TOTAL = 180;
	public static final int OVER_MAX_TOTAL = 181;
	
	// Biến check di chuyển từ màn hình ADM003->ADM004
	public static final String PASSED = "PASSED";
	
	// Tên các trường trong db
	public static final String TBL_USER_USER_ID = "user_id";
	public static final String TBL_USER_GROUP_ID = "group_id";
	public static final String TBL_USER_LOGIN_NAME = "login_name";
	public static final String TBL_USER_PASSWORD = "password";
	public static final String TBL_USER_FULL_NAME= "full_name";
	public static final String TBL_USER_FULL_NAME_KANA = "full_name_kana";
	public static final String TBL_USER_EMAIL= "email";
	public static final String TBL_USER_TEL = "tel";
	public static final String TBL_USER_BIRTHDAY = "birthday";
	public static final String TBL_USER_RULE = "rule";
	public static final String TBL_USER_SALT = "salt";
	public static final String TBL_DETAIL_USER_JAPAN_DETAIL_USER_JAPAN_ID = "detail_user_japan_id";
	public static final String TBL_DETAIL_USER_JAPAN_USER_ID = "user_id";
	public static final String TBL_DETAIL_USER_JAPAN_CODE_LEVEL = "code_level";
	public static final String TBL_DETAIL_USER_JAPAN_START_DATE = "start_date";
	public static final String TBL_DETAIL_USER_JAPAN_END_DATE = "end_date";
	public static final String TBL_DETAIL_USER_JAPAN_TOTAL = "total";
	public static final String MST_GROUP_GROUP_ID = "group_id";
	public static final String MST_GROUP_GROUP_NAME = "group_name";
	public static final String MST_JAPAN_CODE_LEVEL = "code_level";
	public static final String MST_JAPAN_NAME_LEVEL = "name_level";
	
	// Định dạng utf-8
	public static String UTF8 = "UTF-8";
	
	// Tên attribute loginName
	public static final String LOGIN_NAME = "loginName";
	// Tên attribute password
	public static final String PASSWORD = "password";
	// Tên attribute error
	public static final String ERROR = "error";
	
	// Tên attribute lấy từ req và session màn hình
	public static final String ATTRIBUTE_FULL_NAME = "fullName";
	public static final String ATTRIBUTE_GROUP_ID = "groupId";
	public static final String ATTRIBUTE_CURRENT_PAGE = "currentPage";
	public static final String ATTRIBUTE_SORT_TYPE = "sortType";
	public static final String ATTRIBUTE_SORT_BY_FULL_NAME = "sortByFullName";
	public static final String ATTRIBUTE_SORT_BY_CODE_LEVEL = "sortByCodeLevel";
	public static final String ATTRIBUTE_SORT_BY_END_DATE = "sortByEndDate";
	public static final String ATTRIBUTE_LIST_MST_GROUP = "listMstGroup";
	public static final String ATTRIBUTE_LIMIT_PAGE = "limitPage";
	public static final String ATTRIBUTE_TOTAL_PAGE = "totalPage";
	public static final String ATTRIBUTE_LIST_PAGING = "listPaging";
	public static final String ATTRIBUTE_LIST_USERS = "listUsers";
	public static final String ATTRIBUTE_TOTAL_RECORDS = "totalRecords";
	public static final String ATTRIBUTE_FULL_NAME_KANA = "fullNameKana";
	public static final String ATTRIBUTE_YEAR_BIRTHDAY = "yearBirthday";
	public static final String ATTRIBUTE_MONTH_BIRTHDAY = "monthBirthday";
	public static final String ATTRIBUTE_DAY_BIRTHDAY = "dayBirthday";
	public static final String ATTRIBUTE_EMAIL = "email";
	public static final String ATTRIBUTE_TEL = "tel";
	public static final String ATTRIBUTE_PASSWORD = "password";
	public static final String ATTRIBUTE_CONFIRM_PASSWORD = "confirmPassword";
	public static final String ATTRIBUTE_CODE_LEVEL = "codeLevel";
	public static final String ATTRIBUTE_YEAR_START_DATE = "yearStartDateJapan";
	public static final String ATTRIBUTE_MONTH_START_DATE = "monthStartDateJapan";
	public static final String ATTRIBUTE_DAY_START_DATE = "dayStartDateJapan";
	public static final String ATTRIBUTE_YEAR_END_DATE = "yearEndDateJapan";
	public static final String ATTRIBUTE_MONTH_END_DATE = "monthEndDateJapan";
	public static final String ATTRIBUTE_DAY_END_DATE = "dayEndDateJapan";
	public static final String ATTRIBUTE_TOTAL = "total";
	
	public static final String ATTRIBUTE_NEXT_PAGE = "nextPage";
	public static final String ATTRIBUTE_NEXT= "next";
	public static final String ATTRIBUTE_PREVIOUS_PAGE = "previousPage";
	public static final String ATTRIBUTE_PREVIOUS = "previous";
	
	public static final String ATTRIBUTE_LIST_MST_JAPAN = "listMstJapan";
	public static final String ATTRIBUTE_LIST_YEAR = "listYear";
	public static final String ATTRIBUTE_LIST_MONTH= "listMonth";
	public static final String ATTRIBUTE_LIST_DAY = "listDay";
	public static final String ATTRIBUTE_LIST_YEAR_END_DATE_JAPAN = "listYearEndDateJapan";
	
	public static final String ATTRIBUTE_USER_INFOR = "userInfor";
	public static final String ATTRIBUTE_SALT = "salt";
	public static final String ATTRIBUTE_USER_ID = "userId";
	public static final String ATTRIBUTE_KEY = "key";
	
	
	// Đường dẫn đến file ADM001.jsp
	public static final String JSP_ADM001 = "/jsp/ADM001.jsp";
	// Đường dẫn đến file ADM002.jsp
	public static final String JSP_ADM002 = "/jsp/ADM002.jsp";
	// Đường dẫn đến file ADM003.jsp
	public static final String JSP_ADM003 = "/jsp/ADM003.jsp";
	// Đường dẫn đến file ADM004.jsp
	public static final String JSP_ADM004 = "/jsp/ADM004.jsp";
	// Đường dẫn đến file ADM005.jsp
	public static final String JSP_ADM005 = "/jsp/ADM005.jsp";
	// Đường dẫn đến file ADM006.jsp
	public static final String JSP_ADM006 = "/jsp/ADM006.jsp";
	// Đường dẫn đến file System_Error.jsp
	public static final String JSP_SYSTEM_ERROR = "/jsp/System_Error.jsp";
	
	// Biến giá trị của message.properties
	// Msg
	public static final String MSG = "msg";
	// Đăng ký User thành công
	public static final String MSG001 = "MSG001";
	// Update User thành công
	public static final String MSG002 = "MSG002";
	// Delete User thành công
	public static final String MSG003 = "MSG003";
	// Xác nhận trước khi xóa
	public static final String MSG004 = "MSG004";
	// Không tim thấy user
	public static final String MSG005 = "MSG005";
	// Msg Noti ADM006
	public static final String MESS_NOTI = "msgNoti";
	// Msg Error SystemError
	public static final String MESS_ERROR = "messError";
	
	// Đường dẫn đến file database.properties
	public static String DATABASE_PROPERTIES = "database.properties";	
	// Đường dẫn đến file message_error.properties
	public static String MESSAGE_ERROR_PROPERTIES = "message_error.properties";
	// Đường dẫn đến file config.properties
	public static String CONFIG_PROPERTIES = "config.properties";
	// Đường dẫn đến file message.properties
	public static String MESSAGE_PROPERTIES = "message.properties";
	
	// Url
	public static final String URL_LOGIN = "/login.do";
	public static final String URL_LOGOUT = "/logout.do";
	public static final String URL_LIST_USER = "/listuser.do";
	public static final String URL_SYSTEM_ERROR = "/systemerror";
	public static final String URL_ADD_USER_CONFIRM = "/AddUserConfirm.do";
	public static final String URL_ADD_USER_VALIDATE = "/AddUserValidate.do";
	public static final String URL_ADD_USER = "/AddUserInput.do";
	public static final String URL_ADD_USER_OK = "/AddUserOK.do";
	public static final String URL_SUCCESS = "/Success.do";
	public static final String URL_VIEW_DETAIL_USER = "/ViewDetailUser.do";
	public static final String URL_EDIT_USER_INPUT = "/EditUserInput.do";
	public static final String URL_EDIT_USER_VALIDATE = "/EditUserValidate.do";
	public static final String URL_EDIT_USER_CONFIRM = "/EditUserConfirm.do";
	
	// Rule admin
	public static final int RULE_ADMIN = 0;
	// Rule user
	public static final int RULE_USER = 1;	
	
	// Các giá trị mặc định
	// Giá trị rỗng
	public static final String EMPTY = "";
	// Giá trị group id mặc định
	public static final int GROUP_ID_DEFAULT = 0;
	// Vị trí lấy dữ liệu default
	public static final int OFFSET_DEFAULT = 0;
	// Giá trị tìm kiếm tên default
	public static final String FULL_NAME_DEFAULT = "";
	// Page hiện tại default
	public static final int CURRENT_PAGE_DEFAULT = 1;
	// Giá trị sort tăng dần
	public static final String SORT_ASC = "ASC";
	// Giá trị sort giảm dần
	public static final String SORT_DESC = "DESC";
	// Giá trị sắp xếp ưu tiên fullName
	public static final String SORT_TYPE_FULLNAME = "FULLNAME";
	// Giá trị sắp xếp ưu tiên codeLevel
	public static final String SORT_TYPE_CODE_LEVEL = "CODELEVEL";
	// Giá trị sắp xếp ưu tiên endDate
	public static final String SORT_TYPE_END_DATE = "ENDDATE";
	// Giá trị default code level
	public static final String CODE_LEVEL_DEFAULT = "0";
	
	
	// Các giá trị action: default, search, sort, paging, back, validate cho action
	public static final String ACTION = "action";
	public static final String DEFAULT_ACTION = "DEFAULT";
	public static final String SEARCH_ACTION = "SEARCH";
	public static final String SORT_ACTION = "SORT";
	public static final String PAGING_ACTION = "PAGING";
	public static final String BACK_ACTION = "BACK";
	public static final String VALIDATE_ACTION = "VALIDATE";
	
	// Giá trị năm bắt đầu của danh sách selectBox
	public static final int FROM_YEAR = 1900;
	
	// Giá trị mặc định của userId = 0
	public static final int USER_ID_DEFAULT = 0;
}
