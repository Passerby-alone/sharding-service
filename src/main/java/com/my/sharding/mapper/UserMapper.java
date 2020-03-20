package com.my.sharding.mapper;

import com.my.sharding.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jinguo_peng
 * @description TODO
 * @date 2020/3/17 下午2:18
 */
public interface UserMapper {

    Integer addUser(User user);

    List<User> list();

    List<User> getUser(User user);

    User selectUserById(@Param("id") Integer id);

    void updateUser(User user);
}
