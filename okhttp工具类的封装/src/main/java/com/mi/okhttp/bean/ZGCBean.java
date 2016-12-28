package com.mi.okhttp.bean;

import java.util.List;

/**
 * Created by 暗示语 on 2016/12/28.
 */

public class ZGCBean {


    /**
     * stitle : 它决定iPhone8屏幕够不够 竟不是三星
     * sdate : 2016-12-27 21:00:12
     * type : 0
     * listStyle : 1
     * label :
     * joinPeople : 19617
     * url : http://mobile.zol.com.cn/621/6213542.html
     * id : 6213542
     * imgsrc : http://i3.article.fd.zol-img.com.cn/t_s170x300_q7/g5/M00/0F/05/ChMkJlhiNMaIKk8yAAB7wMDhXEUAAY6CgDkh5IAAHvY493.jpg
     * imgsrc2 : http://article.fd.zol-img.com.cn/t_s640x2000c4/g5/M00/0F/05/ChMkJ1hiNLOISoazAACLrQQIJLgAAY6CgBJtVYAAIvF913.jpg
     * comment_num : 42
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String stitle;
        private String url;
        private String imgsrc2;

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImgsrc2() {
            return imgsrc2;
        }

        public void setImgsrc2(String imgsrc2) {
            this.imgsrc2 = imgsrc2;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "stitle='" + stitle + '\'' +
                    ", url='" + url + '\'' +
                    ", imgsrc2='" + imgsrc2 + '\'' +
                    '}';
        }
    }
}