# This i18n for ${className}`s entity property: zh_CN, put follow append to {messages.properties,messages_zh_CN.properties}
# ${package}.domain.${className}
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
${className}.${column.changeColumnName}=${column.remark}
    </#if>
    </#list>
</#if>

# This i18n for ${className}`s entity property: en_US, put follow append to messages_en_US.properties
# ${package}.domain.${className}
<#if columns??>
    <#list columns as column>
    <#if column.columnName == "create_time" || column.columnName == "update_time" || column.columnName == "creator_num" || column.columnName == "updater_num" || column.columnName == "remark" >
    <#else>
${className}.${column.changeColumnName}=${column.changeColumnName}
    </#if>
    </#list>
</#if>

# This i18n for ${className}`s entity menu: zh_CN, put follow append to {messages_ui.properties,messages_ui_zh_CN.properties}
Menu.1.${changeClassName}.manage=${apiAlias}
Menu.2.${changeClassName}.add=${apiAlias}增加
Menu.2.${changeClassName}.edit=${apiAlias}编辑
Menu.2.${changeClassName}.delete=${apiAlias}删除

# This i18n for ${className}`s entity menu: en_US, put follow append to messages_ui_en_US.properties
Menu.1.${changeClassName}.manage=${className}
Menu.2.${changeClassName}.add=${className} Add
Menu.2.${changeClassName}.edit=${className} Edit
Menu.2.${changeClassName}.delete=${className} Delete