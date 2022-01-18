package com.example.nodecalculator;

import com.google.gson.annotations.SerializedName;

public class HistoryEntry {
    @SerializedName("operand1")
    private int operand1;
    @SerializedName("operand2")
    private int operand2;
    @SerializedName("result")
    private int result;
    @SerializedName("operation")
    private String operator;

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
