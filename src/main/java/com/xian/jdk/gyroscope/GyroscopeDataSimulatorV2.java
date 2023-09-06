package com.xian.jdk.gyroscope;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/7/5 15:32
 * @Version: 1.0.0
 */
public class GyroscopeDataSimulatorV2 {

    public static void main(String[] args) {
        // 模拟陀螺仪传感器数据获取
        GyroscopeSensor gyroscope = new GyroscopeSensor();

        // 初始姿态角度
        double rollAngle = 0;
        double pitchAngle = 0;
        double yawAngle = 0;

        // 模拟获取陀螺仪数据的循环
        for (int i = 0; i < 10; i++) {
            // 从陀螺仪传感器读取角速度数据
            double angularVelocityX = gyroscope.getAngularVelocityX();
            double angularVelocityY = gyroscope.getAngularVelocityY();
            double angularVelocityZ = gyroscope.getAngularVelocityZ();

            // 计算姿态角的增量
            double deltaRoll = angularVelocityX * 0.1; // 假设采样时间间隔为0.1秒
            double deltaPitch = angularVelocityY * 0.1;
            double deltaYaw = angularVelocityZ * 0.1;

            // 更新姿态角
            rollAngle += deltaRoll;
            pitchAngle += deltaPitch;
            yawAngle += deltaYaw;

            // 在实际应用中，你可以根据姿态角的变化来进行具体的动作识别或姿态判断
            System.out.println("Roll Angle: " + rollAngle);
            System.out.println("Pitch Angle: " + pitchAngle);
            System.out.println("Yaw Angle: " + yawAngle);

            try {
                Thread.sleep(100); // 模拟每0.1秒获取一次数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


