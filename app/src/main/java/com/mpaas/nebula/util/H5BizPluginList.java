package com.mpaas.nebula.util;

import android.os.Build.VERSION;
import com.alipay.mobile.beehive.plugins.capture.CapturePlugin;
import com.alipay.mobile.beehive.plugins.imageedit.ImageEditPlugin;
import com.alipay.mobile.framework.BuildConfig;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5plugin.ConfigPlugin;
import com.alipay.mobile.logging.TinyLoggingConfigPlugin;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.plugin.H5AlertPlugin;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu;
import com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5FSManagePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5HideOptionMenuItemPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5PkgResPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5TabBarPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5TinyWebViewSharePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketClosePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketConnectPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketSendMsgPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppCommonInfoPlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppSharePlugin;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppUpdatePlugin;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramOpenSettingPlugin;
import com.alipay.mobile.tinyappcustom.h5plugin.H5RequestTemplateDataPlugin;
import com.alipay.mobile.tinyappcustom.process.H5EventHandlerServiceImpl;
import com.alipay.multimedia.js.image.H5CompressImagePlugin;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin;
import com.mpaas.nebula.plugin.AntUILinkagePickerPlugin;
import com.mpaas.nebula.plugin.H5ScanPlugin;
import com.mpaas.nebula.plugin.H5ServerTimePlugin;
import com.mpaas.nebula.plugin.H5ServicePlugin;
import com.mpaas.nebula.provider.H5BaseProviderInfo;
import java.util.ArrayList;
import java.util.List;

