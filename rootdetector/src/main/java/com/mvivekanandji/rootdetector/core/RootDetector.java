package com.mvivekanandji.rootdetector.core;

import com.mvivekanandji.rootdetector.utils.FileUtilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright 2019 Vivekanand Mishra.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by Vivekanand Mishra on 06/12/19.
 *
 * @author vivekanand
 * @version 1.0
 * <p>
 * Class to detect(probabilistically) the root on the device
 */
public class RootDetector {

    private Set<String> rootAppsPackages = new HashSet<>(Arrays.asList(
            "com.koushikdutta.superuser",
            "com.noshufou.android.su.elite",
            "com.noshufou.android.su",
            "com.thirdparty.superuser",
            "com.topjohnwu.magisk",
            "com.yellowes.su",
            "eu.chainfire.supersu"
            //any app starting with eu.chainfire
            //activities within com.android.settings- cyanogenmod.superuser activity
    ));

    private Set<String> dangerousAppsPackages = new HashSet<>(Arrays.asList(
            "com.android.vending.billing.InAppBillingService.COIN",
            "com.chelpus.lackypatch",
            "com.chelpus.luckypatcher",
            "com.dimonvideo.luckypatcher",
            "com.koushikdutta.rommanager.license",
            "com.koushikdutta.rommanager",
            "com.ramdroid.appquarantine",
            "com.ramdroid.appquarantinepro"
    ));

    private Set<String> rootCloakingPackages = new HashSet<>(Arrays.asList(
            "com.amphoras.hidemyroot",
            "com.amphoras.hidemyrootadfree",
            "com.devadvance.rootcloak",
            "com.devadvance.rootcloakplus",
            "com.formyhm.hideroot",
            "com.formyhm.hiderootPremium",
            "com.saurik.substrate",
            "com.zachspong.temprootremovejb",
            "de.robv.android.xposed.installer"
    ));

    private Set<String> suPaths = new HashSet<>(Arrays.asList(
            "/cache/",
            "/data/",
            "/data/local/",
            "/data/local/bin/",
            "/data/local/xbin/",
            "/dev/",
            "/magisk/.core/bin/",
            "/sbin/",
            "/su/bin/",
            "/system/", //added
            "/system/app/Superuser.apk",
            "/system/bin/.ext/",
            "/system/bin/",
            "/system/bin/failsafe/",
            "/system/sd/xbin/",
            "/system/usr/we-need-root/",
            "/system/xbin/"
    ));

    private Set<String> writeProtectedPaths = new HashSet<>(Arrays.asList(
            "/", //added
            "/etc",
            "/data", //added
            "/sbin",
            "/system",
            "/system/bin",
            "/system/sbin",
            "/system/xbin",
            "/vendor/bin"
            //"/dev"
            //"/proc",
            //"/sys",
    ));

    /**
     * Methods to check build keys.
     *
     * @return boolean - {@code true} if "text-keys" is found or {@code false} if not.
     */
    public boolean detectTestKeys() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    /**
     *
     * @return boolean - {@code true} if
     */
    public boolean checkForSuBinary() {
        return FileUtilities.checkFileExistence(suPaths.toArray(new String[0]), "su")
                || FileUtilities.checkFileExistence("/system/xbin/mu")
                || FileUtilities.checkFileExistence("/system/usr/we-need-root/su-backup");
    }

    /**
     *
     * @return
     */
    private boolean checkForBusyBoxBinary() {
        return FileUtilities.checkFileExistence(suPaths.toArray(new String[0]), "busybox");
    }

    /**
     * A variation on the checking for SU, this attempts a 'which su'
     * different file system check for the su binary
     *
     * @return true if su exists
     */
    private boolean checkSuExists() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system /xbin/which", "su"});

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = in.readLine();
            process.destroy();

            return line != null;

        } catch (Exception e) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }

}
