package com.computacaoognitiva.calculadora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.computacaoognitiva.calculadora.model.AplCalcular;

import java.util.ArrayList;
import java.util.Arrays;

import static com.computacaoognitiva.calculadora.model.AplTradutorNotacao.ocidentalToPolonesa;
import static com.computacaoognitiva.calculadora.model.AplTradutorNotacao.processaNotacao;

public class MainActivity extends AppCompatActivity {
    private double M, value;
    private boolean error;
    private TextView txResult,textM;
    private String notacao;
    private AplCalcular aplCalcular = new AplCalcular();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notacao = "";
        value = 0;
        error = false;

        textM = findViewById(R.id.m);
        textM.setVisibility(View.GONE);

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
                if (s.length() > 20) {
                    error = true;
                    valueToResult();
                }
            }
        });
    }

    public void clickMC(View view) {
        M = 0;
        textM.setVisibility(View.GONE);
    }

    public void clickMPlus(View view) {
        if (!error) {
            resultToValue();
            M += value;
            if(M != 0)
            textM.setVisibility(View.VISIBLE);
        }
    }

    public void clickMLess(View view) {
        if (!error) {
            resultToValue();
            M -= value;
            if(M != 0)
                textM.setVisibility(View.VISIBLE);
        }
    }

    public void clickMR(View view) {
        value = M;
        valueToResult();
        error = false;
    }

    public void clickAC(View view) {
        value = 0;
        error = false;
        valueToResult();
    }

    public void clickPlusLess(View view) {
        if (!error) {
            resultToValue();
            value *= (-1);
            valueToResult();
        }
    }

    public void clickPercent(View view) {
        if (!error)
            txResult.setText(String.format("%s%%", notacao));
    }

    public void clickDivision(View view) {
        if (!error)
            txResult.setText(String.format("%s/", notacao));
    }

    public void clickNumber(View view) {
        if (!error) {
            if (notacao.equals("0") || notacao.equals("0.0") || notacao.equals(R.string.erro))
                notacao = "";
            txResult.setText(String.format("%s%s", notacao, ((Button) view).getText().toString()));
        }
    }

    public void clickMultiplication(View view) {
        if (!error)
            txResult.setText(String.format("%s*", notacao));
    }

    public void clickLess(View view) {
        if (!error)
            txResult.setText(String.format("%s-", notacao));
    }

    public void clickPlus(View view) {
        if (!error)
            txResult.setText(String.format("%s+", notacao));
    }

    public void clickSplit(View view) {
        if (!error)
            txResult.setText(String.format("%s,", notacao));
    }

    public void clickEqual(View view) {
        if (!error) {
            resultToValue();
            valueToResult();
        }
    }

    private void valueToResult() {
        if (!error)
            txResult.setText(String.valueOf(value));
        else
            txResult.setText(R.string.erro);
    }

    private void resultToValue() {
        if (notacao.equals(R.string.erro)) {
            value = 0;
        } else {
            try {
                notacao = ocidentalToPolonesa(notacao);
                value = aplCalcular.calcular(notacao);

//                ArrayList<Double> numbers = new ArrayList<>();
//                ArrayList<String> operators = new ArrayList<>();
//                ArrayList<String> simbols = new ArrayList<String>(Arrays.asList("*", "/", "+", "-", "%"));
//                StringBuilder number = new StringBuilder();
//                for (Character s : notacao.toCharArray()) {
//
//                    if (simbols.contains(s.toString())) {
//                        numbers.add(Double.parseDouble(number.toString()));
//                        number = new StringBuilder();
//                        operators.add(s.toString());
//                    } else {
//                        number.append(s);
//                    }
//                }
//                if(!number.equals(""))
//                numbers.add(Double.parseDouble(number.toString()));
//
//
//                for (int i = 0; i < operators.size(); i++) {
//                    if (operators.get(i).equals("*")) {
//                        double p1 = numbers.get(i);
//                        double p2 = numbers.get(i++);
//                        double result = p1 * p2;
//                        numbers.remove(i++);
//                        numbers.remove(i);
//                        operators.remove(i);
//                        numbers.add(i < 0 ? 0 : i, result);
//                    }
//                }
//                for (int i = 0; i < operators.size(); i++) {
//                    if (operators.get(i).equals("/")) {
//                        double p1 = numbers.get(i);
//                        double p2 = numbers.get(i++);
//                        double result = p1 / p2;
//                        numbers.remove(i++);
//                        numbers.remove(i);
//                        operators.remove(i);
//                        numbers.add(i < 0 ? 0 : i, result);
//                    }
//                }
//                for (int i = 0; i < operators.size(); i++) {
//                    if (operators.get(i).equals("+")) {
//                        double p1 = numbers.get(i);
//                        double p2 = numbers.get(i++);
//                        double result = p1 + p2;
//                        numbers.remove(i+1);
//                        numbers.remove(i);
//                        operators.remove(i);
//                        numbers.add(i < 0 ? 0 : i, result);
//                    }
//                }
//                for (int i = 0; i < operators.size(); i++) {
//                    if (operators.get(i).equals("-")) {
//                        double p1 = numbers.get(i);
//                        double p2 = numbers.get(i++);
//                        double result = p1 - p2;
//                        numbers.remove(i++);
//                        numbers.remove(i);
//                        operators.remove(i);
//                        numbers.add(i < 0 ? 0 : i, result);
//                    }
//                }
            } catch (Exception e) {
                error = true;
                valueToResult();
                e.printStackTrace();
            }
        }
    }

}
