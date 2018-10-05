package com.bea.server;

import com.bea.server.entity.User;
import com.bea.server.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {

        Page<User> users = findlistUserwithPage(1,10);
        for(User user: users){
            System.out.println(user.toString());
        }

    }

    public Page<User> findlistUserwithPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        //return (Page) userMapper.findlistUser();
        return (Page) userMapper.selectList(null);
    }
}
