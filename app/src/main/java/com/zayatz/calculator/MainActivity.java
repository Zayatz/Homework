package com.zayatz.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    TextView tvInptA;
    TextView tvInptB;
    TextView tvResult;
    Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInptA = (TextView) findViewById(R.id.tvInptA_AM);
        tvInptB = (TextView) findViewById(R.id.tvInptB_AM);
        tvResult = (TextView) findViewById(R.id.tvResult_AM);
        btnCalc = (Button) findViewById(R.id.btnCalc_AM);

        tvInptA.setOnClickListener(this);
        tvInptB.setOnClickListener(this);
        btnCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvInptA_AM:
                openNumInputActivity(Constants.REQUEST_NUMBER_A); //
                break;
            case R.id.tvInptB_AM:
                openNumInputActivity(Constants.REQUEST_NUMBER_B);
                break;
            case R.id.btnCalc_AM:
                //tvResult.setText(sum(tvInptA, tvInptB);
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

   /* public String sum (TextView a, TextView b) {
        float x = new Float(a.getText().toString());
        float y = new Float(a.getText().toString());

        return String.valueOf(x + y);
    } */
}

