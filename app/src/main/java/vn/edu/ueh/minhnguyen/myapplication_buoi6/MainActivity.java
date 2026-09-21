package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnArticleClickListener {
    private static final String STATE_ARTICLES = "state_articles";

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<Article> articleList = new ArrayList<>();

    // Launcher mở DetailActivity và nhận kết quả trả về (registerForActivityResult như trong slide)
    ActivityResultLauncher<Intent> detailLauncher;

    @SuppressWarnings({"unchecked", "deprecation"})
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

        // 1. Dữ liệu: khi xoay màn hình thì lấy lại list cũ (giữ nguyên views), còn không thì thêm bài mẫu
        if (savedInstanceState != null) {
            ArrayList<Article> saved =
                    (ArrayList<Article>) savedInstanceState.getSerializable(STATE_ARTICLES);
            if (saved != null) {
                articleList = saved;
            }
        }
        if (articleList.isEmpty()) {
            createSampleData();
        }

        // 2. Đăng ký launcher: nhận lại Article (đã tăng views) từ DetailActivity
        detailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        int position = data.getIntExtra(DetailActivity.EXTRA_POSITION, -1);
                        Article updated = (Article) data.getSerializableExtra(DetailActivity.EXTRA_ARTICLE);
                        if (updated != null && position >= 0 && position < articleList.size()) {
                            articleList.set(position, updated);          // cập nhật lại vào list
                            if (myAdapter != null) {
                                myAdapter.notifyItemChanged(position);   // vẽ lại đúng dòng đó
                            }
                        }
                    }
                });

        // 3. Tạo RecyclerView + Adapter + LayoutManager (như slide 04_5)
        recyclerView=findViewById(R.id.recyclerView);
        myAdapter =new MyAdapter(this,articleList,this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // đường kẻ giữa các bài
    }

    /** Thêm thủ công vài bài viết mẫu khi chạy lần đầu (âm nhạc, đời sống, mẹo vặt). */
    private void createSampleData() {
        // ---------- 1. Âm nhạc – nghe nhạc để tập trung ----------
        articleList.add(new Article(
                "\uD83C\uDFA7 Nghe nhạc thế nào để học và làm việc tập trung hơn?",
                "Bạn có bao giờ bật một playlist rồi chợt nhận ra mình chẳng làm được gì vì mải hát theo? Không phải bài nhạc nào cũng hợp để làm việc đâu!\n\n"
                        + "\uD83C\uDFB6 Ưu tiên nhạc không lời: nhạc cổ điển, lo-fi, jazz nhẹ hay nhạc cụ acoustic. Khi bài hát có lời, não phải xử lý thêm ngôn ngữ nên rất dễ bị xao nhãng, nhất là lúc bạn đang đọc hay viết.\n\n"
                        + "\uD83D\uDD09 Giữ âm lượng vừa phải: chỉ như một \"nền âm thanh\" chứ không lấn át suy nghĩ của bạn.\n\n"
                        + "\uD83D\uDD01 Đừng đổi bài liên tục: hãy chọn sẵn một playlist dài, bật lên rồi để yên. Việc lướt tìm bài mới cũng là một kiểu mất tập trung.\n\n"
                        + "\uD83D\uDD14 Biến playlist thành \"tín hiệu\": nếu lần nào ngồi vào bàn học bạn cũng bật cùng một danh sách nhạc, lâu dần chỉ cần nghe những giai điệu quen thuộc là đầu óc tự động vào guồng.\n\n"
                        + "Thử ngay hôm nay và xem 25 phút tập trung của bạn thay đổi ra sao nhé!",
                R.drawable.cover_1));

        // ---------- 2. Đời sống – buổi sáng ----------
        articleList.add(new Article(
                "\uD83C\uDF05 15 phút buổi sáng: thói quen nhỏ, ngày mới nhẹ nhàng hơn",
                "Nhiều người bắt đầu ngày mới bằng việc với tay lấy điện thoại và lướt mạng xã hội. Chỉ vài phút sau, đầu óc đã đầy thông tin và cảm giác bị cuốn đi trước khi ngày bắt đầu.\n\n"
                        + "Hãy thử dành 15 phút đầu tiên cho chính mình:\n\n"
                        + "\u2022 Uống một ly nước: sau một đêm dài, cơ thể cần được bổ sung nước.\n"
                        + "\u2022 Mở cửa sổ, đón chút ánh sáng tự nhiên: giúp bạn tỉnh táo hơn mà không cần thêm cà phê.\n"
                        + "\u2022 Vươn vai, giãn cơ vài phút: máu lưu thông tốt hơn, người nhẹ hẳn.\n"
                        + "\u2022 Viết ra 3 việc quan trọng nhất trong ngày: biết mình cần làm gì sẽ đỡ rối hơn nhiều.\n"
                        + "\u2022 Tạm gác điện thoại: hãy để tin nhắn và thông báo chờ bạn 15 phút.\n\n"
                        + "Không cần làm hoàn hảo, bắt đầu với một hoặc hai việc là đủ. Những thay đổi nhỏ nhưng đều đặn thường bền hơn những kế hoạch lớn chỉ kéo dài vài ngày. \u2600\uFE0F",
                R.drawable.cover_3));

        // ---------- 3. Mẹo vặt – nhà bếp ----------
        articleList.add(new Article(
                "\uD83D\uDCA1 5 mẹo vặt nhà bếp đơn giản mà cực hữu ích",
                "Vài mẹo nhỏ dưới đây dùng toàn đồ có sẵn trong nhà, thử một lần là nhớ mãi!\n\n"
                        + "\uD83E\uDDC5 Cắt hành không cay mắt: cho hành vào ngăn mát tủ lạnh khoảng 15\u201330 phút trước khi cắt. Hành lạnh sẽ bớt bay hơi cay, mắt bạn đỡ cay hơn.\n\n"
                        + "\uD83C\uDF3F Giữ rau thơm tươi lâu: rửa sạch, để thật ráo, gói trong khăn giấy hơi ẩm rồi cho vào hộp kín để ngăn mát. Rau xanh lâu hơn thấy rõ.\n\n"
                        + "\uD83C\uDF4C Chuối chín chậm lại: bọc kín phần cuống chuối bằng màng bọc thực phẩm. Nải chuối sẽ để được thêm vài ngày.\n\n"
                        + "\u2615 Làm sạch vết ố trong cốc trà, cốc cà phê: rắc một ít baking soda, thêm vài giọt nước cốt chanh, chà nhẹ bằng bọt biển rồi rửa lại với nước.\n\n"
                        + "\u2744\uFE0F Khử mùi tủ lạnh: đặt một chén nhỏ đựng baking soda hoặc bã cà phê đã phơi khô vào góc tủ, thay mới sau khoảng một tháng.\n\n"
                        + "Bạn còn mẹo nào hay không? Hãy chia sẻ cho mọi người cùng biết nhé!",
                R.drawable.cover_5));

        // ---------- 4. Âm nhạc – ukulele ----------
        articleList.add(new Article(
                "\uD83C\uDFB8 Ukulele: nhạc cụ dễ chơi nhất cho người mới bắt đầu?",
                "Bạn mê âm nhạc nhưng ngại học đàn vì sợ khó? Ukulele có thể là điểm khởi đầu tuyệt vời đấy!\n\n"
                        + "\uD83C\uDFBC Nhỏ gọn, nhẹ nhàng: đàn chỉ có 4 dây (G \u2013 C \u2013 E \u2013 A) và kích thước bé, dễ mang theo đi cà phê, đi du lịch hay đi cắm trại.\n\n"
                        + "\uD83D\uDC46 Dễ làm quen: dây ukulele mềm hơn dây thép của guitar nên ngón tay ít đau hơn, và nhiều hợp âm chỉ cần bấm 1\u20133 ngón.\n\n"
                        + "\uD83C\uDFB5 Chỉ với 4 hợp âm C \u2013 Am \u2013 F \u2013 G, bạn đã có thể đệm hát rất nhiều bài quen thuộc. Đây là một trong những chuỗi hợp âm phổ biến nhất trong nhạc pop.\n\n"
                        + "\u23F0 Luyện mỗi ngày 10\u201315 phút: đều đặn quan trọng hơn luyện thật dài. Sau vài tuần, ngón tay sẽ quen dần và bạn có thể đệm được bài hát đầu tiên của mình.\n\n"
                        + "Tự hát theo tiếng đàn do chính mình chơi \u2014 cảm giác ấy vui lắm! Bạn muốn thử bài nào đầu tiên?",
                R.drawable.cover_2));

        // ---------- 5. Đời sống – giấc ngủ ----------
        articleList.add(new Article(
                "\uD83D\uDE34 Ngủ ngon hơn với 5 thói quen buổi tối",
                "Thức khuya, lướt điện thoại đến tận lúc nhắm mắt rồi sáng hôm sau uể oải\u2026 nghe quen không? Một giấc ngủ ngon bắt đầu từ chính buổi tối của bạn.\n\n"
                        + "\u23F0 Đi ngủ và thức dậy đúng giờ: kể cả cuối tuần, để đồng hồ sinh học của cơ thể ổn định hơn.\n\n"
                        + "\uD83D\uDCF5 Tránh màn hình khoảng 30 phút trước khi ngủ: ánh sáng xanh và tin tức khiến đầu óc khó \"tắt máy\".\n\n"
                        + "\u2615 Hạn chế cà phê, trà đặc vào chiều tối: caffeine có thể còn tác dụng trong cơ thể nhiều giờ sau khi uống.\n\n"
                        + "\uD83C\uDF19 Giữ phòng tối, mát và yên tĩnh: rèm dày hoặc bịt mắt sẽ giúp ích nhiều.\n\n"
                        + "\uD83D\uDCD6 Tạo một \"nghi thức\" thư giãn: đọc vài trang sách, nghe nhạc nhẹ hay hít thở sâu vài phút trước khi ngủ.\n\n"
                        + "Không cần làm tất cả cùng lúc, hãy thử một thói quen trong một tuần rồi thêm dần. Chúc bạn ngủ ngon! \uD83D\uDCA4",
                R.drawable.cover_4));

        // ---------- 6. Mẹo vặt – pin điện thoại ----------
        articleList.add(new Article(
                "\uD83D\uDD0B 6 mẹo giữ pin điện thoại bền hơn mỗi ngày",
                "Pin tụt nhanh đúng lúc cần dùng nhất là nỗi khổ chung của rất nhiều người. Vài thay đổi nhỏ dưới đây sẽ giúp pin trụ lâu hơn:\n\n"
                        + "\uD83D\uDD06 Giảm độ sáng màn hình hoặc bật chế độ tự điều chỉnh: màn hình là một trong những thành phần tốn pin nhất.\n\n"
                        + "\uD83C\uDF11 Bật chế độ tối (Dark mode): với màn hình OLED, điểm ảnh màu đen gần như không phát sáng nên tiết kiệm điện.\n\n"
                        + "\uD83D\uDD15 Tắt thông báo và ứng dụng chạy nền không cần thiết.\n\n"
                        + "\uD83D\uDCF6 Tắt Wi-Fi, Bluetooth, GPS khi không dùng: máy đỡ phải liên tục dò tìm tín hiệu.\n\n"
                        + "\uD83C\uDF21\uFE0F Tránh để máy quá nóng: đừng để điện thoại dưới nắng gắt hay trong xe đóng kín, nhiệt độ cao làm chai pin nhanh hơn.\n\n"
                        + "\u26A1 Sạc thông minh: nên giữ mức pin trong khoảng 20\u201380% khi có thể, hạn chế để máy tụt về 0% thường xuyên và dùng bộ sạc chính hãng hoặc uy tín.\n\n"
                        + "Bạn áp dụng thử vài mẹo rồi xem thời lượng pin cải thiện ra sao nhé! \uD83D\uDCF1",
                R.drawable.cover_6));
    }

    /** Được gọi (từ ViewHolder → Adapter) khi người dùng click vào một bài viết. */
    @Override
    public void onArticleClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_ARTICLE, articleList.get(position)); // gửi cả bài viết
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);                 // gửi vị trí để cập nhật lại đúng dòng
        detailLauncher.launch(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ARTICLES, articleList);
    }
}