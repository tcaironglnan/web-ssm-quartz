package com.ssm.service;

import com.ssm.model.UserModel;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:44
 */
public interface UserService {

    /**
     * 更新用户头像
     * @param id 用户ID
     * @param headImage 用户头像存放路径
     * @return ""
     */
    boolean updateHeadImageById(Long id, String headImage);

    /**
     * 按照用户ID查找用户
     * @param id 用户ID
     * @return ""
     */
    UserModel findUserById(Long id);

    /**
     * 更新用户信息
     * @param userModel 用户实体
     * @return "
     */
    boolean updateUser(UserModel userModel);

    /**
     * 修改密码
     * @param id 用户ID
     * @param passWord 新密码
     * @return 布尔
     */
    boolean updatePassWord(Long id, String passWord);

    /**
     * 按照用户名查询用户
     * @param userName 用户名
     * @return ""
     */
    UserModel findUserByUserName(String userName);

    /**
     * 按照用户名和密码查询用户/用户登录方法
     * @param userName 用户名
     * @param passWord 密码
     * @return ""
     */
    UserModel findUserByUserNameAndPassWord(String userName, String passWord);
}
