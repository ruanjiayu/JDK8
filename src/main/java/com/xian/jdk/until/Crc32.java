package com.xian.jdk.until;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * @Description:
 * @Author: summer
 * @CreateDate: 2023/2/20 19:27
 * @Version: 1.0.0
 */
public class Crc32 {
    
    /**
     * 本地文件
     *
     * @param filePath
     * @return
     */
    public static String localFileCRC32(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            return fileCRC(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建一个临时文件
     *
     * @param url 远端文件Url
     * @return File
     */
    public static String urlFileCRC32(String url) {
        try {
            //对本地文件命名
            String fileName = "otaRuanjiayu" + System.currentTimeMillis();
            // 创建一个临时路径
            File file = File.createTempFile("file", fileName);
            //下载
            URL urlfile = new URL(url);

            try (InputStream inStream = urlfile.openStream();
                 OutputStream os = new FileOutputStream(file)) {
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                return fileCRC(new FileInputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 文件流处理
     *
     * @param inputStream
     * @return
     */
    public static String fileCRC(FileInputStream inputStream) {
        CRC32 crc32 = new CRC32();
        String crcStr = null;
        try (CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, crc32)) {
            while (checkedInputStream.read() != -1) {
            }
            crcStr = Long.toHexString(crc32.getValue());
//            crcStr = String.valueOf(crc32.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crcStr;
    }

    public static void main(String[] args) {

        String path = "/Users/xian/Desktop/tiaosheng_4g_fw_v2.6.bin";
//        String path1 = "/Users/xian/Desktop/tiaosheng_4g_fw_v3.3.bin_aa";
//        String path2 = "/Users/xian/Desktop/tiaosheng_4g_fw_v3.3.bin_ab";
        System.out.println(localFileCRC32(path));
//        System.out.println(localFileCRC32(path1));
//        System.out.println(localFileCRC32(path2));



        String code = "20d0b4b8";

//        String path2 = "https://publicduoguan.oss.duoguan.cc/ota/tiaosheng_4g_fw_v1.2.bin";
//        System.out.println(urlFileCRC32(path2));


//        System.out.println(Integer.parseInt(path, 16));
    }
}
