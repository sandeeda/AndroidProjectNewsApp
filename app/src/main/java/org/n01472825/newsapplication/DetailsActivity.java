package org.n01472825.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.n01472825.newsapplication.models.Article;

public class DetailsActivity extends AppCompatActivity {

    Article articles;
    TextView txt_title, txt_author, txt_time, txt_content;
    ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_content = findViewById(R.id.text_detail_content);

        img_news = findViewById(R.id.img_detail_news);

        articles = (Article) getIntent().getSerializableExtra("data");

        txt_title.setText(articles.getTitle());
        txt_author.setText(articles.getAuthor());
        //txt_detail.setText(articles.getDescription());
        txt_time.setText(articles.getPublishedAt());
        txt_content.setText(articles.getDescription());

        Picasso.get().load(articles.getUrlToImage()).into(img_news);
    }
}