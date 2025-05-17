package jp.keisekisya.webapi.repository;

import org.springframework.stereotype.Repository;

import jp.keisekisya.webapi.entity.MstLogin;
import jp.keisekisya.webapi.entity.pk.MstLoginPk;

@Repository
public interface MstLoginRepository extends BaseJpaRepository<MstLogin, MstLoginPk> {

}
