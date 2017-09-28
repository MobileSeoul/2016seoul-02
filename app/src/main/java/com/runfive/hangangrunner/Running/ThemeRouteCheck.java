package com.runfive.hangangrunner.Running;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.runfive.hangangrunner.R;
import com.runfive.hangangrunner.Service.MyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by JunHo on 2016-09-21.
 */
public class ThemeRouteCheck {
    private boolean istestRouteExecuted = false;
    private boolean testRouteLeftStarted = false;
    private boolean testRouteRightStarted = false;
    private boolean testRouteExecuted = false;
    private Context context;
    private Vibrator vibrator;
    private int id;
    private MediaPlayer mediaPlayer;
    private String userID;
    private static final String GOLD_MEDAL = "G";
    private boolean alarm;
    private boolean voice;

    /**
     *  공원별 변수
     */
    private static final int YEOUIDO_START = 1005;
    private static final int YEOUIDO_END = 1006;
    private static final int NANGI_START = 1007;
    private static final int NANGI_END = 1008;
    private static final int YANGHWA_START = 1009;
    private static final int YANGHWA_END = 1010;
    private static final int GWANGNARU_START = 1011;
    private static final int GWANGNARU_END = 1012;
    private static final int LEECHON_START = 1013;
    private static final int LEECHON_END = 1014;
    private static final int TTUKSUM_START = 1015;
    private static final int TTUKSUM_END = 1016;
    private static final int JAMSIL_START = 1017;
    private static final int JAMSIL_END = 1018;
    private static final int BANPO_START = 1019;
    private static final int BANPO_END = 1020;

    /**
     * 여의도 공원
     */

    private boolean yeouiLeftStarted = false;
    private boolean yeouiRightStarted = false;
    private boolean yeouiExecuted = false;

    /**
     *  난지공원
     */

    private boolean nanjiLeftStarted = false;
    private boolean nanjiRightStarted = false;
    private boolean nanjiExecuted = false;

    /**
     *  양화공원
     */

    private boolean yanghwaLeftStarted = false;
    private boolean yanghwaRightStarted = false;
    private boolean yanghwaExecuted = false;

    /**
     *  광나루공원
     */

    private boolean gwangnaruLeftStarted = false;
    private boolean gwangnaruRightStarted = false;
    private boolean gwangnaruExecuted = false;

    /**
     *  이촌공원
     */

    private boolean leechonLeftStarted = false;
    private boolean leechonRightStarted = false;
    private boolean leechonExecuted = false;

    /**
     *  뚝섬공원
     */

    private boolean ttuksumLeftStarted = false;
    private boolean ttuksumRightStarted = false;
    private boolean ttuksumExecuted = false;

    /**
     *  잠실공원
     */

    private boolean jamsilLeftStarted = false;
    private boolean jamsilRightStarted = false;
    private boolean jamsilExecuted = false;

    /**
     *  반포공원
     */

    private boolean banpoLeftStarted = false;
    private boolean banpoRightStarted = false;
    private boolean banpoExecuted = false;

    public ThemeRouteCheck() {}

    public ThemeRouteCheck(Context context, String userID) {

        this.context = context;
        this.userID = userID;

        vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = new MediaPlayer();
    }

    public void RouteCheck(int id) {
        /**
         * 나중에 id다 string final로 다바꿔
         */


        /**
         *  테스트루트 등록
         */

        if(id == 1001 && testRouteLeftStarted == false && testRouteRightStarted == false && testRouteExecuted == false) {

            vibrator.vibrate(1000);

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "진입");
                context.startService(intent2);
            }

