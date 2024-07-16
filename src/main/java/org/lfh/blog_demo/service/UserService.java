package org.lfh.blog_demo.service;

import org.lfh.blog_demo.util.Result;
import org.lfh.blog_demo.vo.param.UserParam;

public interface UserService {
    Result register(UserParam userParam);

    Result login(UserParam userParam);

    Result me();
}
