package com.raoqian.topactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by raoqian on 2019/5/21.
 */

public class IntentStartActivity extends Activity implements View.OnClickListener {

//    //    设置 界面
//    public static final String ACTION_SETTINGS = "android.settings.SETTINGS";
//    //    APN设置
//    public static final String ACTION_APN_SETTINGS = "android.settings.APN_SETTINGS";
//    //    定位设置
//    public static final String ACTION_LOCATION_SOURCE_SETTINGS = "android.settings.LOCATION_SOURCE_SETTINGS";
//    //    用户 73访客模式？
//    public static final String ACTION_USER_SETTINGS = "android.settings.USER_SETTINGS";
//    //    更多连接方式
//    public static final String ACTION_WIRELESS_SETTINGS = "android.settings.WIRELESS_SETTINGS";
//    //    更多连接方式
//    public static final String ACTION_NFC_SETTINGS = "android.settings.NFC_SETTINGS";
//    public static final String ACTION_AIRPLANE_MODE_SETTINGS = "android.settings.AIRPLANE_MODE_SETTINGS";
//
//    //    连接方式设置 飞行模式、USB网络共享、
////    NFC开关
//    public static final String ACTION_VOICE_CONTROL_AIRPLANE_MODE = "android.settings.VOICE_CONTROL_AIRPLANE_MODE";
//
//    public static final String ACTION_ACCESSIBILITY_SETTINGS = "android.settings.ACCESSIBILITY_SETTINGS";
//    //    无障碍 -检测操作、
////    检索窗口内容
//    public static final String ACTION_USAGE_ACCESS_SETTINGS = "android.settings.USAGE_ACCESS_SETTINGS";
////    有权查看使用情况的应用 列表
//
//    public static final String INTENT_CATEGORY_USAGE_ACCESS_CONFIG = "android.intent.category.USAGE_ACCESS_CONFIG";
//    public static final String METADATA_USAGE_ACCESS_REASON = "android.settings.metadata.USAGE_ACCESS_REASON";
//    public static final String ACTION_SECURITY_SETTINGS = "android.settings.SECURITY_SETTINGS";
////    安全性与位置信息 -
////    包括很多应用权限获取情况列表
//
//    public static final String ACTION_TRUSTED_CREDENTIALS_USER = "com.android.settings.TRUSTED_CREDENTIALS_USER";
////    信任的凭证
//
//    public static final String ACTION_MONITORING_CERT_INFO = "com.android.settings.MONITORING_CERT_INFO";
////    证书检查
//
//    public static final String ACTION_PRIVACY_SETTINGS = "android.settings.PRIVACY_SETTINGS";
////    备份
//
//    public static final String ACTION_VPN_SETTINGS = "android.settings.VPN_SETTINGS";
//    //    VPN 开关列表
//    public static final String ACTION_WIFI_SETTINGS = "android.settings.WIFI_SETTINGS";
////    WLAN WIFI开关
//
//    public static final String ACTION_WIFI_IP_SETTINGS = "android.settings.WIFI_IP_SETTINGS";
////    配置WLAN WIFI设置
//
//    public static final String ACTION_BLUETOOTH_SETTINGS = "android.settings.BLUETOOTH_SETTINGS";
////    蓝牙开关
//
//    public static final String ACTION_CAST_SETTINGS = "android.settings.CAST_SETTINGS";
////    无线投射设置
//
//    public static final String ACTION_DATE_SETTINGS = "android.settings.DATE_SETTINGS";
////    时间日期设置
//
//    public static final String ACTION_SOUND_SETTINGS = "android.settings.SOUND_SETTINGS";
//    //    声音和震动 设置
//    public static final String ACTION_DISPLAY_SETTINGS = "android.settings.DISPLAY_SETTINGS";
////    屏幕显示设置
//
//    public static final String ACTION_NIGHT_DISPLAY_SETTINGS = "android.settings.NIGHT_DISPLAY_SETTINGS";
////    护眼模式 设置
//
//    public static final String ACTION_LOCALE_SETTINGS = "android.settings.LOCALE_SETTINGS";
////    本机语言设置？
//
//    public static final String ACTION_VOICE_INPUT_SETTINGS = "android.settings.VOICE_INPUT_SETTINGS";
////    辅助应用和语音输入
//
//    public static final String ACTION_INPUT_METHOD_SETTINGS = "android.settings.INPUT_METHOD_SETTINGS";
////    虚拟键盘选择 -28
//
//    public static final String ACTION_INPUT_METHOD_SUBTYPE_SETTINGS = "android.settings.INPUT_METHOD_SUBTYPE_SETTINGS";
////    输入语言设置？ 29
//
//    public static final String ACTION_SHOW_INPUT_METHOD_PICKER = "android.settings.SHOW_INPUT_METHOD_PICKER";
//    public static final String ACTION_USER_DICTIONARY_SETTINGS = "android.settings.USER_DICTIONARY_SETTINGS";
////    个人字典
//
//    public static final String ACTION_HARD_KEYBOARD_SETTINGS = "android.settings.HARD_KEYBOARD_SETTINGS";
////    实体键盘设置
//
//    public static final String ACTION_USER_DICTIONARY_INSERT = "com.android.settings.USER_DICTIONARY_INSERT";
////    用户字典 -
////    弹窗
//
//    public static final String ACTION_APPLICATION_SETTINGS = "android.settings.APPLICATION_SETTINGS";
//    应用列表
//
//            ACTION_APPLICATION_DEVELOPMENT_SETTINGS = "android.settings.APPLICATION_DEVELOPMENT_SETTINGS";
//    ACTION_QUICK_LAUNCH_SETTINGS ="android.settings.QUICK_LAUNCH_SETTINGS";
//    ACTION_MANAGE_APPLICATIONS_SETTINGS ="android.settings.MANAGE_APPLICATIONS_SETTINGS";
//    应用管理
//
//            ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS = "android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS";
//    ACTION_MANAGE_OVERLAY_PERMISSION ="android.settings.action.MANAGE_OVERLAY_PERMISSION";
//    顶层显示设置
//
//            ACTION_MANAGE_WRITE_SETTINGS = "android.settings.action.MANAGE_WRITE_SETTINGS";
//    可修改系统设置的应用列表
//
//
//            ACTION_APPLICATION_DETAILS_SETTINGS = "android.settings.APPLICATION_DETAILS_SETTINGS";
//    ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS ="android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS";
//    电池优化设置
//
//            ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = "android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS";
//    ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS ="android.settings.IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS";
//    ACTION_APP_OPS_SETTINGS ="android.settings.APP_OPS_SETTINGS";
//    ACTION_SYSTEM_UPDATE_SETTINGS ="android.settings.SYSTEM_UPDATE_SETTINGS";
//    ACTION_SYNC_SETTINGS ="android.settings.SYNC_SETTINGS";
//    同步设置
//
//            ACTION_ADD_ACCOUNT = "android.settings.ADD_ACCOUNT_SETTINGS";
//
//
//    ACTION_NETWORK_OPERATOR_SETTINGS ="android.settings.NETWORK_OPERATOR_SETTINGS";
//    运营商选择设置
//
//            ACTION_DATA_ROAMING_SETTINGS = "android.settings.DATA_ROAMING_SETTINGS";
//    双卡移动网络设置
//
//            ACTION_MEMORY_CARD_SETTINGS = "android.settings.MEMORY_CARD_SETTINGS";
//    ACTION_INTERNAL_STORAGE_SETTINGS ="android.settings.INTERNAL_STORAGE_SETTINGS";
//    储存信息查看
//
//            ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
//    搜索管理
//
//            ACTION_DEVICE_INFO_SETTINGS = "android.settings.DEVICE_INFO_SETTINGS";
//    手机状态、
//    手机信息查看
//
//
//            ACTION_NFCSHARING_SETTINGS = "android.settings.NFCSHARING_SETTINGS";
//    AndroidBeam 开关界面
//
//    ACTION_NFC_PAYMENT_SETTINGS ="android.settings.NFC_PAYMENT_SETTINGS";
//    触碰付款设置
//
//            ACTION_DREAM_SETTINGS = "android.settings.DREAM_SETTINGS";
//    屏保？？
//
//    ACTION_NOTIFICATION_LISTENER_SETTINGS  ="android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
//    通知使用权 通知权限列表
//
//    ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS  ="android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS";
//            "勿扰"
//    权限 拥有
//    开启勿扰权限应用列表
//
//            ACTION_CONDITION_PROVIDER_SETTINGS = "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS";
//    勿扰模式设置
//
//            ACTION_CAPTIONING_SETTINGS = "android.settings.CAPTIONING_SETTINGS";
//    字幕设置
//
//            ACTION_PRINT_SETTINGS = "android.settings.ACTION_PRINT_SETTINGS";
//    打印 服务
//
//    ACTION_ZEN_MODE_PRIORITY_SETTINGS  ="android.settings.ZEN_MODE_PRIORITY_SETTINGS";
//    仅允许指定的优先事项打扰 通知优先设置
//
//    ACTION_ZEN_MODE_SETTINGS ="android.settings.ZEN_MODE_SETTINGS";
//    ACTION_ZEN_MODE_AUTOMATION_SETTINGS  ="android.settings.ZEN_MODE_AUTOMATION_SETTINGS";
//    防打扰
//
//            ACTION_VOICE_CONTROL_DO_NOT_DISTURB_MODE = "android.settings.VOICE_CONTROL_DO_NOT_DISTURB_MODE";
//    ACTION_ZEN_MODE_SCHEDULE_RULE_SETTINGS    ="android.settings.ZEN_MODE_SCHEDULE_RULE_SETTINGS";
//    ACTION_ZEN_MODE_EVENT_RULE_SETTINGS   ="android.settings.ZEN_MODE_EVENT_RULE_SETTINGS";
//    ACTION_ZEN_MODE_EXTERNAL_RULE_SETTINGS  ="android.settings.ZEN_MODE_EXTERNAL_RULE_SETTINGS";
//    public static final String ACTION_SHOW_REGULATORY_INFO = "android.settings.SHOW_REGULATORY_INFO";
//    DEVICE_NAME_SETTINGS ="android.settings.DEVICE_NAME";
//    ACTION_PAIRING_SETTINGS ="android.settings.PAIRING_SETTINGS";
//    ACTION_BATTERY_SAVER_SETTINGS ="android.settings.BATTERY_SAVER_SETTINGS";
//    省电模式自动开启阈值设置
//
//            ACTION_VOICE_CONTROL_BATTERY_SAVER_MODE = "android.settings.VOICE_CONTROL_BATTERY_SAVER_MODE";
//    ACTION_HOME_SETTINGS  ="android.settings.HOME_SETTINGS";
//    默认应用设置
//
//            ACTION_MANAGE_DEFAULT_APPS_SETTINGS = "android.settings.MANAGE_DEFAULT_APPS_SETTINGS";
//    默认应用设置
//
//            ACTION_NOTIFICATION_SETTINGS = "android.settings.NOTIFICATION_SETTINGS";
//    通知 系统通知开关
//
//    ACTION_APP_NOTIFICATION_SETTINGS ="android.settings.APP_NOTIFICATION_SETTINGS";
//    ACTION_APP_NOTIFICATION_REDACTION  ="android.settings.ACTION_APP_NOTIFICATION_REDACTION";
//    锁屏显示通知内容设置
//
//            ACTION_SHOW_ADMIN_SUPPORT_DETAILS = "android.settings.SHOW_ADMIN_SUPPORT_DETAILS";
//    设备管理用用设置 -59
//
//    ACTION_SHOW_REMOTE_BUGREPORT_DIALOG  ="android.settings.SHOW_REMOTE_BUGREPORT_DIALOG";
//    ACTION_VR_LISTENER_SETTINGS  ="android.settings.VR_LISTENER_SETTINGS";
//    VR 助手服务 79
//
//    ACTION_STORAGE_MANAGER_SETTINGS  ="android.settings.STORAGE_MANAGER_SETTINGS";
//    ACTION_WEBVIEW_SETTINGS ="android.settings.WEBVIEW_SETTINGS";
//    设置 WebView
//    实现 80
//
//    MANAGE_UNKNOWN_APP_SOURCES 安装未知应用权限设置列表
//    PICTURE_IN_PICTURE_SETTINGS 画中画


    Button[] buts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_show);
        LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i) != null) {
                    parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                }
            }
        });
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btn.getLayoutParams();
        Field[] fields = Settings.class.getFields();
        buts = new Button[fields.length];
        try {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].get(null) instanceof String) {
                    buts[i] = new Button(IntentStartActivity.this);
                    buts[i].setText((String) fields[i].get(null));
                    buts[i].setOnClickListener(this);
                    parent.addView(buts[i], lp);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            boolean selected = !v.isSelected();
            v.setBackgroundColor(selected ? Color.GRAY : Color.WHITE);
            v.setSelected(selected);
            String str = ((Button) v).getText().toString();
            try {
                Intent intent = new Intent();
                intent.setAction(str);
                startActivity(intent);
            } catch (Exception e) {
                try {
                    v.setBackgroundColor(Color.YELLOW);
                    Intent intent = new Intent();
                    intent.setAction(str);
                    intent.setData(Uri.parse("package:" + IntentStartActivity.this.getPackageName()));
                    startActivity(intent);
                } catch (Exception e1) {
                    ((Button) v).setTextColor(Color.WHITE);
                    v.setBackgroundColor(Color.BLACK);
                    v.setClickable(false);
                }
            }

        }
    }
}