            Toast.makeText(context.getApplicationContext(), "테스트루트 진입! 체크:"+testRouteExecuted, Toast.LENGTH_SHORT).show();
                /**
                 * 오디오 재생
                 */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.hakgwan);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            testRouteLeftStarted = true; // 시작등록
        }

        if(id == 1002 && testRouteLeftStarted==true && testRouteRightStarted == false && testRouteExecuted == false) {
            /**
             * db 쏘기
             */
            sendGoldMedal();

            vibrator.vibrate(1000);

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "진입");
                context.startService(intent2);
            }

            Toast.makeText(context.getApplicationContext(), "완료 금메달 획득! "+testRouteExecuted, Toast.LENGTH_SHORT).show();
                /**
                 * 오디오 재생
                 */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.hakgwan);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            //istestRouteExecuted = false; //진입여부 끝내기
            testRouteLeftStarted = false; // 시작점 없애기
            testRouteExecuted = true;
        }

        if(id == 1002 && testRouteLeftStarted == false && testRouteRightStarted == false && testRouteExecuted == false) {
            vibrator.vibrate(1000);

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "진입");
                context.startService(intent2);
            }

            Toast.makeText(context.getApplicationContext(), "테스트루트 진입!"+testRouteExecuted, Toast.LENGTH_SHORT).show();
            /**
             * 오디오 재생
             */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.hakgwan);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            testRouteRightStarted = true; // 시작등록
        }

        if(id == 1001 && testRouteLeftStarted == false && testRouteRightStarted == true && testRouteExecuted == false) {
            sendGoldMedal();

            vibrator.vibrate(1000);

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "진입");
                context.startService(intent2);
            }

            Toast.makeText(context.getApplicationContext(), "완료 금메달 획득!"+testRouteExecuted, Toast.LENGTH_SHORT).show();
            /**
             * 오디오 재생
             */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.hakgwan);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            testRouteLeftStarted = false; // 시작점 없애기
            testRouteExecuted = true;

        }

        /**
         *  뚝섬 ##############################################
         */

        if(id == TTUKSUM_START && !ttuksumLeftStarted && !ttuksumRightStarted && !ttuksumExecuted) {

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "뚝섬 공원로에 진입하셨습니다");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);
            /**
             * 오디오 재생
             */
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.ttuksum_enter);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });

            ttuksumLeftStarted = true; // 시작등록
        }

        if(id == TTUKSUM_END && ttuksumLeftStarted && !ttuksumRightStarted && !ttuksumExecuted) {
            /**
             * db 쏘기
             */
            sendGoldMedal();

            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "뚝섬 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);
            /**
             * 오디오 재생
             */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.ttuksum_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            ttuksumLeftStarted = false; // 시작점 없애기
            ttuksumExecuted = true;
        }

        if(id == TTUKSUM_END && !ttuksumLeftStarted && !ttuksumRightStarted && !ttuksumExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "뚝섬 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            /**
             * 오디오 재생
             */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.ttuksum_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            ttuksumRightStarted = true; // 시작등록
        }

        if(id == TTUKSUM_START && !ttuksumLeftStarted && ttuksumRightStarted && !ttuksumExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "뚝섬 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);
            /**
             * 오디오 재생
             */
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.ttuksum_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            ttuksumRightStarted = false; // 시작점 없애기
            ttuksumExecuted = true;
        }

        /**
         *  여의 ##############################################
         */

        if(id == YEOUIDO_START && !yeouiLeftStarted && !yeouiRightStarted && !yeouiExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "여의도 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice== true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yeouido_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            yeouiLeftStarted = true; // 시작등록
        }

        if(id == YEOUIDO_END && yeouiLeftStarted && !yeouiRightStarted && !yeouiExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "여의도 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yeouido_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            yeouiLeftStarted = false; // 시작점 없애기
            yeouiExecuted = true;
        }

        if(id == YEOUIDO_END && !yeouiLeftStarted && !yeouiRightStarted && !yeouiExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "여의도 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yanghwa_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            yeouiRightStarted = true; // 시작등록
        }

        if(id == YEOUIDO_START && !yeouiLeftStarted && yeouiRightStarted && !yeouiExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "여의도 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yeouido_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            yeouiRightStarted = false; // 시작점 없애기
            yeouiExecuted = true;
        }

        /**
         *  난지 ##############################################
         */
        if(id == NANGI_START && !nanjiLeftStarted && !nanjiRightStarted && !nanjiExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "난지 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.nanji_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            nanjiLeftStarted = true; // 시작등록
        }

        if(id == NANGI_END && nanjiLeftStarted && !nanjiRightStarted && !nanjiExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "난지 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.nanji_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            nanjiLeftStarted = false; // 시작점 없애기
            nanjiExecuted = true;
        }

        if(id == NANGI_END && !nanjiLeftStarted && !nanjiRightStarted && !nanjiExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "난지 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.nanji_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            nanjiRightStarted = true; // 시작등록
        }

        if(id == NANGI_START && !nanjiLeftStarted && nanjiRightStarted && !nanjiExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "난지 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.nanji_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            nanjiRightStarted = false; // 시작점 없애기
            nanjiExecuted = true;
        }

        /**
         *  양화 ##############################################
         */
        if(id == YANGHWA_START && !yanghwaLeftStarted && !yanghwaRightStarted && !yanghwaExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "양화 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {

                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yanghwa_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }

            yanghwaLeftStarted = true; // 시작등록
        }

        if(id == YANGHWA_END && yanghwaLeftStarted && !yanghwaRightStarted && !yanghwaExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "양화 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yanghwa_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            yanghwaLeftStarted = false; // 시작점 없애기
            yanghwaExecuted = true;
        }

        if(id == YANGHWA_END && !yanghwaLeftStarted && !yanghwaRightStarted && !yanghwaExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "양화 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yanghwa_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            yanghwaRightStarted = true; // 시작등록
        }

        if(id == YANGHWA_START && !yanghwaLeftStarted && yanghwaRightStarted && !yanghwaExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "양화 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.yanghwa_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            yanghwaRightStarted = false; // 시작점 없애기
            yanghwaExecuted = true;
        }

        /**
         *  광나루 ##############################################
         */
        if(id == GWANGNARU_START && !gwangnaruLeftStarted && !gwangnaruRightStarted && !gwangnaruExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "광나루 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.gwangnaru_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            gwangnaruLeftStarted = true; // 시작등록
        }

        if(id == GWANGNARU_END && gwangnaruLeftStarted && !gwangnaruRightStarted && !gwangnaruExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "광나루 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.gwangnaru_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            gwangnaruLeftStarted = false; // 시작점 없애기
            gwangnaruExecuted = true;
        }

        if(id == GWANGNARU_END && !gwangnaruLeftStarted && !gwangnaruRightStarted && !gwangnaruExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "광나루 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.gwangnaru_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            gwangnaruRightStarted = true; // 시작등록
        }

        if(id == GWANGNARU_START && !gwangnaruLeftStarted && gwangnaruRightStarted && !gwangnaruExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "광나루 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);
            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.gwangnaru_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            gwangnaruRightStarted = false; // 시작점 없애기
            gwangnaruExecuted = true;
        }

        /**
         *  이촌 ##############################################
         */
        if(id == LEECHON_START && !leechonLeftStarted && !leechonRightStarted && !leechonExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "이촌 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.leechon_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            leechonLeftStarted = true; // 시작등록
        }

        if(id == LEECHON_END && leechonLeftStarted && !leechonRightStarted && !leechonExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "이촌 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.leechon_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            leechonLeftStarted = false; // 시작점 없애기
            leechonExecuted = true;
        }

        if(id == LEECHON_END && !leechonLeftStarted && !leechonRightStarted && !leechonExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "이촌 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.leechon_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            leechonRightStarted = true; // 시작등록
        }

        if(id == LEECHON_START && !leechonLeftStarted && leechonRightStarted && !leechonExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "이촌 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.leechon_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            leechonRightStarted = false; // 시작점 없애기
            leechonExecuted = true;
        }

        /**
         *  잠실 ##############################################
         */
        if(id == JAMSIL_START && !jamsilLeftStarted && !jamsilRightStarted && !jamsilExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "잠실 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.jamsil_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            jamsilLeftStarted = true; // 시작등록
        }

        if(id == JAMSIL_END && jamsilLeftStarted && !jamsilRightStarted && !jamsilExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "잠실 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.jamsil_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            jamsilLeftStarted = false; // 시작점 없애기
            jamsilExecuted = true;
        }

        if(id == JAMSIL_END && !jamsilLeftStarted && !jamsilRightStarted && !jamsilExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "잠실 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.jamsil_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            jamsilRightStarted = true; // 시작등록
        }

        if(id == JAMSIL_START && !jamsilLeftStarted && jamsilRightStarted && !jamsilExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "잠실 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.jamsil_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            jamsilRightStarted = false; // 시작점 없애기
            jamsilExecuted = true;
        }

        /**
         *  반포 ##############################################
         */

        if(id == BANPO_START && !banpoLeftStarted && !banpoRightStarted && !banpoExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "반포 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.banpo_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            banpoLeftStarted = true; // 시작등록
        }

        if(id == BANPO_END && banpoLeftStarted && !banpoRightStarted && !banpoExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "반포 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.banpo_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            banpoLeftStarted = false; // 시작점 없애기
            banpoExecuted = true;
        }

        if(id == BANPO_END && !banpoLeftStarted && !banpoRightStarted && !banpoExecuted) {
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "반포 공원로에 진입하셨습니다");
                context.startService(intent2);
            }
            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.banpo_enter);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            banpoRightStarted = true; // 시작등록
        }

        if(id == BANPO_START && !banpoLeftStarted && banpoRightStarted && !banpoExecuted) {
            sendGoldMedal();
            SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
            alarm = pref.getBoolean("alarm", false);
            voice = pref.getBoolean("voice",false);
            if(alarm == true)
            {
                Intent intent2 = new Intent(context.getApplicationContext(),MyService.class);
                intent2.putExtra("parkstatus", "반포 공원로 완료 금메달 획득!");
                context.startService(intent2);
            }

            vibrator.vibrate(1000);

            if(voice == true) {
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.banpo_finished);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }
                });
            }
            banpoRightStarted = false; // 시작점 없애기
            banpoExecuted = true;
        }
    }

    private void sendGoldMedal() {

        class SendGoldMedal extends AsyncTask<String, Void, String> {

            private URL url;
            public SendGoldMedal(String url) throws MalformedURLException {
                this.url=new URL(url);
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
                    String postData = "user_id=" + userID;
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10000);
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
            }
        }

        SendGoldMedal sendGoldMedal = null;

        try {
            sendGoldMedal = new SendGoldMedal("Your Server ULR");
            sendGoldMedal.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
