package ${package}.service.impl;

import ${package}.domain.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import vip.efactory.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import vip.efactory.utils.FileUtil;
import ${package}.repository.${className}Repository;
import ${package}.service.I${className}Service;
import ${package}.service.dto.${className}Dto;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapper.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
<#if !auto && pkColumnType = 'Long'>
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
</#if>
<#if !auto && pkColumnType = 'String'>
import cn.hutool.core.util.IdUtil;
</#if>
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vip.efactory.utils.QueryHelp;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import lombok.AllArgsConstructor;

/**
* ${apiAlias} 服务层实现
* @author ${author}
* @date ${date}
*/
@Service
@CacheConfig(cacheNames = "${changeClassName}")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, ${pkColumnType}, ${className}Repository> implements I${className}Service {

    private final ${className}Mapper ${changeClassName}Mapper;

    @Override
    @Cacheable
    public Page<${className}Dto> queryAll(${className}QueryCriteria criteria, Pageable pageable){
        Page<${className}> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return page.map(${changeClassName}Mapper::toDto);
    }

    @Override
    @Cacheable
    public List<${className}Dto> queryAll(${className}QueryCriteria criteria){
        return ${changeClassName}Mapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }
//
//    @Override
//    //@Cacheable(key = "#p0")
//    public ${className}Dto findDtoById(${pkColumnType} ${pkChangeColName}) {
//        ${className} ${changeClassName} = br.findById(${pkChangeColName}).orElseGet(${className}::new);
//        ValidationUtil.isNull(${changeClassName}.get${pkCapitalColName}(),"${className}","${pkChangeColName}",${pkChangeColName});
//        return ${changeClassName}Mapper.toDto(${changeClassName});
//    }
//
//    @Override
//    //@CacheEvict(allEntries = true)
//    @Transactional(rollbackFor = Exception.class)
//    public ${className}Dto create(${className} resources) {
//<#if !auto && pkColumnType = 'Long'>
//        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
//        resources.set${pkCapitalColName}(snowflake.nextId());
//</#if>
//<#if !auto && pkColumnType = 'String'>
//        resources.set${pkCapitalColName}(IdUtil.simpleUUID());
//</#if>
//<#if columns??>
//    <#list columns as column>
//    <#if column.columnKey = 'UNI'>
//        if(br.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
//           throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
//        }
//    </#if>
//    </#list>
//</#if>
//        return ${changeClassName}Mapper.toDto(br.save(resources));
//    }
//
//    @Override
//    //@CacheEvict(allEntries = true)
//    @Transactional(rollbackFor = Exception.class)
//    public void edit(${className} resources) {
//        ${className} ${changeClassName} = br.findById(resources.get${pkCapitalColName}()).orElseGet(${className}::new);
//        ValidationUtil.isNull( ${changeClassName}.get${pkCapitalColName}(),"${className}","id",resources.get${pkCapitalColName}());
//<#if columns??>
//    <#list columns as column>
//        <#if column.columnKey = 'UNI'>
//        <#if column_index = 1>
//        ${className} ${changeClassName}1 = null;
//        </#if>
//        ${changeClassName}1 = br.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
//        if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
//            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
//        }
//        </#if>
//    </#list>
//</#if>
//        ${changeClassName}.copy(resources);
//        br.save(${changeClassName});
//    }


    @Override
    @CacheEvict(allEntries = true)
    public ${className} update(${className} ${changeClassName}) {
        return super.update(${changeClassName});
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public void deleteById(${pkColumnType} ${pkChangeColName}) {
        super.deleteById(${pkChangeColName});
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public int deleteAllById(Iterable<${pkColumnType}> ${pkChangeColName}s) {
        return super.deleteAllById(${pkChangeColName}s);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(${pkColumnType}[] ${pkChangeColName}s) {
        for (${pkColumnType} ${pkChangeColName} : ${pkChangeColName}s) {
            br.deleteById(${pkChangeColName});
        }
    }

    @Override
    public void download(List<${className}Dto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className}Dto ${changeClassName} : all) {
            Map<String, Object> map = new LinkedHashMap<>();
        <#list columns as column>
            <#if column.columnKey != 'PRI'>
            <#if column.remark != ''>
            <#if column.columnType = 'LocalDateTime' || column.columnType = 'LocalDate' || column.columnType = 'LocalTime' >
            // Excel导出不支持Java8的日期类型,故将值保存为字符串
            map.put("${column.remark}",  ${changeClassName}.get${column.capitalColumnName}().toString());
            <#else>
            map.put("${column.remark}", ${changeClassName}.get${column.capitalColumnName}());
            </#if>
            <#else>
            <#if column.columnType = 'LocalDateTime' || column.columnType = 'LocalDate' || column.columnType = 'LocalTime' >
            // Excel导出不支持Java8的日期类型,故将值保存为字符串
            map.put("${column.changeColumnName}",  ${changeClassName}.get${column.capitalColumnName}().toString());
            <#else>
            map.put("${column.changeColumnName}",  ${changeClassName}.get${column.capitalColumnName}());
            </#if>
            </#if>
            </#if>
        </#list>
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
