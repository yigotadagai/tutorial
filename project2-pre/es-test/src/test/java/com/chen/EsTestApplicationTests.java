package com.chen;

import com.alibaba.fastjson.JSON;
import com.chen.mapper.GoodsMapper;
import com.chen.pojo.Goods;
import com.chen.pojo.Person;
import com.sun.xml.internal.ws.api.model.MEP;
import org.apache.ibatis.javassist.expr.NewExpr;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.channels.NonReadableChannelException;
import java.util.*;

@SpringBootTest
class EsTestApplicationTests {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private GoodsMapper goodsMapper;

	@Test
	void contextLoads() {
		System.out.println(client);
	}

	/**
	 * 添加索引
	 */
	@Test
	public void addIndex() throws IOException {
		//1.使用client获取操作索引的对象
		IndicesClient indicesClient = client.indices();
		//2.具体操作，获取返回值
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("abc");
		CreateIndexResponse response = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		//3.根据返回值判断结果
		System.out.println(response.isAcknowledged());
	}

	/**
	 * 添加索引和映射
	 */
	@Test
	public void addIndexAndMapping() throws IOException {
		IndicesClient indices = client.indices();
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("aaa");

		//设置mapping
		String mapping = "{\n" +
				"      \"properties\" : {\n" +
				"        \"address\" : {\n" +
				"          \"type\" : \"text\",\n" +
				"          \"analyzer\" : \"ik_max_word\"\n" +
				"        },\n" +
				"        \"age\" : {\n" +
				"          \"type\" : \"long\"\n" +
				"        },\n" +
				"        \"name\" : {\n" +
				"          \"type\" : \"keyword\"\n" +
				"        }\n" +
				"      }\n" +
				"    }";

		//以json传输
		createIndexRequest.mapping(mapping, XContentType.JSON);

		CreateIndexResponse response = indices.create(createIndexRequest, RequestOptions.DEFAULT);

		System.out.println(response.isAcknowledged());
	}

	/**
	 * 查询索引
	 * @throws IOException
	 */
	@Test
	public void getIndex() throws IOException {
		IndicesClient indices = client.indices();
		GetIndexRequest getIndexRequest = new GetIndexRequest("aaa");

		GetIndexResponse response = indices.get(getIndexRequest, RequestOptions.DEFAULT);
		Map<String, MappingMetadata> mappings = response.getMappings();
		for (String key : mappings.keySet()) {
			System.out.println(key + ":" + mappings.get(key).getSourceAsMap());
		}
	}

