// 前端界面国际化需要的信息
export default {
  ${className}: {
    // 属性的国际化信息
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
}  