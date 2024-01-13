package gr.nick.stream_service_share.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import gr.nick.stream_service_share.DAO.PaymentDao;
import gr.nick.stream_service_share.Databases.AppDatabase;
import gr.nick.stream_service_share.Models.Payment;

public class PaymentRepository {
    private PaymentDao mPaymentDao;

    private LiveData<List<Payment>> mPayments;

    public PaymentRepository(Application application, int userId) {
        AppDatabase paymentDatabase = AppDatabase.getUserDatabase(application);
        mPaymentDao = paymentDatabase.paymentDao();
        mPayments = mPaymentDao.getPaymentByUserID(userId);
    }

    public LiveData<List<Payment>> getmPayments() {return mPayments;}

    public void insert(Payment payment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPaymentDao.insert(payment);
        });
    }

}
