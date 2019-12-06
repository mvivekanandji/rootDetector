package com.mvivekanandji.rootdetector.utils;

import java.io.File;


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
 *
 * Class having various file related utilities
 */
public class FileUtilities {

    public static boolean checkFileExistence(String path){
        return new File(path).exists();
    }

    public static boolean checkFileExistence(String[] paths){
        return checkFileExistence(paths, "");
    }

    public static boolean checkFileExistence(String[] paths, String fileName) {
        for (String path : paths)
            if (new File(path,fileName).exists()) return true;
        return false;
    }
}
