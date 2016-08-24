package com.xunbaola.record;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xunbaola.record.utils.PictureUtils;

/**
 * Created by Administrator on 2016/8/19.
 */
public class ImageFragment extends DialogFragment {
    public static final String EXTRA_IMAGE_PATH="com.xunbaola.record.image_path";
    public static final String EXTRA_IMAGE_DEGREE="com.xunbaola.record.image_degree";
    private ImageView mImageView;
    public static ImageFragment newInstance(String imagePath,int degree){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH,imagePath);
        args.putInt(EXTRA_IMAGE_DEGREE,degree);
        ImageFragment fragment=new ImageFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImageView=new ImageView(getActivity());
        String path=getArguments().getString(EXTRA_IMAGE_PATH);
        int degree=getArguments().getInt(EXTRA_IMAGE_DEGREE);
        BitmapDrawable image= PictureUtils.getScaledDrawable(getActivity(),path);
        if (degree==90) {
            image=new BitmapDrawable(PictureUtils.rotateBitmap(image.getBitmap(),degree));
        }
        mImageView.setImageDrawable(image);
        return mImageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}

