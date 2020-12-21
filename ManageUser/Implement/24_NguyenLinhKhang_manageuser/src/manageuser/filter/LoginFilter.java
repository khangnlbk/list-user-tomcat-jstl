/**
 * Copyright(C) 2020 Luvina Software
 * LoginFilter.java, [dd/mm/yyyy] Nguyễn Linh Khang
 */
package manageuser.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Servlet Filter thực hiện checkLogin
 * 
 * @author KhangNL
 */
public class LoginFilter implements Filter {

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * Hàm thực hiện checkLogin, 
	 * + Nếu đã đăng nhập và nhập link login thì chuyển hướng sang link listuser.do 
	 * + Nếu đã đăng nhập hoặc nhập link login thì cho qua filter 
	 * + Nếu chưa đăng nhập thì chuyển hướng sang link login
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// ép kiểu biến
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// Khai báo đối tượng lưu session hiện tại
		HttpSession session = req.getSession();
		try {
			// Lấy về đường dẫn root
			String rootURL = req.getContextPath();
			// Lấy về URI
			String url = req.getRequestURI();
			// Khởi tạo biến checkLogin
			boolean checkLogin = Common.checkLogin(session);
			// Nếu nhập link login.do hoặc link root
			if ((rootURL + "/").equals(url) || (rootURL + Constant.URL_LOGIN).equals(url)) {
				// Nếu đã login
				if (checkLogin) {
					// thì chuyển đến màn ADM002
					resp.sendRedirect(rootURL + Constant.URL_LIST_USER);
					// Nếu chưa đăng nhập (trường hợp nhập link login.do hoặc link root)
				} else {
					// Cho qua filter
					chain.doFilter(req, resp);
				}
			// Nếu nhập url có đuôi .do
			} else {
				// Nếu người dùng đã đăng nhập
				if (checkLogin) {
					// Cho qua filter
					chain.doFilter(req, resp);
					// Nếu chưa đăng nhập
				} else {
					// thì về màn hình ADM001
					resp.sendRedirect(rootURL);
				}
			}
		} catch (Exception e) {
			// nếu bắt được exeption thì in ra log
			System.out.println(this.getClass().getName() + "-"
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "-" + e.getMessage());
			// SendRedirect đến SystemErrorController
			resp.sendRedirect(req.getContextPath() + Constant.URL_SYSTEM_ERROR);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}