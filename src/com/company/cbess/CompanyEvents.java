package com.company.cbess;

import com.box.sdk.*;
import com.company.cbess.util.CompanyConfig;
import com.company.cbess.util.CompanyHttpClient;
import com.eclipsesource.json.JsonObject;
import com.oracle.tools.packager.Log;

import java.sql.Timestamp;

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
                String log = String.format("EventID: %s - Type: %s", event.getID(), event.getType());
                Log.debug(log);

                sendLogAsync(log, event);
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

    /**
     * Sends the log to the ELK server.
     * @param log The log message to send.
     */
    private void sendLogAsync(final String log, final BoxEvent event) {
        new Thread() {
            @Override
            public void run() {

                // get current time
                java.util.Date date = new java.util.Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                BoxItem.Info info = null;
                if (event.getSourceInfo() instanceof BoxItem.Info) {
                    info = (BoxItem.Info) event.getSourceInfo();
                }

                // build json object to send
                JsonObject jsonObject = new JsonObject()
                        // assumes CST time
                        .add("@timestamp", String.format("%s+01:00", timestamp.toString().replace(' ', 'T')))
                        .add("@message", log)
                        .add("@id", event.getID())
                        .add("@type", event.getType().toString())
                        ;
                if (info != null) {
                    jsonObject = jsonObject.add("name", info.getName());
                }

                String params = jsonObject.toString();

                try {
                    CompanyHttpClient.sendPostRequest(CompanyConfig.getDefault().getLoggerUrl(), params, new CompanyHttpClient.ResponseHandler() {
                        @Override
                        public void onResponseString(String url, int responseCode, String responseContent) {
                            Log.debug(responseContent);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }
}
