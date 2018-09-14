package glory.semudik;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import Adapter.RecycleAdapterListFriend;
import Module.SharedVariable;

public class ListFriendActivity extends AppCompatActivity {

    FloatingActionButton btnTambah;
    Intent i;
    RecyclerView recycler_listMotor;
    RecycleAdapterListFriend adapter;
    public static ProgressBar progressBar;
    TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);

        btnTambah = (FloatingActionButton) findViewById(R.id.btnCreate);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(),TambahFriendActivity.class);
                startActivity(i);
            }
        });

        recycler_listMotor = (RecyclerView) findViewById(R.id.recycler_listlevel);
        adapter = new RecycleAdapterListFriend(this);
        recycler_listMotor.setAdapter(adapter);
        recycler_listMotor.setLayoutManager(new LinearLayoutManager(this));

        if (SharedVariable.list_friend.isEmpty()){
            txtInfo.setVisibility(View.VISIBLE);
        }
    }
}
