package com.oss;

import com.oss.constant.AliOSSConstant;
import com.oss.constant.PathConstant;
import com.oss.util.AliOSSUtil;
import com.oss.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author JngKang
 * @date 2022-06-08 09:52
 */
public class Application {

    /**
     * 仅在阿里云OSS中存在的文件
     */
    private static List<String> onlyOSSList = new ArrayList<>();
    /**
     * 仅在本地存在的文件
     */
    private static List<String> onlyLocalList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // 获取配置路径下文件列表
        List<String> aliOSSList = AliOSSUtil.listFileName();
        System.out.println("阿里云图床：\"" + AliOSSConstant.BUCKET_NAME + "/" + PathConstant.keyPrefix + "\"，有文件：" + aliOSSList.size() + "个。");
        // 获取本地文件中引用的文件
        Set<String> imagesName = FileUtil.getImagesName();
        System.out.println("本地路径：\"" + PathConstant.LOCAL_PATH + "\"，引用文件：" + imagesName.size() + "个。");

        // 检索出本地有引用，OSS中无文件---手动验证
        for (String item : imagesName) {
            if (!aliOSSList.contains(item)) {
                onlyLocalList.add(AliOSSConstant.OSS_PRE + item);
            }
        }
        if (onlyLocalList.size() > 0) {
            System.out.println("本地已引用，但OSS中没有对应的文件：~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            onlyLocalList.forEach(System.out::println);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        // 检索出本地无引用，OSS中无文件---需删除
        for (String item : aliOSSList) {
            if (!imagesName.contains(item)) {
                onlyOSSList.add(item);
            }
        }
        if (onlyOSSList.size() > 0) {
            System.out.println("本地未引用的文件：++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            onlyOSSList.forEach(System.out::println);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            // 用户输入是否删除
            Scanner scanner = new Scanner(System.in);
            System.out.print("是否删除阿里云OSS中多余的文件(Y/N)：");
            String isDelete = scanner.next();
            if ("Y".equals(isDelete) || "y".equals(isDelete)) {
                List<String> alreadyDeleted = AliOSSUtil.deleteFiles(onlyOSSList);
                alreadyDeleted.forEach(System.out::println);
            }
        }
    }
}
