package com.bea.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bea.server.common.pojo.RespData;
import com.bea.server.entity.Demo;
import com.bea.server.entity.Demodetail;
import com.bea.server.service.DemoService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanjun
 */
@RestController
@Slf4j
@RequestMapping("/demo")
public class DemoController  extends  BaseController{

    @Resource(name="demoService")
    private DemoService demoService;

    /**
     * pagehelper 分页查询
     * @param id
     * @return
     */
    @PostMapping("/demoPageHelper")
    public RespData demoPageHelper(){
        Map<String,Object> map = new HashMap<>(0);
        map.put("age",20);
        map.put("name","Jack");

        Page<?> page = new Page<>();
        getPageParameter(page);
        PageInfo<Demo> pageInfo = demoService.findwithPage(page,map);
        return RespData.ok(pageInfo);
    }

    /**
     * MP 自带分页查询
     * @param id
     * @return 分页对象
     */
    @PostMapping("/demoPagePlus")
    public RespData demoPagePlus(@RequestParam(required = false, defaultValue = "1") int id){
        Page<Demo> page = new Page<>();
        getPageParameter(page);
        IPage<Demo> demos = demoService.page(page,new QueryWrapper<Demo>().eq("age",18));
        return RespData.ok(demos);
    }

    /**
     * MP 自带分页查询
     * @param id
     * @return 分页对象
     * 
     */
    @PostMapping("/demoPagePlus2")
    public RespData demoPagePlus2(@RequestParam(required = false, defaultValue = "1") int id){
        Page<Demo> page = new Page<>();
        getPageParameter(page);
        Demo demo = new Demo();
        demo.setAge(18);
        IPage<Demo> demos = demoService.page(page,new QueryWrapper<>(demo));
        return RespData.ok(demos);
    }

    /**
     * MP 自带分页查询
     * @param id
     * @return 分页Map
     */
    @PostMapping("/demoPageMapPlus")
    public RespData demoPageMapPlus(@RequestParam(required = false, defaultValue = "1") int id){
        Page<Demo> page = new Page<>();
        getPageParameter(page);
        IPage<Map<String,Object>> demos = demoService.pageMaps(page,new QueryWrapper<Demo>().eq("age",20));
        return RespData.ok(demos);
    }

    /**
     * MP 自定义分页查询
     * @param id
     * @return 分页Map
     */
    @PostMapping("/demoPageMapPlusSelf")
    public RespData demoPageMapPlusSelf(@RequestParam(required = false, defaultValue = "1") int id){
        Map<String,Object> map = new HashMap<>(0);
        map.put("age",20);
        map.put("name","Jack");

        Page<Demo> page = new Page<>();
        getPageParameter(page);
        IPage<Demo> demos = demoService.selectUserPage(page,map);
        return RespData.ok(demos);
    }

    /**
     * MP 自定义分页查询
     * @param id
     * @return 分页Map
     */
    @PostMapping("/demoPageObjPlusSelf")
    public RespData demoPageObjPlusSelf(@RequestParam(required = false, defaultValue = "1") int id){
        Demo demo = new Demo();
        demo.setAge(20);
        demo.setName("Jack");

        Page<Demo> page = new Page<>();
        getPageParameter(page);
        IPage<Demo> demos = demoService.selectMyPageByObj(page,demo);
        return RespData.ok(demos);
    }

    /**
     * MP 自定义分页查询
     * @param id
     * @return 分页Wrapper
     */
    @PostMapping("/demoPageWrapper")
    public RespData demoPageWrapper(@RequestParam(required = false, defaultValue = "1") int id){
        Page<Demo> page = new Page<>();
        getPageParameter(page);

        QueryWrapper<Demo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age",21);

        IPage<Demo> demos = demoService.selectMyPageByEW(page,queryWrapper);
        return RespData.ok(demos);
    }



    /**
     * MP 多表分页查询
     * @param id
     * @return 分页Map
     */
    @PostMapping("/demoNtablePage")
    public RespData demoNtablePage(@RequestParam(required = false, defaultValue = "1") int id){
        Map requestParam = new HashMap<>();
        Page<Map> mapPage = new Page<>();
        getPageParameter(mapPage);

        requestParam.put("age",21);
        requestParam.put("name",null);
        Page<Map> myOrders = demoService.queryMyOrders(mapPage,requestParam);
        return RespData.ok(myOrders);
    }

