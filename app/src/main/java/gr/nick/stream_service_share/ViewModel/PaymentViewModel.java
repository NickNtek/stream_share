package gr.nick.stream_service_share.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import gr.nick.stream_service_share.Models.Payment;
import gr.nick.stream_service_share.Repository.PaymentRepository;

public class PaymentViewModel extends AndroidViewModel {

    private PaymentRepository paymentRepository;

    private LiveData<List<Payment>> payments;

    public PaymentViewModel(@NonNull Application application, int userid) {
        super(application);
        paymentRepository = new PaymentRepository(application, userid);
        payments = paymentRepository.getmPayments();
    }

    public LiveData<List<Payment>> getPayments() {return payments;}

    public void insert(Payment payment) {paymentRepository.insert(payment);}
}
