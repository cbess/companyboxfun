package com.company.cbess;

import com.box.sdk.*;
import com.company.cbess.util.CompanyConfig;

public class Main {

    public static void main(String[] args) throws Exception {
        String devToken = CompanyConfig.getDefault().getDeveloperToken();
        if (devToken == null) {
            throw new Exception("No developer token. Please check local.config.json.");
        }

        // connect with Box
        BoxAPIConnection api = new BoxAPIConnection(devToken);
        BoxFolder rootFolder = BoxFolder.getRootFolder(api);

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

        // upload
        CompanyBoxFile file = new CompanyBoxFile(rootFolder, "/Users/caseybrumbaugh/Desktop/Brackets.1.1.Extract.dmg");
        file.upload("");

        System.out.println("Upload complete.");
    }
}
