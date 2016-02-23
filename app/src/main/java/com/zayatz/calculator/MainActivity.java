package com.zayatz.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    TextView tvInptA;
    TextView tvInptB;
    TextView tvResult;
    EditText etInptOperation;
    Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInptA = (TextView) findViewById(R.id.tvInptA_AM);
        tvInptB = (TextView) findViewById(R.id.tvInptB_AM);
        tvResult = (TextView) findViewById(R.id.tvResult_AM);
        etInptOperation = (EditText) findViewById(R.id.etInptOperation_AM);
        btnCalc = (Button) findViewById(R.id.btnCalc_AM);

        tvInptA.setOnClickListener(this);
        tvInptB.setOnClickListener(this);
        btnCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvInptA_AM:
                openNumInputActivity(Constants.REQUEST_NUMBER_A); //REQUEST_NUMBER визначає, куди будуть відправлені дані
                break;
            case R.id.tvInptB_AM:
                openNumInputActivity(Constants.REQUEST_NUMBER_B);
                break;
            case R.id.btnCalc_AM:
                tvResult.setText(Calc(etInptOperation, tvInptA, tvInptB)); // TODO: 23.02.2016 додати перевірку на ввід А та В
                break;
        }
    }

    /*відкриває NumInputActivity, REQUEST_NUMBER задається для диференціації */
    public void openNumInputActivity (int REQUEST_NUMBER) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), NumInputActivity.class);
        startActivityForResult(intent, REQUEST_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String Number = extras.getString(Constants.NUMBER); // записує результат у змінну

        /*перевіряє requestCode, залежно від нього, вирішує, куди відправляти результат*/
            switch (requestCode) {
                case Constants.REQUEST_NUMBER_A:
                    tvInptA.setText(Number);
                    break;
                case Constants.REQUEST_NUMBER_B:
                    tvInptB.setText(Number);
                    break;
            }
        }
    }


    public String Calc (EditText operation, TextView a, TextView b) {
        float x = Float.valueOf(a.getText().toString()); //приведення даних із TextView до float
        float y = Float.valueOf(b.getText().toString());

        String sResult = "";
        String symbol = operation.getText().toString(); //дістає строчку  із EditText

        if (operation.getText().toString().trim().length() > 1) { //перевірка на довжину (не більше 1 символа)
            Toast.makeText(this, "More than 1 symbol", Toast.LENGTH_LONG).show();
        }
        else if (operation.getText().toString().trim().length() == 0) { //перевірка на порожність
            Toast.makeText(this, "Edit operation", Toast.LENGTH_LONG).show();
        }
        else if (symbol != "+" && symbol != "-" && symbol != "*" && symbol != "/") { //перевірка на відповідність (введена дія чи інший символ)
                Toast.makeText(this, "Invalid symbol", Toast.LENGTH_LONG).show();
            }
        else {
            switch (symbol) {  //розпізнання дії, її виконання
                case "+":
                    sResult = Float.toString(x + y);
                    break;
                case "-":
                    sResult = Float.toString(x - y);
                    break;
                case "*":
                    sResult = Float.toString(x * y);
                    break;
                case "/":
                    sResult = Float.toString(x / y);
                    break;
            }
        }
        return sResult;
    }
}

