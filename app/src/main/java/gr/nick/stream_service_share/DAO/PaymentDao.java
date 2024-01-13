package gr.nick.stream_service_share.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import gr.nick.stream_service_share.Models.Payment;
import gr.nick.stream_service_share.Models.User;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM payment")
    LiveData<List<Payment>> getAllPayments();

    @Query("Select * from payment where userId = :userid order by id desc")
    LiveData<List<Payment>> getPaymentByUserID(int userid);

    @Insert
    void insert(Payment payment);


    @Delete
    void delete(Payment payment);
}
