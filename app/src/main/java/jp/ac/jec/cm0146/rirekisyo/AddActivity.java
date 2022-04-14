package jp.ac.jec.cm0146.rirekisyo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    public static final String TABLE_NAME = "COMPANY";
    private int pos = 0;

    private int database_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent = getIntent();
        String whichTap = intent.getStringExtra("whichTap?");
        //何を選択しているか
        String companyName = intent.getStringExtra("companyName");
        //選択したリストのid
        long companyId = intent.getLongExtra("companyId", -1);


        //会社名
        EditText name = (EditText)findViewById(R.id.editCompanyName);

        //住所
        EditText address = (EditText)findViewById(R.id.editCompanyAddress);

        //電話番号
        EditText phoneNum = (EditText)findViewById(R.id.editCompanyTelNumber);

        //資本金
        EditText capital = (EditText)findViewById(R.id.editCompanyCapital);

        //利益
        EditText profit = (EditText)findViewById(R.id.editCompanyProfit);

        //初任給
        EditText salary1st = (EditText)findViewById(R.id.editCompany1stSalary);

        //メモ
        EditText memo = (EditText)findViewById(R.id.editCompanyMemo);



        if("list".equals(whichTap)) {//Listから遷移したら
            //追加ボタンを使えなくする
            findViewById(R.id.btnAdd).setEnabled(false);

            //EditTextを編集できないようにする
            setCannnotUse(name, address, phoneNum, capital, profit, salary1st, memo);

            ArrayList<Company> ary = new ArrayList<>();


            //データベースから項目を持ってきて、Edittextに表示
            CompanySQLiteOpenHelper helper = new CompanySQLiteOpenHelper(this);
            ary = helper.getCompanyData();


            for(; pos < ary.size(); pos++){
                Company temp = ary.get(pos);

                //TODO 多分落ちる原因わかった！idがprimarykeyになっていて、listactivityのintentで渡している値はlist上のid?添字？みたいな感じなんだと思う！てことは、
                // 一度でも削除したら、idが一生会わなくなる現象が起こるみたい
                if((ary.get(pos).getName().equals(companyName)) ){//選択肢の会社名と同じpositionになった　かつ　同じidになったら　抜ける && (ary.get(pos).getId() == companyId + 1)
                    break;
                }
            }

            database_id = ary.get(pos).getId();
            name.setText(ary.get(pos).getName());
            address.setText(ary.get(pos).getAddress());
            phoneNum.setText(ary.get(pos).getTEL());
            capital.setText(ary.get(pos).getCapital());
            profit.setText(ary.get(pos).getProfit());
            salary1st.setText(ary.get(pos).getSalary1st());
            memo.setText(ary.get(pos).getMemo());

            //TODO 削除ボタンの動き
            findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText name = (EditText)findViewById(R.id.editCompanyName);
                    String comName = name.getText().toString();

                    if(comName.isEmpty()){
                        return;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    builder.setTitle("確認");
                    builder.setMessage(comName + "の情報を削除します");
                    builder.setPositiveButton("削除する", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteDB(comName, database_id);
                        }
                    });
                    builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            });


        } else if ("new".equals(whichTap)) {//Newから遷移したら
            //削除ボタンを使えなくする
            findViewById(R.id.btnDel).setEnabled(false);

            //追加ボタン
            findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //会社名の取得
                    String comName = name.getText().toString();
                    if(comName.isEmpty()){//空だったら
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setTitle("確認");
                        builder.setMessage("会社名は必須項目です");
                        builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    } else {//空でなければ
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                        builder.setTitle("確認");
                        builder.setMessage(comName + "の情報を追加します。");
                        builder.setPositiveButton("追加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //setDBの呼び出し
                                setDB(name, address, phoneNum, capital, profit, salary1st, memo);

                            }
                        });
                        builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                }
            });


        }

    }

    private void setDB(EditText name, EditText address, EditText phoneNum, EditText capital, EditText profit, EditText salary, EditText memo){
        //Stringに文字列として値を取得
        String txtName = name.getText().toString();
        String txtAddress = address.getText().toString();
        String txtPhoneNum = phoneNum.getText().toString();
        String txtCapital = capital.getText().toString();
        String txtProfit= profit.getText().toString();
        String txtSalary = salary.getText().toString();
        String txtMemo = memo.getText().toString();

        CompanySQLiteOpenHelper helper = new CompanySQLiteOpenHelper(AddActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //値を入れる
        ContentValues values = new ContentValues();
        values.put("name", txtName);
        values.put("address", txtAddress);
        values.put("TEL", txtPhoneNum);
        values.put("capital", txtCapital);
        values.put("profit", txtProfit);
        values.put("salary1st", txtSalary);
        values.put("memo", txtMemo);

        long result = database.insert(TABLE_NAME, null, values);
        helper.close();

        //結果をダイアログ表示
        if( result != -1 ){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            builder.setTitle("確認");
            builder.setMessage("追加に成功しました");
            builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Textをからにする
                    name.setText("");
                    address.setText("");
                    phoneNum.setText("");
                    capital.setText("");
                    profit.setText("");
                    salary.setText("");
                    memo.setText("");
                }
            });
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            builder.setTitle("確認");
            builder.setMessage("追加に失敗しました。\n" + txtName + "は以前に登録されています。");
            builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    //削除する
    private void deleteDB(String comName, int database_id) {
        CompanySQLiteOpenHelper helper = new CompanySQLiteOpenHelper(AddActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();

        try{
            database.execSQL("DELETE FROM " + TABLE_NAME + " WHERE name = '" + comName + "' AND _id = " + database_id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            builder.setTitle("確認");
            builder.setMessage("削除に成功しました");
            builder.setPositiveButton("了解", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText name = (EditText)findViewById(R.id.editCompanyName);
                    EditText address = (EditText)findViewById(R.id.editCompanyAddress);
                    EditText phoneNum = (EditText)findViewById(R.id.editCompanyTelNumber);
                    EditText capital = (EditText)findViewById(R.id.editCompanyCapital);
                    EditText profit = (EditText)findViewById(R.id.editCompanyProfit);
                    EditText salary1st = (EditText)findViewById(R.id.editCompany1stSalary);
                    EditText memo = (EditText)findViewById(R.id.editCompanyMemo);
                    //Textをからにする
                    name.setText("");
                    address.setText("");
                    phoneNum.setText("");
                    capital.setText("");
                    profit.setText("");
                    salary1st.setText("");
                    memo.setText("");
                }
            });
            builder.show();
        }
    }

    //入力・長押しできないようにする
    private void setCannnotUse(EditText name, EditText address, EditText phoneNum, EditText capital, EditText profit, EditText salary, EditText memo){
        name.setFocusable(false);
        address.setFocusable(false);
        phoneNum.setFocusable(false);
        capital.setFocusable(false);
        profit.setFocusable(false);
        salary.setFocusable(false);
        memo.setFocusable(false);

        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        address.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        phoneNum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        capital.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        profit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        salary.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        memo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

    }
}