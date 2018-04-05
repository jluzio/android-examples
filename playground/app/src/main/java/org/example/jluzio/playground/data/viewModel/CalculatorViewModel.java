package org.example.jluzio.playground.data.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {
    private MutableLiveData<String> previousOperator = new MutableLiveData<>();
    private MutableLiveData<String> operator = new MutableLiveData<>();
    private MutableLiveData<String> input = new MutableLiveData<>();
    private MutableLiveData<String> result = new MutableLiveData<>();

    public MutableLiveData<String> getPreviousOperator() {
        return previousOperator;
    }

    public void setPreviousOperator(MutableLiveData<String> previousOperator) {
        this.previousOperator = previousOperator;
    }

    public MutableLiveData<String> getOperator() {
        return operator;
    }

    public void setOperator(MutableLiveData<String> operator) {
        this.operator = operator;
    }

    public MutableLiveData<String> getInput() {
        return input;
    }

    public void setInput(MutableLiveData<String> input) {
        this.input = input;
    }

    public MutableLiveData<String> getResult() {
        return result;
    }

    public void setResult(MutableLiveData<String> result) {
        this.result = result;
    }
}
