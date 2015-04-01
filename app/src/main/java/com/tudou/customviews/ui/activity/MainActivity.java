package com.tudou.customviews.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.tudou.customviews.R;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{

  @InjectView(R.id.list_main)
  ListView mList;

  private ArrayList<String> mArrayList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    initData();
    mList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayList));
    mList.setOnItemClickListener(this);
  }

  private void initData() {
    mArrayList = new ArrayList<>();
    mArrayList.add("Text Divider View");
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      switch (position) {
        case 0:
          startActivity(new Intent(this, TextDividerActivity.class));
          break;
      }
  }
}
