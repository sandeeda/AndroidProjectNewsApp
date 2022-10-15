package org.n01472825.newsapplication;

import org.n01472825.newsapplication.models.Article;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<Article> listOfArticles, String message);
    void onError(String message);
}
