package com.company.cbess;

import com.box.sdk.*;
import com.company.cbess.util.CompanyConfig;

public class Main {
    static Main mMain = new Main();
    static CompanyConfig mConfig = CompanyConfig.getDefault();

    public static void main(String[] args) throws Exception {
        mMain.runRootFolderDump();

        if (mConfig.getUploadDirectoryPath() != null) {
            mMain.runUpload();
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

        try {
            for (BoxItem.Info itemInfo : rootFolder) {
                System.out.println(String.format("[%s] %s\n", itemInfo.getID(), itemInfo.getName()));
            }
        } catch (BoxAPIException ex) {
            // 401 error = bad dev token
            if (ex.getResponseCode() == 401) {
                System.out.println("Check the Developer Token. It may need to be renewed.");
            }
        }

        // build folder tree from the root of the Box
        CompanyBoxFolder companyBoxFolder = new CompanyBoxFolder(rootFolder.getInfo());
        companyBoxFolder.buildFolderTree(true);

        System.out.println(String.format("Root folder: %s", companyBoxFolder));
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

        System.out.println("Upload complete.");
    }
}
