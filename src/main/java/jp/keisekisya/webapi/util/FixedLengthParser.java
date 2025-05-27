package jp.keisekisya.webapi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jp.keisekisya.webapi.annotation.FixedField;
import lombok.val;

/**
 * 固定長変換
 * 
 * @param <T>
 */
public class FixedLengthParser<T> {

	private final Class<T> clazz;
	private final List<FieldMapping> fieldMappings;

	/**
	 * コンストラクタ
	 * 
	 * @param clazz クラス
	 */
	public FixedLengthParser(Class<T> clazz) {
		this.clazz = clazz;
		this.fieldMappings = extractFieldMappings(clazz);
	}

	/**
	 * フィールド情報の取得
	 * 
	 * @param clazz
	 * @return
	 */
	private List<FieldMapping> extractFieldMappings(Class<T> clazz) {
		val mappings = new ArrayList<FieldMapping>();
		for (val field : clazz.getDeclaredFields()) {
			FixedField annotation = field.getAnnotation(FixedField.class);
			if (annotation != null) {
				field.setAccessible(true);
				mappings.add(new FieldMapping(field, annotation.order(), annotation.length()));
			}
		}
		return mappings.stream().sorted(Comparator.comparingInt(f -> f.order)) // 念のため位置順にソート
				.collect(Collectors.toList());
	}

	/**
	 * ファイルからリストに解析
	 * 
	 * @param file ファイル
	 * @return リスト
	 * @throws Exception
	 */
	public List<T> parse(File file) throws Exception {
		val result = new ArrayList<T>();
		try (val reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				result.add(parseLine(line));
			}
		}
		return result;
	}

	/**
	 * 読み込み行1行を解析
	 * 
	 * @param line 1行の文字列
	 * @return
	 * @throws Exception
	 */
	private T parseLine(String line) throws Exception {
		T instance = clazz.getDeclaredConstructor().newInstance();
		int currentPos = 0;
		for (val mapping : fieldMappings) {
			int end = Math.min(line.length(), currentPos + mapping.length);
			String value = line.substring(currentPos, end);
			Field field = mapping.field;
			Object castedValue = castValue(field.getType(), value);
			field.set(instance, castedValue);
			currentPos += mapping.length;
		}
		return instance;
	}

	private Object castValue(Class<?> type, String value) {
		String trimmed = value.trim();
		if (type == String.class)
			return trimmed;
		if (type == int.class || type == Integer.class)
			return Integer.parseInt(trimmed);
		if (type == long.class || type == Long.class)
			return Long.parseLong(trimmed);
		if (type == double.class || type == Double.class)
			return Double.parseDouble(trimmed);
		// 必要に応じて他の型も対応
		return null;
	}

	private static class FieldMapping {
		Field field;
		int order;
		int length;

		FieldMapping(Field field, int order, int length) {
			this.field = field;
			this.order = order;
			this.length = length;
		}
	}
}
