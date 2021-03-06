/** Copyright (c) 2019 Mesibo
 * https://mesibo.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the terms and condition mentioned on https://mesibo.com
 * as well as following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions, the following disclaimer and links to documentation and source code
 * repository.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 *
 * Neither the name of Mesibo nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written
 * permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Documentation
 * https://mesibo.com/documentation/
 *
 * Source Code Repository
 * https://github.com/mesibo/messenger-app-android
 *
 */
package org.mesibo.messenger

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.mesibo.mediapicker.AlbumListData
import com.mesibo.mediapicker.MediaPicker
import com.mesibo.mediapicker.MediaPicker.ImageEditorListener
import com.mesibo.messaging.MesiboUI
import com.mesibo.uihelper.*
import org.mesibo.messenger.AppSettings.SettingsActivity
import org.mesibo.messenger.MesiboListeners.Companion.instance
import org.mesibo.messenger.StartUpActivity
import java.util.*

object UIManager {
    fun launchStartupActivity(context: Context, skipTour: Boolean) {
        val intent = Intent(context, StartUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(StartUpActivity.SKIPTOUR, skipTour)
        context.startActivity(intent)
    }

    var mMesiboLaunched = false
    fun launchMesibo(context: Context?, flag: Int, startInBackground: Boolean, keepRunningOnBackPressed: Boolean) {
        mMesiboLaunched = true
        MesiboUI.launch(context, flag, startInBackground, keepRunningOnBackPressed)
    }

    fun launchMesiboContacts(context: Context?, forwardid: Long, selectionMode: Int, flag: Int, bundle: Bundle?) {
        MesiboUI.launchContacts(context, forwardid, selectionMode, flag, bundle)
    }

    fun launchUserProfile(context: Context, groupid: Long, peer: String?) {
        val subActivity = Intent(context, ShowProfileActivity::class.java)
        subActivity.putExtra("peer", peer).putExtra("groupid", groupid)
        context.startActivity(subActivity)
    }

    fun launchUserSettings(context: Context) {
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    fun launchUserRegistration(context: Context, flag: Int) {
        val subActivity = Intent(context, EditProfileActivity::class.java)
        if (flag > 0) subActivity.flags = flag
        context.startActivity(subActivity)
    }

    fun launchImageViewer(context: Activity?, filePath: String?) {
        MediaPicker.launchImageViewer(context, filePath)
    }

    fun launchImageViewer(context: Activity?, files: ArrayList<String?>?, firstIndex: Int) {
        MediaPicker.launchImageViewer(context, files, firstIndex)
    }

    fun launchImageEditor(context: Context?, type: Int, drawableid: Int, title: String?, filePath: String?, showEditControls: Boolean, showTitle: Boolean, showCropOverlay: Boolean, squareCrop: Boolean, maxDimension: Int, listener: ImageEditorListener?) {
        MediaPicker.launchEditor(context as AppCompatActivity?, type, drawableid, title, filePath, showEditControls, showTitle, showCropOverlay, squareCrop, maxDimension, listener)
    }

    fun launchAlbum(context: Activity?, albumList: List<AlbumListData?>?) {
        MediaPicker.launchAlbum(context, albumList)
    }

    var mProductTourShown = false
    fun launchWelcomeactivity(context: Activity?, newtask: Boolean, loginInterface: ILoginInterface?, tourListener: IProductTourListener?) {
        val config = MesiboUiHelperConfig()
        val res: MutableList<WelcomeScreen> = ArrayList()
        res.add(WelcomeScreen("Messaging in your apps", "Over 79% of all apps require some form of communications. Mesibo is built from ground-up to power this!", 0, R.drawable.welcome, -0xff7975))
        res.add(WelcomeScreen("Messaging, Voice, & Video", "Complete infrastructure with powerful APIs to get you started, rightaway!", 0, R.drawable.videocall, -0xf062a8))
        //res.add(new WelcomeScreen("Plug & Play", "Not just APIs, you can even use pluggable UI modules - Buid in just a few hours", 0, R.drawable.profile, 0xfff4b400));
        res.add(WelcomeScreen("Open Source", "Quickly integrate Mesibo in your own app using freely available source code", 0, R.drawable.opensource_ios, -0xfab59f))

        //res.add(new WelcomeScreen("No Sweat Pricing", "Start free & only pay as you grow!", 0, R.drawable.users, 0xff0f9d58));

        // dummy - requires
        res.add(WelcomeScreen("", ":", 0, R.drawable.welcome, -0xff7975))
        MesiboUiHelperConfig.mScreens = res
        MesiboUiHelperConfig.mWelcomeBottomText = "Mesibo will never share your information"
        MesiboUiHelperConfig.mWelcomeBackgroundColor = -0xff7975
        MesiboUiHelperConfig.mBackgroundColor = -0x1
        MesiboUiHelperConfig.mPrimaryTextColor = -0xe8d8d9
        MesiboUiHelperConfig.mButttonColor = -0xff7975
        MesiboUiHelperConfig.mButttonTextColor = -0x1
        MesiboUiHelperConfig.mSecondaryTextColor = -0x99999a
        MesiboUiHelperConfig.mScreenAnimation = true
        MesiboUiHelperConfig.mSmartLockUrl = "https://app.mesibo.com"
        val permissions: MutableList<String> = ArrayList()
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_CONTACTS)
        MesiboUiHelperConfig.mPermissions = permissions
        MesiboUiHelperConfig.mPermissionsRequestMessage = "Mesibo requires Storage and Contacts permissions so that you can send messages and make calls to your contacts. Please grant to continue!"
        MesiboUiHelperConfig.mPermissionsDeniedMessage = "Mesibo will close now since the required permissions were not granted"
        MesiboUiHelper.setConfig(config)
        if (mMesiboLaunched) {
            launchLogin(context, instance)
            return
        }
        mProductTourShown = true
        MesiboUiHelper.launchTour(context, newtask, tourListener)
    }

    fun launchLogin(context: Activity?, loginInterface: ILoginInterface?) {
        MesiboUiHelper.launchLogin(context, true, 2, loginInterface)
    }

    @JvmOverloads
    fun showAlert(context: Context?, title: String?, message: String?, pl: DialogInterface.OnClickListener? = null, nl: DialogInterface.OnClickListener? = null) {
        if (null == context) {
            return  //
        }
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        // dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(true)
        dialog.setPositiveButton(android.R.string.ok, pl)
        dialog.setNegativeButton(android.R.string.cancel, nl)
        try {
            dialog.show()
        } catch (e: Exception) {
        }
    }
}