package glory.semudik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

import Module.SharedVariable;


public class SplashActivity extends AppCompatActivity {


    ProgressBar progressBar;
    Intent i;
    int delay =  3000;
    DatabaseReference ref,refDevice;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final long time = 3;
    private CountDownTimer mCountDownTimer;
    private long mTimeRemaining;
    private String now;
    String activeDeviceKirim;

    DialogInterface.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(SplashActivity.this);
        ref = FirebaseDatabase.getInstance().getReference();
        refDevice = ref.child("users");
        fAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        int versionCode = BuildConfig.VERSION_CODE;
        Calendar calendar = Calendar.getInstance();
        int bulan = calendar.get(Calendar.MONTH)+1;
        now = ""+calendar.get(Calendar.DATE)+"-"+bulan+"-"+calendar.get(Calendar.YEAR);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            // User already signed in
            // get the FCM token
            String token = FirebaseInstanceId.getInstance().getToken();
            SharedVariable.nama = fAuth.getCurrentUser().getDisplayName();
            SharedVariable.userID = fAuth.getCurrentUser().getUid();

            mCountDownTimer = new CountDownTimer(time * 1000, 50) {
                @Override
                public void onTick(long millisUnitFinished) {
                    mTimeRemaining = ((millisUnitFinished / 1000) + 1);

                }

                @Override
                public void onFinish() {

                    i = new Intent(SplashActivity.this, BerandaActivity.class);
                    startActivity(i);
                }
            };
            mCountDownTimer.start();


            ref.child("users").child(fAuth.getCurrentUser().getUid()).child("friendList").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SharedVariable.list_friend.clear();
                    SharedVariable.list_key_friend.clear();
                    SharedVariable.list_nama_friend.clear();
                    String idFriend = "";
                    String namaFriend = "";
                    String key= "";

                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        idFriend = child.child("idFriend").getValue().toString();
                        namaFriend = child.child("namaFriend").getValue().toString();

                        key = child.getKey();

                        SharedVariable.list_friend.add(idFriend);
                        SharedVariable.list_nama_friend.add(namaFriend);
                        SharedVariable.list_key_friend.add(key);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            ref.child("users").child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String cek = (String) dataSnapshot.child("check").getValue();
                    String nama = (String) dataSnapshot.child("displayName").getValue();
                    Log.d("cek:",cek);
                    Log.d("nama:",nama);
                    SharedVariable.check = cek;
                    SharedVariable.nama = nama;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else {

            progressBar.setVisibility(View.GONE);
            i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
