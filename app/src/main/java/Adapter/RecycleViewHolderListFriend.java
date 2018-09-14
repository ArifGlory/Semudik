package Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import glory.semudik.R;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderListFriend extends RecyclerView.ViewHolder {

    public TextView txtNama;
    public CardView cardlist_item;
    public RelativeLayout relaList;

    public RecycleViewHolderListFriend(View itemView) {
        super(itemView);

        txtNama= (TextView) itemView.findViewById(R.id.txt_nama);
        cardlist_item = (CardView) itemView.findViewById(R.id.cardlist_item);
        relaList = (RelativeLayout) itemView.findViewById(R.id.relaList);

    }
}
