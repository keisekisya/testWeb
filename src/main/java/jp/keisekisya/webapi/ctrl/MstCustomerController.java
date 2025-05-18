package jp.keisekisya.webapi.ctrl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jp.keisekisya.webapi.dto.DeleteMstCustomerRequest;
import jp.keisekisya.webapi.dto.GetMstCustomerRequest;
import jp.keisekisya.webapi.dto.RegistMstCustomerRequest;
import jp.keisekisya.webapi.dto.ResponseDto;
import jp.keisekisya.webapi.dto.UpdateMstCustomerRequest;
import jp.keisekisya.webapi.service.MstCustomerService;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
public class MstCustomerController extends BaseController {
	private static final String PATH = "/customer";
	private static final String GET = PATH + "/get";

	private final MstCustomerService service;

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	@PostMapping(PATH)
	public void regist(@Valid @RequestBody RegistMstCustomerRequest dto) {
		service.regist(dto);
	}

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	@PutMapping(PATH)
	public void update(@Valid @RequestBody UpdateMstCustomerRequest dto) {
		service.update(dto);
	}

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	@DeleteMapping(PATH)
	public void delete(@Valid @RequestBody DeleteMstCustomerRequest dto) {
		service.delete(dto);
	}

	/**
	 * 取得
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping(GET)
	public ResponseDto getItem(@Valid @RequestBody GetMstCustomerRequest dto) {
		val username = getCurrentUsername();
		return service.getItemList(dto);
	}
}
