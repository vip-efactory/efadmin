package vip.efactory.modules.system.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.mnt.websocket.MsgType;
import vip.efactory.modules.mnt.websocket.SocketMsg;
import vip.efactory.modules.mnt.websocket.WebSocketServer;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.EmployeeService;

import java.util.Set;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Slf4j
@Tag(name = "Employee管理")
@RestController
@RequestMapping("api/employee")
@SuppressWarnings("rawtypes")
public class EmployeeController extends BaseController<Employee, EmployeeService, Long> {

    /**
     * 分页查询
     */
    @Override
    @Log("分页查询Employee")
    @Operation(summary = "获取分页数据", description = "默认每页25条记录,id字段降序")
    @GetMapping("/page")
    @PreAuthorize("@p.check('employee:list')")
    public R getByPage(@PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        try {
            return super.getByPage(page);
        } catch (Exception e) {
            log.error("分页查询Employee失败", e);
            return R.error("分页查询失败：" + e.getMessage());
        }
    }

    /**
     * 高级查询
     */
    @Log("分页高级查询Employee")
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('employee:list')")
    public R advancedQuery(@RequestBody Employee entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        try {
            return super.advancedQueryByPage(page, entity);
        } catch (Exception e) {
            log.error("高级查询Employee失败", e);
            return R.error("高级查询失败：" + e.getMessage());
        }
    }

    /**
     * 多字段模糊查询
     */
    @Log("多字段模糊查询Employee")
    @Operation(summary = "多字段模糊查询,例如:q=abc&fields=name,address,desc", description = "多个字段模糊匹配")
    @GetMapping("/fuzzy")
    @PreAuthorize("@p.check('employee:list')")
    public R getByPage(
            @Parameter(name = "q", description = "模糊查询的值", required = true) @RequestParam String q,
            @Parameter(name = "fields", description = "要查询的字段", required = true) @RequestParam String fields,
            @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        try {
            return super.queryMultiField(q, fields, page);
        } catch (Exception e) {
            log.error("模糊查询Employee失败", e);
            return R.error("模糊查询失败：" + e.getMessage());
        }
    }

    /**
     * 单个查询
     */
    @Override
    @Log("使用Id查询Employee")
    @GetMapping("/{id}")
    @Operation(summary = "依据Id来获取对应的记录", description = "依据Id来获取对应的记录")
    @PreAuthorize("@p.check('employee:list')")
    public R getById(@Parameter(name = "id", description = "主键", required = true) @PathVariable("id") Long id) {
        try {
            return super.getById(id);
        } catch (Exception e) {
            log.error("查询单个Employee失败,ID:{}", id, e);
            return R.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 新增
     */
    @Override
    @Log("新增Employee")
    @PostMapping(value = {"/", ""})
    @Operation(summary = "新增Employee", description = "新增Employee实体")
    @PreAuthorize("@p.check('employee:add')")
    public R save(@Parameter(name = "entity", description = "Json格式", required = true) @RequestBody Employee entity) {
        try {
            // 直接调用Service的create方法，跳过父类校验
            entityService.create(entity);
            log.info("新增Employee成功, 当前用户:{}, 员工编码:{}", getCurrentUsername(), entity.getCode());
            return R.ok("新增成功");
        } catch (EntityExistException e) {
            log.error("新增Employee失败, 重复数据:{}", e.getMessage());
            return R.error("新增失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("新增Employee失败", e);
            return R.error("新增失败：" + e.getMessage());
        }
    }

    /**
     * 修改
     */
    @Override
    @Log("修改Employee")
    @PreAuthorize("@p.check('employee:edit')")
    @PutMapping(value = {"/", ""})
    @Operation(summary = "依据Id来更新Employee对应的记录", description = "依据Id来更新对应的记录,属性值为空则不更新数据表中已有的数据")
    public R updateById(@Parameter(name = "entity", description = "Json格式", required = true) @RequestBody Employee entity) {
        try {
            if (entity.getId() == null) {
                return R.error("更新失败：ID不能为空");
            }
            // 直接调用Service的update方法，跳过父类校验
            entityService.update(entity);
            log.info("修改Employee成功, 当前用户:{}, 员工ID:{}", getCurrentUsername(), entity.getId());
            return R.ok("修改成功");
        } catch (EntityExistException e) {
            log.error("修改Employee失败, 重复数据:{}", e.getMessage());
            return R.error("修改失败：" + e.getMessage());
        } catch (Exception e) {
            log.error("修改Employee失败, ID:{}", entity.getId(), e);
            return R.error("修改失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除
     */
    @Override
    @Log("使用Ids删除Employee")
    @PreAuthorize("@p.check('employee:del')")
    @DeleteMapping(value = {"/", ""})
    @Operation(summary = "依据Ids来删除Employee对应的记录", description = "依据Ids来删除Employee对应的记录")
    public R deleteByIds(@RequestBody Set<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return R.error("删除失败：ID集合不能为空");
            }
            // 循环调用单个删除，复用已有逻辑
            for (Long id : ids) {
                entityService.delete(id);
            }
            log.info("批量删除Employee成功, 当前用户:{}, 员工IDs:{}", getCurrentUsername(), ids);
            return R.ok("删除成功");
        } catch (Exception e) {
            log.error("批量删除Employee失败, IDs:{}", ids, e);
            return R.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 测试WebSocket
     */
    @GetMapping("/deploy")
    @SneakyThrows
    public void testSocket() {
        SocketMsg socketMsg = new SocketMsg("测试Socket发送信息！！！", MsgType.INFO);
        WebSocketServer.sendInfo(socketMsg, "deploy");
    }

    /**
     * 获取当前登录用户名
     */
    private String getCurrentUsername() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            return "未登录用户";
        }
    }
}