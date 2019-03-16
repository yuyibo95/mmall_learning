package com.mmall.util;

import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yyb on 3/15/19.
 */
@Slf4j
public class Cookieutil {

    private final static String COOKIE_DOMAIN=".himooc.com";
    private final static String COOKIE_NAME="mmall_login_token";


    public static String readLoginToken(HttpServletRequest request){
       Cookie[] cks= request.getCookies();
        if(cks!=null){
            for(Cookie ck:cks){
                log.info("read cookiename:{}, cookievalue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookiename:{},cookievalue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }


    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck=new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setMaxAge(60*60*24*365);
        log.info("write cookiename:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cks=request.getCookies();
        if(cks!=null){
            for(Cookie ck:cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);
                    log.info("del cookiename:{},cookievale:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;

                }
            }
        }

    }

}
