package com.ballcat.smaple.i18n;

import com.ballcat.sample.i18n.I18nApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = I18nApplication.class)
class I18nTestValidateTests {

	@Autowired
	private Validator validator;

	@Test
	void testValidate1() {
		// 构造一个 username 为null，age 不满足条件的 实例
		DemoData1 demoData = new DemoData1();
		demoData.setAge(200);
		// 校验并输出结果
		Set<ConstraintViolation<DemoData1>> set = validator.validate(demoData);
		for (ConstraintViolation<DemoData1> violation : set) {
			log.info(violation.getMessage());
		}
	}

	@Test
	void testValidate2() {
		// 构造一个 username 为null，age 不满足条件的 实例
		DemoData2 demoData = new DemoData2();
		demoData.setAge(200);
		// 校验并输出结果
		Set<ConstraintViolation<DemoData2>> set = validator.validate(demoData);
		for (ConstraintViolation<DemoData2> violation : set) {
			log.info(violation.getMessage());
		}
	}

	@Test
	void testValidate3() {
		// 构造一个 username 为null，age 不满足条件的 实例
		DemoData3 demoData = new DemoData3();
		demoData.setAge(200);
		// 校验并输出结果
		Set<ConstraintViolation<DemoData3>> set = validator.validate(demoData);
		for (ConstraintViolation<DemoData3> violation : set) {
			log.info(violation.getMessage());
		}
	}

}