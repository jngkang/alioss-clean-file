package com.oss.util;

import com.oss.constant.PathConstant;
import com.oss.constant.RegExpConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取指定目录所有文件中阿里云OSS链接对应的文件路径名称
 *
 * @author JngKang
 * @date 2022-06-08 10:16
 */
public class FileUtil {

    private static Set<String> res = new HashSet<>();

    /**
     * 获取目录中文件已经引用的图片完整路径
     *
     * @return java.com.oss.util.List<java.lang.String>
     */
    public static Set<String> getImagesName() throws IOException {
        File file = new File(PathConstant.LOCAL_PATH);
        // 递归遍历，检索阿里云OSS链接
        fun(file);
        return res;
    }

    private static void fun(File file) throws IOException {
        if (file.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            // 读取文件内容的每一行，并进行遍历
            while ((line = reader.readLine()) != null) {
                // 获取链接中的文件路径名称
                for (String reg : RegExpConstant.REG_LIST) {
                    Pattern patten = Pattern.compile(reg);// 编译正则表达式
                    Matcher matcher = patten.matcher(line);// 指定要匹配的字符串
                    while (matcher.find()) { // 此处find()每次被调用后，会偏移到下一个匹配
                        // 获取匹配字符串中$1的值
                        res.add(matcher.group(1));
                    }
                }
            }
        }
        // 如果file是目录，则获取子文件列表，并对每个子文件进行相同的操作
        if (file.isDirectory() && !file.getName().equals(".git")) {
            File[] files = file.listFiles();
            for (File temp : files) {
                fun(temp);
            }
        }
    }
}
