package com.ssm.dao;

import com.ssm.model.UserModel;
import org.apache.ibatis.annotations.Param;

/**
 * @author FaceFeel
 * @Created 2018-01-28 19:50
 */

public interface UserMapper {

    /**
     * 更新用户头像
     * @param id 用户ID
     * @param headImage 用户头像存放路径
     * @return ""
     */
    boolean updateHeadImageById(@Param("id") Long id,@Param("headImage")String headImage);

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
     * 按照用户名查询用户
     * @param userName 用户名
     * @return ""
     */
    UserModel findUserByUserName(String userName);

    /**
     * 传入多参数需要这么处理,否则会报错
     * @param userName 用户名
     * @param passWord 密码
     * @return ""
     */
    UserModel findUserByUserNameAndPassWord(@Param("userName") String userName, @Param("passWord") String passWord);
}
