package gr.nick.stream_service_share.Activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import gr.nick.stream_service_share.Models.User;
import gr.nick.stream_service_share.R;
import gr.nick.stream_service_share.RecycleAdapters.UsersAdapter;
import gr.nick.stream_service_share.ViewModel.UserViewModel;

public class MainActivity extends AppCompatActivity implements UsersAdapter.ItemClickListener, LifecycleOwner {

    FloatingActionButton floatingButton;

    UserViewModel userViewModel;

    UsersAdapter usersAdapter;

    private String notifyFlag = "";

    //TODO: 2) EDIT USER ON LONG CLICK AND POSSIBLY ADD A DELETE/INACTIVE FUNCTION
    //TODO: 3) ADD STYLES FOR RECYCLER VIEWS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.userRecylceView);
        usersAdapter = new UsersAdapter(this);
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter.setClickListener(this);

        userViewModel =new ViewModelProvider(this).get(UserViewModel.class);
/*        userViewModel.insert(new User("lemoua", "nikos", "ntetsikas"));
        userViewModel.insert(new User("moncour", "george", "dosis"));
        userViewModel.insert(new User("mpal", "panagiwths", "balomenos"));*/
        floatingButton = findViewById(R.id.FloatButton);
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();


        userViewModel.getAllUsers().observe(this, users -> {
            usersAdapter.setAllUsers(users);
            switch (notifyFlag) {
                    case "insert":
                        usersAdapter.notifyItemInserted(usersAdapter.getItemCount());
                        notifyFlag = "";
                        break;
                    case "delete":
                        usersAdapter.notifyItemRemoved(0);
                        notifyFlag = "";
                        break;
                    default:
                        usersAdapter.notifyDataSetChanged();
                        break;
                }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PaymentsActivity.class);
        User user = usersAdapter.getItem(position);
        intent.putExtra("user_id", user.getId());
        intent.putExtra("username", user.getUsername());
        startActivity(intent);
        //Toast.makeText(this, usersAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
    }

    public void AddUser(View view) {
        Intent intent = new Intent(this, NewUserFormActivity.class);
        //startActivity(intent);
        saveUser.launch(intent);
    }

    ActivityResultLauncher<Intent> saveUser = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    String username, first, last;
                    username = result.getData().getStringExtra("username");
                    first = result.getData().getStringExtra("firstname");
                    last = result.getData().getStringExtra("lastname");

                    User user = new User(username, first, last);
                    notifyFlag = "insert";
                    userViewModel.insert(user);
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Adding User Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
    );

}