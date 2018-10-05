package com.bea.server.common.aop;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author yanjun
 */
@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LogMethodAop {

    /**
     * 打印方法的名称及参数
     *
     * @param point 切面
     */
    public void printMethodParams(JoinPoint point) {
        if (point == null) {
            return;
        }

        //Signature 包含了方法名、申明类型以及地址等信息
        String class_name = point.getTarget().getClass().getName();
        String method_name = point.getSignature().getName();

        //获取方法的参数值数组
        Object[] method_args = point.getArgs();

        try {
            //获取方法的参数名称
            String[] paramNames = getFieldsName(class_name, method_name);
            //打印方法的名称和参数值
            logParam(paramNames, method_args);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String[] getFieldsName(String class_name, String method_name) throws Exception {
        Class<?> clazz = Class.forName(class_name);
        String clazz_name = clazz.getName();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(clazz);
        pool.insertClassPath(classPath);
        CtClass ctClass = pool.get(clazz_name);
        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return null;
        }
        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramsArgsName.length; i++) {
            paramsArgsName[i] = attr.variableName(i + pos);
        }
        return paramsArgsName;
    }

    private boolean isPrimite(Class<?> clazz) {
        if (clazz.isPrimitive() || clazz == String.class) {
            return true;
        } else {
            return false;
        }
    }

    private void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            log.info("该方法没有参数");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i];
            //参数值
            Object value = paramsArgsValue[i];
            buffer.append(name + " = ");
            if (isPrimite(value.getClass())) {
                buffer.append(value + "  ,");
            } else {
                buffer.append(value.toString() + "  ,");
            }
        }
        log.info(buffer.toString());
    }

    @Before("execution(public * com.bea.server.*.impl..*.*(..))")
    public void before(JoinPoint point) {
        this.printMethodParams(point);
    }


}

