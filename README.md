# commons-es

This library provides a simple way to generate basic [Elasticsearch](https://www.elastic.co/products/elasticsearch) [DSL](https://www.elastic.co/guide/en/elasticsearch/reference/5.5/query-dsl.html). To use this library, add the following dependency to your project:

该项目提供了一种生成基本的 [Elasticsearch](https://www.elastic.co/products/elasticsearch) [DSL](https://www.elastic.co/guide/en/elasticsearch/reference/5.5/query-dsl.html) 的简单方法。

    <dependency>
      <groupId>com.github.yang69</groupId>
      <artifactId>commons-es</artifactId>
      <version>1.0</version>
    </dependency>

*Version Compatibility*: This module is compatible with Elasticsearch 2.x - 6.x.

*版本兼容性*：兼容于Elasticsearch 2.x 到 6.x

## Usage | 使用方法

```
String dslString = new ElasticsearchQuery()
          .filter("name", "yang")
          .notFilter("sex", "male")
          .exists("job")
          .notExists("crime")
          .range("age", "gte", 18, "lt", 60)
          .size(10)
          .from(1)
          .build()
```

# Change Log | 更改日志

1.0 支持简单的过滤条件（满足/不满足/满足其一/一个都不满足）、简单的范围过滤（满足/不满足)、分页查询、存在性判断（存在/不存在）
