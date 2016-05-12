package com.example.fotoramka.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.example.fotoramka.Adapter.SamplePagerAdapter;
import com.example.fotoramka.Model.WebDav;
import com.example.fotoramka.R;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends Activity {

    List<View> pages = new ArrayList<View>();
    ImageView _view;
    ArrayList<String> _names;
    List<Bitmap> _images;
    String _currentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallary_layout);

        Intent intent = getIntent();
        _names = intent.getStringArrayListExtra("files");
        _currentPath = intent.getStringExtra("file");

        ClearOtherFile(); // загрузка только изображений(список)
        LayoutInflater inflater = LayoutInflater.from(this);
        View page ;
        try {

                ImageTask task = new ImageTask();
                task.execute();
                _images = task.get();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        for (int i = 0; i < _images.size(); i++) {
            page = inflater.inflate(R.layout.image_fragment_layout, null);

            _view = (ImageView) page.findViewById(R.id.imgView);
            _view.setImageBitmap(_images.get(i));

            pages.add(page);
        }

        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(_names.indexOf(_currentPath));
        setContentView(viewPager);


    }

    private void ClearOtherFile() {
        ArrayList<String> list = new ArrayList<String>();
        for (String d : _names) {
            if (d.contains(".jpg") | d.contains(".png") | d.contains(".bmp"))
                list.add(d);
        }

        _names = list;
    }


    class ImageTask extends AsyncTask<Void, Void, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(Void... params) {

            List<Bitmap> result = new ArrayList<Bitmap>();
            try {
                String url = "https://webdav.yandex.ru";
                HttpClient _client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setHeader("Authorization", "OAuth "+ WebDav.CLIENT_SECRET);
                request.setHeader("User-Agent", "FotoRamka/0.0.1");

                for (int i = 0; i < _names.size(); i++) {
                    request.setURI(new URI(url + _names.get(i) + "?preview&size=XL"));

                    result.add(BitmapFactory.decodeStream(_client.execute(request).getEntity().getContent()));
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    }



}