public class H5BizPluginList {
    public static List<H5PluginConfig> bizPluginList = new ArrayList<H5PluginConfig>() {
        {
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramNavigationPlugin", (String) "page", (String) "navigateToMiniProgram|navigateBackMiniProgram"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5TinyAppxRpcPlugin", (String) "page", (String) "appxrpc"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.h5plugin.TinyAppStoragePlugin", (String) "page", (String) "setTinyLocalStorage|getTinyLocalStorage|removeTinyLocalStorage|clearTinyLocalStorage|getTinyLocalStorageInfo|sendTinyLocalStorageToIDE"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.h5plugin.AntUIViewPlugin", (String) "page", (String) "antUIOptionPicker"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5MpaasSystemInfoPlugin", (String) "page", (String) "getSystemInfo|watchShake"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramOpenSettingPlugin", (String) "page", (String) H5MiniProgramOpenSettingPlugin.JS_FUNCTION_NAME));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5MiniProgramGetSettingPlugin", (String) "page", (String) "getSetting"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppBackHomePlugin", (String) "page", (String) "showBackHome|showBackToHomepage"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppCanvasApiPlugin", (String) "page", (String) "measureText"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5HideOptionMenuItemPlugin", (String) "page", (String) H5HideOptionMenuItemPlugin.HIDE_MENU_ITEM));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5DebugConsolePlugin", (String) "page", (String) "tinyDebugConsole|sendMsgFromVConsoleToAppx"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5RpcPlugin", (String) "page", (String) "rpc"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin", (String) "session", (String) "shouldShowConsoleBtn|toggleDebugPanel|showToggleButton|clearStorage4Debug"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "test.tinyapp.alipay.com.testlibrary.plugin.TestRouter", (String) "session", (String) "showPerformancePanel|hidePerformancePanel|pushWindow|onAppPerfEvent"));
            add(new H5PluginConfig((String) "android-phone-mobilesdk-router", (String) "com.alipay.mobile.pagerouter.impl.PagerRouterH5Plugin", (String) "service", (String) CommonEvents.H5_PAGE_SHOULD_LOAD_URL));
            if ("com.autonavi.minimap".equals(LauncherApplicationAgent.getInstance().getApplicationContext().getPackageName())) {
                add(new H5PluginConfig((String) BuildConfig.BUNDLE_NAME, (String) "com.autonavi.miniapp.plugin.lbs.H5LocationPlugin", (String) "page", (String) "getLocation|getKBLocation|startIndoorLocation|stopIndoorLocation|prefetchLocation|openLocation|chooseLocation|startContinuousLocation|stopContinuousLocation|getCurrentLocation|getResidentLocation"));
            } else {
                add(new H5PluginConfig((String) "com-mpaas-mpaasadapter-commonbiz", (String) "com.alipay.mobile.h5plugin.H5LocationPlugin", (String) "page", (String) "getLocation|startIndoorLocation|stopIndoorLocation|prefetchLocation|openLocation|chooseLocation|startContinuousLocation|stopContinuousLocation|getCurrentLocation|getResidentLocation"));
            }
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.GuideAlivePlugin", (String) "page", (String) "showAuthGuide|hasPermissionGuidePath|startPermissionPage|getPermissionGuideContent"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5PhotoPlugin", (String) "service", (String) "photo"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.template.TinyAppTemplateConfigPlugin", (String) "service", (String) "getExtConfig"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5UploadPlugin", (String) "page", (String) "upload|uploadFile"));
            add(new H5PluginConfig(H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.h5plugin.H5PayPlugin", "page", H5EventHandlerServiceImpl.tradePay, false));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5SharePlugin", (String) "page", (String) "startShare|share|wechatShare|shareToChannel|shareToChannelSafety"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5RpcPlugin", (String) "page", (String) "rpc"));
            add(new H5PluginConfig(H5BaseProviderInfo.tinyappcustom, "com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin", "page", H5ContactPlugin.CONTACT, false));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5ServicePlugin", (String) "service", (String) H5ServicePlugin.GET_CONFIG));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5APDataStoragePlugin", (String) "page", (String) "setAPDataStorage|getAPDataStorage|removeAPDataStorage|clearAPDataStorage|switchControl|getSwitchControlStatus"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5AliAutoLoginPlugin", (String) "session", (String) "aliAutoLogin"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", H5ServerTimePlugin.class.getName(), (String) "page", (String) H5ServerTimePlugin.GET_SERVER_TIME));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.TinyAppMTopPlugin", (String) "page", (String) "sendMtop|getTBCode|getTBSessionInfo|setTBSessionInfo"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5UploadImagePlugin", (String) "page", (String) "uploadImage|downloadImage"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5OpenAuthPlugin", (String) "page", (String) "getAuthCode|getAuthUserInfo|getComponentAuth|getBusinessAuth|getAuthorize"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5PromptPlugin", (String) "page", (String) "prompt"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.AntUILinkagePickerPlugin", (String) "page", (String) AntUILinkagePickerPlugin.GET_LINKAGE_PICKER));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.ApiDynamicPermissionPlugin", (String) "page", (String) ApiDynamicPermissionPlugin.INTERNAL_API));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppSharePlugin", (String) "page", (String) TinyAppSharePlugin.SHARE_TINY_APP_MSG));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5FilePlugin", (String) "page", (String) "saveFile|downloadFile|h5PageClose|getSavedFileInfo|removeSavedFile|getSavedFileList|getFileInfo|operateDownloadTask"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketConnectPlugin", (String) "session", (String) H5WebSocketConnectPlugin.CONNECT_SOCKET));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketClosePlugin", (String) "session", (String) H5WebSocketClosePlugin.CLOSE_SOCKET));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5WebSocketSendMsgPlugin", (String) "session", (String) H5WebSocketSendMsgPlugin.SEND_SOCKET_MESSAGE));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5StartPagePlugin", (String) "service", (String) "startH5App"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.H5AppPlugin", (String) "service", (String) "startApp|exitApp"));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", H5ScanPlugin.class.getName(), (String) "service", (String) "scan"));
            add(new H5PluginConfig((String) "android-phone-wallet-nebulauc", (String) "com.alipay.mobile.nebulauc.plugin.H5UCPlugin", (String) "page", (String) "setServiceWorkerID|clearServiceWorker|pushSWMessage|flushUcLog"));
            add(new H5PluginConfig((String) "multimedia-jsbridge", (String) "com.alipay.multimedia.js.image.H5CompressImagePlugin", (String) "page", (String) H5CompressImagePlugin.ACTION_COMPRESS_IMAGE));
            add(new H5PluginConfig((String) "android-phone-wallet-h5worker", (String) "com.alipay.mobile.worker.H5WorkerPlugin", (String) "service", (String) "registerWorker|postMessage"));
            add(new H5PluginConfig((String) "multimedia-jsbridge", (String) "com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin", (String) "page", (String) H5ImageBuildUrlPlugin.ACTIONS));
            add(new H5PluginConfig((String) "android-phone-wallet-socialcardsdk", (String) "com.alipay.mobile.socialcardsdk.api.plugin.ShareRouteH5Plugin", (String) "page", (String) "ShareRouteH5Plugin.shareJsApi"));
            if (VERSION.SDK_INT >= 18) {
                add(new H5PluginConfig((String) com.alipay.android.phone.bluetoothsdk.BuildConfig.BUNDLE_NAME, (String) "com.alipay.android.phone.bluetoothsdk.H5BlePlugin", (String) "service", (String) "openAPDeviceLib|closeAPDeviceLib|configAPDeviceLib|getAPDeviceInfos|sendDataToAPDevice|startScanAPDevice|stopScanAPDevice|connectAPDevice|disconnectAPDevice"));
                add(new H5PluginConfig((String) com.alipay.android.phone.bluetoothsdk.BuildConfig.BUNDLE_NAME, (String) "com.alipay.android.phone.bluetoothsdk.better.ble.H5BetterBlePlugin", (String) "service", (String) "openBluetoothAdapter|closeBluetoothAdapter|getBluetoothAdapterState|startBluetoothDevicesDiscovery|stopBluetoothDevicesDiscovery|getBluetoothDevices|getConnectedBluetoothDevices|connectBLEDevice|disconnectBLEDevice|writeBLECharacteristicValue|readBLECharacteristicValue|notifyBLECharacteristicValueChange|getBLEDeviceServices|getBLEDeviceCharacteristics"));
                add(new H5PluginConfig((String) com.alipay.android.phone.bluetoothsdk.BuildConfig.BUNDLE_NAME, (String) "com.alipay.android.phone.bluetoothsdk.beacon.H5BeaconPlugin", (String) "service", (String) "startBeaconDiscovery|stopBeaconDiscovery|getBeacons"));
            }
            add(new H5PluginConfig((String) "android-phone-mobilesdk-liteprocess", (String) "com.alipay.mobile.liteprocess.perf.H5PerformancePlugin", (String) "page", (String) "h5PageStarted|h5PageFinished|h5PageRender|pushWindow|h5PageClosed|onAppPerfEvent"));
            add(new H5PluginConfig((String) "android-phone-wallet-publicplatformcommon", (String) "com.alipay.android.phone.publicplatform.common.service.H5LifePanelPlugin", (String) "page", (String) CommonEvents.H5_PAGE_STARTED));
            add(new H5PluginConfig((String) "android-phone-wallet-publicplatformcommon", (String) "com.alipay.mobile.publicplatform.gray.GrayH5Plugin", (String) "page", (String) "callBackLifeContentStatus"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5ShowShareParamPlugin", (String) "page", (String) "setShowShareMenu|hideShareMenu"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5ImageInfoPlugin", (String) "page", (String) "getImageInfo"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppCommonInfoPlugin", (String) "service", (String) TinyAppCommonInfoPlugin.SET_APPX_VERSION));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.logging.TinyLoggingConfigPlugin", (String) "page", (String) TinyLoggingConfigPlugin.ACTION_TRACKER_CONFIG));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.h5plugin.ConfigPlugin", (String) "page", (String) ConfigPlugin.ACTION));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppSharePlugin", (String) "page", (String) TinyAppSharePlugin.SHARE_TINY_APP_MSG));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin", (String) "page", (String) "navigateToMiniService|navigateBackFromMiniService|startMiniService"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5TinyWebViewSharePlugin", (String) "page", (String) H5TinyWebViewSharePlugin.ACTION_SHARE));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin", (String) "page", (String) "watchShake"));
            add(new H5PluginConfig((String) "android-phone-mobilecommon-falconrecmanager", (String) "com.alipay.android.phone.falcon.aiinterface.FalconAIH5Plugin", (String) "page", (String) "FalconAIRec|FalconAIModify"));
            add(new H5PluginConfig((String) "multimedia-xmedia", (String) "com.ant.phone.xmedia.hybrid.H5XMediaPlugin", (String) "page", (String) "startXMedia|stopXMedia"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.capture.CapturePlugin", (String) "page", (String) CapturePlugin.METHOD_PHOTO_CAPTURE));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.capture.VideoSelectPlugin", (String) "page", (String) "BEEVideoCapture|chooseVideo"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.photo.ChooseImagePlugin", (String) "page", (String) "chooseImage"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5SaveVideoPlugin", (String) "page", (String) "saveVideoToPhotosAlbum"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.poiselect.service.Impl.H5POIPickPlugin", (String) "page", (String) "beehiveGetPOI"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.audio.AudioBackgroundPlayPlugin", (String) "service", (String) "playBackgroundAudio|pauseBackgroundAudio|stopBackgroundAudio|seekBackgroundAudio|getBackgroundAudioPlayerState|startMonitorBackgroundAudio|stopMonitorBackgroundAudio|getBackgroundAudioOption|setBackgroundAudioOption"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.audio.AudioForegroundPlayPlugin", (String) "session", (String) "playForegroundAudio|pauseForegroundAudio|stopForegroundAudio|seekForegroundAudio|destroyForegroundAudio|setForegroundAudioOption|getForegroundAudioOption|startMonitorForegroundAudio|stopMonitorForegroundAudio"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.finchannel.FinChannelIconPlugin", (String) "page", (String) "beehive.getChannelIcon"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.imageedit.ImageEditPlugin", (String) "page", (String) ImageEditPlugin.ACTION_IMAGE_EDIT));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.plugins.capture.CaptureForIndustryPlugin", (String) "page", (String) "CaptureForIndustry|UpdateCaptureUI"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.compositeui.app.H5BeehiveViewPlugin", (String) "page", (String) "beehiveOptionsPicker|beehiveLimitedHoursPicker|beehiveMultilevelSelect"));
            add(new H5PluginConfig((String) "android-phone-wallet-ppchat", (String) "com.alipay.mobile.pubsvc.life.view.webview.H5LifeCityPlugin", (String) "page", (String) "switchCity"));
            add(new H5PluginConfig((String) "android-phone-thirdparty-devicecenter", (String) "com.aliyun.alink.business.devicecenter.extant.BoneDeviceWifiConfigurePlugin", (String) "page", (String) "BoneDeviceWifiConfigure_getCurrentSsid|BoneDeviceWifiConfigure_startConfigure|BoneDeviceWifiConfigure_stopConfigure"));
            add(new H5PluginConfig((String) "android-phone-thirdparty-devicecenter", (String) "com.aliyun.alink.business.devicecenter.extant.BoneDeviceHotspotConfigurePlugin", (String) "page", (String) "BoneDeviceHotspotConfigure_isSupportedHotspot|BoneDeviceHotspotConfigure_autoSetupHotspot|BoneDeviceHotspotConfigure_closeHotspot|BoneDeviceHotspotConfigure_discoverDevices|BoneDeviceHotspotConfigure_stopDiscoverDevices|BoneDeviceHotspotConfigure_searchWifiList|BoneDeviceHotspotConfigure_stopSearchWifiList|BoneDeviceHotspotConfigure_switchToAccessPoint"));
            add(new H5PluginConfig((String) "android-phone-thirdparty-devicecenter", (String) "com.aliyun.alink.business.devicecenter.extant.BoneDeviceDiscoveryPlugin", (String) "page", (String) "BoneDeviceDiscovery_discoverDevices|BoneDeviceDiscovery_stopDiscoverDevices"));
            add(new H5PluginConfig((String) "android-phone-thirdparty-devicecenter", (String) "com.aliyun.alink.business.devicecenter.extant.BoneDeviceAuthPlugin", (String) "page", (String) "BoneDeviceAuth_authGetUuid|BoneDeviceAuth_authSetAuthToken"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.embedview.H5WebViewMessagePlugin", (String) "page", (String) "postWebViewMessage|getEmbedWebViewEnv"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.CertifyServicePlugin", (String) "page", (String) "startAPVerify"));
            add(new H5PluginConfig((String) "android-phone-wallet-beehive", (String) "com.alipay.mobile.beehive.cityselect.impl.H5PickCityPlugin", (String) "page", (String) "getCities|getCustomCities|beehive.getProvinceCitys|setLocatedCity"));
            if (H5Utils.isDebuggable(H5Utils.getContext())) {
                add(new H5PluginConfig((String) H5BaseProviderInfo.nebuladebug, (String) "test.tinyapp.alipay.com.testlib.router.NebulaTestPlugin", (String) "session", (String) "monitorH5Performance|h5PageFinishedSync|h5PerformanceError|h5PerformanceInfo|h5PageBack|h5Performance.coverage"));
                add(new H5PluginConfig((String) "android-phone-wallet-nebulauc", (String) "com.alipay.mobile.nebulauc.plugin.H5WirelessDebugPlugin", (String) "service", (String) "openWirelessDebug"));
            }
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5PhoneContactAddPlugin", (String) "service", (String) "addPhoneContact"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRunScenePlugin", (String) "service", (String) "getRunScene"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5VibratePlugin", (String) "service", (String) "vibrateLong|vibrateShort"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5WifiManagerPlugin", (String) "session", (String) "startWifi|stopWifi|connectWifi|getWifiList|getConnectedWifi"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5MemoryWarningPlugin", (String) "session", (String) "startMonitorMemoryWarning|stopMonitorMemoryWarning"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5AppearancePlugin", (String) "page", (String) "setBackgroundColor|setBackgroundTextStyle|setBackButton"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppRequestPlugin", (String) "page", (String) "request|operateRequestTask"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5TabBarPlugin", (String) "session", (String) H5TabBarPlugin.ACTION_SET_TAB_BAR));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyCustomPopMenuPlugin", (String) "service", (String) H5TinyPopMenu.DEVELOPER_CUSTOM_MENU));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppUpdatePlugin", (String) "service", (String) TinyAppUpdatePlugin.ACTION_UPDATE_APP));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppUpdateManagerPlugin", (String) "service", (String) "applyUpdate|registerUpdateManager"));
            add(new H5PluginConfig("android-phone-wallet-tinyappcommon", "com.alipay.mobile.tinyappcommon.h5plugin.TinyGetBatteryInfoPlugin", "service", "getBatteryInfo", false));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyDeviceOrientationPlugin", (String) "service", (String) "startDeviceMotionListening|stopDeviceMotionListening"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin", (String) "service", (String) "loadSubPackage"));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5FSManagePlugin", (String) "service", (String) H5FSManagePlugin.ACTION_FS_MANAGE));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5PkgResPlugin", (String) "service", (String) H5PkgResPlugin.ACTION_ADD_PKG_RES));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.H5TinyAppRemoteLogPlugin", (String) "service", (String) H5TinyAppLogUtil.TINY_APP_STANDARD_LOG));
            add(new H5PluginConfig((String) "android-phone-wallet-tinyappcommon", (String) "com.alipay.mobile.tinyappcommon.h5plugin.TinyAppUCFailPlugin", (String) "service", (String) H5AlertPlugin.showUCFailDialog));
            add(new H5PluginConfig((String) "com-mpaas-nebula-adapter-mpaasnebulaadapter", (String) "com.mpaas.nebula.plugin.apservice.H5APServicePlugin", (String) "service", (String) "startAPService"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5OCRPlugin", (String) "page", (String) "ocr|ocrIdCard|ocrGeneral|ocrBankCard|ocrVehicle|ocrBusinessLicense|ocrTrainTicket|ocrDriverLicense|ocrBusinessCard|ocrPassport|ocrVehiclePlate|ocrVin"));
            add(new H5PluginConfig((String) H5BaseProviderInfo.tinyappcustom, (String) "com.alipay.mobile.tinyappcustom.h5plugin.H5RequestTemplateDataPlugin", (String) "page", (String) H5RequestTemplateDataPlugin.REQUEST_TEMPLATE_DATA));
        }
    };
}