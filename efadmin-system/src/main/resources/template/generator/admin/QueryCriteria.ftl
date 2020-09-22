package ${package}.service.dto;

import lombok.Data;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasLocalDateTime>
import java.time.LocalDateTime;
</#if>
<#if hasLocalDate>
import java.time.LocalDate;
</#if>
<#if hasLocalTime>
import java.time.LocalTime;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if betweens??>
import java.util.List;
</#if>
<#if queryColumns??>
import vip.efactory.annotation.Query;
</#if>

/**
* ${apiAlias} 查询条件
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}QueryCriteria{
<#if queryColumns??>
    <#list queryColumns as column>

<#if column.queryType = '='>
    /** 精确 */
    @Query
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = 'Like'>
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '!='>
    /** 不等于 */
    @Query(type = Query.Type.NOT_EQUAL)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = 'NotNull'>
    /** 不为空 */
    @Query(type = Query.Type.NOT_NULL)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '>='>
    /** 大于等于 */
    @Query(type = Query.Type.GREATER_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '<='>
    /** 小于等于 */
    @Query(type = Query.Type.LESS_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
    </#list>
</#if>
<#if betweens??>
    <#list betweens as column>

    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    <#if column.columnType = 'LocalDateTime'>
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if column.columnType = 'LocalDate'>
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    </#if>
    <#if column.columnType = 'LocalTime'>
    @DateTimeFormat(pattern = "HH:mm:ss")
    </#if>
    private List<${column.columnType}> ${column.changeColumnName};
    </#list>
</#if>
}
