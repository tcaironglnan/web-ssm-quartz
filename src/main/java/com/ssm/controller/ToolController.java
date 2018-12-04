package com.ssm.controller;

import com.ssm.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author FaceFeel
 * @Created 2018-05-05 23:02
 */
public class ToolController {

    /**
     * 获取当前用户
     * @param request http
     * @return "
     */
    public static UserModel getCurrentUser(HttpServletRequest request){

        UserModel currentUser = (UserModel) request.getSession().getAttribute("currentUser");
        if (currentUser == null){
            return null;
        }
        return currentUser;
    }

    /**
     * 登录成功后,把当前登录的用户数据放入会话中
     * @param request http
     * @param userModel 用户实体
     * @return ""
     */
    public static boolean saveCurrentUser(HttpServletRequest request,UserModel userModel){
        request.getSession().setAttribute("currentUser",userModel);
        return true;
    }
}
