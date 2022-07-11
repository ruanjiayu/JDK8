package com.xian.jdk.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;


/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/8 10:46 上午
 * @Version: 0.0.1-SNAPSHOT
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
            //获取到DoWork的所有实现
        ExtensionLoader<DoWork> extensionLoader = ExtensionLoader.getExtensionLoader(DoWork.class);
        DoWork code = extensionLoader.getExtension("code");
        code.doWork();
        DoWork bug = extensionLoader.getExtension("bug");
        bug.doWork();
    }
}
