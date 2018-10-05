package com.bea.server.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bea.server.entity.Demo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yanjun
 */
public interface DemoService extends IService<Demo>{
    /**
     * 条件查询
     * @param map
     * @return
     */
    List<Demo> find(Map<String,Object> map);

    /**
     * 分页查询
     * @param page
     * @param map
     * @return
     */
    PageInfo<Demo> findwithPage(Page<?> page, Map<String, Object> map);

    /**
     * MP 自定义分页
     * @param page
     * @param map
     * @return
     */
    IPage<Demo> selectUserPage(Page page, Map<String,Object> map);

    /**
     * MP 自定义分页
     * @param page
     * @param demo
     * @return
     */
    IPage<Demo> selectMyPageByObj(Page page,Demo demo);

    /**
     * MP 自定义分页
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<Demo> selectMyPageByEW(IPage<Demo> iPage, @Param("ew") Wrapper<Demo> wrapper);

    /**
     * MP 多表分页查询
     * @param mapPage
     * @param requestParam
     * @return
     */
    Page<Map> queryMyOrders(Page<Map> mapPage, Map requestParam);

}
