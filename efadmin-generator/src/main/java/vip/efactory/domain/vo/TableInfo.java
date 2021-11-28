package vip.efactory.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表的数据信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {

    /**
     * 表名称
     **/
    private Object tableName;

    /**
     * 创建日期
     **/
    private Object createTime;

    /**
     * 数据库引擎
     */
    private Object engine;

    /**
     * 编码集
     */
    private Object coding;

    /**
     * 备注
     */
    private Object remark;


}
