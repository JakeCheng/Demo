package com.example.android.demo.Utils;

import android.app.Activity;
import android.content.Context;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 图片选择
 */

public class ImageChooseUtils {
    /**
     * 图片选择
     * type   1、头像 圆形
     *        2、正方形图片  HelpActivity
     *        3、原图
     * @param mContext
     * @param type   类型
     */
    public static void initImageChoose(Context mContext, int type) {
        int width, height;
        width = ScreenUtils.getScreenMetrics(mContext).widthPixels;
        switch (type) {
            case 1:
                PictureSelector.create((Activity) mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .imageSpanCount(3)
                        .previewImage(false)
                        .circleDimmedLayer(true)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .compress(true)
                        .scaleEnabled(false)
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .freeStyleCropEnabled(true)
                        .rotateEnabled(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case 2:
                PictureSelector.create((Activity) mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .imageSpanCount(3)
                        .previewImage(false)
                        .circleDimmedLayer(false)
                        .enableCrop(true)
                        .withAspectRatio(width, width)
                        .compress(true)
                        .scaleEnabled(false)
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .freeStyleCropEnabled(true)
                        .rotateEnabled(false)
                        .isDragFrame(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case 3:
                PictureSelector.create((Activity) mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .imageSpanCount(3)
                        .previewImage(false)
                        .enableCrop(false)
                        .compress(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(false)
                        .rotateEnabled(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
        }
    }


    /**
     * 多张图片选择
     * @param mContext
     * @param selectList  已选中的图片
     * @param number 个数
     */
    public static void initImageMoreChoose(Context mContext,List<LocalMedia> selectList,int number) {
        PictureSelector.create((Activity) mContext)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.MULTIPLE)
                .imageSpanCount(3)
                .maxSelectNum(number)
                .selectionMedia(selectList)
                .withAspectRatio(1, 1)
                .previewImage(false)
                .enableCrop(false)
                .compress(true)
                .showCropFrame(false)
                .showCropGrid(false)
                .freeStyleCropEnabled(false)
                .rotateEnabled(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
    /**
     * 选种图片之后操作
     */
//    if (resultCode == RESULT_OK) {
//        switch (requestCode) {
//            case PictureConfig.CHOOSE_REQUEST:
//                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                if (selectList.size() > 0) {
//                    LocalMedia media = selectList.get(0);
//                    if (media.isCompressed()) {
//                        path = media.getCompressPath();
//                    } else {
//                        if (media.isCut()) {
//                            path = media.getCutPath();
//                        } else {
//                            path = media.getPath();
//                        }
//                    }
//                    tempFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(new File(path),1200.0f);
//                    Bitmap bitmap = ScreenUtils.decodeUriAsBitmap(mContext, Uri.fromFile(tempFile));
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                    byte[] bytes=baos.toByteArray();
//                    ImageLoaderHelper.displayByteCircle(mContext,mAdapter.getImageView(),bytes,R.mipmap.user_pixmap);
//                }
//                break;
//        }
//    }
}
