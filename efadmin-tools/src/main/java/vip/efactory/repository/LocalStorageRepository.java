package vip.efactory.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.base.repository.BaseRepository;

public interface LocalStorageRepository extends BaseRepository<LocalStorage, Long>, JpaSpecificationExecutor<LocalStorage> {
}
