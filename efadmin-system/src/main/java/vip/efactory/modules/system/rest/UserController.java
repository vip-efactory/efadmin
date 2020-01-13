package vip.efactory.modules.system.rest;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.config.DataScope;
import vip.efactory.domain.VerificationCode;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.valid.Update;
import vip.efactory.ejpa.utils.R;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.User;
import vip.efactory.modules.system.domain.vo.UserPassVo;
import vip.efactory.modules.system.service.DeptService;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.RoleSmallDto;
import vip.efactory.modules.system.service.dto.UserDto;
import vip.efactory.modules.system.service.dto.UserQueryCriteria;
import vip.efactory.service.VerificationCodeService;
import vip.efactory.utils.EfAdminConstant;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.SecurityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, UserService, Long> {

    @Value("${rsa.private_key}")
    private String privateKey;
    private final PasswordEncoder passwordEncoder;
    private final DataScope dataScope;
    private final DeptService deptService;
    private final RoleService roleService;
    private final VerificationCodeService verificationCodeService;

    public UserController(PasswordEncoder passwordEncoder, DataScope dataScope, DeptService deptService, RoleService roleService, VerificationCodeService verificationCodeService) {
        this.passwordEncoder = passwordEncoder;
        this.dataScope = dataScope;
        this.deptService = deptService;
        this.roleService = roleService;
        this.verificationCodeService = verificationCodeService;
    }

    @Log("导出用户数据")
    @ApiOperation("导出用户数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('user:list')")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询用户")
    @ApiOperation("查询用户")
    @GetMapping
    @PreAuthorize("@p.check('user:list')")
    public R getUsers(UserQueryCriteria criteria, Pageable pageable) {
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            deptSet.add(criteria.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
        }
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();
        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {
            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);
            // 若无交集，则代表无数据权限
            criteria.setDeptIds(result);
            if (result.size() == 0) {
                return R.ok(PageUtil.toPage(null, 0));
            } else {
                return R.ok(entityService.queryAll(criteria, pageable));
            }
            // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            criteria.setDeptIds(result);
            return R.ok(entityService.queryAll(criteria, pageable));
        }
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("@p.check('user:add')")
    public R create(@Validated @RequestBody User resources) {
        checkLevel(resources);
        // 默认密码 123456
        resources.setPassword(passwordEncoder.encode("123456"));
        return R.ok(entityService.create(resources));
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    @PreAuthorize("@p.check('user:edit')")
    public R update(@Validated(Update.class) @RequestBody User resources) {
        checkLevel(resources);
        entityService.update(resources);
        return R.ok();
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public R center(@Validated(Update.class) @RequestBody User resources) {
        UserDto userDto = entityService.findByName(SecurityUtils.getUsername());
        if (!resources.getId().equals(userDto.getId())) {
            throw new BadRequestException("不能修改他人资料");
        }
        entityService.updateCenter(resources);
        return R.ok();
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    @PreAuthorize("@p.check('user:del')")
    public R delete(@RequestBody Set<Long> ids) {
        UserDto user = entityService.findByName(SecurityUtils.getUsername());
        for (Long id : ids) {
            Integer currentLevel = Collections.min(roleService.findByUsersId(user.getId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel = Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw new BadRequestException("角色权限不足，不能删除：" + entityService.findByName(SecurityUtils.getUsername()).getUsername());
            }
        }
        entityService.delete(ids);
        return R.ok();
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public R updatePass(@RequestBody UserPassVo passVo) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String oldPass = new String(rsa.decrypt(passVo.getOldPass(), KeyType.PrivateKey));
        String newPass = new String(rsa.decrypt(passVo.getNewPass(), KeyType.PrivateKey));
        UserDto user = entityService.findByName(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        entityService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
        return R.ok();
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public R updateAvatar(@RequestParam MultipartFile file) {
        entityService.updateAvatar(file);
        return R.ok();
    }

    @Log("修改邮箱")
    @ApiOperation("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public R updateEmail(@PathVariable String code, @RequestBody User user) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(user.getPassword(), KeyType.PrivateKey));
        UserDto userDto = entityService.findByName(SecurityUtils.getUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, EfAdminConstant.RESET_MAIL, "email", user.getEmail());
        verificationCodeService.validated(verificationCode);
        entityService.updateEmail(userDto.getUsername(), user.getEmail());
        return R.ok();
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     *
     * @param resources /
     */
    private void checkLevel(User resources) {
        UserDto user = entityService.findByName(SecurityUtils.getUsername());
        Integer currentLevel = Collections.min(roleService.findByUsersId(user.getId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
