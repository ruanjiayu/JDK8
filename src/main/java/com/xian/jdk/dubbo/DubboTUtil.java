package com.xian.jdk.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/4/18 11:36
 * @Version: 1.0.0
 */
public class DubboTUtil {
    public static void main(String[] args) {

        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("websockt-dubbo-consumer");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("nacos://localhost:8848?namespace=ec47d06a-6539-466f-8d46-6b9eaca593ad");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("cc.duoguan.websocket.api.service.WsPushApiService");
        // 声明为泛化接口
        reference.setGeneric("true");
        reference.setVersion("1.0");
        try {
            GenericService genericService = reference.get();
            Map<String, Object> maps = new HashMap<>();
            maps.put("message","hello");
            maps.put("token","1");
            // 基本类型以及Date,List,Map等不需要转换，直接调用
            Object result = genericService.$invoke("send", new String[]{"cc.duoguan.websocket.api.request.MessageRequest"}, new Object[]{maps});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
