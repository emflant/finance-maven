package com.emflant.accounting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * DTO Ŭ������ �ʵ忡 �����ϴ� annotation.
 * �ʵ��� ���� null �Ǵ� "" ���� ���̰� 0�� ���ڰ� �Ұ��� �ʵ忡 �����Ѵ�.
 * @author home
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntNotNull {
	
}
