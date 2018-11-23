# JsonFilter

自动将controller返回的bean包装为要求的Json格式：

动态的去返回 Json 数据，通过注解方式进行过滤

-实现如下效果的自动筛选：

{
    id: "xxxx",
    title: "xxxx",
    content: "xxxx",
    tag: {
       id: "",
       tagName: ""
    }
}

{
    id: "xxxx",
    title: "xxxx"
}


- [controller层调用](/src/main/java/com/monkey/jsonfilter/controller/Demo.java) 

坑：
- （1）使用@ResponseBody会跳过拦截，不进行过滤操作
不使用@ResponseBody，会导致404，因为会默认去寻找模板

- （2）通过继承RequestResponseBodyMethodProcessor，但是ArgumentResolver分为两类, 一类是Spring默认的处理器, 一类是用户自定义的处理器. Spring MVC在默认情况下会配置常用类型的处理器, 按顺序(默认处理器先, 用户自定义后)依次用各个处理器去匹配请求参数, 直到找到第一个满足匹配要求的处理器. 一旦找到第一个满足要求的处理器, 就不会继续寻找其他满足要求的了, 也就是说, 在相同的匹配条件下, 系统默认处理器比自定义处理器优先级高. 因此仍没有生效(自定义消息处理器convert)
