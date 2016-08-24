package com.xunbaola.record.camera;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xunbaola.record.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


/**
 * Created by Administrator on 2016/8/11.
 */
public class RecordCameraFrament extends Fragment {
    private static final String TAG="RecordCameraFrament";
    public static final String EXTRA_RECORD_PICTURE_TITLE = "com.xunbaola.record.record_picture_title";
    public static final String EXTRA_PHOTO_FILENAME = "com.xunbaola.record.photo_filename";
    public static final String EXTRA_PHOTO_DEGREE = "com.xunbaola.record.photo_degree";

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private View mProgressContainer;
    private String mPictureTitle;
    private int mDegree;

    private Camera.ShutterCallback mShutterCallback=new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            int rotation = getActivity().getResources().getConfiguration().orientation;
            mDegree= 90 * rotation;
            Log.d(TAG,"mDegree= "+mDegree);
            mProgressContainer.setVisibility(View.VISIBLE);
        }
    };
    private Camera.PictureCallback mJpegCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String filename=UUID.randomUUID().toString()+"_"+mPictureTitle+".jpg";
            Log.d(TAG,"filename= "+filename);
            FileOutputStream os=null;
            boolean success=true;
            try {
                os=getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                os.write(data);
            } catch (Exception e) {
                Log.e(TAG,"error writing to file " +filename,e);
                success=false;
            }finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        Log.e(TAG,"error closing file " +filename,e);
                        success=false;
                    }
                }
            }
            if (success) {
                Intent i=new Intent();
                i.putExtra(EXTRA_PHOTO_FILENAME,filename);
                i.putExtra(EXTRA_PHOTO_DEGREE,mDegree);
                getActivity().setResult(Activity.RESULT_OK,i);
            }else {
                getActivity().setResult(Activity.RESULT_CANCELED);
            }
            getActivity().finish();
        }
    };
    /**
     * 创建实例，设置参数
     *
     * @param pictureTitle pictureTitle
     * @return RecordCameraFrament
     */
    public static RecordCameraFrament newInstance(String pictureTitle) {
        RecordCameraFrament fragment = new RecordCameraFrament();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_RECORD_PICTURE_TITLE,pictureTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPictureTitle=getArguments().getString(EXTRA_RECORD_PICTURE_TITLE);
        Log.d(TAG,"mPictureTitle= "+mPictureTitle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_record_camera,container,false);
        Button takePictureButton= (Button) view.findViewById(R.id.record_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.takePicture(mShutterCallback,null,mJpegCallback);
                }
            }
        });
        mSurfaceView= (SurfaceView) view.findViewById(R.id.record_camera_surfaceView);
        SurfaceHolder holder=mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mCamera!=null){
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        Log.e(TAG,"Error setting up preview display",e);
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera==null) return;
                Camera.Parameters parameters=mCamera.getParameters();
                Camera.Size s=getBestSupportedSize(parameters.getSupportedPreviewSizes(),width,height);
                parameters.setPreviewSize(s.width,s.height);
                s=getBestSupportedSize(parameters.getSupportedPictureSizes(),width,height);
                parameters.setPictureSize(s.width,s.height);
                mCamera.setParameters(parameters);
                if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
                    mCamera.setDisplayOrientation(90);
                }
                try{
                    mCamera.startPreview();
                }catch (Exception e){

                        mCamera.release();
                        mCamera=null;
                        Log.e(TAG,"could not start preview",e);

                }


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mCamera!=null){
                    mCamera.stopPreview();
                }
            }
        });
        mProgressContainer=view.findViewById(R.id.record_camera_progressContainer);
        mProgressContainer.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
            mCamera=Camera.open(0);
        }else {
            mCamera=Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.release();
            mCamera=null;
        }
    }
    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes,int width,int height){
        Camera.Size bestSize=sizes.get(0);
        int largestSize=bestSize.width * bestSize.height;
        for (Camera.Size s:sizes){
            int area=s.width*s.height;
            if (area>largestSize){
                bestSize=s;
                largestSize=area;
            }
        }
        return bestSize;
    }
}
