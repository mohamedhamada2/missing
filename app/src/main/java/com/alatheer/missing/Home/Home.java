package com.alatheer.missing.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Authentication.AboutActivity;
import com.alatheer.missing.Authentication.LegalLetterActivity;
import com.alatheer.missing.Authentication.Splash;
import com.alatheer.missing.Categories.CategoryActivity;
import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.Model.RemoveToken.RemoveToken;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;
import com.alatheer.missing.Search.SearchActivity;
import com.alatheer.missing.Utilities.Utilities;

public class Home extends AppCompatActivity {
    @BindView(R.id.menu_imgbtn)
    View menu_imgbtn;
    @BindView(R.id.txt_home)
    TextView txt_home;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_search)
    Button btn_search;
    String language;
    PopupMenu popup;
    Context context;
    Resources resources;
    User user;
    MySharedPreference sharedPreferences;
    boolean active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Paper.init(this);
        sharedPreferences = MySharedPreference.getInstance();
        user = sharedPreferences.Get_UserData(Home.this);
        popup = new PopupMenu(this, menu_imgbtn);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onOptionsItemSelected);
        language = Paper.book().read("language");
        updateview(language);
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
    private void updateview(String language) {
         context = LocaleHelper.setLocale(this, language);
         resources = context.getResources();
        txt_home.setText(resources.getString(R.string.home));
        btn_add.setText(resources.getString(R.string.add_missing_or_existing));
        btn_search.setText(resources.getString(R.string.searching_for_missing_or_existing));
        popup.getMenu().getItem(0).setTitle(resources.getString(R.string.about));
        popup.getMenu().getItem(1).setTitle(resources.getString(R.string.legalletter));
        popup.getMenu().getItem(2).setTitle(resources.getString(R.string.logout));
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Add(View view) {
        startActivity(new Intent(this, CategoryActivity.class));
    }

    public void search(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void menu_imgbtn() {

    }

    @OnClick(R.id.menu_imgbtn)
    public void menu_imgbtn(View view) {
        popup.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Logout();
                return true;
            case R.id.legal_letter:
                startActivity(new Intent(Home.this, LegalLetterActivity.class));
                return true;
            case R.id.about:
                startActivity(new Intent(Home.this, AboutActivity.class));
                return true;
            default:
                return true;
        }
    }

    private void Logout() {
        if(Utilities.isNetworkAvailable(Home.this)){
            GetDataService getDataService =RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<RemoveToken> call = getDataService.remove_token(user.getId()+"");
            call.enqueue(new Callback<RemoveToken>() {
                @Override
                public void onResponse(Call<RemoveToken> call, Response<RemoveToken> response) {
                    if(response.isSuccessful()){
                        if(response.body().getSuccess()==1){
                            sharedPreferences = new MySharedPreference(Home.this);
                            sharedPreferences.ClearData(Home.this);
                            startActivity(new Intent(Home.this, Splash.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RemoveToken> call, Throwable t) {

                }
            });
        }
    }

    private BroadcastReceiver mhandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            //message.setText(msg);
            if(active == true){
                Utilities.showNotificationInADialog(Home.this,msg);
            }

        }
    };

}