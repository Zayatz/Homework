package com.zayatz.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumInputActivity extends Activity implements View.OnClickListener{

    EditText editNumber;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_input);

        editNumber = (EditText) findViewById(R.id.etInptNum_ANI);
        btnOk = (Button) findViewById(R.id.btnOk_ANI);

        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk_ANI:
                if (TextUtils.isEmpty(editNumber.getText().toString())) { //перевіряє на порожність editNumber
                    setResult(RESULT_CANCELED);
                    finish();
                }
                else if (editNumber.getText().toString().trim().length() > 3) {            //перевірка на к-сть символів
                    Toast.makeText(this, "Wrong number input", Toast.LENGTH_LONG).show();  //якщо більше 3, виводить тост із помилкою
                }
                else {
                    sendNumber();
                    finish();
                }
                break;
        }
    }

    // створює інтент, передає в нього дані з editNumber
    private void sendNumber() {
        Intent intent = new Intent();
        intent.putExtra(Constants.NUMBER, editNumber.getText().toString());

        setResult(RESULT_OK, intent);
    }


}