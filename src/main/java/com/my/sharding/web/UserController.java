package com.my.sharding.web;

import com.my.sharding.entity.User;
import com.my.sharding.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jinguo_peng
 * @description TODO
 * @date 2020/3/17 下午2:21
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public List<User> getUserById(@RequestParam Integer id) {

        User user = new User();
        user.setId(id);
        return userService.getUser(user);
    }

    @GetMapping("/getUserByUsername")
    public List<User> getUserByUsername(@RequestParam String username) {

        User user = new User();
        user.setUsername(username);
        return userService.getUser(user);
    }

    @GetMapping("/getUser")
    public List<User> getUser() {

        User user = new User();
        return userService.getUser(user);
    }

    @GetMapping("/addUser")
    public String addUser(@RequestParam Integer index, @RequestParam Integer count) {

        for (; index < count; index ++) {

            User user = new User();
            user.setId(index);
            user.setUsername("admin" + index);
            user.setPassword("123");
            userService.addUser(user);
        }
        return "添加成功";
    }

    @GetMapping("testTransaction")
    public String testTransaction() {

        userService.testTransaction();
        return "测试分布式事务";
    }
}
