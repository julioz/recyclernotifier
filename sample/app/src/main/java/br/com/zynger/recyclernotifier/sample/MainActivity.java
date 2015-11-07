package br.com.zynger.recyclernotifier.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button codeInflated = (Button) findViewById(R.id.activity_main_code_button);
        Button layoutInflated = (Button) findViewById(R.id.activity_main_xml_button);

        codeInflated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CodeInflatedSampleActivity.class);
                startActivity(intent);
            }
        });

        layoutInflated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LayoutInflatedSampleActivity.class);
                startActivity(intent);
            }
        });
    }
}
