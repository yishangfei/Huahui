package com.ysf.huahui;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by yishangfei on 2017/3/3 0003.
 * 邮箱：yishangfei@foxmail.com
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * 单词的适配器
 */
public class WordApapter extends BaseQuickAdapter<WordInfo, BaseViewHolder> {
    public WordApapter(List<WordInfo> data) {
        super(R.layout.word_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WordInfo item) {
        helper.setText(R.id.word,item.word)
                .setText(R.id.correct,item.correct);
    }
}
