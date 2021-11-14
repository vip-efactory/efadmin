package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.common.base.valid.Update;
import javax.persistence.*;
<#if isNotNullColumns??>
import javax.validation.constraints.*;
</#if>
<#if hasDateAnnotation>
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
</#if>
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

/**
* ${apiAlias} 实体
* @author ${author}
* @date ${date}
*/
@Entity
@Data
@Table(name="${tableName}")
@ApiModel(value = "${apiAlias}实体", description = "${apiAlias}实体管理")
public class ${className} extends BaseEntity<${pkColumnType}> {
    private static final long serialVersionUID = 1L;
<#if columns??>
    <#list columns as column>

    <#if column.remark != ''>
    /** ${column.remark} */
    </#if>
    <#if column.columnKey = 'PRI'>
    @Id
    <#if auto>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    @NotNull(groups = Update.class)
    </#if>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    @Column(name = "${column.columnName}"<#if column.columnKey = 'UNI'>,unique = true</#if><#if column.istNotNull && column.columnKey != 'PRI'>,nullable = false</#if>)
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    </#if>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    // ${column.changeColumnName} extends from BaseEntity
    <#else>
    <#if column.dateAnnotation??>
    <#if column.dateAnnotation = 'CreationTimestamp'>
    @CreationTimestamp
    <#else>
    @UpdateTimestamp
    </#if>
    </#if>
    @ApiModelProperty(value = "${column.remark}", dataType = "${column.columnType}"<#if column.istNotNull && column.columnKey != 'PRI'>, required = true</#if>)
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

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
