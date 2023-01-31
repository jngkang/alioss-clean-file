package com.oss.constant;

import java.util.List;

/**
 * 图片匹配规则
 *
 * @author wangwenkang
 * @date 2023-01-31
 */
public interface RegExpConstant {
    List<String> REG_LIST = List.of(
            AliOSSConstant.OSS_PRE + "([^.]{1,}.[0-9a-z]{1,})"
    );
    // 匹配阿里云图床中有的所有文件
    // AliOSSConstant.OSS_PRE + "([^.]{1,}(.jpg|.txt|.bat|.gif|.png|.jpeg|.webp))"
    // 匹配所有的阿里云OSS链接
    //AliOSSConstant.OSS_PRE + "([^.]{1,}.[0-9a-z]{1,})"
}
