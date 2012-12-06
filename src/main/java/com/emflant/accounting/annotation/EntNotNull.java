package com.emflant.accounting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * DTO 클래스의 필드에 정의하는 annotation.
 * 필드의 값이 null 또는 "" 같이 길이가 0인 문자가 불가한 필드에 정의한다.
 * @author home
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntNotNull {
	
}
