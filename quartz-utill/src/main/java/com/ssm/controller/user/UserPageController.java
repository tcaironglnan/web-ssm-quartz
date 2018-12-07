package com.ssm.controller.user;

import com.ssm.model.UserModel;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 关于用户的所有相关的页面跳转操作
 *
 * @author FaceFeel
 * @Created 2018-05-05 23:27
 */
@Controller
@RequestMapping("/user/page")
public class UserPageController {

    @Autowired
    private UserService userService;


    /**
     * 跳转编辑用户信息界面
     * @param request htt
     * @param id 用户ID
     * @return ""
     */
    @RequestMapping("/updateUserPage")
    public String updateUserPage(HttpServletRequest request,Long id){

        UserModel userById = userService.findUserById(id);
        request.setAttribute("user",userById);
        return "admin/editUser";
    }

    /**
     * 跳转修改密码界面
     *
     * @return 页面名称
     */
    @RequestMapping("/updatePassWordPage")
    public String updatePassWordPage(HttpServletRequest request) {
        UserModel currentUser = (UserModel) request.getSession().getAttribute("currentUser");
        request.setAttribute("user", currentUser);
        return "root/updatePassWord";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {

        UserModel userModel = (UserModel) request.getSession().getAttribute("currentUser");
        if (userModel == null) {
            return "redirect:/login";
        }
        request.setAttribute("user", userModel);
        return "root/index";
    }
}
