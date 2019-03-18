package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.Cookieutil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by yyb on 3/17/19.
 */
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        String looginToken= Cookieutil.readLoginToken(httpServletRequest);
        if(StringUtils.isNotEmpty(looginToken)){
            String userJsonStr= RedisPoolUtil.get(looginToken);
            User user= JsonUtil.string2Obj(userJsonStr,User.class);
            if(user!=null){
                RedisPoolUtil.expire(looginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
