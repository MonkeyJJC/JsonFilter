# JsonFilter
动态的去返回 Json 数据，通过注解方式进行过滤

实现如下效果的自动筛选：

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


- [controller层调用](/src/main/java/com/json/filter/controller/TestController.java) 

目前bug：
使用@ResponseBody会跳过拦截，不进行过滤操作
不使用@ResponseBody，会导致404，因为会默认去寻找模板

