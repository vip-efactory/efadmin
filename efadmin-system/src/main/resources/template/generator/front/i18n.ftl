// 前端界面国际化需要的信息
export default {
    // 属性的国际化信息 --zh_CN,追加到前端项目的src/lang/zh_CN.js中
    ${changeClassName}: {
    TITLE: '${apiAlias}',
<#if columns??>
    <#list columns as column>
    <#-- #基础实体的属性和deleted的属性不生成! deleted属性将来会用作逻辑删除字段 -->
    <#if baseEntityFields?seq_contains(column.changeColumnName) || column.columnName == "deleted" >
    <#else>
    ${column.changeColumnName}: '${column.remark}',
    </#if>
    </#list>
</#if>
    // 属性非空的国际化信息
<#if columns??>
    <#list columns as column>
    <#if baseEntityFields?seq_contains(column.changeColumnName) || column.columnName == "deleted" >
    <#else>
    ${column.changeColumnName}Required: '${column.remark}不能为空'<#sep>,</#sep>
    </#if>
    </#list>
</#if>
  },
    // 属性的国际化信息 --en_US,追加到前端项目的src/lang/en_US.js中
    ${changeClassName}: {
    TITLE: '${className}',
<#if columns??>
    <#list columns as column>
    <#if baseEntityFields?seq_contains(column.changeColumnName) || column.columnName == "deleted" >
    <#else>
    ${column.changeColumnName}: '${column.changeColumnName}',
    </#if>
    </#list>
</#if>
    // 属性非空的国际化信息
<#if columns??>
    <#list columns as column>
    <#if baseEntityFields?seq_contains(column.changeColumnName) || column.columnName == "deleted" >
    <#else>
    ${column.changeColumnName}Required: '${column.changeColumnName} is reqiured!'<#sep>,</#sep>
    </#if>
    </#list>
</#if>
    },
}
