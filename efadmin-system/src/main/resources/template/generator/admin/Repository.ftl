package ${package}.repository;

import ${package}.entity.${className};
import vip.efactory.ejpa.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* ${tableRemark}
* @author ${author}
*/
public interface ${className}Repository extends BaseRepository<${className}, ${pkColumnType}>, JpaSpecificationExecutor {
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>

    /**
     * findBy${column.capitalColumnName}
     * @param ${column.columnName}
     * @return
     */
    ${className} findBy${column.capitalColumnName}(${column.columnType} ${column.columnName});
        </#if>
    </#list>
</#if>
}
