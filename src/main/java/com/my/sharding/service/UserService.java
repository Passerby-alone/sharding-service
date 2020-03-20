package com.my.sharding.service;

import com.my.sharding.entity.User;
import com.my.sharding.mapper.UserMapper;
import io.shardingsphere.core.constant.transaction.TransactionType;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @author jinguo_peng
 * @description TODO
 * @date 2020/3/17 下午2:23
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    public void testTransaction() {
        // step1: 先查询库1中的user 然后进行更新操作
        // step2: 在查询库2中的user 然后进行更新操作，更新失败，看两个库的数据会不会进行回滚

        try {
            // step1
            User user1 = userMapper.selectUserById(1);
            user1.setUsername("测试事务1");
            userMapper.updateUser(user1);
            // step2
            User user0 = userMapper.selectUserById(2);
            user0.setUsername("测试事务2");
            int result = 1/0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("报错，事务回滚");
        }
    }

    public List<User> getUser(User user) {
        return userMapper.getUser(user);
    }
}
