package com.wcj.meiriyiwendemo.bean;

public class ArticleBean {

    private data data;

    public ArticleBean.data getData() {
        return data;
    }

    public void setData(ArticleBean.data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "data=" + data +
                '}';
    }

    public class data {
        private date date;
        private String author;
        private String title;
        private String digest;
        private String content;

        public ArticleBean.date getDate() {
            return date;
        }

        public void setDate(ArticleBean.date date) {
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "data{" +
                    "date=" + date +
                    ", author='" + author + '\'' +
                    ", title='" + title + '\'' +
                    ", digest='" + digest + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public class date {
        private String curr;
        private String prev;
        private String next;


        public String getCurr() {
            return curr;
        }

        public void setCurr(String curr) {
            this.curr = curr;
        }

        public String getPrev() {
            return prev;
        }

        public void setPrev(String prev) {
            this.prev = prev;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "date{" +
                    "curr='" + curr + '\'' +
                    ", prev='" + prev + '\'' +
                    ", next='" + next + '\'' +
                    '}';
        }
    }
}
