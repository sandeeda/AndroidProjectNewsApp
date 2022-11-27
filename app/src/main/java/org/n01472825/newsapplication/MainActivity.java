package org.n01472825.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.n01472825.newsapplication.models.Article;
import org.n01472825.newsapplication.models.NewsApiResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;

    Button b1,b2,b3,b4,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching top stories for you");
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);

        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles for " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //return false;

                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener, "general", newText);
                return true;
            }
        });


        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Article> listOfArticles, String message) {
            if(listOfArticles.isEmpty()){
                Toast.makeText(MainActivity.this,"No news found",Toast.LENGTH_SHORT);
            }
            showNews(listOfArticles);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An error occured",Toast.LENGTH_SHORT);
        }
    };

    private void showNews(List<Article> listOfArticles) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, listOfArticles, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(Article articles) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", articles));
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category = button.getText().toString();
        dialog.setTitle("Fetching new articles of "+ category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, category, null);

    }
}