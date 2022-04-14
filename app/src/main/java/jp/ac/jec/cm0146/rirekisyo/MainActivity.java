package jp.ac.jec.cm0146.rirekisyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //新規経歴ボタン
        findViewById(R.id.newImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                intent.putExtra("new_or_record", "new");
                startActivity(intent);
            }
        });

        //志望リストボタン
        findViewById(R.id.listImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        //前回の記録ボタン
//        findViewById(R.id.lastRecordButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewActivity.class);
//                intent.putExtra("new_or_record", "record");
//                startActivity(intent);
//            }
//        });
    }


}