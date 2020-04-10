# This i18n for entity property: zh_CN, put follow append to messages_zh_CN.properties
# ${package}.domain.${className}
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
${className}.${column.changeColumnName}=${column.remark}
    </#if>
    </#list>
</#if>

# This i18n for entity property: en_US, put follow append to messages_en_US.properties
# ${package}.domain.${className}
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
${className}.${column.changeColumnName}=${column.changeColumnName}
    </#if>
    </#list>
</#if>