package lilitam.andyciu.lianliankan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MenuActivity extends AppCompatActivity implements Button.OnClickListener{

    Button btn_menu_start;
    Spinner choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        choice = (Spinner)findViewById(R.id.spinner);
        btn_menu_start = (Button)findViewById(R.id.btn_menu_start);
        btn_menu_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent it22 = new Intent(this, Main2Activity.class);
        Intent it44 = new Intent(this, MainActivity.class);

        int index = choice.getSelectedItemPosition();

        if (index == 0)
        {
            startActivity(it22);
        }

        else if(index == 1)
        {
            startActivity(it44);
        }
    }
}
