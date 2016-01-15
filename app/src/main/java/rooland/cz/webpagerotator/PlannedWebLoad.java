package rooland.cz.webpagerotator;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.TimerTask;

public class  PlannedWebLoad extends TimerTask {
    public AppCompatActivity localActivity;

    public Integer pages_position;
    public ProgressBar timer_progress_bar;
    public Integer fraction;
    public String[] pages;


    public PlannedWebLoad(AppCompatActivity activity, ProgressBar inputProgressBar, Integer inputFraction) {
        pages = new String[] {
                "http://www.rooland.cz/tag/dev.html",
                "http://www.rooland.cz/tag/komunikace.html",
                "http://www.rooland.cz/tag/zacatecnik.html"
        };

        pages_position = 0;
        localActivity = activity;
        timer_progress_bar = inputProgressBar;
        fraction = inputFraction;
    }


    public void run() {
        //This method will be called from another thread,and Contacts.Intents.UI work must
        // happen in the main thread,so we dispatch it via a Handler object.

        timer_progress_bar.incrementProgressBy(fraction);

        if (timer_progress_bar.getProgress() == timer_progress_bar.getMax()) {
            timer_progress_bar.setProgress(0);
            localActivity.webview.loadUrl(next_page());
        }
    }

    public String next_page() {

        next_page_address = pages[pages_position];

        if(pages_position > pages.size()) {
            pages_position += 1;
        } else {
            pages_position = 0;
        }

        return next_page_address;
    }
}
