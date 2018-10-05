package com.bea.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bea.server.entity.ApiTokenInfo;
import com.bea.server.mapper.ApiTokenInfoMapper;
import com.bea.server.service.ApiTokenInfoService;
import org.springframework.stereotype.Service;

/**
 * @author yanjun
 */
@Service("apiTokenInfoService")
public class ApiTokenInfoServiceImpl extends ServiceImpl<ApiTokenInfoMapper,ApiTokenInfo> implements ApiTokenInfoService {

}
