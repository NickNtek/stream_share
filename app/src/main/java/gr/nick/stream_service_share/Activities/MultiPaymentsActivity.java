package gr.nick.stream_service_share.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import gr.nick.stream_service_share.R;

public class MultiPaymentsActivity extends AppCompatActivity {

    Spinner fromMonthSP, fromYearSP, toMonthSP, toYearSP;
    Button saveBT;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_payments);

        fromMonthSP = findViewById(R.id.FromMonth);
        fromYearSP = findViewById(R.id.FromYear);
        toMonthSP = findViewById(R.id.ToMonth);
        toYearSP = findViewById(R.id.ToYear);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromMonthSP.setAdapter(monthAdapter);
        toMonthSP.setAdapter(monthAdapter);

        List<Integer> yearArray = new ArrayList<>();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        for (int i = 0; i < 10; i++, year++) {
            yearArray.add(year);
        }
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, yearArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromYearSP.setAdapter(yearAdapter);
        toYearSP.setAdapter(yearAdapter);

        saveBT = findViewById(R.id.insertMulti);

        saveBT.setOnClickListener(view -> {
            //Toast.makeText(this, "start: "+fromMonthSP.getSelectedItem().toString()+" "+fromYearSP.getSelectedItem().toString()+"\nEnd: "+toMonthSP.getSelectedItem().toString()+" "+toYearSP.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            int fromYear =Integer.parseInt(fromYearSP.getSelectedItem().toString()),
                    toYear =Integer.parseInt(toYearSP.getSelectedItem().toString()),
                    fromMonth = monthConverter(fromMonthSP.getSelectedItem().toString()),
                    toMonth = monthConverter(toMonthSP.getSelectedItem().toString());

            Intent replyIntent = new Intent();
            setResult(RESULT_OK, replyIntent);
            replyIntent.putExtra("fromYear", fromYear);
            replyIntent.putExtra("toYear", toYear);
            replyIntent.putExtra("fromMonth", fromMonth);
            replyIntent.putExtra("toMonth", toMonth);
            finish();
        });

    }

    private int monthConverter(String month) {
        switch (month) {
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;
        }
    }
}