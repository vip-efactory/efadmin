package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.EntityExistException;
import vip.efactory.exception.EntityNotFoundException;
import vip.efactory.modules.monitor.service.RedisService;
import vip.efactory.modules.system.entity.User;
import vip.efactory.modules.system.repository.UserRepository;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.UserDTO;
import vip.efactory.modules.system.service.dto.UserQueryCriteria;
import vip.efactory.modules.system.service.mapper.UserMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public UserDTO findUserDTOById(long id) {
        Optional<User> user = br.findById(id);
        ValidationUtil.isNull(user, "User", "id", id);
        return userMapper.toDto(user.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {

        if (br.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (br.findByUsercode(resources.getUsercode()) != null) {
            throw new EntityExistException(User.class, "usercode", resources.getUsercode());
        }

        if (br.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        // 默认密码 123456，此密码是加密后的字符
        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        resources.setAvatar("https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg");
        return userMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(User resources) {
        Optional<User> userOptional = br.findById(resources.getId());
        ValidationUtil.isNull(userOptional, "User", "id", resources.getId());

        User user = userOptional.get();

        User user1 = br.findByUsername(user.getUsername());
        User user2 = br.findByEmail(user.getEmail());

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJob(resources.getJob());
        user.setPhone(resources.getPhone());
        br.save(user);

        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }

    @Override
    public UserDTO findByName(String userName) {
        User user = null;
        if (ValidationUtil.isEmail(userName)) {
            user = br.findByEmail(userName);
        } else {
            user = br.findByUsername(userName);
        }

        // 如果还为null,再匹配工号字段
        if (null == user) {
            user = br.findByUsercode(userName);
        }

        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        br.updatePass(username, pass, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        br.updateAvatar(username, url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        br.updateEmail(username, email);
    }
}
