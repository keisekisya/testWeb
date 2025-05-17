package jp.keisekisya.webapi.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.keisekisya.webapi.bean.GetMstCustomerBean;
import jp.keisekisya.webapi.dto.DeleteMstCustomerRequest;
import jp.keisekisya.webapi.dto.GetMstCustomerRequest;
import jp.keisekisya.webapi.dto.GetMstCustomerResponse;
import jp.keisekisya.webapi.dto.RegistMstCustomerRequest;
import jp.keisekisya.webapi.dto.UpdateMstCustomerRequest;
import jp.keisekisya.webapi.entity.MstCustomer;
import jp.keisekisya.webapi.entity.pk.MstCustomerPk;
import jp.keisekisya.webapi.handler.BusinessException;
import jp.keisekisya.webapi.repository.MstCustomerRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class MstCustomerService {
	private final MstCustomerRepository mstCustomerRepository;

	/**
	 * 登録処理
	 * 
	 * @param dto
	 */
	@Transactional
	public void regist(final RegistMstCustomerRequest dto) {
		val entity = new MstCustomer();
		entity.setPk(new MstCustomerPk(dto.getCompanyId(), UUID.randomUUID()));
		entity.setCustomerName(dto.getCustomerName());
		entity.setCustomerAddr(dto.getCustomerAddr());

		mstCustomerRepository.save(entity);
	}

	/**
	 * 更新処理
	 * 
	 * @param dto
	 */
	@Transactional
	public void update(final UpdateMstCustomerRequest dto) {
		val opt = mstCustomerRepository.findById(new MstCustomerPk(dto.getCompanyId(), dto.getCustomerId()));
		if (opt.isEmpty()) {
			throw new BusinessException("B00003", HttpStatus.NOT_FOUND);
		}
		val entity = opt.get();
		entity.setCustomerName(dto.getCustomerName());
		entity.setCustomerAddr(dto.getCustomerAddr());
		mstCustomerRepository.save(entity);
	}

	/**
	 * 削除処理
	 * 
	 * @param dto
	 */
	@Transactional
	public void delete(final DeleteMstCustomerRequest dto) {
		val pk = new MstCustomerPk(dto.getCompanyId(), dto.getCustomerId());
		if (!mstCustomerRepository.existsById(pk)) {
			throw new BusinessException("B00003", HttpStatus.NOT_FOUND);
		}
		mstCustomerRepository.deleteById(pk);
	}

	/**
	 * 取得処理
	 * 
	 * @param dto
	 * @return
	 */
	@Transactional(readOnly = true)
	public GetMstCustomerResponse getItemList(final GetMstCustomerRequest dto) {
		val entity = new MstCustomer();
		entity.setPk(new MstCustomerPk(dto.getCompanyId(), null));
		val items = mstCustomerRepository.findAll(Example.of(entity), Sort.by(Sort.Direction.ASC, "createdAt"));
		if (items.isEmpty()) {
			throw new BusinessException("B00003", HttpStatus.NOT_FOUND);
		}
		val itemList = items.stream().map(item -> {
			val bean = new GetMstCustomerBean();
			BeanUtils.copyProperties(item.getPk(), bean);
			BeanUtils.copyProperties(item, bean);
			return bean;
		}).toList();
		return new GetMstCustomerResponse(itemList);
	}
}
