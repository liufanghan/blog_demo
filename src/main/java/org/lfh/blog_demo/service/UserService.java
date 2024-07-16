package org.lfh.blog_demo.service;

import org.lfh.blog_demo.util.Result;
import vo.param.UserParam;

public interface UserService {
    Result register(UserParam userParam);

    Result login(UserParam userParam);

    Result me();
}
