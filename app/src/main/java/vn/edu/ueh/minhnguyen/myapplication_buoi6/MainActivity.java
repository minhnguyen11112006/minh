package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btLoad;
    List countryList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        countryList.add("Vietnam");
        countryList.add("China");
        countryList.add("French");
        countryList.add("USA");

        recyclerView=findViewById(R.id.recyclerView);
        btLoad= findViewById(R.id.btLoad);
        btLoad.setOnClickListener(v -> {
            MyAdapter myAdapter =new MyAdapter(v.getContext(),countryList);
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(this)
            );
            recyclerView.setAdapter(myAdapter);
        });
    }
}