package cn.imgrocket.imgrocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Button buttonSubmit = findViewById(R.id.test_button_submit);
        final TextView textViewView = findViewById(R.id.test_textview_view);
        final EditText editTextInput = findViewById(R.id.test_edit_input);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Function.INSTANCE.post("https://api.huhaorui.com/test.php", editTextInput.getText().toString(), new Function.Callback() {
                    @Override
                    public void onResponse(String result) {
                        textViewView.setText(result);
                    }
                });

            }
        });
        //我希望，在textViewView上显示出返回的东西
    }
}
