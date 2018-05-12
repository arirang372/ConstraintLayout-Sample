package com.an.constraintlayout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.an.constraintlayout.R;
import com.an.constraintlayout.model.Video;
import com.an.constraintlayout.utils.BaseUtils;
import com.an.constraintlayout.databinding.VideoListWithConstraintAdapterBinding;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

public class VideoListWithConstraintAdapter extends RecyclerView.Adapter<VideoListWithConstraintAdapter.CustomViewHolder> {

    private String YOUTUBE_API_KEY = "AIzaSyCZY8Vnw_6GcJcESL-NilTZDMSvg9ViLt8";

    private Context context;
    private List<Video> videoList;

    private int screenWidth;
    private int screenHeight;
    private final double ASPECT_RATIO_WIDTH = 61.1;
    private final double ASPECT_RATIO_HEIGHT = 20.27;

    public VideoListWithConstraintAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
        this.screenWidth = BaseUtils.getScreenWidth(context);
        this.screenHeight = BaseUtils.getScreenHeight(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        VideoListWithConstraintAdapterBinding itemBinding = VideoListWithConstraintAdapterBinding.inflate(layoutInflater, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(itemBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.binding.vidFrame.setVisibility(View.VISIBLE);
                holder.binding.btnPlay.setImageResource(R.drawable.ic_play);
            }
        };

        holder.binding.youtubeThumbnail.initialize(YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                final Video item = getItem(position);
                youTubeThumbnailLoader.setVideo(item.getKey());
                youTubeThumbnailView.setImageBitmap(null);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //write something for failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public Video getItem(int position) {
        return videoList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private final VideoListWithConstraintAdapterBinding binding;
        public CustomViewHolder(VideoListWithConstraintAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            ViewGroup.LayoutParams lp = binding.youtubeThumbnail.getLayoutParams();

            Double width = Math.ceil((ASPECT_RATIO_WIDTH * screenWidth)/100);
            Double height = Math.ceil((ASPECT_RATIO_HEIGHT * screenHeight)/100);
            lp.width = width.intValue();
            lp.height = height.intValue();
            binding.youtubeThumbnail.setLayoutParams(lp);
        }
    }
}