package jp.keisekisya.webapi.ctrl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.keisekisya.webapi.dto.RegistMstCustomerRequest;
import jp.keisekisya.webapi.service.MstCustomerService;
import lombok.val;

@WebMvcTest(MstCustomerController.class)
class MstCustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MstCustomerService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void regist_shouldCallServiceWithValidRequest() throws Exception {
		// テスト用のリクエストデータ作成（仮のフィールド名）
		val request = new RegistMstCustomerRequest();
		request.setCompanyId("01000");
		request.setCustomerName("あいうえお");

		// JSON に変換
		String json = objectMapper.writeValueAsString(request);

		// POST リクエスト実行
		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

		// サービスが呼ばれているか確認
		Mockito.verify(service, times(1)).regist(any(RegistMstCustomerRequest.class));
	}
}
