# JsonFilter

自动将controller返回的bean包装为要求的Json格式：

动态的去返回 Json 数据，通过注解方式进行过滤

-(简单筛选)实现如下效果的自动筛选：

```
{
    id: "xxxx",
    title: "xxxx",
    content: "xxxx"
}
```

筛选后：

```
{
    id: "xxxx",
    title: "xxxx"
}
```

- [controller层调用](/src/main/java/com/monkey/jsonfilter/controller/Demo.java) 


```
    @GetMapping("user")
    @SerializeField(clazz = User.class, includes = {"name", "id"})
    public User user() {
        User user = new User(1L, "jjc", "123456");
        return user;
    }
```

结果输出：

```
{
    id: 1,
    name: "jjc"
}
```

(嵌套类字段的筛选)

```
    @GetMapping("userMulti")
    @SerializeField(clazz = User.class, includes = {"name", "id", "addresses"})
    /**
     * 进行二次过滤，如使用相同注解SerializeField，Advice处只会判定执行一次
     */
    @MultiSerializeField(clazz = Address.class, excludes = {"user"})
    public User userMulti() {
        User user = new User(1L, "jjc", "123456");
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address("liuyis's home", "liuyis's school", user);
        Address a2 = new Address("liuyis's home2", "liuyis's school2", user);
        addresses.add(a1);
        addresses.add(a2);
        user.setAddresses(addresses);
        return user;
    }
```

结果输出：

```
{
    addresses: [
        {
            home: "liuyis's home",
            school: "liuyis's school"
        },
        {
            home: "liuyis's home2",
            school: "liuyis's school2"
        }
    ],
    id: 1,
    name: "jjc"
}
```


坑：
- （1）使用@ResponseBody会跳过拦截，不进行过滤操作
不使用@ResponseBody，会导致404，因为会默认去寻找模板

- （2）通过继承RequestResponseBodyMethodProcessor，但是ArgumentResolver分为两类, 一类是Spring默认的处理器, 一类是用户自定义的处理器. Spring MVC在默认情况下会配置常用类型的处理器, 按顺序(默认处理器先, 用户自定义后)依次用各个处理器去匹配请求参数, 直到找到第一个满足匹配要求的处理器. 一旦找到第一个满足要求的处理器, 就不会继续寻找其他满足要求的了, 也就是说, 在相同的匹配条件下, 系统默认处理器比自定义处理器优先级高. 因此仍没有生效(自定义消息处理器convert)
