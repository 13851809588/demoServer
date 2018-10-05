package com.bea.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bea.server.entity.Demo;
import com.bea.server.mapper.DemoMapper;
import com.bea.server.service.DemoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yanjun
 */
@Service("demoService")
public class DemoServiceImpl extends ServiceImpl<DemoMapper,Demo> implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public List<Demo> find(Map<String, Object> map) {
        return demoMapper.find(map);
    }

    @Override
    public PageInfo<Demo> findwithPage(Page<?> page, Map<String, Object> map) {
        PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
        return new PageInfo<>(demoMapper.find(map));
    }

    @Override
    public IPage<Demo> selectUserPage(Page page, Map<String,Object> map){
        return demoMapper.selectPageVo(page,map);
    }

    @Override
    public IPage<Demo> selectMyPageByObj(Page page, Demo demo) {
        return demoMapper.selectMyPageByObj(page,demo);
    }

    @Override
    public IPage<Demo> selectMyPageByEW(IPage<Demo> iPage, Wrapper<Demo> wrapper) {
        return demoMapper.selectMyPageByEW(iPage,wrapper);
    }

    @Override
    public Page<Map> queryMyOrders(Page<Map> mapPage, Map requestParam) {
        List<Map> myOrders = demoMapper.queryMyOrders(mapPage,requestParam);
        mapPage.setRecords(myOrders);
        return mapPage;
    }

}
