package com.example.initialphase.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.initialphase.Adapter.VideoAdapter;
import com.example.initialphase.R;
import com.example.initialphase.model.YouTubeVideos;

import java.util.Vector;

public class PaisesECulturasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paises_eculturas);

        recyclerView = findViewById(R.id.rvPaises);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/MtCMtC50gwY\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/45ETZ1xvHS0\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/idg6vW3vXtE\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope\" allowfullscreen></iframe>") );
        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
        recyclerView.setAdapter(videoAdapter);

    }
}
