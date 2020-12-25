package com.alatheer.missing.Notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Authentication.AboutActivity;
import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.Model.Notification.Datum;
import com.alatheer.missing.Data.Remote.Model.Notification.Notification;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.notifications_recycler)
    RecyclerView notification_recycler;
    @BindView(R.id.txt_notification)
    TextView txt_notification;
    GridLayoutManager gridLayoutManager;
    NotificationsAdapter notificationsAdapter;
    int page = 1;
    MySharedPreference sharedPreferences;
    int user_id;
    User user;
    private int pastvisibleitem,visibleitemcount,totalitemcount,previous_total=0;
    int view_threshold = 10;
    private boolean isloading;
    List<Datum> notificationlist,notificationlist2;
    boolean active = false;
    String language;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        sharedPreferences = MySharedPreference.getInstance();
        user = sharedPreferences.Get_UserData(this);
        user_id = user.getId();
        Log.e("user_id",user_id+"");
        Paper.init(this);
        language = Paper.book().read("language");
        updateview(language);
        LocalBroadcastManager.getInstance(this).registerReceiver(mhandler,new IntentFilter("com.alatheer.missing_FCM-MESSAGE"));
        getmynotification(user_id,page);
        notification_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleitemcount = gridLayoutManager.getChildCount();
                totalitemcount = gridLayoutManager.getItemCount();
                pastvisibleitem = gridLayoutManager.findFirstVisibleItemPosition();
                if(dy>0){
                    if(isloading){
                        if(totalitemcount>previous_total){
                            isloading = false;
                            previous_total = totalitemcount;
                        }
                    }
                    if(!isloading &&(totalitemcount-visibleitemcount)<= pastvisibleitem+view_threshold){
                        page++;
                        PerformPagination(page);
                        isloading = true;
                    }

                }
            }
        });
    }

    private void updateview(String language) {
        context = LocaleHelper.setLocale(this,language);
        resources = context.getResources();
        txt_notification.setText(resources.getString(R.string.notification));
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }

    private void PerformPagination(int page) {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Notification> call = getDataService.get_notifications(user_id,page);
            call.enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if(response.isSuccessful()){
                        if(page<= response.body().getLastPage()){
                            notificationlist2 = response.body().getData();
                            notificationsAdapter.add_notification(notificationlist2);
                            //Toast.makeText(NotificationActivity.this, "page:"+page+"is loaded", Toast.LENGTH_SHORT).show();
                        }else {
                            //Toast.makeText(NotificationActivity.this, "no more available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {

                }
            });
        }
    }

    private void getmynotification(int user_id, int page) {
        if(Utilities.isNetworkAvailable(NotificationActivity.this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Notification> call = getDataService.get_notifications(user_id,page);
            call.enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    if(response.isSuccessful()){
                        notificationlist = response.body().getData();
                        init_recycler(notificationlist);
                    }
                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {

                }
            });
        }
    }

    private void init_recycler(List<Datum> notificationlist) {
        //Toast.makeText(this, "user_id "+user_id, Toast.LENGTH_SHORT).show();
        notificationsAdapter = new NotificationsAdapter(NotificationActivity.this,notificationlist);
        notification_recycler.setAdapter(notificationsAdapter);
        gridLayoutManager = new GridLayoutManager(this,1);
        notification_recycler.setLayoutManager(gridLayoutManager);
        notification_recycler.setHasFixedSize(true);
    }

    public void Back(View view) {
        onBackPressed();
    }
    private BroadcastReceiver mhandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            //message.setText(msg);
            if(active == true){
                Utilities.showNotificationInADialog(NotificationActivity.this,msg);
            }
        }
    };
}
