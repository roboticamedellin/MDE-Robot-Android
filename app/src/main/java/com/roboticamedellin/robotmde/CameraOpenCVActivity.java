package com.roboticamedellin.robotmde;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
    Base on:
    http://www.discoversdk.com/blog/opencv-with-android-studio

    Take present the following issue:
    https://stackoverflow.com/questions/35090838/no-implementation-found-for-long-org-opencv-core-mat-n-mat-error-using-opencv
 */

public class CameraOpenCVActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;

    @BindView(R.id.photo_button)
    Button imageButton;

    @BindView(R.id.frame_image_view)
    ImageView frameImageView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    Mat imageMat = new Mat();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_opencv);
        ButterKnife.bind(this);
    }

    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @OnClick(R.id.photo_button)
    public void OnPhotoButtonClicked() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");

        Mat source = new Mat();
        Mat dest = new Mat();
        Utils.bitmapToMat(photo, source);

        Imgproc.cvtColor(source, dest, Imgproc.COLOR_RGB2GRAY);
        Bitmap btmp = Bitmap.createBitmap(dest.width(), dest.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dest, btmp);
        frameImageView.setImageBitmap(btmp);
    }
}
