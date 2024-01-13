package gr.nick.stream_service_share.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import gr.nick.stream_service_share.Models.Payment;
import gr.nick.stream_service_share.R;
import gr.nick.stream_service_share.RecycleAdapters.PaymentAdapter;
import gr.nick.stream_service_share.RecycleAdapters.UsersAdapter;
import gr.nick.stream_service_share.ViewModel.PaymentViewModel;
import gr.nick.stream_service_share.ViewModel.UserViewModel;

public class PaymentsActivity extends AppCompatActivity implements PaymentAdapter.ItemClickListener, LifecycleOwner {

    FloatingActionButton floatingButton;

    Button quickAddButton;
    PaymentViewModel paymentViewModel;

    PaymentAdapter paymentAdapter;

    private int position = -1, userid = -1;
    private String  notifyFlag = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView userTV = findViewById(R.id.userTV);
        userTV.setText(username);

        floatingButton = findViewById(R.id.FloatButtonPay);
        quickAddButton = findViewById(R.id.QuickPay);



        userid = intent.getIntExtra("user_id", -1);

        RecyclerView recyclerView = findViewById(R.id.paymentsRecylceView);
        paymentAdapter = new PaymentAdapter(this);
        recyclerView.setAdapter(paymentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentAdapter.setClickListener(this);

        paymentViewModel = new PaymentViewModel(getApplication(), userid);

        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

        paymentViewModel.getPayments().observe(this, payments -> {
            paymentAdapter.setPaymentList(payments);
            switch (notifyFlag) {
                case "insert":
                    paymentAdapter.notifyItemInserted(0);
                    notifyFlag = "";
                    llm.scrollToPositionWithOffset(0, 0);
                    break;
                case "delete":
                    if (position>0){
                        paymentAdapter.notifyItemRemoved(position);
                        notifyFlag = "";
                        position=-1;
                    } else {
                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    paymentAdapter.notifyDataSetChanged();
                    break;
            }
        });

        quickAddButton.setOnClickListener(view -> {
            Payment payment = new Payment();
            if (paymentAdapter.getItemCount() == 0 ){
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                payment.setMonth(localDate.getMonthValue());
                payment.setYear(localDate.getYear());
                payment.setUserId(userid);

            } else {
                Payment latest = paymentAdapter.getPayment(0);
                int month = latest.getMonth(), year = latest.getYear();

                if (month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }

                payment.setMonth(month);
                payment.setYear(year);
                payment.setUserId(userid);
            }
            notifyFlag = "insert";
            paymentViewModel.insert(payment);
        });

        floatingButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), MultiPaymentsActivity.class);
            //startActivity(intent1);
            saveMultiPayments.launch(intent1);
        });
    }

    @Override
    public void onItemLongClick(View view, int position) {
        //TODO: 1) DELETE ITEM ON LONG CLICK
        Toast.makeText(this, paymentAdapter.getPayment(position).toString(), Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> saveMultiPayments = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    int fromMonth, fromYear, toMonth, toYear;
                    boolean bool;
                    assert result.getData() != null;
                    fromMonth = result.getData().getIntExtra("fromMonth", 0);
                    fromYear = result.getData().getIntExtra("fromYear", 0);
                    toMonth = result.getData().getIntExtra("toMonth", 0);
                    toYear = result.getData().getIntExtra("toYear", 0);
                    Payment payment = new Payment();
                    payment.setUserId(userid);
                    if (fromMonth != 0 && fromYear != 0 && toMonth != 0 && toYear != 0) {
                        if (toYear < fromYear || (toYear == fromYear && toMonth < fromMonth)) {
                            Toast.makeText(this, "starting date is earlier than ending date", Toast.LENGTH_SHORT).show();
                        } else {
                            do {
                                payment.setMonth(fromMonth);
                                payment.setYear(fromYear);
                                notifyFlag = "insert";
                                paymentViewModel.insert(payment);
                                bool = !((fromMonth == toMonth) && (fromYear == toYear));
                                if (fromMonth != 12) {
                                    fromMonth++;
                                } else {
                                    fromYear++;
                                    fromMonth = 1;
                                }
                            } while (bool);
                        }
                    }
                }
            }
    );

}