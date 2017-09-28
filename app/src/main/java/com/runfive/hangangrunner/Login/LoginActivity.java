package com.runfive.hangangrunner.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.runfive.hangangrunner.Common.NetworkUtil;
import com.runfive.hangangrunner.Main.MainActivity;
import com.runfive.hangangrunner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import static android.R.attr.name;

/**
 * Created by JunHo on 2016-08-08.
 */
// BaseActivity 상속하지 말것 !!!!!!!
public class LoginActivity extends AppCompatActivity {

    // 네이버 API
    private String OAUTH_CLIENT_ID = "API_KEY";
    private String OAUTH_CLIENT_SECRET = "API_KEY";
    private String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    public static OAuthLogin mOAuthLoginInstance;
    public static Context mContext;

    // 네이버 로그인 버튼
    private OAuthLoginButton mOAuthLoginButton;

    // facebook 로그인
    private CallbackManager callbackManager;
    private AccessToken token;
    private LoginButton facebook_login_btn;


    private String userName;
    private String userID;
    private String userBirth;
    private String userGender;
    private String userEmail;

    //
    private LoginPHPRequest loginPHPRequest;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.content_login);

        // Server
        NetworkUtil.setNetworkPolicy();

        //네이버 로그인
        OAuthLoginDefine.DEVELOPER_VERSION = true;
        mContext = this;



        initNaver();

        initFacebook();




    }

    private void initFacebook(){

        // 페이스북 로그인
        facebook_login_btn = (LoginButton) findViewById(R.id.facebook_login_btn);


        // 로그인여부 확인
        loginCheck();

        // 페이스북 계정으로 로그인
        loginFacebook();
    }
    private  void loginFacebook(){


        // 페이스북 유저 읽어올 정보 set
        facebook_login_btn.setReadPermissions("public_profile", "email", "user_birthday");

        // 페이스북 로그인 콜백
        facebook_login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // 페이스북 계정 로그인 성공시
                Toast.makeText(LoginActivity.this, "login Success", Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                // 페이스북 JSON 회원정보
                                Log.d("graph", object.toString());
                                try {
                                    userID = object.getString("id");
                                    userEmail = object.getString("email");
                                    userName = object.getString("name");

                                    // 페이스북 gender 정보 요청 결과 male,female을 M,F로 저장
                                    if((object.getString("gender")).equals("male"))
                                    {
                                        userGender = "M";
                                    }
                                    else
                                    {
                                        userGender = "F";
                                    }

                                    // 페이스북 birthday 요청결과 MM/DD/YYYY 를 MM-DD로 저장
                                    userBirth = (object.getString("birthday")).substring(0,2) + "-"+(object.getString("birthday")).substring(3,5);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    isLogin = loginDataInput();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                                goMainActivity();

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void loginCheck(){
        // 페이스북으로 로그인이 되어 있는지 확인
        token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            Log.d("USER_ID", token.getUserId());
            userID = token.getUserId();
            mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.naver_login_btn);
            mOAuthLoginButton.setVisibility(View.INVISIBLE);
            goMainActivity();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    // 메인엑티비티로 ㄱㄱ
    private void goMainActivity() {

            removeAllPreferences();

        System.out.println("name::::"+name+"birth:::"+userBirth);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", userName);
        editor.putString("id", userID);
        editor.putString("birth", userBirth);
        editor.putString("gender", userGender);
        editor.putString("email", userEmail);
        editor.commit();



        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("userID", userID);
        startActivity(intent);
        finish();

    }


    private void initNaver() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.naver_login_btn);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        if (OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(this))) {
            facebook_login_btn = (LoginButton) findViewById(R.id.facebook_login_btn);
            facebook_login_btn.setVisibility(View.INVISIBLE);
            mOAuthLoginInstance.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);
        } else {
            return ;
        }
    }

    // 네이버 로그인 핸들러
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                // 회원프로필 정보 JSON 형태로 받아오기
                String naverResult = mOAuthLoginInstance.requestApi(mContext, accessToken, "https://openapi.naver.com/v1/nid/me");

                try {
                    // JSON 파싱
                    JSONObject reader = new JSONObject(naverResult);
                    JSONObject response = reader.getJSONObject("response");
                    // 고유 ID
                    userID = response.getString("id");
                    // 이메일
                    userEmail = response.getString("email");
                    // 닉네임
                    userName = response.getString("nickname");
                    // 성별
                    userGender = response.getString("gender");
                    //생일
                    userBirth = response.getString("birthday");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    isLogin = loginDataInput();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if(isLogin)
                {

                    Log.d("USER_ID", userID);
                    goMainActivity();
                }
                else
                {
                    Toast.makeText(mContext, "다시 로그인 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     *  네이버와 페이스북으로 로그인요청을 하고 돌려받은 정보를 PHP로 넘겨주기
     * @return
     * @throws MalformedURLException
     */
    private boolean loginDataInput() throws MalformedURLException {
        loginPHPRequest = new LoginPHPRequest("your server");
        AsyncTask<String, Void, String> result = loginPHPRequest.execute(userID, userEmail, userName, userGender, userBirth);
        return true;
    }


    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}