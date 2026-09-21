package vn.edu.ueh.minhnguyen.myapplication_buoi6;

import java.io.Serializable;

/**
 * Lớp dữ liệu Article: title, content, imgCover (id ảnh trong res/drawable), views.
 * implements Serializable để đóng gói được vào Intent (putExtra) và gửi qua lại giữa các Activity.
 */
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private int imgCover;   // ảnh cục bộ: R.drawable.xxx
    private int views;

    public Article(String title, String content, int imgCover) {
        this.title = title;
        this.content = content;
        this.imgCover = imgCover;
        this.views = 0;     // views ban đầu là 0
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getImgCover() { return imgCover; }
    public void setImgCover(int imgCover) { this.imgCover = imgCover; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    /** Tăng lượt xem thêm 1. */
    public void increaseViews() {
        views++;
    }
}
