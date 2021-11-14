package ${package}.service.dto;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasLocalDateTime || hasLocalDate || hasLocalTime>
import org.springframework.format.annotation.DateTimeFormat;
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
import java.io.Serializable;
<#if !auto && pkColumnType = 'Long'>
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
</#if>


/**
* ${apiAlias} DTO
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}Dto extends BaseEntity<${pkColumnType}> implements Serializable {
<#if columns??>
    <#list columns as column>
    <#if column.columnKey = 'PRI'>
    <#if !auto && pkColumnType = 'Long'>
    /** 防止精度丢失 */
    @JsonSerialize(using= ToStringSerializer.class)
    </#if>
    </#if>
    <#if column.remark != ''>
    /**
    * ${column.remark}
    */
    </#if>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    // ${column.changeColumnName} extends from BaseEntity
    <#else>
        <#if column.columnType = 'LocalDateTime'>
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        </#if>
        <#if column.columnType = 'LocalDate'>
    @DateTimeFormat(pattern = "yyyy-MM-dd")
        </#if>
        <#if column.columnType = 'LocalTime'>
    @DateTimeFormat(pattern = "HH:mm:ss")
        </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    </#list>
</#if>
}
