package com.company.cbess;

import com.box.sdk.*;
import com.company.cbess.util.CompanyConfig;

/**
 * Represents the company events.
 */
public class CompanyEvents {
    EventStream mEventStream;

    public void startCaptureEvents() {
        BoxAPIConnection api = new BoxAPIConnection(CompanyConfig.getDefault().getDeveloperToken());
        mEventStream = new EventStream(api);
        // handles deduplication
        mEventStream.addListener(new EventListener() {
            public void onEvent(BoxEvent event) {
                System.out.println(String.format("Event: %s - Type: %s", event.toString(), event.getType()));
            }

            @Override
            public void onNextPosition(long l) {

            }

            @Override
            public boolean onException(Throwable throwable) {
                return false;
            }
        });
        mEventStream.start();
    }

    public void stopCaptureEvents() {
        if (mEventStream != null) {
            mEventStream.stop();
        }
    }
}
