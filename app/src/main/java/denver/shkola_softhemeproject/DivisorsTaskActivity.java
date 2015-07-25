package denver.shkola_softhemeproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DivisorsTaskActivity extends ActionBarActivity {


    EditText editTextEnterNumber;
    TextView textViewResult;
    List<Integer> listDivisors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisors_task);

        editTextEnterNumber = (EditText) findViewById(R.id.editTextEnterNumber);
        textViewResult = (TextView) findViewById(R.id.textViewResult);


    }


    public void onClickResultButton(View view){
        listDivisors = new ArrayList<>();
        if(editTextEnterNumber.getText().length()>0) {
            if(Integer.parseInt(editTextEnterNumber.getText().toString()) != 0) {
                findAllDivisorsOfNumder(Integer.parseInt(editTextEnterNumber.getText().toString()));
                Collections.sort(listDivisors);
                textViewResult.setText(listDivisors.toString());
            }else{
                editTextEnterNumber.setError("Введите число не равное 0");
                editTextEnterNumber.requestFocus();
            }
        }else{
            editTextEnterNumber.setError("Введите число");
            editTextEnterNumber.requestFocus();
        }

    }


    private void findAllDivisorsOfNumder(int number){
        List<PairNumberDegree> pairNumberDegreeList = getNumberInCanonicalForm(number);
        ArrayList<Integer> degrees = new ArrayList<>();
        for(int i = 0; i < pairNumberDegreeList.size(); ++i){
            degrees.add(new Integer(pairNumberDegreeList.get(i).getDegree()));
        }
        bustAllDegreesAndCalculateDevisors(degrees, pairNumberDegreeList, 0 );
    }


    private void bustAllDegreesAndCalculateDevisors(ArrayList<Integer> degrees, List<PairNumberDegree> pairNumberDegreeList, int currentChangedDegree ){
        for (int i = 0; i <= degrees.get(currentChangedDegree).intValue(); ++i){
            ArrayList<Integer> newDegrees = (ArrayList) degrees.clone();
            newDegrees.set(currentChangedDegree, new Integer(i));
            if(currentChangedDegree + 1 < degrees.size()){
                bustAllDegreesAndCalculateDevisors(newDegrees,pairNumberDegreeList,currentChangedDegree + 1);
            }else{
                listDivisors.add(new Integer(multiplicationToGetDivisor(newDegrees, pairNumberDegreeList)));
            }
        }
    }


    private int multiplicationToGetDivisor(ArrayList<Integer> degrees, List<PairNumberDegree> pairNumberDegreeList){
        int divisor = 1;

        for (int i = 0; i < pairNumberDegreeList.size(); ++ i){
            int numInDeg = (int) Math.pow(pairNumberDegreeList.get(i).getNumber(),degrees.get(i));
            divisor *= numInDeg;
        }

        return divisor;
    }


    private List<PairNumberDegree> getNumberInCanonicalForm(int num){

        List<PairNumberDegree> pairNumberDegreeList = new ArrayList<>();
        int p = 2;

        while (num != 1 ){
            if(num % p == 0){
                if(pairNumberDegreeList.size() == 0){
                    pairNumberDegreeList.add(new PairNumberDegree(p,1));
                }else{
                    if (pairNumberDegreeList.get(pairNumberDegreeList.size()-1).getNumber() == p){
                        pairNumberDegreeList.get(pairNumberDegreeList.size()-1).incrementDegree();
                    }else{
                        pairNumberDegreeList.add(new PairNumberDegree(p,1));
                    }
                }

                num = num / p;
            }
            else{
                ++p;
            }
        }

        return pairNumberDegreeList;
    }




    class PairNumberDegree{
        int number;
        int degree;

        PairNumberDegree(int num, int deg){number = num; degree = deg;}

        int getNumber(){return  number;}
        int getDegree(){return degree;}
        void incrementDegree(){++degree;}
    }

}
