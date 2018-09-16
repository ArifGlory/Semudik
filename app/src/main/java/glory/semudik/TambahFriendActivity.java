package glory.semudik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Module.Friend;
import Module.Utils;

public class TambahFriendActivity extends AppCompatActivity {

    EditText etIdFriend;
    Button btnSimpan;
    Intent i;
    DatabaseReference ref,refUser;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    public static List<String> list_kode_device = new ArrayList();
    public static List<String> list_key = new ArrayList();
    public static List<String> list_nama = new ArrayList();
    ProgressBar progressBar;
    String userID;
    Friend friendlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_friend);
        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(TambahFriendActivity.this);
        ref = FirebaseDatabase.getInstance().getReference();
        refUser = FirebaseDatabase.getInstance().getReference().child("users");
        fAuth = FirebaseAuth.getInstance();
        ref = ref.child("users");
        userID = fAuth.getCurrentUser().getUid();

        etIdFriend = (EditText) findViewById(R.id.userEmailId);
        btnSimpan = (Button) findViewById(R.id.signUpBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        getDataDevice();

    }

    private void getDataDevice(){
        matikanKomponen();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list_kode_device.clear();
                list_nama.clear();
                list_key.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String key = child.child("id").getValue().toString();
                    String kunci = child.getKey();
                    String nama = child.child("displayName").getValue().toString();
                    list_kode_device.add(key);
                    list_nama.add(nama);
                    list_key.add(kunci);
                }

            /*    if (list_kode_device.contains(fAuth.getCurrentUser().getEmail())){
                    int jmlDevice = list_kode_device.size();
                    for (int a =0;a<jmlDevice;a++){
                        if (list_kode_device.get(a).toString().equals(fAuth.getCurrentUser().getEmail())){
                            list_kode_device.remove(a);
                            list_nama.remove(a);
                            list_key.remove(a);
                        }
                    }
                }*/
                hidupkanKomponen();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void matikanKomponen(){
        progressBar.setVisibility(View.VISIBLE);
        etIdFriend.setEnabled(false);
    }

    private void hidupkanKomponen(){
        progressBar.setVisibility(View.GONE);
        etIdFriend.setEnabled(true);
    }

    public  void customToast(String s){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_root));

        TextView text = (TextView) layout.findViewById(R.id.toast_error);
        text.setText(s);
        Toast toast = new Toast(getApplicationContext());// Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
        toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
        toast.setView(layout); // Set Custom View over toast
        toast.show();// Finally show toast
    }

    private void checkValidation() {

        matikanKomponen();

        // Get all edittext texts
        String getEmailId = etIdFriend.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                ) {

            customToast("Semua field harus diiisi");
            hidupkanKomponen();
        }
        // Else do signup or do your stuff
        else
            addFriend(getEmailId);
    }

    private void addFriend(final String deviceID){
        matikanKomponen();
        int jmlDevice = list_kode_device.size();

        if (list_kode_device.contains(deviceID)){


            for (int c=0;c<jmlDevice;c++){


                if (list_kode_device.get(c).toString().equals(deviceID)) {
                    customToast("Device Berhasil ditambahkan !");

                    //tambahkan device ke list nya user tsb

                    friendlist = new Friend(list_kode_device.get(c).toString(),list_nama.get(c).toString());

                    //String key = ref.child(userID).child("friendList").push().getKey();
                    refUser.child(userID).child("friendList").child(list_key.get(c).toString()).setValue(friendlist);

                    i = new Intent(getApplicationContext(),SplashActivity.class);
                    startActivity(i);
                }
            }
            hidupkanKomponen();

        }else {
            customToast("Device tidak ditemukan !");
            hidupkanKomponen();
        }
    }
}
