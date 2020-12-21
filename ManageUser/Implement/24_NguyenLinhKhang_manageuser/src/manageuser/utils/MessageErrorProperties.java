/**
 * Copyright(C) 2020 Luvina Software
 * MessageErrorProperties.java, 15/07/2020, KhangNL
 */
package manageuser.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Lớp đọc file message_error.properties
 *
 * @author KhangNL
 *
 */
public class MessageErrorProperties {
	// lưu các cặp <key, value> trong file properties
	private static Map<String, String> mapMessProperties = new HashMap<String, String>();
	static {
		try {
			// tạo đối tượng kiểu Properties
			Properties properties = new Properties();
			// đọc file properties
			properties.load(new InputStreamReader(
				DatabaseProperties.class.getClassLoader().getResourceAsStream(Constant.MESSAGE_ERROR_PROPERTIES), Constant.UTF8));
			// lưu các giá trị key trong file properties
			Enumeration<?> enumeration = properties.propertyNames();
			// true nếu vẫn còn phần tử, false nếu tất cả phần tử đã được lấy ra
			while (enumeration.hasMoreElements()) {
				// key = key tiếp theo
				String key = (String) enumeration.nextElement();
				// lấy value tương ứng với key
				String value = properties.getProperty(key);
				// thêm vào list
				mapMessProperties.put(key, value);
			}
		} catch (IOException e) {
			// Nếu bắt được exeption thì in ra màn hình console
			System.out.println(e.getMessage());
		}
	}

	/**
	* lấy value tương ứng với key trong file properties
	* 
	* @param key tên key trong file properties
	* @return trả về value tương ứng với key
	*/
	public static String getValueByKey(String key) {
		String value = mapMessProperties.get(key);
		return value;
	}
}
