package jp.ac.jec.cm0146.rirekisyo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;

public class NewActivity extends AppCompatActivity {

    private static int whichTerms = 2;

    private static int userBornYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        setActionBar((Toolbar) findViewById(R.id.toolbar));

        //貼り付けできないように
        findViewById(R.id.primary_graduate).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.junior_graduate).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.junior_enter).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.high_enter).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.high_graduate).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.after_high_enter).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        findViewById(R.id.after_high_graduate).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });


        //EditText
        EditText setBirthday = (EditText)findViewById(R.id.editTextDate);
        setBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                EditText setBirthday = (EditText)findViewById(R.id.editTextDate);
                String editSetBirthday = setBirthday.getText().toString();

                if(!editSetBirthday.isEmpty()){
                    String year = editSetBirthday.split(" / ")[0];
                    int yearInt = Integer.parseInt(year);
                    String month = editSetBirthday.split(" / ")[1];
                    int monthInt = Integer.parseInt(month);
                    String day = editSetBirthday.split(" / ")[2];
                    int dayInt = Integer.parseInt(day);

                    DatePickerDialog dialog = new DatePickerDialog(
                            NewActivity.this,
                            new DialogDataSetEvent(),
                            yearInt,
                            //１月が０だからマイナス１をする
                            monthInt -1,
                            dayInt
                    );
                    dialog.show();
                } else {
                    DatePickerDialog dialog = new DatePickerDialog(
                            NewActivity.this,
                            new DialogDataSetEvent(),
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    dialog.show();
                }


            }
        });

        Spinner sp = (Spinner)findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String userBornDate = setBirthday.getText().toString();
                if(userBornDate.isEmpty()){
                    Toast.makeText(NewActivity.this, "生年月日を入力してください", Toast.LENGTH_SHORT).show();
                    return;
                }


                String item = sp.getSelectedItem().toString();
                if ( item.equals("２年制")) {
                    EditText afterHighGraduate = (EditText)findViewById(R.id.after_high_graduate);
                    String check = afterHighGraduate.getText().toString();
                    if(check.isEmpty()) return;
                    String str = check.substring(check.indexOf('年'));
                    int year = userBornYear + 21;

                    String endStr = year + str;

                    afterHighGraduate.setText(endStr);

                    whichTerms = 2;
                } else if (item.equals("３年制")) {
                    EditText afterHighGraduate = (EditText)findViewById(R.id.after_high_graduate);
                    String check = afterHighGraduate.getText().toString();
                    if(check.isEmpty()) return;
                    String str = check.substring(check.indexOf('年'));

                    String endStr = userBornYear + 22 + str;
                    afterHighGraduate.setText(endStr);


                    whichTerms = 3;
                } else if (item.equals("４年制")) {
                    EditText afterHighGraduate = (EditText)findViewById(R.id.after_high_graduate);
                    String check = afterHighGraduate.getText().toString();
                    if(check.isEmpty()) return;
                    String str = check.substring(check.indexOf('年'));

                    String endStr = userBornYear + 23 + str;
                    afterHighGraduate.setText(endStr);

                    whichTerms = 4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    class DialogDataSetEvent implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            EditText setBirthday = (EditText) findViewById(R.id.editTextDate);
            setBirthday.setText(String.format("%d / %02d / %02d", year, monthOfYear + 1, dayOfMonth));


            //経歴の枠を埋める
            //ユーザの誕生日を表示
            int userYears = dispBirthday(year, monthOfYear + 1, dayOfMonth);
            userBornYear = year;

            ///小学校
            enterGradPrimary(userYears, year, monthOfYear + 1);
            ///中学校
            enterGradJunior(userYears, year, monthOfYear + 1);
            ///高校
            enterGradHigh(userYears, year, monthOfYear + 1);
            ///大学短大専門
            enterGradAfterHighSchool(userYears, year, monthOfYear + 1);

        }
    }

    //誕生日を表示
    private int dispBirthday(int userYear, int userMonth, int userDate){
        Calendar calendar = Calendar.getInstance();
        //自分の年齢表示（これ結構めんどくさいかも？）
        TextView userBirthday = (TextView) findViewById(R.id.userBirthday);
        ///今年と誕生年の差を求める
        int thisYear = calendar.get(Calendar.YEAR);
        int years = thisYear - userYear;

        //今日が誕生月以前　かつ　誕生日よりも前だったらyears -1　する
        if(calendar.get(Calendar.MONTH) <= userMonth && calendar.get(Calendar.DAY_OF_MONTH) < userDate){
            years--;
        }

        userBirthday.setText( years + "歳");
        return years;
    }

    //入学卒業リストを埋める
    private void enterGradPrimary(int userYears, int userYear, int userMonth){
        EditText primaryGraduate = (EditText)findViewById(R.id.primary_graduate);


        if((userYears <= 12 && userMonth  <= 3) || (userYears < 12)){
            primaryGraduate.setText(userYear + 13 + "年　３月予定");
        } else if((userYears == 12 && userMonth >= 4) || userYears >= 13){
            primaryGraduate.setText(userYear + 13 + "年　３月");
        }
    }

    private void enterGradJunior(int userYears, int userYear, int userMonth){
        EditText juniorEnter = (EditText)findViewById(R.id.junior_enter);
        EditText juniorGraduate = (EditText)findViewById(R.id.junior_graduate);

        //入学
        if((userYears <= 12 && userMonth  <= 3) || (userYears < 12)){
            juniorEnter.setText(userYear + 13 + "年　４月予定");
        } else if((userYears == 12 && userMonth >= 4) || userYears >= 13){
            juniorEnter.setText(userYear + 13 + "年　４月");
        }

        //卒業
        if((userYears <= 15 && userMonth <= 3) || (userYears < 15)){
            juniorGraduate.setText(userYear + 16 + "年　３月予定");
        } else if((userYears == 15 && userMonth >= 4) || userYears >= 16){
            juniorGraduate.setText(userYear + 16 + "年　３月");
        }
    }


    private void enterGradHigh(int userYears, int userYear, int userMonth){
        EditText highEnter = (EditText)findViewById(R.id.high_enter);
        EditText highGraduate = (EditText)findViewById(R.id.high_graduate);

        //入学
        if((userYears <= 15 && userMonth <= 3) || (userYears < 15)){
            highEnter.setText(userYear + 16 + "年　４月予定");
        } else if((userYears == 15 && userMonth >= 4) || userYears >= 16){
            highEnter.setText(userYear + 16 + "年　４月");
        }

        //卒業
        if((userYears <= 18 && userMonth <= 3) || (userYears < 18)){
            highGraduate.setText(userYear + 19 + "年　３月予定");
        } else if((userYears == 18 && userMonth >= 4) || userYears >= 19){
            highGraduate.setText(userYear + 19 + "年　３月");
        }
    }

    private void enterGradAfterHighSchool(int userYears, int userYear, int userMonth){
        EditText afterHighEnter = (EditText)findViewById(R.id.after_high_enter);
        EditText afterHighGraduate = (EditText)findViewById(R.id.after_high_graduate);

        //入学
        if((userYears <= 18 && userMonth <= 3) || (userYears < 18)){
            afterHighEnter.setText(userYear + 19 + "年　４月予定");
        } else if((userYears == 18 && userMonth >= 4) || userYears >= 19){
            afterHighEnter.setText(userYear + 19 + "年　４月");
        }

        Log.i("android123", "enterGradAfter");

        //卒業
        //２年制
        if((userYears <=20 && userMonth <= 3) || (userYears < 20)){
            afterHighGraduate.setText(userYear + 21 + "年　３月予定");
        } else if((userYears == 20 && userMonth >= 4) || userYears >= 21){
            afterHighGraduate.setText(userYear + 21 + "年　３月");
        }
    }
}

