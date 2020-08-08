package com.alatheer.missing.Utilities;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Authentication.LoginActivity;
import com.alatheer.missing.Authentication.RegisterationActivity;
import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.Model.Authentication.UserData;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Home.Home;
import com.alatheer.missing.R;

import java.io.File;

import androidx.appcompat.app.AlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utilities {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            Toast.makeText(context, context.getString(R.string.checkNetworkConnection), Toast.LENGTH_LONG).show();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Typeface getTypeFace(Context context){
        return Typeface.createFromAsset(context.getAssets(),"font/twolight.otf");
    }

    public static String getLang(Context context){
        return "ar";
    }
    public static RequestBody getRequestBodyText(String data)
    {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),data);
        return requestBody;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getImagePath(Context context, Uri uri)
    {
        int currentApiVersion;
        try
        {
            currentApiVersion = Build.VERSION.SDK_INT;
        }
        catch(NumberFormatException e)
        {
            //API 3 will crash if SDK_INT is called
            currentApiVersion = 3;
        }
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {


            if ( DocumentsContract.isDocumentUri(context, uri)) {

                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                }
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] { split[1] };

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }
        else if (currentApiVersion <= Build.VERSION_CODES.HONEYCOMB_MR2 && currentApiVersion >= Build.VERSION_CODES.HONEYCOMB)

        {
            String[] proj = {MediaStore.Images.Media.DATA};
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(
                    context,
                    uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null)
            {
                int column_index =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
            }
            return result;
        }
        else
        {

            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index
                    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }

    public static MultipartBody.Part getMultiPart(Context context, Uri uri, String partName)
    {
        File file =  getFileFromImagePath(getImagePath(context,uri));
        RequestBody requestBody = getRequestBodyImage(file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(partName,file.getName(),requestBody);
        Log.e("file",file.getName());
        return part;

    }

    private static RequestBody getRequestBodyImage(File file)
    {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        return requestBody;
    }

    private static File getFileFromImagePath(String path)
    {
        File file = new File(path);
        return file;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static void CreateAlertDialog(Context context,String phone,int flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View view = inflater.inflate(R.layout.question_item, null);
        EditText et_code = view.findViewById(R.id.et_code);
        //txt_code.setText(code);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_code.getText().toString();
                MySharedPreference mprefs = MySharedPreference.getInstance();
                if(!TextUtils.isEmpty(code)){
                    GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call <User> call = getDataService.verify_code(phone,code);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()){
                                Log.e("success100","1");
                                if(response.body().getSuccess() == 1 && response.body().getActivate()==1){
                                    Log.e("success1","1");
                                    if(flag == 1){
                                        RegisterationActivity registerationActivity = (RegisterationActivity) context;
                                        context.startActivity(new Intent(context, LoginActivity.class));
                                        registerationActivity.finish();
                                        mprefs.Create_Update_UserData(context,response.body());
                                    }else if(flag ==2){
                                        LoginActivity loginActivity = (LoginActivity) context;
                                        context.startActivity(new Intent(context, Home.class));
                                        loginActivity.finish();
                                    }
                                    Toast.makeText(context, "تمت تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    et_code.setError("الكود غير صحيح");
                                    //dialog.dismiss();
                                }
                            }else {
                                et_code.setError("الكود غير صحيح");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                              Log.e("failure",t.getMessage());
                        }
                    });
                }else {
                    et_code.setError("برجاء ادخال الكود الخاص بك");
                }

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(750, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
