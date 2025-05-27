package jp.keisekisya.webapi.bean;

import jp.keisekisya.webapi.annotation.FixedField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonBean {
	@FixedField(order = 1, length = 10)
	private String name;

	@FixedField(order = 2, length = 3)
	private Integer age;

	@FixedField(order = 3, length = 6)
	private String gender;
}
