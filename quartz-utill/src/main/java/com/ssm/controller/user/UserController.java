package com.ssm.controller.user;

import com.ssm.model.UserModel;
import com.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:12
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //region 变量定义部分
    @Autowired
    private UserService userService;
    //endregion

    /**
     * 修改密码
     *
     * @param request http
     * @return json
     */
    @RequestMapping("/updatePassWord")
    @ResponseBody
    public String updatePassWord(HttpServletRequest request) {

        String passWord = request.getParameter("passWord");
        UserModel currentUser = (UserModel) request.getSession().getAttribute("currentUser");

        if (currentUser == null) {
            return "2";
        }

        boolean result = userService.updatePassWord(currentUser.getId(), passWord);
        if (result) {
            //修改成功
            return "1";
        } else {
            return "0";
        }
    }
}
