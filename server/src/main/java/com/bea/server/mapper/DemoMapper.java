package com.bea.server.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bea.server.entity.Demo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yanjun
 */
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 查询demo列表
     * @param map
     * @return
     */
    List<Demo> find(Map<String, Object> map);

    /**
     * MP 自定义分页  by Map
     * @param page
     * @param map
     * @return
     */
    IPage<Demo> selectPageVo(Page page, @Param("em")Map<String,Object> map);

    /**
     * MP  自定义分页  by Object
     * @param page
     * @param demo
     * @return
     */
    IPage<Demo> selectMyPageByObj(Page page,@Param("et")Demo demo);


    /**
     * MP 自定义分页 by Wrapper
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
    List<Map> queryMyOrders(Page<Map> mapPage, @Param("em")Map requestParam);


}
