package gr.nick.stream_service_share.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import gr.nick.stream_service_share.Models.User;
import gr.nick.stream_service_share.R;
import gr.nick.stream_service_share.ViewModel.UserViewModel;

public class NewUserFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);

        UserViewModel userViewModel = new UserViewModel(this.getApplication());
        EditText usernameET, firstNameET, lastNameET;
        Button submit = findViewById(R.id.submitUserButton);
        Button cancel = findViewById(R.id.CancelUserButton);
        usernameET = findViewById(R.id.usernameEditText);
        firstNameET = findViewById(R.id.firstNameEditText);
        lastNameET = findViewById(R.id.lastNameEditText);


        submit.setOnClickListener( view -> {
            boolean b = true;
            if (usernameET.length() == 0) {
                b = false;
                usernameET.setError("This Field is Required");
            }

            if (firstNameET.length() == 0) {
                b = false;
                firstNameET.setError("This Field is Required");
            }

            if (lastNameET.length() == 0) {
                b = false;
                lastNameET.setError("This Field is Required");
            }

            if (b) {
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                replyIntent.putExtra("username", usernameET.getText().toString());
                replyIntent.putExtra("firstname", firstNameET.getText().toString());
                replyIntent.putExtra("lastname", lastNameET.getText().toString());
                finish();

            }
        });

        cancel.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

    }

}