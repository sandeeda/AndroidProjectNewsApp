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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;

    Button b1,b2,b3,b4,b5,b6,b7;
    Button bc1,bc2,bc3,bc4,bc5,bc6,bc7;

    String category = "general";        String country = "us";


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

        bc1 = findViewById(R.id.btn_c1);
        bc1.setOnClickListener(this);

        bc2 = findViewById(R.id.btn_c2);
        bc2.setOnClickListener(this);

        bc3 = findViewById(R.id.btn_c3);
        bc3.setOnClickListener(this);

        bc4 = findViewById(R.id.btn_c4);
        bc4.setOnClickListener(this);

        bc5 = findViewById(R.id.btn_c5);
        bc5.setOnClickListener(this);

        bc6 = findViewById(R.id.btn_c6);
        bc6.setOnClickListener(this);

        bc7 = findViewById(R.id.btn_c7);
        bc7.setOnClickListener(this);


        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles for " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener, null, null, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;

//                RequestManager manager = new RequestManager(MainActivity.this);
//                manager.getNewsHeadLines(listener, "general", country, newText);
//                return true;
            }
        });


        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, category, country, null);
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
        List<String> categories = new ArrayList<>();

        categories.add("business");
        categories.add("entertainment");
        categories.add("general");
        categories.add("health");
        categories.add("science");
        categories.add("sports");
        categories.add("technology");
        if(categories.contains(button.getText().toString())){
            category = button.getText().toString();
        }

        HashMap<String, String>  countries = new HashMap<>();
        countries.put("USA", "us");
        countries.put("Malaysia", "my");
        countries.put("Canada", "ca");
        countries.put("India", "in");
        countries.put("Germany", "de");
        countries.put("Brazil", "br");
        countries.put("Iran","ir");
        if(countries.containsKey(button.getText().toString())){
            country = countries.get(button.getText().toString());
        }

        dialog.setTitle("Fetching new articles of "+ category + " from "+country);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadLines(listener, category, country, null);

    }
}