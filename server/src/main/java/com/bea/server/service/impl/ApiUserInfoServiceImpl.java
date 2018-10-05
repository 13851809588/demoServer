package com.bea.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bea.server.entity.ApiUserInfo;
import com.bea.server.mapper.ApiUserInfoMapper;
import com.bea.server.service.ApiUserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author yanjun
 */
@Service("apiUserInfoService")
public class ApiUserInfoServiceImpl extends ServiceImpl<ApiUserInfoMapper,ApiUserInfo> implements ApiUserInfoService {

}
