package com.cnjy99.cyq.easemob.helper;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.cnjy99.cyq.easemob.manager.PreferenceManager;
import com.cnjy99.cyq.easemob.manager.UserProfileManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class AppHelper {

    /**
     * data sync listener
     */
    public interface DataSyncListener {
        /**
         * sync complete
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }

    protected static final String TAG = "DemoHelper";

    private EaseUI easeUI;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;
    private Map<String, EaseUser> contactList;
    private static AppHelper instance = null;
    private UserProfileManager userProManager;

    /**
     * sync groups status listener
     */
    private List<DataSyncListener> syncGroupsListeners;
    /**
     * sync contacts status listener
     */
    private List<DataSyncListener> syncContactsListeners;
    /**
     * sync blacklist status listener
     */
    private List<DataSyncListener> syncBlackListListeners;

    private boolean isSyncingGroupsWithServer = false;
    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingBlackListWithServer = false;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    private String username;
    private Context appContext;
    private LocalBroadcastManager broadcastManager;
    private boolean isGroupAndContactListenerRegisted;

    private ModelHelper modelHelper;

    private AppHelper() {
    }

    public synchronized static AppHelper getInstance() {
        if (instance == null) {
            instance = new AppHelper();
        }
        return instance;
    }

    public void init(Context context){

        modelHelper = new ModelHelper(context);
        EMOptions options = initChatOptions();

        if (EaseUI.getInstance().init(context, options)) {

            appContext = context;

            EMClient.getInstance().init(context, options);
            EMClient.getInstance().setDebugMode(true);

            easeUI = EaseUI.getInstance();

            //to set user's profile and avatar
            //setEaseUIProviders();
            PreferenceManager.init(context);
            //initialize profile manager
            //getUserProfileManager().init(context);
            // EMClient.getInstance().callManager().getVideoCallHelper().setAdaptiveVideoFlag(getModel().isAdaptiveVideoEncode());
            //setGlobalListeners();
            broadcastManager = LocalBroadcastManager.getInstance(appContext);
            //initDbDao();
        }
    }

    private EMOptions initChatOptions(){

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        //you need apply & set your own id if you want to use google cloud messaging.
        options.setGCMNumber("324169311137");
        //you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        //you need apply & set your own id if you want to use Huawei push notification
        options.setHuaweiPushAppId("10492024");

        //set custom servers, commonly used in private deployment
        if(modelHelper.isCustomServerEnable() && modelHelper.getRestServer() != null && modelHelper.getIMServer() != null) {
            options.setRestServer(modelHelper.getRestServer());
            options.setIMServer(modelHelper.getIMServer());
            if(modelHelper.getIMServer().contains(":")) {
                options.setIMServer(modelHelper.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(modelHelper.getIMServer().split(":")[1]));
            }
        }

        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());

        return options;
    }

    public ModelHelper getModel(){
        return modelHelper;
    }

    /**
     * 判断是否登录
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }


    /**
     * set current username
     * @param username
     */
    public void setCurrentUserName(String username){
        this.username = username;
        modelHelper.setCurrentUserName(username);
    }
    /**
     * get current user's id
     */
    public String getCurrentUsernName(){
        if(username == null){
            username = modelHelper.getCurrentUsernName();
        }
        return username;
    }

    /**
     * 获取联系人
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        if (isLoggedIn() && contactList == null) {
            contactList = modelHelper.getContactList();
        }

        // return a empty non-null object to avoid app crash
        if(contactList == null){
            return new Hashtable<String, EaseUser>();
        }

        return contactList;
    }

    public UserProfileManager getUserProfileManager() {
        if (userProManager == null) {
            userProManager = new UserProfileManager();
        }
        return userProManager;
    }
}
