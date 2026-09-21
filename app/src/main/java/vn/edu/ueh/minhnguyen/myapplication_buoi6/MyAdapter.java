package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    /** Callback: báo cho Activity biết người dùng vừa click vào bài viết ở vị trí nào. */
    public interface OnArticleClickListener {
        void onArticleClick(int position);
    }

    private List<Article> articleList;
    private OnArticleClickListener listener;
    LayoutInflater mInflater;

    public MyAdapter(Context context, List<Article> list, OnArticleClickListener listener){
        mInflater=LayoutInflater.from(context);
        this.articleList=list;
        this.listener=listener;
    }

    public OnArticleClickListener getListener() {
        return listener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.country_layout,parent,false);
        CountryViewHolder holder=new CountryViewHolder(view,this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.ttitle.setText(article.getTitle());
        holder.tcontent.setText(article.getContent());
        holder.tviews.setText("Views: " + article.getViews());
        holder.imgCover.setImageResource(article.getImgCover());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
