package Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import Module.SharedVariable;
import glory.semudik.BerandaActivity;
import glory.semudik.ListFriendActivity;
import glory.semudik.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapterListFriend extends RecyclerView.Adapter<RecycleViewHolderListFriend> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_nama = new ArrayList();
    String key = "";
    Firebase Vref,refLagi;
    Bitmap bitmap;
    DatabaseReference ref;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    DialogInterface.OnClickListener listener;


    public RecycleAdapterListFriend(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        FirebaseApp.initializeApp(context.getApplicationContext());
        ref = FirebaseDatabase.getInstance().getReference();

      /*  ref.child("users").child("friendList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_nama.clear();
                String nama = "";

                int c = 0;

                ListFriendActivity.progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    int a  = (int) child.getChildrenCount();
                    nama = child.child("namaFriend").getValue().toString();
                    list_nama.add(nama);

                    c++;
                }
                ListFriendActivity.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }

    @Override
    public RecycleViewHolderListFriend onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listfriend, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderListFriend viewHolderChat = new RecycleViewHolderListFriend(view);
        return viewHolderChat;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListFriend holder, int position) {

        Resources res = context.getResources();

      // holder.txtNamaMotor.setText(nama[position].toString());
        //holder.txtPlatNomor.setText(plat[position].toString());
        //holder.contentWithBackground.setGravity(Gravity.LEFT);
       holder.txtNama.setText(SharedVariable.list_nama_friend.get(position).toString());
        holder.txtStatus.setText(SharedVariable.list_status_friend.get(position).toString());


        holder.cardlist_item.setOnClickListener(clicklistener);
        holder.txtNama.setOnClickListener(clicklistener);
        holder.relaList.setOnClickListener(clicklistener);

        holder.txtNama.setTag(holder);
        holder.relaList.setTag(holder);


    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListFriend vHolder = (RecycleViewHolderListFriend) v.getTag();
            final int position = vHolder.getPosition();
          //  Toast.makeText(context.getApplicationContext(), "Key  : "+SharedVariable.list_key_friend.get(position).toString(),Toast.LENGTH_SHORT).show();

            String status = SharedVariable.list_status_friend.get(position).toString();
            if (status.equals("aman")){
                Toast.makeText(context.getApplicationContext(), "Teman Anda dalam kondisi aman",Toast.LENGTH_SHORT).show();

            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ingin merubah status kondisi teman menjadi aman ?");
                builder.setCancelable(false);

                listener = new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_POSITIVE){
                            Toast.makeText(context.getApplicationContext(), "Status teman diubah",Toast.LENGTH_SHORT).show();
                           ref.child("users").child(SharedVariable.list_key_friend.get(position)).child("status").setValue("aman");
                            i = new Intent(context.getApplicationContext(), BerandaActivity.class);
                            context.startActivity(i);

                        }

                        if(which == DialogInterface.BUTTON_NEGATIVE){
                            dialog.cancel();
                        }
                    }
                };
                builder.setPositiveButton("Ya",listener);
                builder.setNegativeButton("Tidak", listener);
                builder.show();

            }

        }
    };


    public int getItemCount() {

        return SharedVariable.list_nama_friend == null ? 0 : SharedVariable.list_nama_friend.size();
       //return nama.length;

    }



}
