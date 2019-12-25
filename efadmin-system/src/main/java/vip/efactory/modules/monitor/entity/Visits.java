package vip.efactory.modules.monitor.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * pv 与 ip 统计
 */
@Entity
@Data
@Table(name = "sys_visits")
public class Visits extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Column(unique = true)
    private String date;

    @Column(name = "pv_counts")
    private Long pvCounts;

    @Column(name = "ip_counts")
    private Long ipCounts;

    @Column(name = "week_day")
    private String weekDay;
}
