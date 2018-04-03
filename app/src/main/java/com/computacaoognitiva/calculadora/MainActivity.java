package com.computacaoognitiva.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.computacaoognitiva.calculadora.model.AplCalcular;
import com.computacaoognitiva.calculadora.view.ViewConsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.computacaoognitiva.calculadora.model.AplTradutorNotacao.ocidentalToPolonesa;

public class MainActivity extends AppCompatActivity {
    private double M, value;
    private boolean error;
    private TextView txResult;
    private String notacao = "";
    private AplCalcular aplCalcular = new AplCalcular();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txResult = findViewById(R.id.result);
        txResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notacao = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 20)
                    txResult.setText(R.string.erro);
                resultToValue();
            }
        });
    }

    public void clickMC(View view) {
        M = 0;
    }

    public void clickMPlus(View view) {
        if(!error){
        resultToValue();
        M += value;
        }
    }

    public void clickMLess(View view) {
        if(!error) {
            resultToValue();
            M -= value;
        }
    }

    public void clickMR(View view) {
        value = M;
        valueToResult();
        error = false;
    }

    public void clickAC(View view) {
        M = 0;
        value = 0;
        valueToResult();
        error = false;
    }

    public void clickPlusLess(View view) {
        if(!error) {
            resultToValue();
            value *= (-1);
            valueToResult();
        }
    }

    public void clickPercent(View view) {
        
    }

    public void clickDivision(View view) {
        txResult.setText(String.format("%s/", notacao));
    }

    public void clickNumber(View view) {
        if(notacao )
        txResult.setText(String.format("%s%s", notacao, ((Button) view).getText().toString()));
    }

    public void clickMultiplication(View view) {
        txResult.setText(String.format("%s*", notacao));
    }

    public void clickLess(View view) {
        txResult.setText(String.format("%s-", notacao));
    }

    public void clickPlus(View view) {
        txResult.setText(String.format("%s+", notacao));
    }

    public void clickSplit(View view) {
        txResult.setText(String.format("%s,", notacao));
    }

    public void clickEqual(View view) {
        valueToResult();
    }

    private void valueToResult() {
        resultToValue();
        if(!error)
        txResult.setText(String.valueOf(value));
        else
            txResult.setText(R.string.erro);
    }

    private void resultToValue() {
        if (notacao.equals(R.string.erro)) {
            value = 0;
        } else {
            numbers = new ArrayList<>();
            operators = new ArrayList<>();
            StringBuilder number = new StringBuilder();
            for (Character s : notacao.toCharArray()) {
                if (simbols.contains(s)) {
                    numbers.add(Double.parseDouble(number.toString()));
                    number = new StringBuilder();
                    operators.add(s);
                } else {
                    number.append(s);
                }
            }

            for (int i = 0; i < operators.size(); i++) {
                if (operators.equals('*')) {
                    double p1 = numbers.get(i);
                    double p2 = numbers.get(i++);
                    double result = p1 * p2;
                    numbers.remove(i++);
                    numbers.remove(i);
                    operators.remove(i);
                    numbers.add(i < 0 ? 0 : i, result);
                }
            }
            for (int i = 0; i < operators.size(); i++) {
                if (operators.equals('/')) {
                    double p1 = numbers.get(i);
                    double p2 = numbers.get(i++);
                    double result = p1 / p2;
                    numbers.remove(i++);
                    numbers.remove(i);
                    operators.remove(i);
                    numbers.add(i < 0 ? 0 : i, result);
                }
            }
            for (int i = 0; i < operators.size(); i++) {
                if (operators.equals('+')) {
                    double p1 = numbers.get(i);
                    double p2 = numbers.get(i++);
                    double result = p1 + p2;
                    numbers.remove(i++);
                    numbers.remove(i);
                    operators.remove(i);
                    numbers.add(i < 0 ? 0 : i, result);
                }
            }
            for (int i = 0; i < operators.size(); i++) {
                if (operators.equals('-')) {
                    double p1 = numbers.get(i);
                    double p2 = numbers.get(i++);
                    double result = p1 - p2;
                    numbers.remove(i++);
                    numbers.remove(i);
                    operators.remove(i);
                    numbers.add(i < 0 ? 0 : i, result);
                }
            }
        }
        try{
            notacao = ocidentalToPolonesa(notacao);
            value = aplCalcular.calcular(notacao);
        }
        catch(Exception e){
            view.printError();
            e.printStackTrace();
        }
    }

}