    /**
     * 自定义查询列表
     * @param id
     * @return
     */
    @PostMapping("/demoQuery")
    public RespData demoQuery(@RequestParam(required = false, defaultValue = "1") int id){
        Map<String,Object> map = new HashMap<>(0);
        map.put("age",20);

        List<Demo> demos = demoService.find(map);
        return RespData.ok(demos);
    }

    /**
     * 请求参数为json数据，转化为JSONObject对象 如：{"iDisplayStart":1,"iDisplayLength":5,"name":"yanjun","age":33}
     * @param jsonParam
     * @return
     */
    @PostMapping(name="/demoJson",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespData demoJson(@RequestBody JSONObject jsonParam){
        Demo demo = new Demo();
        demo.setAge(jsonParam.getInteger("age"));
        demo.setName(jsonParam.getString("name"));

        Page<Demo> page = new Page<>();
        page.setCurrent(jsonParam.getInteger("iDisplayStart"));
        page.setSize(jsonParam.getInteger("iDisplayLength"));
        IPage<Demo> demos = demoService.selectMyPageByObj(page,demo);
        return RespData.ok(demos);
    }


    /**
     * 请求参数为json数据，转化为Demo对象 如：{"name":"yanjun","age":33}
     * @param demo
     * @return
     */

    /**
     * json数据转化为List<Demo>对象
     * [
     *  {"age":20,"name":"yanjun"},
     *  {"age":30,"name":"lif"}
     * ]
     * @param demos
     * @return
     */
    @PostMapping("/demoJsonListObj")
    @ResponseBody
    public RespData demoJsonListObj(@RequestBody List<Demo> demos){
        return RespData.ok(demos);
    }

    /**
     * 支持json转化嵌套对象 如：
     * [
     * {
     * "id":1,
     * "nmae":"yanjun",
     * "age":30,
     * "demodetails":[
     * {"id":1,"rid":1,"orderno":3},
     * {"id":2,"rid":1,"orderno":4}
     * ]
     * },
     * {
     * "id":2,
     * "nmae":"yanjun",
     * "age":30,
     * "demodetail":[
     * {"id":3,"rid":2,"orderno":5},
     * {"id":4,"rid":2,"orderno":6}
     * ]
     * }
     * ]
     * @param demos
     * @return
     */
    @PostMapping("/demoJsonNestObj")
    @ResponseBody
    public RespData demoJsonNestObj(@RequestBody List<Demo> demos){
        return RespData.ok(demos);
    }

    /**
     * 不支持嵌套 如：
     * name:"aaa"
     * age:33
     * orderno:1
     * rid:2
     * ids:1 #数组参数
     * ids:2
     * ids:3
     * id:100 #id在demo和demodetail中同时转化
     * @param name
     * @param age
     * @param ids
     * @param detail
     * @param demo
     * @return
     */
    @PostMapping("/xWwwFormUrlencoded")
    @ResponseBody
    public RespData xWwwFormUrlencoded(@RequestParam("name") String name, @RequestParam("age") Integer age, @RequestParam(name="ids",required = false) List<Integer> ids,
                                       Demodetail detail,
                                       //@RequestParam Demodetail detail 不能获得对象参数值
                                       Demo demo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("age",age);
        jsonObject.put("ids",ids);
        jsonObject.put("detail",detail);
        jsonObject.put("demo",demo);

        return RespData.ok(jsonObject);
    }

    /**
     * 对象参数
     * @param demo
     * @param email
     * @return
     */
    @PostMapping("/demoObj")
    @ResponseBody
    public RespData demoObj(Demo demo,String email){
        return RespData.ok(demo);
    }

    /**
     * json转化为map对象
     * @param params
     * @return
     */
    @PostMapping("/demoJsonMap")
    @ResponseBody
    public RespData demoObj(@RequestBody Map<String,Object> params){
        return RespData.ok(params);
    }

}