	/**
	 * 判断索引是否存在
	 * @throws IOException
	 */
	@Test
	public void existIndex() throws IOException {
		IndicesClient indices = client.indices();
		GetIndexRequest getIndexRequest = new GetIndexRequest("abc");
		boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	/**
	 * 删除索引
	 * @throws IOException
	 */
	@Test
	public void deleteIndex() throws IOException {
		IndicesClient indices = client.indices();
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("abc");
		AcknowledgedResponse response = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		System.out.println(response.isAcknowledged());
	}

	/**
	 * 添加文档，使用map作为数据
	 * @throws IOException
	 */
	@Test
	public void addDoc() throws IOException {
		//数据对象map
		Map data = new HashMap();
		data.put("address", "湖南长沙");
		data.put("age", 24);
		data.put("name", "李四");

		//1.获取操作文档的对象
		IndexRequest request = new IndexRequest("aaa").id("1").source(data);
		//添加数据，获取结果
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
	}

	/**
	 * 添加文档，以对象作为数据
	 * @throws IOException
	 */
	@Test
	public void addDoc2() throws IOException {
		Person person = new Person();
		person.setId("2");
		person.setName("robert");
		person.setAddress("广东深圳宝安");
		person.setAge(25);

		String data = JSON.toJSONString(person);

		IndexRequest request = new IndexRequest("aaa").id(person.getId()).source(data, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
	}

	/**
	 * 修改数据，若id存在则修改，不存在则为添加
	 * @throws IOException
	 */
	@Test
	public void updateDoc() throws IOException {
		Person person = new Person();
		person.setId("3");
		person.setAge(27);
		person.setName("王五");
		person.setAddress("广东广州");

		String data = JSON.toJSONString(person);

		IndexRequest request = new IndexRequest("aaa").id(person.getId()).source(data, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		System.out.println(response.getId());
	}

	/**
	 * 根据id查询文档信息
	 * @throws IOException
	 */
	@Test
	public void getDocById() throws IOException {
		GetRequest getRequest = new GetRequest("aaa", "3");

		GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(response.getSourceAsString());
	}

	/**
	 * 根据id删除文档
	 * @throws IOException
	 */
	@Test
	public void deleteDocById() throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest("aaa", "1");
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		System.out.println(response.getId());
		System.out.println(response.getResult());
	}

	@Test
	public void testBulk() throws IOException {
		//1.创建BulkRequest对象，整合所有操作
		BulkRequest bulkRequest = new BulkRequest();

		/*
        1. 删除1号记录
        2. 添加6号记录
        3. 修改3号记录 名称为 “三号”
         */
		//删除1号记录
		DeleteRequest deleteRequest = new DeleteRequest("aaa", "1");
		bulkRequest.add(deleteRequest);

		//添加六号记录
		Map map = new HashMap();
		map.put("name", "六号");
		IndexRequest indexRequest = new IndexRequest("aaa").id("6").source(map);
		bulkRequest.add(indexRequest);

		//修改三号记录
		Map map2 = new HashMap();
		map2.put("name", "3号");
		UpdateRequest updateRequest = new UpdateRequest("aaa", "3").doc(map2);
		bulkRequest.add(updateRequest);

		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(responses.status());

	}

	/**
	 * 批量导入
	 * 从mysql导入到es
	 */
	@Test
	public void importData() throws IOException {
		//1.查询mysqlz所有数据
		List<Goods> goodsList = goodsMapper.findAll();
//		for (Goods goods : goodsList) {
//			System.out.println(goods);
//		}
		//2.从bulk导入
		//2.1遍历goodsList，创建IndexRequest添加数据
		BulkRequest bulkRequest = new BulkRequest();
		for (Goods goods : goodsList) {
			//2.2设置spec规格信息map的数据 specStr{}
			String specStr = goods.getSpecStr();
			//将json格式字符串转为map集合
			Map map = JSON.parseObject(specStr, Map.class);
			//将goods转为json字符串
			String data = JSON.toJSONString(goods);
			IndexRequest indexRequest = new IndexRequest("goods");
			indexRequest.id(goods.getId() + "").source(data, XContentType.JSON);
			bulkRequest.add(indexRequest);
		}

		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(responses.status());
	}


	/**
	 * 查询所有 matchAll
	 * @throws IOException
	 */
	@Test
	public void testMatchAll() throws IOException {
		//构建查询请求对象，指定查询的索引名称
		SearchRequest searchRequest = new SearchRequest("goods");
		//创建查询条件构建器SearchSourceBuilder
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		//查询条件
		MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
		//指定查询条件
		sourceBuilder.query(query);
		//添加查询条件构建器
		searchRequest.source(sourceBuilder);
		//指定分页信息
		sourceBuilder.from(0);
		sourceBuilder.size(50);

		//获取查询结果
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		//获取命中对象
		SearchHits hits = searchResponse.getHits();
		//获取总记录数
		long value = hits.getTotalHits().value;
		System.out.println("总记录数为：" + value);

		List<Goods> goodsList = new ArrayList<>();
		//获取命中数组
		SearchHit[] hitsArr = hits.getHits();
		for (SearchHit hit : hitsArr) {
			//获取json字符串格式的对象
			String sourceAsString = hit.getSourceAsString();
			//转换为java对象
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * term查询
	 * term 词条查询不会分析查询条件，只有当词条和查询字符串完全匹配时才匹配搜索
	 */
	@Test
	public void testTermSearch() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		TermQueryBuilder query = QueryBuilders.termQuery("title", "小米手机");

		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 词条分词查询，分词之后的等值匹配
	 */
	@Test
	public void testMatchQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder query = QueryBuilders.matchQuery("title", "华为手机");
		//求并集
		query.operator(Operator.AND);
		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 模糊查询
	 */
	@Test
	public void testWildCardQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		WildcardQueryBuilder query = QueryBuilders.wildcardQuery("title", "华*");

		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 模糊查询
	 */
	@Test
	public void testPrefixQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		PrefixQueryBuilder query = QueryBuilders.prefixQuery("brandName", "三");
		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 范围查询
	 */
	@Test
	public void testRangeQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		RangeQueryBuilder query = QueryBuilders.rangeQuery("price");
		//指定上限 lte小于等于
		query.lte(3000);
		//指定下限
		query.gte(2000);
		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * • 会对查询条件进行分词。
	 * • 然后将分词后的查询条件和词条进行等值匹配
	 * • 默认取并集（OR）
	 * • 可以指定多个查询字段
	 */
	@Test
	public void testQueryStringQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		QueryStringQueryBuilder query = QueryBuilders.queryStringQuery("华为手机")
				.field("title")
				.field("categoryName")
				.field("brandName")
				.defaultOperator(Operator.AND);
		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 布尔查询
	 */
	@Test
	public void testBoolQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		//1构建各个查询条件
		//1.1查询品牌名称
		TermQueryBuilder termQuery = QueryBuilders.termQuery("brandName", "华为");
		query.must(termQuery);
		//1.2查询标题包含：手机
		MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("title", "手机");
		query.filter(matchQuery);
		//1.2查询价格在2000-3000
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
		((RangeQueryBuilder) rangeQuery).gte(2000);
		((RangeQueryBuilder) rangeQuery).lte(3000);
		query.filter(rangeQuery);
		//2使用boolQuery链接
		sourceBuilder.query(query);
		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 * 聚合查询：桶聚合，分组查询
	 * 1. 查询title包含手机的数据
	 * 2. 查询品牌列表
	 */
	@Test
	public void testAggQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder query = QueryBuilders.matchQuery("title", "手机");
		sourceBuilder.query(query);
		//1.查询品牌列表
		/*
			参数：
				1.自定义的名称，用于获取数据
				2.分组的字段
		 */
		AggregationBuilder agg = AggregationBuilders.terms("good_brands").field("brandName").size(100);
		sourceBuilder.aggregation(agg);


		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}

	/**
	 *
	 * 高亮查询：
	 *  1. 设置高亮
	 *      * 高亮字段
	 *      * 前缀
	 *      * 后缀
	 *  2. 将高亮了的字段数据，替换原有数据
	 */

	@Test
	public void testHighLightQuery() throws IOException {
		SearchRequest searchRequest = new SearchRequest("goods");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder query = QueryBuilders.matchQuery("title", "电视");
		sourceBuilder.query(query);
		//1设置高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		//1.1设置三要素
		highlightBuilder.field("title");
		highlightBuilder.preTags("<font color='red'>");
		highlightBuilder.postTags("</font>");
		sourceBuilder.highlighter(highlightBuilder);
		//2.查询品牌列表
		/*
			参数：
				1.自定义的名称，用于获取数据
				2.分组的字段
		 */
		AggregationBuilder agg = AggregationBuilders.terms("good_brands").field("brandName").size(100);
		sourceBuilder.aggregation(agg);

		searchRequest.source(sourceBuilder);
		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = response.getHits();
		long value = searchHits.getTotalHits().value;
		System.out.println("总记录数：" + value);

		List<Goods> goodsList = new ArrayList<>();
		SearchHit[] hits = searchHits.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Goods goods = JSON.parseObject(sourceAsString, Goods.class);
			goodsList.add(goods);
		}

		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}


}
