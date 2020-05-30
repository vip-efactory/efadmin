// 前端界面国际化需要的信息
export default {
    // 属性的国际化信息 --zh_CN,追加到前端项目的src/lang/zh_CN.js中
    ${changeClassName}: {
    TITLE: '${apiAlias}',
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    ${column.changeColumnName}: '${column.remark}',
    </#if>
    </#list>
</#if>
    // 属性非空的国际化信息
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    ${column.changeColumnName}Required: '${column.remark}不能为空',
    </#if>
    </#list>
</#if>
  },
    // 属性的国际化信息 --en_US,追加到前端项目的src/lang/en_US.js中
    ${changeClassName}: {
    TITLE: '${apiAlias}',
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    ${column.changeColumnName}: '${column.changeColumnName}',
    </#if>
    </#list>
</#if>
    // 属性非空的国际化信息
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
    ${column.changeColumnName}Required: '${column.changeColumnName} is reqiured!',
    </#if>
    </#list>
</#if>
    },
}
