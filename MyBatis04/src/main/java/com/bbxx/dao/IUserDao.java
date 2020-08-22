package com.bbxx.dao;

import com.bbxx.pojo.UserVO;

import java.util.List;

public interface IUserDao {
    // 查询所有 一对多。
    List<UserVO> findAll();

}
