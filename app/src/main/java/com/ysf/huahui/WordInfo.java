package com.ysf.huahui;

/**
 * Created by yishangfei on 2017/3/3 0003.
 * 邮箱：yishangfei@foxmail.com
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * 单词实体类
 */
public class WordInfo {
    public String title;//获取到          中国程序员容易发音错误的单词
    public String name;//获取到           单词 正确发音 错误发音  文字
    public String word;//获取到单词
    public String correct;//获取到 读音

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

}
