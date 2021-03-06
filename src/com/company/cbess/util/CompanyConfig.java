package com.company.cbess.util;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Represents the company box config.
 */
public class CompanyConfig {
    private static final String CONFIG_PATH = "local.config.json";
    static CompanyConfig mCompanyConfig;
    static {
        String string = Util.getResourceContents(CONFIG_PATH);
        mCompanyConfig = new CompanyConfig(string);
    }
    private String mDeveloperToken;
    private String mBoxUploadDirectoryPath;
    private String mUploadDirectoryPath;
    private boolean mCaptureEventsEnabled;
    private String mLoggerUrl;

    /**
     * The default config instance.
     * @return CompanyConfig object.
     */
    public static CompanyConfig getDefault() {
        return mCompanyConfig;
    }

    public CompanyConfig(String jsonString) {
        loadJson(jsonString);
    }

    private void loadJson(String jsonString) {
        try {
            JsonObject jsonObject = JsonObject.readFrom(jsonString);

            // setup config
            mDeveloperToken = jsonString(jsonObject, "dev_token");
            mBoxUploadDirectoryPath = jsonString(jsonObject, "box_upload_dir");
            mUploadDirectoryPath = jsonString(jsonObject, "upload_dir");
            mCaptureEventsEnabled = jsonObject.get("capture_events").asBoolean();
            mLoggerUrl = jsonString(jsonObject, "logger_url");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The developer token used to connect to the Box API.
     * @return The token string.
     */
    public String getDeveloperToken() {
        return mDeveloperToken;
    }

    /**
     * The upload directory.
     * @return Path string.
     */
    public String getUploadDirectoryPath() {
        return mUploadDirectoryPath;
    }

    /**
     * The directory to upload at Box.
     * @return Directory name.
     */
    public String getBoxUploadDirectoryPath() {
        return mBoxUploadDirectoryPath;
    }

    /**
     * Returns true if the capture events operations should be enabled.
     */
    public boolean isCaptureEventsEnabled() {
        return mCaptureEventsEnabled;
    }

    /**
     * The logger URL to send log messages to.
     * @return The url string.
     */
    public String getLoggerUrl() {
        return mLoggerUrl;
    }

    private String jsonString(JsonObject jsonObject, String name) {
        JsonValue value =  jsonObject.get(name);
        if (value == null) {
            return null;
        }

        return value.asString();
    }
}
