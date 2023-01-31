package com.oss.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileUtilTest {

    @Test
    void getImagesName() throws IOException {
        FileUtil.getImagesName().forEach(System.out::println);
    }
}