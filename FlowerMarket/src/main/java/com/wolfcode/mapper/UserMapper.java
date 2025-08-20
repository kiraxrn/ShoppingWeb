package com.wolfcode.mapper;



import com.wolfcode.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    User selectByUsernameAndPassword(@Param("username") String userid, @Param("password") String password);

    User findByUserid(@Param("userid") String userid);
    void insertUser(User user);
    User selectByUsername(String username);
    User findByEmail(String email);

    void update(User user);
}

