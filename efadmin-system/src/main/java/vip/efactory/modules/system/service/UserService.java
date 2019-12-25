package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.User;
import vip.efactory.modules.system.service.dto.UserDTO;
import vip.efactory.modules.system.service.dto.UserQueryCriteria;

@CacheConfig(cacheNames = "user")
public interface UserService extends IBaseService<User, Long> {

    /**
     * get
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserDTO findUserDTOById(long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserDTO create(User resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    User update(User resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * findByName
     *
     * @param userName
     * @return
     */
    @Cacheable(key = "'loadUserByUsername:'+#p0")
    UserDTO findByName(String userName);

    /**
     * 修改密码
     *
     * @param username
     * @param encryptPassword
     */
    @CacheEvict(allEntries = true)
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param username
     * @param url
     */
    @CacheEvict(allEntries = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     *
     * @param username
     * @param email
     */
    @CacheEvict(allEntries = true)
    void updateEmail(String username, String email);

    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(UserQueryCriteria criteria, Pageable pageable);
}
