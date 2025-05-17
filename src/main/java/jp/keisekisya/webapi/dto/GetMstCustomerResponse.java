package jp.keisekisya.webapi.dto;

import java.util.List;

import jp.keisekisya.webapi.bean.GetMstCustomerBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMstCustomerResponse implements ResponseDto {
	private List<GetMstCustomerBean> itemList;
}
