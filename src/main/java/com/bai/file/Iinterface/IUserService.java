package com.bai.file.Iinterface;

import com.bai.file.domain.user.Role;
import com.bai.file.domain.user.UserBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiwenshare.common.result.RestResult;

import java.util.List;

public interface IUserService extends IService<UserBean> {


    String getUserIdByToken(String token);


    /**
     * 用户注册
     *
     * @param userBean 用户信息
     * @return 结果
     */
    RestResult<String> registerUser(UserBean userBean);



    UserBean findUserInfoByTelephone(String telephone);
    List<Role> selectRoleListByUserId(String userId);
    String getSaltByTelephone(String telephone);
    UserBean selectUserByTelephoneAndPassword(String username, String password);







}
