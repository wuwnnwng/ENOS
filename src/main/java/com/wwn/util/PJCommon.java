package com.wwn.util;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class PJCommon {

	/**
	 * 从request中取出所有参数(单键值对形式),并返回Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map getRequestParamMap(HttpServletRequest request) {
		Map paramMap = new HashMap();
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paramName = (String) enu.nextElement();
			String[] values = request.getParameterValues(paramName);
			
			for (int i = 0; i < values.length; i++) {
				paramMap.put(paramName, values[i]);
			}

		}
		return paramMap;
	}
	
	public static Map getRequestParamForJS(HttpServletRequest request) {
		Map paramMap = new HashMap();
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paramName = (String) enu.nextElement();
			String[] values = request.getParameterValues(paramName);
			
			for (int i = 0; i < values.length; i++) {
				try {
					paramMap.put(paramName,  URLDecoder.decode(values[i], "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

		}
		return paramMap;
	}
	
	
}
