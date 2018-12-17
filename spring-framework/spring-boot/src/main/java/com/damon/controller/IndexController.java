/**
 * 2015-2016 龙果学院 (www.roncoo.com)
 */
package com.damon.controller;

import com.damon.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping(value = "/index")
@Validated
public class IndexController {

    @RequestMapping
    public String index() {
        return "hello world";
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    @RequestMapping(value = "/get")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        return map;
    }

    // @PathVariable 获得请求url中的动态参数
    @RequestMapping(value = "/get/{id}/{name}")
    public User getUser(@PathVariable int id, @PathVariable String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDate(new Date());
        return user;
    }

    @GetMapping(value = "/name")
    public User name(@RequestParam("name") @NotBlank(message = "名字不能为空2") String name) {
        User user = new User();
        user.setName(name);
        user.setDate(new Date());
        return user;
    }

    @GetMapping(value = "/u")
    public User u(@RequestParam("name") @NotEmpty(message = "名字不能为空") String name,
                  @RequestParam("id") @NotNull(message = "id不能为空") Integer id) {
        User user = new User();
        user.setName(name);
        user.setId(id);
        user.setDate(new Date());
        return user;
    }

    @GetMapping(value = "/id")
    public User id(@RequestParam("id") @NotNull(message = "id不能为空") Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("s");
        user.setDate(new Date());
        return user;
    }

    // @PathVariable 获得请求url中的动态参数
    @GetMapping(value = "/user")
    public User user(@ModelAttribute @Valid User user) {
        return user;
    }

}