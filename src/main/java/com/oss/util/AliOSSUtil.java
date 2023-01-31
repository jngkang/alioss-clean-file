package com.oss.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.oss.constant.AliOSSConstant;
import com.oss.constant.PathConstant;

import java.util.ArrayList;
import java.util.List;

public class AliOSSUtil {

    /**
     * 查询指定路径下所有文件名称
     * <br>
     * 注：首先会获取根文件的名称
     *
     * @return java.com.oss.util.List<java.lang.String> 返回该路径下所有文件的完整路径
     */
    public static List<String> listFileName() {
        List<String> res = new ArrayList<>();
        // 一次读取的文件数[100, 1000]
        final int maxKeys = 1000;
        OSS ossClient = new OSSClientBuilder().build(AliOSSConstant.END_POINT, AliOSSConstant.ACCESS_KEY_ID, AliOSSConstant.ACCESS_KEY_SECRET);
        try {
            ObjectListing objectListing;
            String nextMarker = null;
            do {
                objectListing = ossClient.listObjects(new ListObjectsRequest(AliOSSConstant.BUCKET_NAME)
                        .withPrefix(PathConstant.keyPrefix).withMarker(nextMarker).withMaxKeys(maxKeys));
                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary objectSummary : sums) {
                    String name = objectSummary.getKey();
                    // 过滤掉文件夹
                    if (name.charAt(name.length() - 1) != '/') {
                        res.add(name);
                    }
                }
                nextMarker = objectListing.getNextMarker();
            } while (objectListing.isTruncated());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return res;
    }

    /**
     * 批量删除文件或目录
     *
     * @param keys 相对于阿里云bucket存储空间的文件的完整路径
     * @return java.com.oss.util.List<java.lang.String> 返回已删除文件的完整路径
     */
    public static List<String> deleteFiles(List<String> keys) {
        if (keys == null || keys.size() == 0) {
            return null;
        }
        OSS ossClient = new OSSClientBuilder().build(AliOSSConstant.END_POINT, AliOSSConstant.ACCESS_KEY_ID, AliOSSConstant.ACCESS_KEY_SECRET);
        List<String> deletedObjects = null;
        try {
            // 删除文件。
            DeleteObjectsResult deleteObjectsResult = ossClient
                    .deleteObjects(new DeleteObjectsRequest(AliOSSConstant.BUCKET_NAME)
                            .withKeys(keys));
            deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return deletedObjects;
    }

}