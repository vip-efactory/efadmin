package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;
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
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)
    </#if>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    @Column(name = "${column.columnName}"<#if column.columnKey = 'UNI'>,unique = true</#if><#if column.istNotNull && column.columnKey != 'PRI'>,nullable = false</#if>)
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank(message = "{${className}.${column.changeColumnName}}{property.not.allow.empty}")
        <#else>
    @NotNull(message = "{${className}.${column.changeColumnName}}{property.not.allow.empty}")
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
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
