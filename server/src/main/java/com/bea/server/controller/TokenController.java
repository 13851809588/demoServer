package com.bea.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bea.server.common.pojo.RespData;
import com.bea.server.entity.ApiTokenInfo;
import com.bea.server.entity.ApiUserInfo;
import com.bea.server.service.ApiTokenInfoService;
import com.bea.server.service.ApiUserInfoService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yanjun
 */
@RestController
@Slf4j
@RequestMapping("/jwt")
public class TokenController extends BaseController {

    @Autowired
    private ApiTokenInfoService apiTokenInfoService;

    @Autowired
    private ApiUserInfoService apiUserInfoService;

    /**
     * 获取token，更新token
     *
     * @param appId     用户编号
     * @param appSecret 用户密码
     * @return
     */
    @RequestMapping(value = "/token", method = {RequestMethod.POST, RequestMethod.GET})
    public RespData token(@RequestParam String appId, @RequestParam String appSecret) {
        RespData respData = new RespData();
        if (StringUtils.isBlank(appId)) {
            //检验appId是否为空
            respData.setCode(-1);
            respData.setMsg("appId没有发现!");
        } else if (StringUtils.isBlank(appSecret)) {
            //检验appSecret是否为空
            respData.setCode(-1);
            respData.setMsg("appSecret没有发现!");
        } else {
            //根据appId查询用户实体
            ApiUserInfo apiUserInfo = apiUserInfoService.getById(appId);
            //如果不存在

            if (null == apiUserInfo) {
                respData.setCode(-1);
                respData.setMsg("appId:" + appId + ", 没有发现！");
            } else if (!new String(apiUserInfo.getAppSecret()).equals(appSecret.replace(" ", "+"))) {
                respData.setCode(-1);
                respData.setMsg("appId: " + appId + ", 没有发现!");
            } else {
                //检测数据库是否存在该appId的token值
                ApiTokenInfo apiTokenInfo = apiTokenInfoService.getOne(new QueryWrapper<ApiTokenInfo>().eq("app_id", appId));
                //返回token值
                String tokenStr = null;
                //apiTokenInfo == null -> 生成newToken -> 保存数据库 -> 写入内存 -> 返回newToken
                if (null == apiTokenInfo) {
                    //生成jwt,Token
                    tokenStr = createNewToken(appId);
                    //将token保持到数据库
                    apiTokenInfo = new ApiTokenInfo();
                    apiTokenInfo.setAppId(apiUserInfo.getAppId());
                    apiTokenInfo.setCreatetime(String.valueOf(System.currentTimeMillis()));
                    apiTokenInfo.setToken(tokenStr.getBytes());
                    apiTokenInfoService.save(apiTokenInfo);
                } else {
                    //apiTokenInfo != null -> 验证是否超时 ->
                    //不超时 -> 直接返回dbToken
                    //超时 -> 生成newToken -> 更新dbToken -> 更新内存Token -> 返回newToken
                    //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
                    //数据库中生成时间
                    long dbCreateTime = Long.valueOf(apiTokenInfo.getCreatetime());
                    //当前时间
                    long currentTime = System.currentTimeMillis();
                    //如果当前时间 - 数据库中生成时间 < 7200 证明可以正常使用
                    long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - currentTime);
                    System.out.println("second=" + second);
                    if (second > 0 && second < 7200) {
                        tokenStr = new String(apiTokenInfo.getToken());
                    } else {
                        //生成newToken
                        tokenStr = createNewToken(appId);
                        //更新token
                        apiTokenInfo.setToken(tokenStr.getBytes());
                        //更新生成时间
                        apiTokenInfo.setCreatetime(String.valueOf(System.currentTimeMillis()));
                        //执行更新
                        apiTokenInfoService.saveOrUpdate(apiTokenInfo);
                    }
                }
                //设置返回token
                respData.setData(tokenStr);
            }
        }
        return respData;
    }

    /**
     * 创建新token
     *
     * @param appId
     * @return
     */
    private String createNewToken(String appId) {
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000);
        return Jwts
                .builder()
                .setSubject(appId)
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now)
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "HengYuAuthv1.0.0")
                .compact();
    }
}