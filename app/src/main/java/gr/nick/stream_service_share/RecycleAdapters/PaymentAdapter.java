package gr.nick.stream_service_share.RecycleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gr.nick.stream_service_share.Models.Payment;
import gr.nick.stream_service_share.Models.User;
import gr.nick.stream_service_share.R;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    List<Payment> paymentList;

    private LayoutInflater mInflater;

    private ItemClickListener mItemClickListener;

    public PaymentAdapter(Context context, List<Payment> payments) {
        this.mInflater = LayoutInflater.from(context);
        this.paymentList = payments;
    }

    public PaymentAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setPaymentList(List<Payment> payments) { this.paymentList = payments; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        String date = payment.dateConverter();
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        if (paymentList != null){
            return paymentList.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.RecyclerItemTextView);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
            return false;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemLongClick(View view, int position);
    }

    public Payment getPayment(int id) {return paymentList.get(id);}
}
