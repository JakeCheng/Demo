package com.example.android.demo.Utils;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * OSS上传图片
 */

public class OssUtils {
    /**
     * 图片上传OOS
     * @param mContext
     * @param bean
     * @param folder
     * @param path
     */
//    public static void setOSSDate(final Context mContext, UploadBean bean, final String folder, String path) {
//        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
//        final long time = System.currentTimeMillis();
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(getAccessKeyId(),getAccessKeySecret(),getToken());
//        final OSS oss = new OSSClient(mContext.getApplicationContext(), endpoint, credentialProvider);
//        PutObjectRequest put = new PutObjectRequest("kedouyun", folder + SharePreferenceUtil.getUserNo(mContext) + "/" + time + ".jpeg", path);
//        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                String url = "";
//                try {
//                    url = oss.presignPublicObjectURL("kedouyun", folder + SharePreferenceUtil.getUserNo(mContext) + "/" + time + ".jpeg");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Logger.error("url", url);
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Logger.error("ErrorCode", serviceException.getErrorCode());
//                    Logger.error("RequestId", serviceException.getRequestId());
//                    Logger.error("HostId", serviceException.getHostId());
//                    Logger.error("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
//    }
}
