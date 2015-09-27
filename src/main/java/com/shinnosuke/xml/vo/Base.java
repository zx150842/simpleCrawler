package com.shinnosuke.xml.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Field;

public class Base {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getString(Object object) {
		StringBuilder sb = new StringBuilder("[");
		Field[] fields = object.getClass().getDeclaredFields();
		boolean isFirst = true;
		for (Field field : fields) {
			field.setAccessible(true);
			if (!isFirst) {
				sb.append(" ,");
			} else {
				isFirst = false;
			}
			sb.append(field.getName()).append("=");
			try {
				if (field.get(object) instanceof Date) {
					sb.append(sdf.format(field.get(object)));
				} else {
					sb.append(field.get(object));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
