package com.alatheer.missing.Notifications;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.Model.Notification.Datum;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsHolder> {
    Context context,m_context;
    List<Datum> datumList;
    Resources resources;
    String language;
    NotificationActivity notificationActivity;
    public NotificationsAdapter(Context context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
        notificationActivity = (NotificationActivity) context;
    }

    @NonNull
    @Override
    public NotificationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new NotificationsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsHolder holder, int position) {
        language =  Paper.book().read("language");
        Log.e("language",language);
        if(language == null){
            Paper.book().write("language","ar");
        }
        holder.setData(datumList.get(position));
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class NotificationsHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt_name)
        TextView item_name;
        @BindView(R.id.txt_find)
        TextView txt_find;
        @BindView(R.id.txt_user_name)
        TextView txt_user_name;
        public NotificationsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Paper.init(notificationActivity);
        }

        public void setData(Datum datum) {
            m_context = LocaleHelper.setLocale(notificationActivity,language);
            resources = m_context.getResources();
            item_name.setText(datum.getName());
            txt_user_name.setText(datum.getUserName());
            txt_find.setText(resources.getString(R.string.find));
        }
    }
    public void add_notification(List<Datum> datumList2){
        for (Datum datum:datumList2){
            datumList.add(datum);
        }
        notifyDataSetChanged();
    }
}
