package vip.efactory.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * sm.ms图床
 */
@Data
@Entity
@Table(name = "sys_picture")
public class Picture extends BaseEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @Column(name = "delete_url")
    private String delete;

    private String username;

    @Override
    public String toString() {
        return "Picture{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
