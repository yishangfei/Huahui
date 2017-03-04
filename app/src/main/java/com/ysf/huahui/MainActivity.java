package com.ysf.huahui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yishangfei on 2017/3/3 0003.
 * 邮箱：yishangfei@foxmail.com
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */

public class MainActivity extends AppCompatActivity {

    private List<WordInfo> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        HelloWorld.start();//开始爬虫github上单词
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Github:
                Uri uri = Uri.parse("https://github.com/yishangfei/Huahui");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    Thread HelloWorld = new Thread() {
        public void run() {
            Document doc;
            Elements elements;
            try {
                doc = Jsoup.connect("https://github.com/yishangfei/chinese-programmer-wrong-pronunciation").get();
                elements = doc.select("div.readme");
                for (Element e : elements) {
//                        wordInfo.title=e.select("p").get(0).text();
//                        wordInfo.name=e.select("th").text();
                    e.select("td").select("g-emoji").remove();
                    String word = e.select("tr td:eq(0)").text();
                    String correct = e.select("tr td:eq(1)").text();
                    String[] aword = word.split("\\s+");
                    String[] acorrect = correct.split("\\s+");
                    mlist = new ArrayList<WordInfo>();
                    for (int i = 0; i < aword.length; i++) {
                        WordInfo sb = new WordInfo();
                        sb.setWord(aword[i]);
                        sb.setCorrect(acorrect[i]);
                        mlist.add(sb);
                        if (i == aword.length - 1) {//判断是否爬虫完毕
                            EventBus.getDefault().post("完成");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WordEventBus(String msg) {//使用EventBus 进行实时加载
        if (msg.equals("完成")) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            WordApapter wordAdapter = new WordApapter(mlist);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(wordAdapter);
            recyclerView.addItemDecoration(new RecycleViewDivider(this, RecycleViewDivider.VERTICAL_LIST));
            recyclerView.addOnItemTouchListener(listener);
        }
    }

    //适配器的点击item点击事件
    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(MainActivity.this, AudioService.class);
            intent.putExtra("word", mlist.get(position).getWord());
            startService(intent);
        }
    };
}


