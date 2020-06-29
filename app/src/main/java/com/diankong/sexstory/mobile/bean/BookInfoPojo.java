package com.diankong.sexstory.mobile.bean;

/**
 * =============================================
 * 湖南高信网络有限公司源代码，版权@归而然科技所有。
 * 项目: Civilized_Community 客户端
 * 作者：Created by 胡清 on 2019/3/20.
 * 描述：
 * =============================================
 */

public class BookInfoPojo {
    public String bookUrl;
    public String bookAuthor;

    public String bookIntroduc;
    public String bookType;
    public String bookNum;

    public BookInfoPojo(String bookUrl, String bookName, String bookAuthor) {
        this.bookUrl = bookUrl;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
    }

    public BookInfoPojo(String bookUrl, String bookName, String bookAuthor, String bookIntroduc, String bookType, String bookNum) {
        this.bookUrl = bookUrl;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookIntroduc = bookIntroduc;
        this.bookType = bookType;
        this.bookNum = bookNum;
    }

    public int articleid;
    public int chapters;
    public int bookId;
    public int chapterId;
    public int chapterorder;
    public String articlename;
    public String bookName;
    public String chapterName;
    public String typeName;
    public String author;
    public String intro;
    public String lastchapter;
    public int redCharter;
    public boolean upload;



    public BookInfoPojo(int _articleid, String _articlename, String _author, String _intro,int _chapterId,int _chapterorder) {
        articleid = _articleid;
        articlename = _articlename;
        author = _author;
        intro = _intro;
        chapterId = _chapterId;
        chapterorder = _chapterorder;
    }


    public String avatar;
    public String userNickname;
    public String userName;
    public String comment;
    public long publishTime;
    public long createTime;
    public int id;
}
