package denver.shkola_softhemeproject;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileTaskActivity extends ActionBarActivity {
    final String LOG_TAG = "myLogs";


    final String DIR_SD = "TaskFiles";
    final String FILENAME_INPUT = "INPUT";
    final String FILENAME_OUTPUT = "OUTPUT";

    EditText editTextInputFile;
    TextView textMaxNullNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_task);

        editTextInputFile = (EditText) findViewById(R.id.editTextInputFile);
        textMaxNullNumber = (TextView) findViewById(R.id.textMaxNullNumber);

        editTextInputFile.setText(readFileSD(FILENAME_INPUT));

    }


    private static boolean checkStringFor01MembersOnly(String str){
        Pattern p = Pattern.compile("^[0-1]{0,100}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private int getMaxNumberOfZeroInLine(String str){
        int count = 0;
        String[] parts = str.split("1");
        for(int i = 0; i < parts.length; ++i){
            if (count < parts[i].length()){count = parts[i].length();}
        }
        return count;
    }

    public void onClickButtonSaveAndCalculate(View view){
        String str = editTextInputFile.getText().toString();
        if (checkStringFor01MembersOnly(str)){
            int countOfZeroMax = getMaxNumberOfZeroInLine(str);
            textMaxNullNumber.setText(Integer.toString(countOfZeroMax));
            writeFileSD(FILENAME_INPUT, str);
            writeFileSD(FILENAME_OUTPUT,Integer.toString(countOfZeroMax));
        }else{
            editTextInputFile.setError("Строка введена не правильно!");
            editTextInputFile.requestFocus();
        }
    }

    void writeFileSD(String fileName, String textToWrite) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }

        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, fileName);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(textToWrite);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFileSD(String fileName) {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return "";
        }

        File sdPath = Environment.getExternalStorageDirectory();
        String str = "";
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        File sdFile = new File(sdPath, fileName);
        try {

            BufferedReader br = new BufferedReader(new FileReader(sdFile));

            String tmp;
            while ((tmp = br.readLine()) != null) {
                str = str + tmp;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        str = str + "";
        return str;
    }




}
