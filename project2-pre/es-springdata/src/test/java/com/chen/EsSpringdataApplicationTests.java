package com.chen;

import com.chen.pojo.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
class EsSpringdataApplicationTests {

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreate() {
		//创建索引，会根据Item类的@Document注解信息来创建
		elasticsearchRestTemplate.createIndex(Item.class);
		// 配置映射，会根据Item类中的id、Field等字段来自动完成映射
		elasticsearchRestTemplate.putMapping(Item.class);
	}

}
