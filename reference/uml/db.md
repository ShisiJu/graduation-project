导师
学生 
导师-学生
学生申请记录
导师回复记录

学院
专业
班级
学院-专业
专业-班级
班级-学生

数据字典 设计一张字典表，然后专门对这个表进行维护，把字典数据在项目中用缓存存取
- dictionary_type
- dictionary_info

小的字典使用 enum  such as sex , but Chinese words do not suit. Just feel weird.
大的字典使用 table表

[参考](https://blog.csdn.net/u012373815/article/details/49539239)



登录用户
用户权限



Our team uses such common table for similar (plain) dictionaries. It is very inconvenient.

So, It is better to use this approach:

For small dictionaries use enums. You can store ordinals for large enums, if you will not use the external SQL (for example, for reports, generated using SQL). For small enums, names can be stored.
For large dictionaries use separate tables with corresponding entities. It is convenient to have @MappedSuperclass for the common fields.
An example

enum Sex {
  MALE, FEMALE, UNKNOWN
}

@MappedSuperclass
class Dictionary {

    @Id
    private Long code;

    @Column
    private String name;

}

@Entity
class Country extends Dictionary {

}

@Entity
class User {

    @Column
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne
    private Country country;

}