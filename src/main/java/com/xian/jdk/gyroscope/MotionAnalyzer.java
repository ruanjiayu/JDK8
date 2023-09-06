package com.xian.jdk.gyroscope;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/7/5 17:57
 * @Version: 1.0.0
 */
public class MotionAnalyzer {

    private double rotationThreshold; // 旋转动作的阈值
    private double swingThreshold; // 挥动动作的阈值

    public MotionAnalyzer(double rotationThreshold, double swingThreshold) {
        this.rotationThreshold = rotationThreshold;
        this.swingThreshold = swingThreshold;
    }

    public boolean detectRotation(double angularSpeedZ) {
        // 判断角速度是否超过旋转阈值
        return Math.abs(angularSpeedZ) > rotationThreshold;
    }

    public boolean detectSwing(double angularSpeedX, double angularSpeedY) {
        // 计算合成角速度
        double compositeAngularSpeed = Math.sqrt(angularSpeedX * angularSpeedX + angularSpeedY * angularSpeedY);

        // 判断合成角速度是否超过挥动阈值
        return compositeAngularSpeed > swingThreshold;
    }

    public static void main(String[] args) {
        // 在应用程序中使用 MotionAnalyzer 进行旋转和挥动动作检测
        MotionAnalyzer analyzer = new MotionAnalyzer(3.0, 2.0); // 设置旋转阈值和挥动阈值（根据实际情况调整）
        // 模拟陀螺仪传感器数据获取
        GyroscopeSensor gyroscope = new GyroscopeSensor();
        double angularSpeedX = gyroscope.getAngularVelocityX();
        double angularSpeedY = gyroscope.getAngularVelocityZ();
        double angularSpeedZ = gyroscope.getAngularVelocityZ();
        boolean isRotating = analyzer.detectRotation(angularSpeedZ);
        boolean isSwinging = analyzer.detectSwing(angularSpeedX, angularSpeedY);

        if (isRotating) {
            System.out.println("设备正在发生旋转动作！");
        } else {
            System.out.println("设备未发生旋转动作。");
        }

        if (isSwinging) {
            System.out.println("设备正在发生挥动动作！");
        } else {
            System.out.println("设备未发生挥动动作。");
        }
    }

}
