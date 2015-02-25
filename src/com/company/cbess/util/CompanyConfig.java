package com.company.cbess.util;

import com.eclipsesource.json.JsonObject;

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
        JsonObject jsonObject = JsonObject.readFrom(jsonString);

        // setup config
        mDeveloperToken = jsonObject.get("dev_token").asString();
    }

    /**
     * The developer token used to connect to the Box API.
     * @return The token string.
     */
    public String getDeveloperToken() {
        return mDeveloperToken;
    }
}
