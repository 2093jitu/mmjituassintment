package com.dynamicform.app.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dynamicform.app.config.CustomHttpServletRequest;

/**
 * @author Mohammad Lockman
 *
 */
public interface AuthCommonFunctions {

	default HttpServletRequest getCurrentRequest() {

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
		HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		Assert.state(servletRequest != null, "Could not find current HttpServletRequest");

		return servletRequest;
	}

	default CustomHttpServletRequest userAgent() {

		return new CustomHttpServletRequest(getCurrentRequest());

	}

}
