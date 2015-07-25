package denver.shkola_softhemeproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickButtonFileTask(View view){
        Intent intent = new Intent(MainActivity.this, FileTaskActivity.class);
        startActivity(intent);
    }

    public void onClickButtonDivisorsTask(View view){
        Intent intent = new Intent(MainActivity.this, DivisorsTaskActivity.class);
        startActivity(intent);
    }


}