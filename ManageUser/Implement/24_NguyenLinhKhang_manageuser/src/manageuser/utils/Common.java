/**
  * Copyright(C) 2020 Luvina Software
 * Common.java, 14/07/2020, KhangNL
 */
package manageuser.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import manageuser.dao.TblUserDAO;
import manageuser.dao.impl.TblUserDAOImpl;
import manageuser.entities.MstGroupEntities;
import manageuser.entities.MstJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;

/**
 * Lớp chứa các phương thức dùng chung
 *
 * @author KhangNL
 *
 */
public class Common {
	/**
	 * Hàm mã hóa password
	 * 
	 * @param password
	 *            là mật khẩu người dùng nhập vào
	 * @param salt
	 *            là salt được tạo khi đăng kí
	 * @return mật khẩu đã được mã hóa nếu không có lỗi, nếu có lỗi trả về null
	 * @throws NoSuchAlgorithmException
	 *             không tìm thấy thuật toán mã hóa encrypt SHA-1
	 */
	public static String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
		// Khởi tạo biến lưu password
		String passEncrypt = null;
		try {
			// cộng 2 chuỗi password và salt nhập vào
			String input = password + salt;
			// Khai báo đối tượng MessageDigest với kiểu mã hóa SHA1
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// update giá trị cho messageDigest
			md.update((input).getBytes());
			// Mã hóa password
			byte[] hashedPassword = md.digest(input.getBytes());
			// khởi tạo đối tượng formatter
			Formatter formatter = new Formatter();
			// format string cho mảng arrConvert
			for (byte b : hashedPassword) {
				formatter.format("%02x", b);
			}
			// gán lại biến text = giá trị format chuyển sang kiểu String
			passEncrypt = formatter.toString();
			// đóng formatter
			formatter.close();
		} catch (NoSuchAlgorithmException e) {
			// Nếu bắt được exeption thì in ra màn hình console
			System.out.println("Common.encryptPassword - " + e.getMessage());
			throw e;
		}
		return passEncrypt;
	}

	/**
	 * Hàm kiểm tra 2 String giống nhau
	 *
	 * @param string1
	 *            chuỗi thứ 1
	 * @param string2
	 *            chuỗi thứ 2
	 * @return nếu giống return true, nếu khác return false
	 */
	public static boolean compareString(String string1, String string2) {
		boolean result = false;
		if (string1.equals(string2)) {
			result = true;
		}
		return result;
	}

	/**
	 * Hàm check không rỗng
	 * 
	 * @param value
	 *            giá trị cần kiểm tra
	 * @return nếu rỗng trả về true, nếu đã không rỗng trả về false
	 */
	public static boolean checkEmpty(String value) {
		boolean check = false;
		if ("".equals(value)) {
			check = true;
		}
		return check;
	}

	/**
	 * Hàm kiểm tra login
	 * 
	 * @param session
	 *            session hiện tại
	 * @return nếu đã login và tồn tại user trả về true, nếu chưa login hoặc
	 *         loginName đã bị xóa trong db trả về false
	 * @throws SQLException
	 *             lỗi kết nối db
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 */
	public static boolean checkLogin(HttpSession session) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến kiểm tra đã login chưa
		boolean check = false;
		// Lấy giá trị loginName trên session
		String loginName = (String) session.getAttribute(Constant.LOGIN_NAME);
		if (loginName != null) {
			// Khởi tạo và gọi đến phương thức getTblUserByLoginName để lấy về
			// user theo loginName
			TblUserDAO tbluserDAO = new TblUserDAOImpl();
			TblUserEntities tbluser = tbluserDAO.getTblUserByLoginName(loginName);
			// Kiểm tra nếu user đó khác null (có tồn tại user như vậy)
			if (tbluser != null) {
				// Gán bằng true nếu đã login và tồn tại user
				check = true;
			}
		}
		// Gán bằng false nếu chưa login hoặc không tồn tại user
		return check;
	}

	/**
	 * Hàm replace ký tự wildcard, ký tự đặc biệt trong câu sql sử dụng mệnh đề LIKE
	 * 
	 * @param string
	 *            giá trị lấy từ req
	 * @return giá trị đã convert
	 */
	public static String replaceWildCard(String string) {
		// Nếu fullName khác rỗng
		if (!Common.checkEmpty(string)) {
			// replace các kí tự đặc biệt
			string = string.replace("%", "\\%");
			string = string.replace("_", "\\_");
		}
		return string;
	}

	/**
	 * Tạo chuỗi paging
	 * 
	 * @param totalUser
	 *            tổng sô user
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @param currentPage
	 *            trang hiện tại
	 * @return List<Integer> Nếu có chuỗi thì trả về listPaging, nếu không thì
	 *         trả về null
	 */
	public static List<Integer> getListPaging(int totalRecords, int limit, int currentPage) {
		// Khởi tạo chuỗi paging
		List<Integer> listPaging = new ArrayList<Integer>();
		// Nếu tổng số user lớn hơn số lượng cần hiển thị trên 1 trang
		if (totalRecords > limit) {
			// Lấy tổng số page
			int totalPage = getTotalPage(totalRecords, limit);
			// Lấy giá trị đầu tiên của chuỗi paging
			int startPage = ((currentPage - 1) / Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE)))
					* Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE)) + 1;
			// Lấy giá trị cuối cùng của chuỗi paging
			int endPage = startPage + Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE)) - 1;
			// Nếu trang cuối cùng lớn hơn tổng số trang
			if (endPage > totalPage) {
				// Gán trang cuỗi cùng = tổng số trang tìm được
				endPage = totalPage;
			}
			// Chuỗi paging = startPage -> endPage
			for (int i = startPage; i <= endPage; i++) {
				listPaging.add(i);
			}
		}
		// Trả về chuỗi paging
		return listPaging;
	}

	/**
	 * Lấy vị trí data cần lấy
	 * 
	 * @param currentPage
	 *            Trang hiện tại
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @return vị trí cần lấy
	 */
	public static int getOffset(int currentPage, int limit) {
		// Gán offset = 0
		int offset = 0;
		// Nếu trang hiện tại lớn hơn 0
		if (currentPage > 0) {
			// Vị trí lấy data = (trang hiện tại -1) * số lượng cần hiển thị
			// trong 1 trang
			offset = (currentPage - 1) * limit;
		}
		// Trả về vị trí cần lần ra
		return offset;
	}

	/**
	 * Lấy số lượng hiển thị bản ghi trên 1 trang
	 * 
	 * @return số lượng records cần lấy
	 */
	public static int getLimit() {
		// lấy ra limit record từ file properties
		int limitRecord = Integer.parseInt(ConfigProperties.getValueByKey(Constant.LIMIT_RECORD));
		// trả về số bản ghi tối đa hiển thị trong 1 trang
		return limitRecord;
	}

	/**
	 * Tính tổng số trang
	 * 
	 * @param totalUser
	 *            tổng số User
	 * @param limit
	 *            số lượng cần hiển thị trên 1 trang
	 * @return tổng số trang
	 */
	public static int getTotalPage(int totalUser, int limit) {
		// Gán totalPage = 0
		int totalPage = 0;
		// totalPage bằng số bản ghi chia cho số bản ghi hiển thị 1 trang (lấy
		// phần nguyên)
		totalPage = totalUser / limit;
		// Nếu tổng số user chia dư cho số bản ghi tối đa của 1 trang
		if (totalUser % limit > 0) {
			// totalPage = kết quả chia lấy phần nguyên + 1
			totalPage++;
		}
		// Trả về tổng số page
		return totalPage;
	}

	/**
	 * Phương thức có chức năng kiểm tra tên trường có trong db không
	 * 
	 * @param sortType
	 *            sortType cần kiểm tra
	 * @return nếu hợp lệ trả về true, không hợp lệ trả về false
	 * @throws ClassNotFoundException
	 *             không tìm thấy class
	 * @throws SQLException
	 *             lỗi kết nối db
	 */
	public static boolean checkSortTypeWhiteList(String sortType) throws ClassNotFoundException, SQLException {
		// khởi tạo biến checkSortType chứa kết quả kiểm tra, mặc định = false
		boolean checkSortType = false;
		// khởi tạo đối tượng tblUserDAO
		TblUserDAO tblUserDAO = new TblUserDAOImpl();
		// Lấy ra whileList
		List<String> whiteList = tblUserDAO.getColumnWhiteList(DatabaseProperties.getValueByKey(Constant.DB_NAME));
		if (whiteList.contains(sortType)) {
			// nếu đúng gán lại checkSortType = true
			checkSortType = true;
		}
		// trả về giá trị check
		return checkSortType;
	}

	/**
	 * Phương thức có chức năng lấy năm hiện tại
	 * 
	 * @return năm hiện tại
	 */
	public static int getYearNow() {
		// Khởi tạo biến lấy giá trị lịch
		Calendar calendar = Calendar.getInstance();
		// Trả về năm hiện tại
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Phương thức có chức năng lấy tháng hiện tại
	 * 
	 * @return tháng hiện tại
	 */
	public static int getMonthNow() {
		// Khởi tạo biến lấy giá trị lịch
		Calendar calendar = Calendar.getInstance();
		// Trả về năm hiện tại
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * Phương thức có chức năng lấy ngày hiện tại
	 * 
	 * @return ngày hiện tại
	 */
	public static int getDayNow() {
		// Khởi tạo biến lấy giá trị lịch
		Calendar calendar = Calendar.getInstance();
		// Trả về năm hiện tại
		return calendar.get(Calendar.DATE);
	}

	/**
	 * Phương thức có chức năng lấy danh sách hiển thị năm từ fromYear->toYear
	 * 
	 * @param fromYear
	 *            năm bắt đầu danh sách
	 * @param toYear
	 *            năm kết thúc danh sách
	 * @return danh sách năm
	 */
	public static List<Integer> getListYear(int fromYear, int toYear) {
		// Khởi tạo biến danh sách năm
		List<Integer> listYear = new ArrayList<Integer>();
		// Lấy giá trị fromYear từ constant
		fromYear = Constant.FROM_YEAR;
		// Vòng lặp để add năm vào mảng danh sách năm
		// Start fix bug ID 85 – KhangNL fix 2020/08/18
		for (int i = toYear; i >= fromYear; i--) {
			// add năm vào mảng
			listYear.add(i);
		}
		// End fix bug ID 85 – KhangNL fix 2020/08/18
		// Trả về danh sách năm
		return listYear;
	}

	/**
	 * Phương thức có chức năng lấy danh sách tháng từ tháng 1->12
	 * 
	 * @return danh sách tháng
	 */
	public static List<Integer> getListMonth() {
		// Khởi tạo biến danh sách tháng
		List<Integer> listMonth = new ArrayList<Integer>();
		// Vòng lặp để add tháng vào mảng danh sách tháng
		for (int i = 1; i <= 12; i++) {
			// add năm vào mảng
			listMonth.add(i);
		}
		// Trả về danh sách tháng
		return listMonth;
	}

	/**
	 * Phương thức có chức năng lấy danh sách ngày từ ngày 1->31
	 * 
	 * @return danh sách ngày
	 */
	public static List<Integer> getListDay() {
		// Khởi tạo biến danh sách ngày
		List<Integer> listDay = new ArrayList<Integer>();
		// Vòng lặp để add ngày vào mảng
		for (int i = 1; i <= 31; i++) {
			// add ngày vào mảng
			listDay.add(i);
		}
		// Trả về danh sách mảng
		return listDay;
	}

	/**
	 * Hàm convert từ ngày tháng lấy về từ selectbox thành String
	 * 
	 * @param year
	 *            năm
	 * @param month
	 *            tháng
	 * @param day
	 *            ngày
	 * @return năm tháng ngày dạng String với định dạng yyyy-MM-dd
	 */
	public static String convertToString(int year, int month, int day) {
		// Khởi tạo chuỗi
		StringBuilder sb = new StringBuilder();
		// Thêm year, month, day cách nhau bằng dấu -
		sb.append(year);
		sb.append("-");
		sb.append(month);
		sb.append("-");
		sb.append(day);
		// Chuỗi ngày tháng theo định dạng
		return sb.toString();
	}

	/**
	 * Hàm lấy salt để tạo password cho user
	 * 
	 * @return salt lấy thời gian hiện tại, tính đến millisecond
	 */
	public static String getSalt() {
		String salt = "";
		// Lấy ngày tháng hiện tại
		Date date = new Date();
		// Chuyển về String theo định dạng lấy đến millisecond giây
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		salt = df.format(date);
		return salt;
	}

	/**
	 * Hàm check format cho các hạng mục loginName, fullNameKana, email, tel,
	 * pass, total
	 * 
	 * @param value
	 *            giá trị cần kiểm tra
	 * @param regex
	 *            Biểu thức của hạng mục muốn kiểm tra
	 * @return true nếu đúng format, ngược lại trả về false
	 */
	public static boolean checkFormat(String value, String regex) {
		boolean check = false;
		// Thực hiện so khớp và lấy về kết quả so khớp
		check = value.matches(regex);
		return check;
	}

	/**
	 * Hàm check độ dài của các hạng mục loginName, fullName, fullNameKana,
	 * email, tel, password, confirmPassword, total
	 * 
	 * @param value
	 *            giá trị cần kiểm tra
	 * @param minLength
	 *            độ dài tối thiểu
	 * @param maxLength
	 *            độ dài tối đa
	 * @return true nếu thỏa mãn, ngược lại trả về false
	 */
	public static boolean checkLength(String value, int minLength, int maxLength) {
		boolean check = false;
		// Lấy độ dài của giá trị
		int length = value.length();
		if (minLength <= length && maxLength >= length) {
			check = true;
		}
		return check;
	}

	/**
	 * Hàm check chọn selectBox
	 * 
	 * @param valueSelectBox
	 *            selectBox cần kiểm tra
	 * @return nếu chưa chọn trả về true, nếu đã chọn trả về false
	 */
	public static boolean checkNotSelectedSelectBox(int groupId) {
		// Khởi tạo biến check = false
		boolean check = false;
		// Nếu giá trị groupId bằng giá trị default = 0
		if (Constant.GROUP_ID_DEFAULT == groupId) {
			// Gán check = true
			check = true;
		}
		// Trả lại giá trị check
		return check;
	}

	/**
	 * Hàm thêm lỗi vào mảng lỗi
	 * 
	 * @param listError
	 *            danh sách lỗi
	 * @param err
	 *            câu thông báo lỗi
	 */
	public static void addErr(List<String> listErr, String err) {
		// Nếu có lỗi
		if (!Common.checkEmpty(err)) {
			// Thêm vào danh sách lỗi
			listErr.add(err);
		}
	}

	/**
	 * Hàm kiểm tra ngày tháng hợp lệ
	 * 
	 * @param value
	 *            giá trị cần kiểm tra
	 * @return true nếu ngày tháng hợp lệ, ngược lại trả về false
	 */
	public static boolean checkDate(String value) {
		boolean check = false;
		try {
			// Khai báo và khởi tạo đối tượng SimpleDateFormat
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// Set false để kiểm tra tính hợp lệ của date
			sdf.setLenient(false);
			// Parse String thành kiểu date
			sdf.parse(value);
			// Nếu không có lỗi gán biến check = true
			check = true;
		} catch (ParseException e) {
			// Nếu có lỗi gán biến check = false
			check = false;
		}
		return check;
	}

	/**
	 * Hàm check kí tự 1 byte
	 * 
	 * @param text
	 *            giá trị check
	 * @return true nếu tất cả là kí tự 1 byte, false nếu có ký tự không phải 1
	 *         byte
	 */
	public static boolean isOneByteCharater(String text) {
		// khởi tạo biến kiểm
		boolean check = true;
		// chuyển String đầu vào thành mảng ký tự để duyệt lần lượt từng ký tự
		char[] charArr = text.toCharArray();
		// lặp lần lượt từng ký tự trong mảng
		for (char c : charArr) {
			// kiểm tra nếu không phải ký tự 1 byte
			if ((int) c > 127) {
				check = false;
				break;
			}
		}
		// trả về biến kiểm tra
		return check;
	}

	/**
	 * Hàm kiểm tra ngày kết thúc phải lớn hơn ngày bắt đầu
	 * 
	 * @param startDate
	 *            ngày bắt đầu
	 * @param endDate
	 *            ngày kết thúc
	 * @return true nếu ngày kết thúc lớn hơn ngày bắt đầu, ngược lại trả về
	 *         false
	 */
	public static boolean checkEndDate(String startDate, String endDate) {
		// Khởi tạo biến check
		boolean check = false;
		// tách startDate thành mảng gồm 3 phần tử năm, tháng, ngày
		String[] startDateArrs = startDate.split("-");
		// tách endDate thành mảng gồm 3 phần tử năm, tháng, ngày
		String[] endDateArrs = endDate.split("-");
		// So sánh năm
		if (Integer.parseInt(startDateArrs[0]) < Integer.parseInt(endDateArrs[0])) {
			// Nếu năm nhỏ hơn trả về true
			check = true;
		} else if (Integer.parseInt(startDateArrs[0]) == Integer.parseInt(endDateArrs[0])) {
			// Nếu năm bằng nhau thì check tháng
			if (Integer.parseInt(startDateArrs[1]) < Integer.parseInt(endDateArrs[1])) {
				// Nếu tháng nhỏ hơn trả về true
				check = true;
			} else if (Integer.parseInt(startDateArrs[1]) == Integer.parseInt(endDateArrs[1])) {
				// Nếu tháng bằng nhau thì check ngày
				if (Integer.parseInt(startDateArrs[2]) < Integer.parseInt(endDateArrs[2])) {
					// Nếu ngày nhỏ hơn trả về true
					check = true;
				}
			}
		}
		// Trả về biến kiểm tra
		return check;
	}

	/**
	 * Hàm convert từ String về int, nếu đầu vào không là int thì trả về giá trị
	 * default
	 * 
	 * @param value
	 *            chuỗi cần kiểm tra
	 * @param defaultValue
	 *            giá trị default của hạng mục cần kiểm tra
	 * @return giá trị parseInt(value) nếu thành công, nếu không thành công thì
	 *         trả về giá trị default của hạng mục có giá trị cần kiểm tra
	 */
	public static int convertStringToInt(String value, int defaultValue) {
		// Khai báo giá trị trả về
		int result = 0;
		try {
			// Parse từ string sang int
			result = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			// Nếu có lỗi, gán giá trị mặc định cho result
			result = defaultValue;
		}
		// Trả về giá trị
		return result;
	}

	/**
	 * Hàm tạo key
	 * 
	 * @return trả về chuỗi key
	 */
	public static String createKey() {
		// Trả về key
		return getSalt();
	}

	/**
	 * Phương thức thực hiện set giá trị cho các hạng mục selectbox ở màn hình
	 * ADM003
	 * 
	 * @param req
	 *            Request truyền vào
	 * @throws ClassNotFoundException
	 *             lỗi không tìm thấy class
	 * @throws SQLException
	 *             lỗi không kết được với db
	 */
	public static void setDataLogic(HttpServletRequest req) throws ClassNotFoundException, SQLException {
		// Khởi tạo biến mstGroupLogic thao tác với danh sách mstGroup
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		// Khởi tạo danh sách mstGroup
		List<MstGroupEntities> listMstGroup = mstGroupLogic.getAllMstGroup();
		// Khởi tại biến mstJapanLogic thao tác với danh sách mstJapan
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		// Khởi tạo danh sách mstJapan
		List<MstJapanEntities> listMstJapan = mstJapanLogic.getAllMstJapan();
		// Lấy giá trị năm hiện tại
		int toYear = Common.getYearNow();

		// Lấy danh sách ngày tháng năm
		// Danh sách năm từ năm 1900->năm hiện tại
		List<Integer> listYear = Common.getListYear(Constant.FROM_YEAR, toYear);
		// Danh sách tháng từ 1->12
		List<Integer> listMonth = Common.getListMonth();
		// Danh sách ngày từ 1->31
		List<Integer> listDay = Common.getListDay();
		// Danh sách năm từ 1900->(năm hiện tại + 1) trường hợp endDate trình độ
		// tiếng Nhật
		List<Integer> listYearEndDateJapan = Common.getListYear(Constant.FROM_YEAR, toYear + 1);

		// Set các giá trị lên request
		req.setAttribute(Constant.ATTRIBUTE_LIST_MST_GROUP, listMstGroup);
		req.setAttribute(Constant.ATTRIBUTE_LIST_MST_JAPAN, listMstJapan);
		req.setAttribute(Constant.ATTRIBUTE_LIST_YEAR, listYear);
		req.setAttribute(Constant.ATTRIBUTE_LIST_YEAR_END_DATE_JAPAN, listYearEndDateJapan);
		req.setAttribute(Constant.ATTRIBUTE_LIST_MONTH, listMonth);
		req.setAttribute(Constant.ATTRIBUTE_LIST_DAY, listDay);
	}
}
