package ${package}.repository;

import ${package}.domain.${className};
import vip.efactory.ejpa.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* ${apiAlias} 持久化层
* @author ${author}
* @date ${date}
*/
public interface ${className}Repository extends BaseRepository<${className}, ${pkColumnType}>, JpaSpecificationExecutor<${className}> {
    
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>

    /**
    * 根据 ${column.capitalColumnName} 查询
    * @param ${column.columnName} /
    * @return /
    */
    ${className} findBy${column.capitalColumnName}(${column.columnType} ${column.columnName});
        </#if>
    </#list>
</#if>
}