package com.example.project_4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;

    private ListView listView;
    private View prevSelectedCanvas;
    private View prevSelectedProgressBar;
    private ImageView prevSelectedIcon;
    private TextView prevSelectedArtistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.music_list);

        String[] musicTitles = {"musicTitles1", "musicTitles2", "musicTitles3", "musicTitles4", "musicTitles5", "musicTitles6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list, R.id.music_title, musicTitles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prevSelectedCanvas != null) {
                    prevSelectedCanvas.setBackgroundColor(Color.WHITE);
                }
                if (prevSelectedProgressBar != null) {
                    prevSelectedProgressBar.setVisibility(View.GONE);
                }
                if (prevSelectedIcon != null) {
                    prevSelectedIcon.setImageResource(R.drawable.music_icon);
                }
                if (prevSelectedArtistName != null) {
                    prevSelectedArtistName.setVisibility(View.VISIBLE);
                }

                RelativeLayout selectedCanvas = view.findViewById(R.id.item_canvas);
                selectedCanvas.setBackgroundColor(Color.rgb(246, 205, 79));
                prevSelectedCanvas = selectedCanvas;

                View progressBar = view.findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                prevSelectedProgressBar = progressBar;

                ImageView icon = view.findViewById(R.id.music_icon);
                icon.setImageResource(R.drawable.music_click_icon);
                prevSelectedIcon = icon;

                TextView artistName = view.findViewById(R.id.artist_name);
                artistName.setVisibility(View.GONE);
                prevSelectedArtistName = artistName;
            }
        });
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "외부 저장소 읽기 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "외부 저장소 읽기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}