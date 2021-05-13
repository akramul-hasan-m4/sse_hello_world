package com.social.sseandroid;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.here.oksse.OkSse;
import com.here.oksse.ServerSentEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView sseMsg = findViewById(R.id.tv_sse_msg);

        String path = "http://192.168.1.14:8080/notification";
        Request request = new Request.Builder().url(path).build();
        OkSse okSse = new OkSse();
        ServerSentEvent sse = okSse.newServerSentEvent(request, new ServerSentEvent.Listener() {
            @Override
            public void onOpen(ServerSentEvent sse, Response response) {
                Log.d(TAG, "onOpen: When the channel is opened");
                // When the channel is opened
            }

            @Override
            public void onMessage(ServerSentEvent sse, String id, String event, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sseMsg.setText(message);
                    }
                });

                Log.d(TAG, "onMessage: When a message is received");
                // When a message is received
            }

            @Override
            public void onComment(ServerSentEvent sse, String comment) {
                // When a comment is received
                Log.d(TAG, "onComment: When a comment is received");
            }

            @Override
            public boolean onRetryTime(ServerSentEvent sse, long milliseconds) {
                // True to use the new retry time received by SSE
                Log.d(TAG, "onRetryTime: True to use the new retry time received by SSE");
                return true;
            }

            @Override
            public boolean onRetryError(ServerSentEvent sse, Throwable throwable, Response response) {
                // True to retry, false otherwise
                Log.d(TAG, "onRetryError: True to retry, false otherwise"+throwable.getMessage());
                return true;
            }

            @Override
            public void onClosed(ServerSentEvent sse) {
                Log.d(TAG, "onClosed: Channel closed");
                // Channel closed
            }

            @Override
            public Request onPreRetry(ServerSentEvent sse, Request originalRequest) {
                Log.d(TAG, "onPreRetry: ");
                return originalRequest;
            }
        });

      //  sse.close();
    }
}