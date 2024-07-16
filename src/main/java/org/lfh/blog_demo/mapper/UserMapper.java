package org.lfh.blog_demo.mapper;

import org.lfh.blog_demo.entity.User;

public interface UserMapper {

    int insertUser(User user);

    User findByEmail(String email);

    User findByUserId(Integer userId);
}
