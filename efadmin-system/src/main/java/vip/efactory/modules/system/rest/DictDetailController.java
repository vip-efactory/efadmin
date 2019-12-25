package vip.efactory.modules.system.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.service.DictDetailService;
import vip.efactory.modules.system.service.dto.DictDetailQueryCriteria;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class DictDetailController extends BaseController<DictDetail, DictDetailService> {

    private static final String ENTITY_NAME = "dictDetail";

    @Log("查询字典详情")
    @GetMapping(value = "/dictDetail")
    public ResponseEntity getDictDetails(DictDetailQueryCriteria criteria,
                                         @PageableDefault(value = 10, sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        String[] names = criteria.getDictName().split(",");
        return new ResponseEntity(entityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("查询多个字典详情")
    @GetMapping(value = "/dictDetail/map")
    public ResponseEntity getDictDetailMaps(DictDetailQueryCriteria criteria,
                                            @PageableDefault(value = 10, sort = {"sort"}, direction = Sort.Direction.ASC) Pageable pageable) {
        String[] names = criteria.getDictName().split(",");
        Map map = new HashMap(names.length);
        for (String name : names) {
            criteria.setDictName(name);
            map.put(name, entityService.queryAll(criteria, pageable).get("content"));
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Log("新增字典详情")
    @PostMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(entityService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改字典详情")
    @PutMapping(value = "/dictDetail")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_EDIT')")
    public ResponseEntity update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
        entityService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除字典详情")
    @DeleteMapping(value = "/dictDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DICT_ALL','DICT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        entityService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
