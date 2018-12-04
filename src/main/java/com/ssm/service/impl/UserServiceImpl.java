package com.ssm.service.impl;

import com.ssm.dao.user.UserMapper;
import com.ssm.model.UserModel;
import com.ssm.service.UserService;
import com.ssm.utils.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:48
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新用户头像
     *
     * @param id        用户ID
     * @param headImage 用户头像存放路径
     * @return ""
     */
    @Override
    public boolean updateHeadImageById(Long id, String headImage) {

        if (id == null || headImage.length() < 1) {
            return false;
        }

        try {
            userMapper.updateHeadImageById(id, headImage);
            return true;
        } catch (Exception e) {
            logger.error("更新用户头像失败,失败信息是:", e);
            return false;
        }
    }

    /**
     * 按照用户ID查找用户
     *
     * @param id 用户ID
     * @return ""
     */
    @Override
    public UserModel findUserById(Long id) {

        if (id == null) {
            return null;
        }

        try {
            return userMapper.findUserById(id);
        } catch (Exception e) {
            logger.error("查询用户失败,失败的信息是:", e);
            return null;
        }
    }

    /**
     * 更新用户信息
     *
     * @param userModel 用户实体
     * @return "
     */
    @Override
    public boolean updateUser(UserModel userModel) {

        if (userModel == null) {
            return false;
        }

        try {
            userMapper.updateUser(userModel);
            return true;
        } catch (Exception e) {
            logger.error("更新用户信息失败,失败的信息是:", e);
            return false;
        }
    }

    /**
     * 修改用户密码
     *
     * @param id       用户ID
     * @param passWord 新密码
     * @return ""
     */
    @Override
    public boolean updatePassWord(Long id, String passWord) {

        if (id == null || Str.isBlank(passWord)) {
            logger.warn("用户ID或新密码为空");
            return false;
        }

        try {
            return true;
        } catch (Exception e) {
            logger.error("更新密码失败,失败信息是:", e);
            return false;
        }
    }

    /**
     * 按照用户名查询用户
     *
     * @param userName 用户名
     * @return ""
     */
    @Override
    public UserModel findUserByUserName(String userName) {

        if (Str.isBlank(userName)) {
            logger.warn("输入的用户名为空");
            return null;
        }

        try {
            return userMapper.findUserByUserName(userName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按照用户名和密码查询用户/用户登录方法
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return ""
     */
    @Override
    public UserModel findUserByUserNameAndPassWord(String userName, String passWord) {

        if (Str.isBlank(userName) || Str.isBlank(passWord)) {
            logger.warn("传入的帐号或密码为空");
            return null;
        }

        try {
            return userMapper.findUserByUserNameAndPassWord(userName, passWord);
        } catch (Exception e) {
            logger.error("查询出错,错误信息是:", e);
            return null;
        }
    }
}
