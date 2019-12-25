package ${package}.service.impl;

import ${package}.entity.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import vip.efactory.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import vip.efactory.utils.ValidationUtil;
import ${package}.repository.${className}Repository;
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapper.${className}Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;

/**
* ${tableRemark}
* @author ${author}
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, ${pkColumnType}, ${className}Repository> implements ${className}Service {

    @Autowired
    private ${className}Mapper ${changeClassName}Mapper;

    @Override
    public Object queryAll(${className}QueryCriteria criteria, Pageable pageable){
        Page<${className}> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(${changeClassName}Mapper::toDto));
    }

    @Override
    public Object queryAll(${className}QueryCriteria criteria){
        return ${changeClassName}Mapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ${className}DTO findById(${pkColumnType} ${pkChangeColName}) {
        Optional<${className}> ${changeClassName} = br.findById(${pkChangeColName});
        ValidationUtil.isNull(${changeClassName},"${className}","${pkChangeColName}",${pkChangeColName});
        return ${changeClassName}Mapper.toDto(${changeClassName}.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}DTO create(${className} resources) {
<#if !auto && pkColumnType = 'Long'>
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.set${pkCapitalColName}(snowflake.nextId());
</#if>
<#if !auto && pkColumnType = 'String'>
        resources.set${pkCapitalColName}(IdUtil.simpleUUID());
</#if>
<#if columns??>
    <#list columns as column>
    <#if column.columnKey = 'UNI'>
        if(br.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}()) != null){
            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
        }
    </#if>
    </#list>
</#if>
        return ${changeClassName}Mapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        Optional<${className}> optional${className} = br.findById(resources.get${pkCapitalColName}());
        ValidationUtil.isNull( optional${className},"${className}","id",resources.get${pkCapitalColName}());
        ${className} ${changeClassName} = optional${className}.get();
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
        <#if column_index = 1>
        ${className} ${changeClassName}1 = null;
        </#if>
        ${changeClassName}1 = br.findBy${column.capitalColumnName}(resources.get${column.capitalColumnName}());
        if(${changeClassName}1 != null && !${changeClassName}1.get${pkCapitalColName}().equals(${changeClassName}.get${pkCapitalColName}())){
            throw new EntityExistException(${className}.class,"${column.columnName}",resources.get${column.capitalColumnName}());
        }
        </#if>
    </#list>
</#if>
        ${changeClassName}.copy(resources);
        br.save(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(${pkColumnType} ${pkChangeColName}) {
        br.deleteById(${pkChangeColName});
    }
}
