package jp.ac.jec.cm0146.rirekisyo;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    public static final String TABLE_NAME = "COMPANY";

    private ArrayList<Company> ary = new ArrayList<>();
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        CompanySQLiteOpenHelper helper = new CompanySQLiteOpenHelper(this);
        ary = helper.getAllList();
//
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String[] column = new String[]{"name"};
//        Cursor cur = db.query(TABLE_NAME, column, null, null, null, null, null);
//
//        String[] from = {"_id", "name"};
//        int[] to = {android.R.id.text1, android.R.id.text2};



        // ListViewに表示するリスト項目をArrayListで準備する
//        ArrayList<Company> ary = new ArrayList<>();
//        ary.add(new Company("株式会社日電"));
//        ary.add(new Company("株式会社アップル"));
//        ary.add(new Company("株式会社マイクロソフト"));
//        ary.add(new Company("ニトリ"));
//        ary.add(new Company("NTT"));
//        ary.add(new Company("富士通"));
//        ary.add(new Company("西武鉄道"));
//        ary.add(new Company("すかいらーく"));
//        ary.add(new Company("IKEA"));

        // ArrayAdapterを作成
        // 一行の表示に使用するのは、標準で用意されているandroid.R.layout.simple_list_item_1を指定
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cur, from ,to, 0);
//        // ListViewにArrayAdapterを関連付け、データの表示を行う
//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetInvalidated();




        ListView listView = (ListView)findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter(this, R.layout.list_item, ary);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
                ListView listView = (ListView)adapterView;
                Company item = (Company) listView.getItemAtPosition(position);
                Intent intent  = new Intent(ListActivity.this, AddActivity.class);
                intent.putExtra("whichTap?", "list");
                intent.putExtra("companyName", item.getName());
                intent.putExtra("companyId", id);//リストのidを送る
//                Log.i("android112233", " intentputExtra_id " + item.getId());
                startActivity(intent);
            }
        });


        //＋ボタン
        findViewById(R.id.fab_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // テキスト登録画面 Activity へのインテントを作成
                Intent intent  = new Intent(ListActivity.this, AddActivity.class);
                intent.putExtra("whichTap?", "new");
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onRestart(){
        super.onRestart();
        reload();
    }


    public void reload(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
    }
}