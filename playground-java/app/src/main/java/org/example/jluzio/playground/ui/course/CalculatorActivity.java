package org.example.jluzio.playground.ui.course;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.viewModel.CalculatorViewModel;

import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        List<Integer> numberButtons = Lists.newArrayList(
                R.id.button_n0, R.id.button_n1, R.id.button_n2,
                R.id.button_n3, R.id.button_n4, R.id.button_n5,
                R.id.button_n6, R.id.button_n7, R.id.button_n8,
                R.id.button_n9, R.id.buttonDot);
        List<Integer> operationButtons = Lists.newArrayList(
                R.id.buttonDivide, R.id.buttonMultiply, R.id.buttonPlus,
                R.id.buttonMinus);

        EditText textInput = findViewById(R.id.textInput);
        TextView textOperation = findViewById(R.id.textOperation);
        EditText textResult = findViewById(R.id.textResult);

        CalculatorViewModel calculatorViewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);
        calculatorViewModel.getOperator().observe(this, value -> {
            textOperation.setText(value);
        });
        calculatorViewModel.getInput().observe(this, value -> {
            textInput.setText(value);
        });
        calculatorViewModel.getResult().observe(this, value -> {
            textResult.setText(value);
        });

        View.OnClickListener numberOnClick = view -> {
            Button numberButton = (Button) view;
            String numberText = numberButton.getText().toString();
            String currentNumber = MoreObjects.firstNonNull(calculatorViewModel.getInput().getValue(), "");
            calculatorViewModel.getInput().setValue(currentNumber + numberText);
        };
        View.OnClickListener operationOnClick = view -> {
            Button operationButton = (Button) view;
            String operationText = operationButton.getText().toString();

            calculatorViewModel.getPreviousOperator().setValue(calculatorViewModel.getOperator().getValue());
            calculatorViewModel.getOperator().setValue(operationText);

            performOperation(calculatorViewModel);
        };
        View.OnClickListener equalsOnClick = operationOnClick;

        for (int numberButton : numberButtons) {
            findViewById(numberButton).setOnClickListener(numberOnClick);
        }
        for (int operationButton : operationButtons) {
            findViewById(operationButton).setOnClickListener(operationOnClick);
        }
        findViewById(R.id.buttonEquals).setOnClickListener(equalsOnClick);
    }

    private void performOperation(CalculatorViewModel viewModel) {
        String inputValueText = viewModel.getInput().getValue();
        String resultValueText = viewModel.getResult().getValue();
        String operator = viewModel.getOperator().getValue();
        String previousOperator = viewModel.getPreviousOperator().getValue();
        String actualOperator = "=".equals(operator) ? previousOperator : operator;

        if (Strings.isNullOrEmpty(inputValueText))  {
            viewModel.getInput().setValue("");
            return;
        } else if (Strings.isNullOrEmpty(operator) || Strings.isNullOrEmpty(resultValueText)) {
            viewModel.getResult().setValue(inputValueText);
            viewModel.getInput().setValue("");
            return;
        }

        Double inputValue = Double.parseDouble(inputValueText);
        Double resultValue = Double.parseDouble(resultValueText);
        switch (actualOperator) {
            case "*":
                resultValue *= inputValue;
                break;
            case "/":
                if (inputValue != 0) {
                    resultValue /= inputValue;
                } else {
                    resultValue = 0d;
                }
                break;
            case "+":
                resultValue += inputValue;
                break;
            case "-":
                resultValue -= inputValue;
                break;
        }
        viewModel.getInput().setValue("");
        viewModel.getResult().setValue(resultValue.toString());
    }
}
