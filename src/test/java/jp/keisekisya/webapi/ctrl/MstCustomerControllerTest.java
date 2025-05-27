package jp.keisekisya.webapi.ctrl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.keisekisya.webapi.bean.PersonBean;
import jp.keisekisya.webapi.dto.RegistMstCustomerRequest;
import jp.keisekisya.webapi.security.JwtUtil;
import jp.keisekisya.webapi.service.MstCustomerService;
import jp.keisekisya.webapi.util.FixedLengthParser;
import lombok.val;

@SpringBootTest
@AutoConfigureMockMvc
class MstCustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;

	@MockitoBean
	private MstCustomerService service;

	@Autowired
	private ObjectMapper objectMapper;

	private String getToken(String userName) {
		return jwtUtil.generateToken(userName);
	}

	@Test
	void regist_shouldCallServiceWithValidRequest() throws Exception {
		// テスト用のリクエストデータ作成（仮のフィールド名）
		val request = RegistMstCustomerRequest.builder().companyId("01000").customerName("あいうえお").build();

		// JSON に変換
		String json = objectMapper.writeValueAsString(request);

		String token = getToken("testUser");

		service.regist(null);

		Mockito.doNothing().when(service).regist(Mockito.any(RegistMstCustomerRequest.class));

		// POST リクエスト実行
		mockMvc.perform(post("/customer").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());

		// サービスが呼ばれているか確認
		Mockito.verify(service, times(1)).regist(any(RegistMstCustomerRequest.class));
	}

	@Test
	void test01() throws Exception {
		File file = new File("C:\\temp\\data.txt"); // 固定長ファイル
		val parser = new FixedLengthParser<>(PersonBean.class);
		val list = parser.parse(file);

		for (val person : list) {
			System.out.println(
					"Name: " + person.getName() + ", Age: " + person.getAge() + ", gender: " + person.getGender());
		}
	}
}
