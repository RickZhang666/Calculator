package com.cst2335.calculator;

import static android.text.method.TextKeyListener.clear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "CalculatorActivity";
    private TextView tv_result;
    private String operator = ""; // operator
    private String prevNum = ""; // prev number
    private String nextNum = ""; // next number
    private String result = ""; // calculate result
    private String showText = ""; // content in the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tv_result = findViewById(R.id.tv_result);

        tv_result.setMovementMethod(new ScrollingMovementMethod());

        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.ib_sqrt).setOnClickListener(this);
    }

    public void onClick(View v) {
        int btn_id = v.getId();
        String inputText;
        if (btn_id == R.id.ib_sqrt) {
            inputText = "√";
        } else {
            inputText = ((TextView) v).getText().toString();
        }
        Log.d(TAG, "btn_id" + btn_id + ",inputText=" + inputText);
        if (btn_id == R.id.btn_clear) {
            clear("");
        } else if (btn_id == R.id.btn_cancel) {
            if (operator.equals("")) {
                if (prevNum.length() == 1) {
                    prevNum = "0";
                } else if (prevNum.length() > 0) {
                    prevNum = prevNum.substring(0, prevNum.length() - 1);
                } else {
                    Toast.makeText(this, "No number to delete", Toast.LENGTH_SHORT).show();
                    return;
                }
                showText = prevNum;
                tv_result.setText(showText);
            } else {
                if (nextNum.length() == 1) {
                    nextNum = "";
                } else if (nextNum.length() == 1) {
                    nextNum = nextNum.substring(0, nextNum.length() - 1);
                } else {
                    Toast.makeText(this, "No number to delete", Toast.LENGTH_SHORT).show();
                    return;
                }
                showText = showText.substring(0, showText.length() - 1);
                tv_result.setText(showText);
            }
        } else if (btn_id == R.id.btn_equal) {
            if (operator.length() == 0 || operator.equals("=")) {
                Toast.makeText(this, "Please enter operator", Toast.LENGTH_SHORT).show();
                return;
            } else if (nextNum.length() <= 0) {
                Toast.makeText(this, "please enter number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (calculate()) {
                operator = inputText;
                showText = showText + "=" + result;
                tv_result.setText(showText);
            } else {
                return;
            }
        } else if (btn_id == R.id.btn_plus || btn_id == R.id.btn_minus || btn_id == R.id.btn_multiply || btn_id == R.id.btn_divide) {
            if (prevNum.length() <= 0) {
                Toast.makeText(this, "please enter number", Toast.LENGTH_SHORT).show();
                return;
            } else if (operator.length() == 0 || operator.equals("=") || operator.equals("√")) {
                operator = inputText;
                showText = showText + operator;
                tv_result.setText(showText);
            } else {
                Toast.makeText(this, "please enter number", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (btn_id == R.id.ib_sqrt) {
            if (prevNum.length() <= 0) {
                Toast.makeText(this, "please enter number", Toast.LENGTH_SHORT).show();
                return;
            } else if (Double.parseDouble(prevNum) < 0) {
                Toast.makeText(this, "The can't less than 0", Toast.LENGTH_SHORT).show();
                return;
            }
            result = String.valueOf(Math.sqrt(Double.parseDouble(prevNum)));
            prevNum = result;
            nextNum = "";
            operator = inputText;
            showText = showText + "√" + result;
            tv_result.setText(showText);
            Log.d(TAG, "result=" + result + ", prevNum =" + prevNum + ", operator" + operator);
        } else {
            if (operator.equals("=")) {
                operator = "";
                prevNum = "";
                showText = "";
            }
            if (btn_id == R.id.btn_dot) {
                inputText = ".";
            }
            if (operator.equals("")) {
                if (prevNum.contains(".") && inputText.equals(".")) {
                    return;
                }
                prevNum = prevNum + inputText;
            }
            showText = showText + inputText;
            tv_result.setText(showText);
        }
        return;
    }

    private void clear(String text) {
        showText = text;
        tv_result.setText(showText);
        operator = "";
        prevNum = "";
        nextNum = "";
        result = "";
    }

}