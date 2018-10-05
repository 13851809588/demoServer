package com.bea.server.common.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bea.server.entity.ApiTokenInfo;
import com.bea.server.service.ApiTokenInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author yanjun
 */
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Resource(name = "apiTokenInfoService")
    private ApiTokenInfoService apiTokenInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //自动排除生成token的路径,并且如果是options请求是cors跨域预请求，设置allow对应头信息
        if("/token".equals(request.getRequestURI()) || RequestMethod.OPTIONS.toString().equals(request.getMethod()))
        {
            return true;
        }

        //其他请求获取头信息
        final String authHeader = request.getHeader("X-YAuth-Token");
        try {
            //如果没有header信息
            if (authHeader == null || authHeader.trim().equals("")) {
                throw new SignatureException("not found X-YAuth-Token.");
            }

            //获取jwt实体对象接口实例
            final Claims claims = Jwts.parser().setSigningKey("HengYuAuthv1.0.0")
                    .parseClaimsJws(authHeader).getBody();
            //从数据库中获取token
            ApiTokenInfo apiTokenInfo = apiTokenInfoService.getOne(new QueryWrapper<ApiTokenInfo>().eq("app_id",claims.getSubject()));
            //数据库中的token值
            String tokenval = new String(apiTokenInfo.getToken());
            //如果内存中不存在,提示客户端获取token
            if(StringUtils.isBlank(tokenval)) {
                throw new SignatureException("not found token info, please get token agin.");
            }
            //判断内存中的token是否与客户端传来的一致
            if(!tokenval.equals(authHeader))
            {
                throw new SignatureException("not found token info, please get token agin.");
            }
        }
        //验证异常处理
        catch (SignatureException | ExpiredJwtException e)
        {
            //输出对象
            PrintWriter writer = response.getWriter();

            //输出error消息
            writer.write("need refresh token");
            writer.close();
            return false;
        }
        //出现异常时
        catch (final Exception e)
        {
            //输出对象
            PrintWriter writer = response.getWriter();
            //输出error消息
            writer.write(e.getMessage());
            writer.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
