package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.User;

import java.util.Date;

public interface UserRepository extends BaseRepository<User, Long>, JpaSpecificationExecutor {

    /**
     * findByUsername
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * findByEmail
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 利用工号获取
     * @param usercode 用户编号,或工号
     * @return
     */
    User findByUsercode(String usercode);

    /**
     * 修改密码
     *
     * @param username
     * @param pass
     */
    @Modifying
    @Query(value = "update sys_user set password = ?2 , last_password_reset_time = ?3 where username = ?1", nativeQuery = true)
    void updatePass(String username, String pass, Date lastPasswordResetTime);

    /**
     * 修改头像
     *
     * @param username
     * @param url
     */
    @Modifying
    @Query(value = "update sys_user set avatar = ?2 where username = ?1", nativeQuery = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     *
     * @param username
     * @param email
     */
    @Modifying
    @Query(value = "update sys_user set email = ?2 where username = ?1", nativeQuery = true)
    void updateEmail(String username, String email);
}
