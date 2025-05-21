package jp.keisekisya.webapi.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	ACTIVE(1), INACTIVE(2), DELETED(99);

	private final int code;

	Status(int code) {
		this.code = code;
	}

	@JsonValue
	public int getCode() {
		return code;
	}

	@JsonCreator
	public static Status fromCode(int code) {
		for (Status s : values()) {
			if (s.code == code)
				return s;
		}
		throw new IllegalArgumentException("Invalid Status code: " + code);
	}
}
