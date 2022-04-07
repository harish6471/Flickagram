package com.example.flickagram.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickagram.Adapter.PhotosAdapter;
import com.example.flickagram.MainActivity;
import com.example.flickagram.Model.Photo;
import com.example.flickagram.Model.Root;
import com.example.flickagram.Network.NetworkRetrofit;
import com.example.flickagram.Network.PhotosApiInterface;
import com.example.flickagram.R;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private PhotosAdapter photosAdapter;
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Group group;
    private onClickItem onClickItem;





    public HomeFragment(HomeFragment.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(requireContext()).inflate(R.layout.list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = requireView().findViewById(R.id.rcDataPhotos);
        progressBar = requireView().findViewById(R.id.progressBar);
        group = requireView().findViewById(R.id.groupLoad);



        photosAdapter = new PhotosAdapter(new ArrayList(), photo -> {
            onClickItem.onClick();
            Bundle bundle = new Bundle();
            bundle.putParcelable("Photo", photo);
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(bundle);

            requireActivity().
                    getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame,
                            fragment, "").addToBackStack("").commit();
        });

        recyclerView.setAdapter(photosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadData();




//        photoViewModal=new ViewModelProvider(this).get(PhotoViewModal.class);
//        loadData();
//        photoViewModal.getAllPhotos().observe(getViewLifecycleOwner(), new Observer<List<Photo>>() {
//            @Override
//            public void onChanged(List<Photo> photoList) {
//                    recyclerView.setAdapter(photosAdapter);
//               photosAdapter.setPhotoList(photoList);
//
//                Log.d("main", "onChanged: "+photoList);
//            }
//


    }




    private void loadData() {
        NetworkRetrofit.getInstance().getPhotosApiInterface()
        .getMethodData("flickr.photos.getRecent", null, null)
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {
                        if (response.isSuccessful() && response.body().getStat().equals("ok"))
                            try {

//                                photoRepository.insert(response.body().getPhotos().getPhoto());
                                photosAdapter.setPhotoList(response.body().getPhotos().getPhoto());
                            } catch (Exception e) {

                            }
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        group.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }



    public interface onClickItem {
        void onClick();
    }
}




