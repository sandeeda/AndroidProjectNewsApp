package org.n01472825.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.n01472825.newsapplication.models.Article;
import org.n01472825.newsapplication.models.CustomViewHolder;
import org.n01472825.newsapplication.models.Source;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Article> articles;
    private SelectListener listener;

    public CustomAdapter(Context context, List<Article> articles, SelectListener listener) {
        this.context = context;
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ////Comment next 5 lines later
//        articles.get(position).setTitle("Dummy_Title");
//        Source source = articles.get(position).getSource();
//        source.setName("Dummy_Source");
//        articles.get(position).setSource(source);
//        articles.get(position).setUrlToImage(null);

        holder.getText_title().setText(articles.get(position).getTitle());
        holder.getText_source().setText(articles.get(position).getSource().getName());

        if(articles.get(position).getUrlToImage()!=null){
            Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.getImg_headline());
        }
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(articles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
