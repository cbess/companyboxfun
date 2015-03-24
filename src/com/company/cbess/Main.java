package com.company.cbess;

import com.box.sdk.*;
import com.company.cbess.util.CompanyConfig;
import com.company.cbess.util.Log;
import com.company.cbess.util.Util;

public class Main {
    static Main mMain = new Main();
    static CompanyConfig mConfig;

    public static void main(String[] args) throws Exception {

        if (args.length > 0) {
            mConfig = new CompanyConfig(Util.getContents(args[0]));
        } else {
            mConfig = CompanyConfig.getDefault();
        }

        try {
            mMain.runSamples();
        } catch (BoxAPIException ex) {
            // 401 error = bad dev token
            if (ex.getResponseCode() == 401) {
                System.out.println("Check the Developer Token. It may need to be renewed.");
            }
        }
    }

    private void runSamples() throws Exception {
        Log.debug("Running samples.");

        mMain.runRootFolderDump();

        if (mConfig.getUploadDirectoryPath() != null) {
            mMain.runUpload();
        }

        if (mConfig.isCaptureEventsEnabled()) {
            new Thread() {
                @Override
                public void run() {
                    mMain.runCaptureEvents();
                }
            }.start();
        }
    }

    private BoxAPIConnection getApiConnection() throws Exception {
        String devToken = mConfig.getDeveloperToken();
        if (devToken == null) {
            throw new Exception("No developer token. Please check local.config.json.");
        }

        return new BoxAPIConnection(devToken);
    }

    private BoxFolder getRootFolder() {
        try {
            return BoxFolder.getRootFolder(getApiConnection());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void runRootFolderDump() {
        BoxFolder rootFolder = getRootFolder();

        for (BoxItem.Info itemInfo : rootFolder) {
            Log.debug(String.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName()));
        }

        // build folder tree from the root of the Box
        CompanyBoxFolder companyBoxFolder = new CompanyBoxFolder(rootFolder.getInfo());
        companyBoxFolder.buildFolderTree(true);

        Log.debug(String.format("Root folder: %s", companyBoxFolder));
    }

    private void runUpload() throws Exception {
        // upload
        CompanyBoxFolder companyBoxFolder = new CompanyBoxFolder(getRootFolder().getInfo());
        companyBoxFolder.buildFolderTree(true);

        // use subdirectory if defined
        if (mConfig.getBoxUploadDirectoryPath() != null) {
            CompanyBoxFolder uploadBoxFolder = companyBoxFolder.findFolderByName(mConfig.getBoxUploadDirectoryPath());

            // If the upload directory exists use it
            if (uploadBoxFolder != null) {
                companyBoxFolder = uploadBoxFolder;
            }
        }

        CompanyBoxFile file = new CompanyBoxFile(companyBoxFolder, mConfig.getUploadDirectoryPath());
        file.upload(null, null);

        Log.debug("Upload complete.");
    }

    // keep thread-safe, async operation
    private void runCaptureEvents() {
        Log.debug("Capturing Box events...");

        CompanyEvents events = new CompanyEvents();
        events.startCaptureEvents();
    }
}
