package com.oss.util;

import org.junit.jupiter.api.Test;

import java.util.List;

class AliOSSUtilTest {

    @Test
    void listFileName() {
        List<String> res = AliOSSUtil.listFileName();
        res.forEach(System.out::println);
    }

    @Test
    void deleteFiles() {
        List<String> keys = List.of("2.png");
        AliOSSUtil.deleteFiles(keys).forEach(System.out::println);
    }

}