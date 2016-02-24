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
        syncTheme(this.getIntent()); //задає тему у відповідності до теми MainActivity
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
                if (checkInput(editNumber)) { //перевірка вводу
                    sendNumber(); //передає дані вводу до MainActivity
                    finish();
                }
                break;
        }
    }

    /*перевіряє ввід editNumber, true - відповідає вимогам*/
    private boolean checkInput (EditText input) {
        boolean result;

        if (TextUtils.isEmpty(input.getText().toString())) { //перевіряє на порожність editNumber
            result = false;

            setResult(RESULT_CANCELED); //відсилає негативний результат
            finish();                   //закриває актівіті
        }
        else if (editNumber.getText().toString().trim().length() > 3) {            //перевірка на к-сть символів
            Toast.makeText(this, R.string.wrong_number_input_NIA, Toast.LENGTH_LONG).show();  //якщо більше 3, виводить тост із помилкою
            result = false;
        }
        else {
            result = true;
        }

        return result;
    }

    // створює інтент, передає в нього дані з editNumber
    private void sendNumber() {
        Intent intent =
                new Intent().putExtra(Constants.NUMBER, editNumber.getText().toString());

        setResult(RESULT_OK, intent);
    }

    /*обробляє інтент, задає тему у відповідності зі станом switchBtn MainActivity
    * (true - день, false - ніч)*/
    private void syncTheme(Intent intent) {
        boolean b;
        b = intent.getBooleanExtra(Constants.PUT_EXTRA, true);
        if (b) this.setTheme(R.style.switchOnStyle);
        else this.setTheme(R.style.switchOffStyle);
    }
}