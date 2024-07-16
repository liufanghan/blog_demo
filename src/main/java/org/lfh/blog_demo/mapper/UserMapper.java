package org.lfh.blog_demo.mapper;

import org.lfh.blog_demo.entity.User;
import vo.param.UserParam;


import java.util.List;

public interface UserMapper {

    int insertUser(User user);


    User findByEmail(String email);

    User findByUserId(Integer userId);
}
