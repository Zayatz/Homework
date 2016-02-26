package com.zayatz.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView tvInptA;
    TextView tvInptB;
    TextView tvResult;
    EditText etInptOperation;
    Button btnCalc;
    Switch switchBtn;

    SharedPreferences viewsState;

    boolean switchState; //для відображення стану switchBtn: true - увімкнено, false - вимкнено

    Activity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this); // задає тему
        setContentView(R.layout.activity_main);

        tvInptA = (TextView) findViewById(R.id.tvInptA_AM);
        tvInptB = (TextView) findViewById(R.id.tvInptB_AM);
        tvResult = (TextView) findViewById(R.id.tvResult_AM);
        etInptOperation = (EditText) findViewById(R.id.etInptOperation_AM);
        btnCalc = (Button) findViewById(R.id.btnCalc_AM);
        switchBtn = (Switch) findViewById(R.id.swChangeStyle_AM);
        mMainActivity = this; // для передачі об'єкта актівіті у кліклісенер

        tvInptA.setOnClickListener(this);
        tvInptB.setOnClickListener(this);
        btnCalc.setOnClickListener(this);

        loadCurrentState(); //завантажує стан актівіті
        switchBtn.setChecked(switchState); // перемикає switchBtn відповідно до теми. Ніч - false, день - true

        /*ClickListener для switchButton, через implements треба створювати
          зайвий об'єкт*/
        CompoundButton.OnCheckedChangeListener occlSwitch =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Utils.changeToTheme(mMainActivity, Constants.THEME_ON);
                        switchState = true;
                    }
                    else {
                        Utils.changeToTheme(mMainActivity, Constants.THEME_OFF);
                        switchState = false;
                    }
                    saveCurrentState(); //зберігає стан актівіті
                }
            };

        switchBtn.setOnCheckedChangeListener(occlSwitch);
    }

    /*при закритті (системою, користувачем) програми очищає поля, щоб при запуску знову, вони
     * були чистими. При зміні теми актівіті перезапускається, але onDestroy не запускається,
     * таким чином при зміні теми дані зберігаються, при виході - ні*/
    @Override
    protected void onDestroy() {
        clearFields();
        saveCurrentState();
        super.onDestroy();
    }

    void saveCurrentState() {
        viewsState = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = viewsState.edit();
        ed.putBoolean(Constants.SWITCH_STATE, switchState);
        ed.putString(Constants.INPUT_OPERATION_STATE, String.valueOf(etInptOperation.getText()));
        ed.putString(Constants.INPUT_A_STATE, String.valueOf(tvInptA.getText()));
        ed.putString(Constants.INPUT_B_STATE, String.valueOf(tvInptA.getText()));
        ed.putString(Constants.RESULT_STATE, String.valueOf(tvResult.getText()));
        ed.apply();

    }

    void loadCurrentState() {
        viewsState = getPreferences(MODE_PRIVATE);
        switchState = viewsState.getBoolean(Constants.SWITCH_STATE, false);
        etInptOperation.setText(viewsState.getString(Constants.INPUT_OPERATION_STATE, null));
        tvInptA.setText(viewsState.getString(Constants.INPUT_A_STATE, null));
        tvInptB.setText(viewsState.getString(Constants.INPUT_B_STATE, null));
        tvResult.setText(viewsState.getString(Constants.RESULT_STATE, null));
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
                if(inputsCheck(tvInptA, tvInptB) && operationCheck(etInptOperation)) {
                    tvResult.setText(Calc(etInptOperation, tvInptA, tvInptB));
                }
                break;
        }
    }

    /*відкриває NumInputActivity, REQUEST_NUMBER задається для диференціації
     * також через інтент передає стан switchButton, для синохронізації стилів */
    public void openNumInputActivity (int REQUEST_NUMBER) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), NumInputActivity.class);
        intent.putExtra(Constants.PUT_EXTRA, switchState);
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
    /*перевіряє поля для вводу(чи не пусті, чи не пусте А, чи не пусте В), true - поля задовільняють
    вимогам, false - виводить тост із помилкою*/
    public boolean inputsCheck (TextView a, TextView b) {
        String strA = a.getText().toString().trim();
        String strB = b.getText().toString().trim();

        if (strA.length() == 0 && strB.length() == 0) { //чи не пусті А та В
            Toast.makeText(this, R.string.input_variables_MA, Toast.LENGTH_LONG).show();

            return false;
        }
        else if (strA.length() == 0) {  //чи не пусте А
            Toast.makeText(this, R.string.input_variableA_MA, Toast.LENGTH_LONG).show();

            return false;
        }
        else if (strB.length() == 0){  //чи не пусте В
            Toast.makeText(this, R.string.input_variableB_MA, Toast.LENGTH_LONG).show();

            return false;
        }

        else return true;
    }

    /*виконує операції над змінними, повертає строчку з результатом*/
    public String Calc (EditText operation, TextView a, TextView b) {
        float x = Float.valueOf(a.getText().toString()); //приведення даних із TextView до float
        float y = Float.valueOf(b.getText().toString());

        String sResult = "";
        String symbol = operation.getText().toString(); //дістає строчку  із EditText
        char chSymbol = symbol.charAt(0); //на стрінгу матюкався, із символом працює

                switch (chSymbol) {  //розпізнання дії, її виконання
                    case '+':
                        sResult = Float.toString(x + y);
                        break;
                    case '-':
                        sResult = Float.toString(x - y);
                        break;
                    case '*':
                        sResult = Float.toString(x * y);
                        break;
                    case '/':
                        sResult = Float.toString(x / y);
                        break;
                }

        return sResult;
    }

   /*перевіряє, чи введена операція відповідає вимогам (чи не пусте поле, довжина, чи є операційним
   символом). true - ідповідає, false - не відповідає, виводить тост із помилкою*/
    public boolean operationCheck(EditText etSymbol) {

        String symbol = etSymbol.getText().toString().trim();

        if (symbol.length() > 1) { //перевірка на довжину (не більше 1 символа)
            Toast.makeText(this, R.string.more_than_1_symbol_MA, Toast.LENGTH_LONG).show();

            return false;
        }
        else if (symbol.length() == 0) { //перевірка на порожність
            Toast.makeText(this, R.string.edit_operation_MA, Toast.LENGTH_LONG).show();

            return false;
        }
        else {
            char chSymbol = symbol.charAt(0); //дубово, але працює. Щоб не використовувати equals
            if (chSymbol != '+' && chSymbol != '-' && chSymbol != '*' && chSymbol != '/') { //перевірка на відповідність (введена дія чи інший символ)
                Toast.makeText(this, R.string.invalid_symbol, Toast.LENGTH_LONG).show();

                return false;
            }
        }

        return true;
    }

    void clearFields() {
        switchState = false;
        etInptOperation.setText(null);
        tvInptA.setText(null);
        tvInptB.setText(null);
        tvResult.setText(null);
    }
}

