package com.cnjy99.cyq.easemob.helper;


import android.content.Context;

import com.cnjy99.cyq.easemob.easedao.UserDao;
import com.cnjy99.cyq.easemob.manager.PreferenceManager;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class ModelHelper {

    protected Context context = null;

    public ModelHelper(Context ctx){
        context = ctx;
        PreferenceManager.init(context);
    }

    public boolean saveContactList(List<EaseUser> contactList) {
        UserDao dao = new UserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    public Map<String, EaseUser> getContactList() {
        UserDao dao = new UserDao(context);
        return dao.getContactList();
    }

    public void saveContact(EaseUser user){
        UserDao dao = new UserDao(context);
        dao.saveContact(user);
    }



    /**
     * save current username
     * @param username
     */
    public void setCurrentUserName(String username){
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    public String getCurrentUsernName(){
        return PreferenceManager.getInstance().getCurrentUsername();
    }


    public void setGroupsSynced(boolean synced){
        PreferenceManager.getInstance().setGroupsSynced(synced);
    }

    public boolean isGroupsSynced(){
        return PreferenceManager.getInstance().isGroupsSynced();
    }

    public void setContactSynced(boolean synced){
        PreferenceManager.getInstance().setContactSynced(synced);
    }

    public boolean isContactSynced(){
        return PreferenceManager.getInstance().isContactSynced();
    }

    public void setBlacklistSynced(boolean synced){
        PreferenceManager.getInstance().setBlacklistSynced(synced);
    }

    public boolean isBacklistSynced(){
        return PreferenceManager.getInstance().isBacklistSynced();
    }

    public void allowChatroomOwnerLeave(boolean value){
        PreferenceManager.getInstance().setSettingAllowChatroomOwnerLeave(value);
    }

    public boolean isChatroomOwnerLeaveAllowed(){
        return PreferenceManager.getInstance().getSettingAllowChatroomOwnerLeave();
    }

    public void setDeleteMessagesAsExitGroup(boolean value) {
        PreferenceManager.getInstance().setDeleteMessagesAsExitGroup(value);
    }

    public boolean isDeleteMessagesAsExitGroup() {
        return PreferenceManager.getInstance().isDeleteMessagesAsExitGroup();
    }

    public void setAutoAcceptGroupInvitation(boolean value) {
        PreferenceManager.getInstance().setAutoAcceptGroupInvitation(value);
    }

    public boolean isAutoAcceptGroupInvitation() {
        return PreferenceManager.getInstance().isAutoAcceptGroupInvitation();
    }


    public void setAdaptiveVideoEncode(boolean value) {
        PreferenceManager.getInstance().setAdaptiveVideoEncode(value);
    }

    public boolean isAdaptiveVideoEncode() {
        return PreferenceManager.getInstance().isAdaptiveVideoEncode();
    }

    public void setRestServer(String restServer){
        PreferenceManager.getInstance().setRestServer(restServer);
    }

    public String getRestServer(){
        return  PreferenceManager.getInstance().getRestServer();
    }

    public void setIMServer(String imServer){
        PreferenceManager.getInstance().setIMServer(imServer);
    }

    public String getIMServer(){
        return PreferenceManager.getInstance().getIMServer();
    }

    public void enableCustomServer(boolean enable){
        PreferenceManager.getInstance().enableCustomServer(enable);
    }

    public boolean isCustomServerEnable(){
        return PreferenceManager.getInstance().isCustomServerEnable();
    }

    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
