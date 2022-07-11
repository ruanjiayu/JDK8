package com.xian.jdk.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * @Description:
 * @Author: Summer
 * @DateTime: 2022/7/8 10:43 上午
 * @Version: 0.0.1-SNAPSHOT
 */
@SPI
public interface DoWork {

    void doWork();
}
