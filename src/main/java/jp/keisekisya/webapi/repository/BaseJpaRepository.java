package jp.keisekisya.webapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.LockModeType;

@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID> {

	/**
	 * プライマリキーで取得 （行ロック)
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Override
	Optional<T> findById(ID id);

	/**
	 * プライマリキーで取得 （行ロックしない)
	 * 
	 * @param id
	 * @return
	 */
	Optional<T> findByPk(ID id);
}
