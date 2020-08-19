
package com.bbxx.dao;

import com.bbxx.pojo.UserVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IUserDao {
    @Select("select * from users")
    List<UserVO> findAll();



}
