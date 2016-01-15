package rooland.cz.webpagerotator;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    private Timer timer;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        webview_setup();


        Integer web_load_interval = 60_000 * 5;

        ProgressBar timer_progress_bar = timer_progress_setup(web_load_interval);
        timer_setup(web_load_interval, timer_progress_bar);
    }

    protected ProgressBar timer_progress_setup(Integer interval) {
        ProgressBar timer_progress_bar = (ProgressBar) findViewById(R.id.timerProgress);

        timer_progress_bar.setMax(interval);
        return timer_progress_bar;
    }


    protected void timer_setup(Integer interval, ProgressBar timer_progress_bar) {
        timer = new Timer();
        Integer time_one_percent = interval/1000;
        long zero = 0;

        PlannedWebLoad task = new PlannedWebLoad(
                this,
                timer_progress_bar,
                time_one_percent
        );
        task.timer_progress_bar = timer_progress_bar;
        task.fraction = time_one_percent;
        timer.schedule(task, zero, time_one_percent);
    }

    protected void webview_setup() {
        webview = (WebView) findViewById(R.id.mainWebView);

        WebSettings settings = webview.getSettings();
        settings.setSavePassword(true);
        settings.setSaveFormData(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);

        webview.setWebChromeClient(new WebChromeClient());

        webview.loadUrl("http://www.rooland.cz");
    }

}

