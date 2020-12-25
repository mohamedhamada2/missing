package com.alatheer.missing.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.missing.Countries.CountriesActivity;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.About.About;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;
import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {
    String language;
    Context context;
    Resources resources;
    @BindView(R.id.txt_about)
    TextView txt_about;
    @BindView(R.id.about_title)
    TextView txt_title;
    @BindView(R.id.txt_details)
    TextView txt_details;
    @BindView(R.id.about_img)
    ImageView about_img;
    boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Paper.init(this);
        language = Paper.book().read("language");
        updateview(language);
        get_about();
        LocalBroadcastManager.getInstance(this).registerReceiver(mhandler,new IntentFilter("com.alatheer.missing_FCM-MESSAGE"));
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

    private void get_about() {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<About> call = getDataService.get_about();
            call.enqueue(new Callback<About>() {
                @Override
                public void onResponse(Call<About> call, Response<About> response) {
                    if(response.isSuccessful()){
                        Picasso.get().load("https://mymissing.online/uploads/images/"+response.body().getLogo()).into(about_img);
                        txt_title.setText(response.body().getSiteName());
                        txt_details.setText(Html.fromHtml(response.body().getNotes()));
                        txt_details.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }

                @Override
                public void onFailure(Call<About> call, Throwable t) {

                }
            });
        }
    }

    private void updateview(String language) {
        context = LocaleHelper.setLocale(this,language);
        resources = context.getResources();
        txt_about.setText(resources.getString(R.string.about));
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
                Utilities.showNotificationInADialog(AboutActivity.this,msg);
            }
        }
    };
}