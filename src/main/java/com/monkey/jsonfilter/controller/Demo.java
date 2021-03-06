package com.monkey.jsonfilter.controller;

import com.monkey.jsonfilter.annotation.MoreSerializeField;
import com.monkey.jsonfilter.annotation.MultiSerializeField;
import com.monkey.jsonfilter.annotation.SerializeField;
import com.monkey.jsonfilter.controller.bean.Address;
import com.monkey.jsonfilter.controller.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: JJC
 * @createTime: 2018/11/23
 */
@RestController
public class Demo {

    @GetMapping("user")
    @SerializeField(clazz = User.class, includes = {"name", "id"})
    public User user() {
        User user = new User(2L, "JJC", "123456");
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address("JJC's home", "JJC's school", user);
        Address a2 = new Address("JJC's home2", "JJC's school2", user);
        addresses.add(a1);
        addresses.add(a2);
        user.setAddresses(addresses);
        return user;
    }

    @GetMapping("userMulti")
    @SerializeField(clazz = User.class, includes = {"name", "id", "addresses"})
    /**
     * 进行二次过滤，如使用相同注解SerializeField，Advice处只会判定执行一次
     */
    @MultiSerializeField(clazz = Address.class, excludes = {"user"})
    public User userMulti() {
        User user = new User(1L, "JJC", "123456");
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address("JJC's home", "JJC's school", user);
        addresses.add(a1);
        user.setAddresses(addresses);
        return user;
    }

    @GetMapping("address")
    @MoreSerializeField({
            @SerializeField(clazz = Address.class,includes = {"school", "home", "user"}),
            @SerializeField(clazz = User.class, includes = {"id", "name"})})
    public Address address2(){
        User user = new User(1L, "JJC", "123456");
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address("JJC's home", "JJC's school", user);
        Address a2 = new Address("JJC's home2", "JJC's school2", user);
        addresses.add(a1);
        addresses.add(a2);
        user.setAddresses(addresses);
        return a1;
    }
}
