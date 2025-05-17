package jp.keisekisya.webapi.repository;

import org.springframework.stereotype.Repository;

import jp.keisekisya.webapi.entity.MstCustomer;
import jp.keisekisya.webapi.entity.pk.MstCustomerPk;

@Repository
public interface MstCustomerRepository extends BaseJpaRepository<MstCustomer, MstCustomerPk> {
	// 例：List<MstCustomer> findByPkCompanyId(String companyId);
}
