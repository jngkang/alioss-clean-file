package com.oss.constant;

/**
 * @author JngKang
 * @date 2022-06-08 10:04
 */
public interface AliOSSConstant {
    /**
     * #存储区域 例：oss-cn-shanghai.aliyuncs.com
     */
    String END_POINT = "oss-cn-hangzhou.aliyuncs.com";

    /**
     * yourAccessKeyId
     */
    String ACCESS_KEY_ID = "LTAI5t5fKAcwkgwgnLQxucRN";

    /**
     * yourAccessKeySecret
     */
    String ACCESS_KEY_SECRET = "Y7SYT20TkZnbn2XP8zu7HJUgzQYmlf";

    /**
     * #bucket存储空间名称
     */
    String BUCKET_NAME = "jngkang-images";

    /**
     * 阿里云OSS链接前缀
     */
    String OSS_PRE = "https://" + AliOSSConstant.BUCKET_NAME + "."+AliOSSConstant.END_POINT +"/";
}
