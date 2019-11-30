package com.example.microservicegatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Kyle Wong
 * @Date: 2019/11/28 15:50
 * @Version 1.0
 */
@Component
public class TokenFilter extends ZuulFilter {
    /**
     * 拦截类型
     * 1.pre 访问微服务前拦截
     * 2.router 路由前拦截
     * 3.post 给用户响应时拦截
     * 4.error 发生错误时拦截
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 拦截次序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 代表该拦截器是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 拦截后应处理的业务
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // RequestContext请求的上下文，包含所有的请求参数，默认和线程绑定
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 从请求头中获取token
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            requestContext.setResponseBody("token is null");
            requestContext.setResponseStatusCode(401);
            requestContext.setSendZuulResponse(false);
            return null;
        }
        if (!"123456".equals(token)) {
            requestContext.setResponseBody("token is error");
            requestContext.setResponseStatusCode(401);
            requestContext.setSendZuulResponse(false);
            return null;
        }
        requestContext.setSendZuulResponse(true);
        return null;
    }
}
