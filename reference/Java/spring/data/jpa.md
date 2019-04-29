# JPA

Java Persistence API

JPA 是一个规范,而hibernate是JPA的一个实现.通过JPA可以大大减少对CRUD的书写.

## JPA 问题

hibernate hbm2ddl 顺序

### Hibernate N+1问题


[](http://www.blogjava.net/RoyPayne/archive/2012/01/30/369017.html)


解决方案


延迟检索策略和迫切左外连接检索策略
## Repository Bean

## default value

- set DB default

@ColumnDefault(value = "0")

- set Java Object default value

private Integer deleted=0;


@Temporal

[Temporal](https://www.cnblogs.com/crawl/p/7703679.html)

## jpa 查询 (JPQL)

>JPA 适用于简单的CRUD,复杂的还是用原生SQL比较好


```java
String jpql = "UPDATE Customer c SET c.lastName = ? WHERE c.id = ?";
Query query = entityManager.createQuery(jpql).setParameter(1, "YYY").setParameter(2, 12);

query.executeUpdate();
```


[@Query注解的用法(Spring Data JPA)](https://www.cnblogs.com/zj0208/p/6008627.html)
[JPQL](https://blog.csdn.net/suncaishen/article/details/6512028)

[SpringDataJPA学习记录(二)–增删改查](https://blog.csdn.net/u012706811/article/details/53218083)


## 原生SQL


```java
@Query(value = "sql语句",nativeQuery = true)

em.createNativeQuery("insert into user (name, password) values (?, ?)").
	setParameter(1, "zhangsan").setParameter(2, "zhangsan");
```


 Modifying queries can only use void or int/Integer as return type!;
 
  nested exception is java.lang.IllegalArgumentException: Modifying queries can only use void or int/Integer as return type!

如何拿到 刚刚插入的数据id?

如果随即进行查询最新数据,很可能会查询到错误数据(高并发下)

## jpa dynamic sql

[jpa dynamic sql](https://segmentfault.com/a/1190000016305396)

### 更新
[关于Spring Data JPA更新部分字段的问题](https://blog.csdn.net/qq_42564846/article/details/82287298)


## 模糊查询

```java
findByStudnoContainingAndGroup_id
```
注意 模糊查询字段 只能查String 而不能 Long

[Spring Data JPA使用Specification动态构建多表查询、复杂查询及排序示例](https://www.jianshu.com/p/659e9715d01d)


