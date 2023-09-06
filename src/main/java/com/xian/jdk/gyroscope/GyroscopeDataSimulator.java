package com.xian.jdk.gyroscope;

import java.util.Random;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/7/5 15:30
 * @Version: 1.0.0
 */
public class GyroscopeDataSimulator {

    public static void main(String[] args) {
        // 模拟陀螺仪传感器数据获取
        GyroscopeSensor gyroscope = new GyroscopeSensor();

        // 模拟获取陀螺仪数据的循环
        for (int i = 0; i < 10; i++) {
            // 从陀螺仪传感器读取角速度数据
            double angularVelocityX = gyroscope.getAngularVelocityX();
            double angularVelocityY = gyroscope.getAngularVelocityY();
            double angularVelocityZ = gyroscope.getAngularVelocityZ();

            // 在实际应用中，你可以使用上述数据进行姿态分析或其他应用
            System.out.println("Angular Velocity X: " + angularVelocityX);
            System.out.println("Angular Velocity Y: " + angularVelocityY);
            System.out.println("Angular Velocity Z: " + angularVelocityZ);

            try {
                Thread.sleep(1000); // 模拟每秒获取一次数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class GyroscopeSensor {
    private Random random;

    public GyroscopeSensor() {
        random = new Random();
    }

    // 模拟获取X轴角速度
    public double getAngularVelocityX() {
        return random.nextDouble() * 100; // 假设范围在0到100之间
    }

    // 模拟获取Y轴角速度
    public double getAngularVelocityY() {
        return random.nextDouble() * 100; // 假设范围在0到100之间
    }

    // 模拟获取Z轴角速度
    public double getAngularVelocityZ() {
        return random.nextDouble() * 100; // 假设范围在0到100之间
    }
}