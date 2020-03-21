/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.zms.fansfixels;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import androidx.multidex.MultiDexApplication;


public class ApplicationClass extends MultiDexApplication {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }




    public static Context getAppContext() {
        return ApplicationClass.context;
    }


}
