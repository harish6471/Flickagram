package com.example.flickagram.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.flickagram.Model.Photo;
import com.example.flickagram.R;

import java.io.ByteArrayOutputStream;

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_details, container, false);
    }

    ImageView touchImageView;
    TextView textView,shareurl,shareimage;

    Bitmap p;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        touchImageView = requireView().findViewById(R.id.imageView);
        textView = requireView().findViewById(R.id.textview1);
        shareurl = requireView().findViewById(R.id.share_link);
        shareimage = requireView().findViewById(R.id.share_link1);




        Photo photo = requireArguments().getParcelable("Photo");

        String url = getUrl(photo);
        Glide.with(requireContext())
                .load(url)
                .into(touchImageView);
        textView.setText(photo.getTitle());



        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        p=resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        shareurl.setOnClickListener(V -> {
            Intent share_url = new Intent(Intent.ACTION_SEND);
            share_url.setType("text/plain");
            share_url.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
            share_url.putExtra(Intent.EXTRA_TEXT,getUrl(photo));
            startActivity(Intent.createChooser(share_url, "Share URL"));
        });

        shareimage.setOnClickListener(V -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            p.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(requireActivity().getContentResolver(), p, "Title", null);
            Uri imageUri = Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));
        });

    }


    private String getUrl(Photo photo) {
        return "https://live.staticflickr.com/" + photo.getServer()
                + "/" + photo.getId() + "_" + photo.getSecret() + "_w.jpg";
    }

//    public Fragment newInstance(int position) {
//        DetailsFragment detailsFragment = new DetailsFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("position", position);
//        detailsFragment.setArguments(bundle);
//        return detailsFragment;
//    }
}



