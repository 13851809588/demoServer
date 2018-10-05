package com.bea.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanjun
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    HttpServletRequest request;

    protected void getPageParameter(Page<?> page){
        Integer pageDisStart = Integer.parseInt(request.getParameter("iDisplayStart"));
        Integer pageSize = Integer.parseInt(request.getParameter("iDisplayLength"));
        page.setCurrent(pageDisStart);
        page.setSize(pageSize);
    }


}
