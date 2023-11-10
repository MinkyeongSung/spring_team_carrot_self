package com.example.team_project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJPARepository extends JpaRepository<User, Integer> {

    @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query(value = "select u from User u where u.nickname = :nickname")
    User findByNickname(@Param("nickname") String nickname);

    @Query(value = "select u from User u where u.id = :id")
    User findByUserId(@Param("id") String id);

    @Modifying
    @Query("UPDATE User u SET u.userPicUrl = :userPicUrl, u.password = :password , u.nickname = :nickname WHERE u.id = :userId")
    void mUpdateUser(@Param("userId") Integer userId, @Param("userPicUrl") String userPicUrl,
            @Param("password") String password, @Param("nickname") String nickname);

}
