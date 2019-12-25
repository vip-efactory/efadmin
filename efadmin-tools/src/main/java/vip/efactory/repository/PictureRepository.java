package vip.efactory.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.entity.Picture;

public interface PictureRepository extends BaseRepository<Picture, Long>, JpaSpecificationExecutor {
}
