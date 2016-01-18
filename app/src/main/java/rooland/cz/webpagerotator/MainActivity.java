package rooland.cz.webpagerotator;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public Handler handler;
    public Timer timer;
    public WebView webview;



    public WebView getWebview() {
        return webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        webview_setup();


        Integer web_load_interval = 1_000 * 5;

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
                time_one_percent,
                handler
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


    public class PlannedWebLoad extends TimerTask {
        public AppCompatActivity localActivity;

        public Integer pages_position;
        public ProgressBar timer_progress_bar;
        public Integer fraction;
        public String[] pages;
        public Handler handler;



        public PlannedWebLoad(AppCompatActivity activity, ProgressBar inputProgressBar, Integer inputFraction, Handler inputHandler) {
            pages = new String[] {
                    "http://www.rooland.cz/tag/dev.html",
                    "http://www.rooland.cz/tag/komunikace.html",
                    "http://www.rooland.cz/tag/zacatecnik.html"
            };

            pages_position = 0;
            localActivity = activity;
            timer_progress_bar = inputProgressBar;
            fraction = inputFraction;
            handler = inputHandler;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    // This method will be called from another thread,and Contacts.Intents.UI work must
                    // happen in the main thread,so we dispatch it via a Handler object.

                    timer_progress_bar.incrementProgressBy(fraction);

                    if (timer_progress_bar.getProgress() == timer_progress_bar.getMax()) {
                        timer_progress_bar.setProgress(0);
                        webview.loadUrl(next_page());
                    }
                }
            });
        }

        public String next_page() {

            String next_page_address = pages[pages_position];

            if(pages_position > pages.length) {
                pages_position += 1;
            } else {
                pages_position = 0;
            }

            return next_page_address;
        }
    }

}

