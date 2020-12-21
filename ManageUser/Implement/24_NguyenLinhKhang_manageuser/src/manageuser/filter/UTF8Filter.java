package manageuser.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import manageuser.utils.Constant;

/**
 * Servlet Filter thực hiện setCharacterEncoding UTF-8
 */
public class UTF8Filter implements Filter {
	// Set giá trị default cho UTF8
	private String UTF8 = Constant.UTF8;
    /**
     * Default constructor. 
     */
    public UTF8Filter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Set encoding cho requset, response
		request.setCharacterEncoding(UTF8);
		response.setCharacterEncoding(UTF8);
		// Cho qua filter
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// Đọc giá trị của biến UTF8 được cấu hình trong web.xml
		String UTF8Param = fConfig.getInitParameter("UTF8");
		// Nếu lấy được giá trị của biến UTF8
        if (UTF8Param != null) {
        	// Gán giá trị cho biến UTF để setCharacterEncoding
            UTF8 = UTF8Param;
        }
	}
}
