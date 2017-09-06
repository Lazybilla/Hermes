package c.alpha_hermes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zeeshan on 30-07-2017.
 */

public class Dapter extends ArrayAdapter<User> {

    private final Context context ;
    private final ArrayList<User> data ;
    private final int  layoutResourceId;


    public Dapter(Context context, int layoutResourceId, ArrayList<User> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();

            holder.mMessage = (TextView)row.findViewById(R.id.aakhiri);
            holder.mLastTime = (TextView)row.findViewById(R.id.timebitch);
            holder.mGroupName = (TextView)row.findViewById(R.id.ZeeshanChatRoom);
            holder.mPhoto  = (CircleImageView)row.findViewById(R.id.thumbnail);


            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }



        User person = data.get(position);


        holder.mGroupName.setText(person.getmName());
        holder.mLastTime.setText(person.getTime());
        holder.mMessage.setText(person.getLast());
        String Photo = person.getImage();
        Picasso.with(getContext()).load(Photo).into(holder.mPhoto);

        return row;
    }

    private  static class ViewHolder
    {

        TextView mMessage ;
        TextView mLastTime ;
        CircleImageView mPhoto ;
        TextView mGroupName ;
        //Context context = this;

        View mView ;



    }




}
