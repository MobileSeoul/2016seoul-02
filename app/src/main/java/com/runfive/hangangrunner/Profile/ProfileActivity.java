package com.runfive.hangangrunner.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runfive.hangangrunner.Common.BaseActivity;
import com.runfive.hangangrunner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

// 프로필 수정
public class ProfileActivity extends BaseActivity {
    private TextView textViewName;
    private TextView textViewBirth;
    private TextView textViewGender;
    private ProfilePHPRequest profilePHPRequest;
    private ProfileImageRequest profileImageRequest;
    private TextView textViewTotalKm;
    private TextView textviewTotalPoint;
    private TextView textViewGoldMedal;
    private TextView textViewSilverMedal;
    private TextView textViewBronzeMedal;
    private ImageView profileImage;
    private String selectedImagePath;
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int CROP_FROM_CAMERA = 3;


    private String user_id;
    private double totalKm=0.0;
    private int goldMedal=0;
    private int totalPoint=0;
    private double totalKcal=0.0;
    private int silverMedal=0;
    private int bronzeMedal=0;

    public static final String UPLOAD_URL = "Your Server ULR";
    public static final String UPLOAD_KEY = "image";
    private Bitmap photo;
    private String encodedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Intent intent = getIntent();
        //UserObject uservo = (UserObject) intent.getSerializableExtra("UserObject");

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        String username = sharedPreferences.getString("name", null);
        String userid = sharedPreferences.getString("id", null);
        String birth = sharedPreferences.getString("birth", null);
        String gender = sharedPreferences.getString("gender", null);

        //String json = sharedPreferences.getString("login","");

        //Gson gson = new Gson();
        //UserObject userObject = gson.fromJson(json, UserObject.class);


        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewBirth = (TextView) findViewById(R.id.textView_birth);
        textViewGender = (TextView) findViewById(R.id.textView_gender);
        textViewTotalKm = (TextView) findViewById(R.id.total_km_profile);
        textViewGoldMedal = (TextView) findViewById(R.id.textview_goldmedal);
        textViewSilverMedal = (TextView) findViewById(R.id.textview_silvermedal);
        textViewBronzeMedal = (TextView) findViewById(R.id.textview_bronzemedal);
        textviewTotalPoint = (TextView) findViewById(R.id.total_point_profile);

        profileImage = (ImageView) findViewById(R.id.profile_image);

        //profileImage.setImageBitmap();

        textViewName.setText(username);
        textViewBirth.setText(birth );
        textViewGender.setText(gender);

        /**
         * 갤러리 연동
         */

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);*/

                /*
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e){e.printStackTrace();}
                */
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_GALLERY);
            }
        });

        /**
         * db연결
         */
        try {
            profilePHPRequest = new ProfilePHPRequest("Your Server ULR");
            profilePHPRequest.execute(userid);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            profileImageRequest = new ProfileImageRequest("Your Server ULR");
            profileImageRequest.execute(userid);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /**
        textViewName.setText(userObject.getName());
        textViewBirth.setText(userObject.getBirth());
        textViewGender.setText(userObject.getGender());
        */
        //Set nav drawer selected to second item in list
        mNavigationView.getMenu().getItem(1).setChecked(true);

    }

    private class ProfileImageRequest extends AsyncTask<String, Void, String> {
        private URL url;

        public ProfileImageRequest(String url) throws MalformedURLException {
            this.url = new URL(url);
        }

        private String readStream(InputStream in) throws IOException {
            StringBuilder jsonHtml = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;

            while((line = reader.readLine()) != null)
                jsonHtml.append(line);

            reader.close();
            return jsonHtml.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String postData = "user_id=" + params[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                String result = readStream(conn.getInputStream());

                conn.disconnect();

                return result;
            }
            catch (Exception e) {
                Log.i("PHPRequest", "request was failed.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jObject = new JSONObject(s);
                JSONArray results = jObject.getJSONArray("result");

                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    encodedimage = temp.getString("imagepath");
                }

                try {
                    /*
                    URL url = new URL(encodedimage);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    profileImage.setImageBitmap(bm);
                    */
                    ImageUrlTask imageUrlTask = new ImageUrlTask();
                    imageUrlTask.execute(encodedimage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  내부요청
     */

    private class ImageUrlTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap bm;
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            profileImage.setImageBitmap(bm);
        }
    }

    private class ProfilePHPRequest extends AsyncTask<String, Void, String> {
        private URL url;

        public ProfilePHPRequest(String url) throws MalformedURLException {
            this.url = new URL(url);
            // this.user_id = user_id;
        }

        private String readStream(InputStream in) throws IOException {
            StringBuilder jsonHtml = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;

            while((line = reader.readLine()) != null)
                jsonHtml.append(line);

            reader.close();
            return jsonHtml.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String postData = "user_id=" + params[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(50000);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                String result = readStream(conn.getInputStream());

                conn.disconnect();

                return result;
            }
            catch (Exception e) {
                Log.i("PHPRequest", "request was failed.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String whichMedal;
            super.onPostExecute(s);
            try {
                JSONObject jObject = new JSONObject(s);
                JSONArray results = jObject.getJSONArray("result");

                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    totalKm += Double.parseDouble(temp.getString("distance"));
                    totalKcal += Double.parseDouble(temp.getString("kcal"));
                    totalPoint += Integer.parseInt(temp.getString("user_point").trim());

                    whichMedal = temp.getString("medal");
                    switch (whichMedal) {
                        case "G":
                            goldMedal++;
                            break;
                        case "S":
                            silverMedal++;
                            break;
                        case "B":
                            bronzeMedal++;
                            break;
                        default:
                            continue;
                    }
                }

                textViewTotalKm.setText(String.valueOf(totalKm));
                textViewGoldMedal.setText(Integer.toString(goldMedal));
                textViewSilverMedal.setText(Integer.toString(silverMedal));
                textViewBronzeMedal.setText(Integer.toString(bronzeMedal));
                textviewTotalPoint.setText(String.valueOf(totalPoint));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

            String userid = sharedPreferences.getString("id", null);
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler(userid);


            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(photo);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        if(requestCode == PICK_FROM_GALLERY) {
            Bundle extras = data.getExtras();
            if(extras != null) {
                Bitmap photo = extras.getParcelable("data");
                profileImage.setImageBitmap(photo);

            }
        }
        */

        if(resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_FROM_CAMERA:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    photo = extras.getParcelable("data");
                    profileImage.setImageBitmap(photo);
                    uploadImage();

                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }

                break;
            }

            case PICK_FROM_GALLERY:
                mImageCaptureUri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 90);
                intent.putExtra("outputY", 90);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;

        }

        /*
        if(resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getPath(selectedImageUri);
        }

        Bundle extras = data.getExtras();
        Bitmap photo = extras.getParcelable("data");
        profileImage.setImageBitmap(photo);
        */
    }





    /** HIDE TOOLBAR **/
//    @Override
//    protected boolean useToolbar() {
//        return false;
//    }



    /** HIDE hamburger menu **/
//    @Override
//    protected boolean useDrawerToggle() {
//        return false;
//    }

}
