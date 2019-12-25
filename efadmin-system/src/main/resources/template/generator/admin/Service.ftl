package ${package}.service;

import ${package}.entity.${className};
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
import vip.efactory.ejpa.base.service.IBaseService;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* ${tableRemark}
* @author ${author}
*/
//@CacheConfig(cacheNames = "${changeClassName}")
public interface ${className}Service extend IBaseService<${className}, ${pkColumnType}>{

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(${className}QueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(${className}QueryCriteria criteria);

    /**
     * findById
     * @param ${pkChangeColName}
     * @return
     */
    //@Cacheable(key = "#p0")
    ${className}DTO findDTOById(${pkColumnType} ${pkChangeColName});

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    ${className}DTO create(${className} resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    ${className} update(${className} resources);

    /**
     * delete
     * @param ${pkChangeColName}
     */
    //@CacheEvict(allEntries = true)
    void delete(${pkColumnType} ${pkChangeColName});
}
