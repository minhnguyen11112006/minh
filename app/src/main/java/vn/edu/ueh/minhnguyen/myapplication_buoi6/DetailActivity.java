package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE =
            "vn.edu.ueh.nghibui.myapplication_b7.extra.ARTICLE";
    public static final String EXTRA_POSITION =
            "vn.edu.ueh.nghibui.myapplication_b7.extra.POSITION";
    private static final String STATE_ARTICLE = "state_article";

    private vn.edu.ueh.minhnguyen.myapplication_buoi6.Article article;
    private int position;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        position = intent.getIntExtra(EXTRA_POSITION, -1);

        if (savedInstanceState == null) {
            // Mở lần đầu: lấy bài viết từ Intent và TĂNG VIEWS thêm 1
            article = (vn.edu.ueh.minhnguyen.myapplication_buoi6.Article) intent.getSerializableExtra(EXTRA_ARTICLE);
            if (article != null) {
                article.increaseViews();
            }
        } else {
            // Xoay màn hình: lấy lại bài viết đã lưu, KHÔNG tăng views lần nữa
            article = (vn.edu.ueh.minhnguyen.myapplication_buoi6.Article) savedInstanceState.getSerializable(STATE_ARTICLE);
        }

        if (article == null) {
            finish();
            return;
        }

        // 2. Hiển thị chi tiết bài viết
        ImageView imgCover = findViewById(R.id.imgCover);
        TextView ttitle = findViewById(R.id.ttitle);
        TextView tviews = findViewById(R.id.tviews);
        TextView tcontent = findViewById(R.id.tcontent);
        Button btBack = findViewById(R.id.btBack);

        imgCover.setImageResource(article.getImgCover());
        ttitle.setText(article.getTitle());
        tviews.setText("Views: " + article.getViews());
        tcontent.setText(article.getContent());   // nội dung đầy đủ (không bị cắt)

        // Nút quay lại MainActivity
        btBack.setOnClickListener(v -> finish());

        // 3. Đặt sẵn kết quả trả về cho MainActivity.
        //    Làm ngay trong onCreate để dù người dùng bấm nút Back của điện thoại
        //    hay nút "Quay lại" thì MainActivity vẫn nhận được bài viết đã tăng views.
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_ARTICLE, article);
        replyIntent.putExtra(EXTRA_POSITION, position);
        setResult(RESULT_OK, replyIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ARTICLE, article);
    }
}
