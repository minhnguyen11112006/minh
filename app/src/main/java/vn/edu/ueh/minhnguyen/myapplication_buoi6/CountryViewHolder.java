package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private vn.edu.ueh.minhnguyen.myapplication_buoi6.MyAdapter adapter;
    public TextView ttitle;
    public TextView tcontent;
    public TextView tviews;
    public ImageView imgCover;

    public CountryViewHolder(View item, vn.edu.ueh.minhnguyen.myapplication_buoi6.MyAdapter adapter){
        super(item);
        this.adapter=adapter;
        this.ttitle=item.findViewById(R.id.ttitle);
        this.tcontent=item.findViewById(R.id.tcontent);
        this.tviews=item.findViewById(R.id.tviews);
        this.imgCover=item.findViewById(R.id.imgCover);
        item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Báo cho MainActivity biết dòng nào vừa được click (MainActivity sẽ mở DetailActivity)
        int position = getBindingAdapterPosition();
        if (position != RecyclerView.NO_POSITION && adapter.getListener() != null) {
            adapter.getListener().onArticleClick(position);
        }
    }
}
