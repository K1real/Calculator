package com.example.calculator;
//Купил мужик шляпу, а она ему как раз
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
 //Создаем поля и переменные:
    TextView resultField; // Создаем поле для результата
    EditText numberField;   // Создаем поле для ввода числа
    TextView operationField;    // Создаем поле для вывода знака
    Double operand = null;  // Операнд операции
    String lastOperation = "="; // Последняя операция
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Получаем все поля по айди
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }
    // Сохраняем состояние
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    // Получение сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }
    // Обработка нажатия на кнопку числа
    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }
    // Обработка нажатия на кнопку операции
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        // Прорабатываем логику. Если что-то введено:
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation){

        // Если операнд не был установлен:
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                //Прорабатываем знак равенства:
                case "=":
                    operand =number;
                    break;
                //Прорабатываем знак деления:
                case "/":
                    if(number==0){
                        // Придется сделать так, чтобы при делении на 0 получался 0. Я пробовал сделать так, чтобы высвечивалась надпись "ERROR", но не вышло
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                //Прорабатываем знак умножения:
                case "*":
                    operand *=number;
                    break;
                //Прорабатываем знак плюс:
                case "+":
                    operand +=number;
                    break;
                //Прорабатываем знак минус:
                case "-":
                    operand -=number;
                    break;
                //Прорабатываем очистку экрана:
                case "C":
                    operand =0.0;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}