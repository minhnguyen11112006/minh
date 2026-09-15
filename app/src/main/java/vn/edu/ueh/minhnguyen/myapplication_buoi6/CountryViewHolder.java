package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private MyAdapter adapter;
    public TextView tid;
    public TextView tcountry;
    public CountryViewHolder(View item, MyAdapter adapter){
        super(item);
        this.adapter=adapter;
        this.tid=item.findViewById(R.id.tid);
        this.tcountry=item.findViewById(R.id.tcountry);
        item.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String msg= tid.getText()+" | "+ tcountry.getText();
        Toast.makeText(v.getContext(),msg,Toast.LENGTH_SHORT).show();;
    }
}
