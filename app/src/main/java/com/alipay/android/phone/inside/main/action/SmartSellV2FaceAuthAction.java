package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.smartsellv2.PayAuthV2ResultCode;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import org.json.JSONObject;

public class SmartSellV2FaceAuthAction implements ISmartSellV2Action {
    private static final String a = "SmartSellV2FaceAuthAction";

    public final String a() {
        return ActionEnum.SMART_SELL_PAY_AUTH_V2_FACE_REQUEST.getActionName();
    }

    public final OperationResult<PayAuthV2ResultCode> a(JSONObject jSONObject) {
        String str;
        String str2;
        Bundle bundle;
        JSONObject jSONObject2;
        LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "main").g = a;
        OperationResult<PayAuthV2ResultCode> operationResult = new OperationResult<>(PayAuthV2ResultCode.SUCCESS, ActionEnum.SMART_SELL_PAY_AUTH_V2_FACE_REQUEST.getActionName());
        String str3 = null;
        try {
            bundle = (Bundle) ServiceExecutor.b("ONCE_AUTH_V2_PLUGIN_FACEAUTH_SERVICE", jSONObject);
            String string = bundle.getString("resultCode", GenBusCodeService.CODE_FAILED);
            char c = 65535;
            int hashCode = string.hashCode();
            if (hashCode != -1149187101) {
                if (hashCode != -595928767) {
                    if (hashCode != 503926173) {
                        if (hashCode == 1980572282) {
                            if (string.equals(RPCDataItems.CANCEL)) {
                                c = 2;
                            }
                        }
                    } else if (string.equals("PARAMS_ILLEGAL")) {
                        c = 3;
                    }
                } else if (string.equals(GenBusCodeService.CODE_TIMEOUT)) {
                    c = 1;
                }
            } else if (string.equals(GenBusCodeService.CODE_SUCESS)) {
                c = 0;
            }
            switch (c) {
                case 0:
                    operationResult.setCode(PayAuthV2ResultCode.SUCCESS);
                    break;
                case 1:
                    operationResult.setCode(PayAuthV2ResultCode.TIMEOUT);
                    break;
                case 2:
                    operationResult.setCode(PayAuthV2ResultCode.CANCEL);
                    break;
                case 3:
                    operationResult.setCode(PayAuthV2ResultCode.PARAMS_ILLEGAL);
                    break;
                default:
                    operationResult.setCode(PayAuthV2ResultCode.FAILED);
                    break;
            }
            jSONObject2 = new JSONObject();
            String string2 = bundle.getString("resultValue");
            if (string2 != null) {
                jSONObject2.put("data", new JSONObject(string2));
            } else {
                jSONObject2.put("data", new JSONObject());
            }
        } catch (Throwable th) {
            th = th;
            str2 = null;
            operationResult.setCode(PayAuthV2ResultCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "OnceAuthV2StartEx", th);
            th.printStackTrace();
            str = str3;
            Behavior a2 = LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "exit");
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(operationResult.getCodeValue());
            a2.g = sb.toString();
            a2.h = str2;
            a2.i = str;
            return operationResult;
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("errorType", bundle.getString("errorType", "null"));
        jSONObject3.put("errorCode", bundle.getString("errorCode", "null"));
        jSONObject3.put("errorMsg", bundle.getString("errorMsg", "null"));
        str2 = bundle.getString("errorType", "null");
        try {
            str = bundle.getString("errorCode", "null");
            try {
                jSONObject2.put("subCode", jSONObject3);
                operationResult.setResult(jSONObject2.toString());
            } catch (Throwable th2) {
                Throwable th3 = th2;
                str3 = str;
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            operationResult.setCode(PayAuthV2ResultCode.FAILED);
            LoggerFactory.e().a((String) "main", (String) "OnceAuthV2StartEx", th);
            th.printStackTrace();
            str = str3;
            Behavior a22 = LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "exit");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append(MergeUtil.SEPARATOR_KV);
            sb2.append(operationResult.getCodeValue());
            a22.g = sb2.toString();
            a22.h = str2;
            a22.i = str;
            return operationResult;
        }
        Behavior a222 = LoggerFactory.d().a("onceauth_v2", BehaviorType.EVENT, "exit");
        StringBuilder sb22 = new StringBuilder();
        sb22.append(a);
        sb22.append(MergeUtil.SEPARATOR_KV);
        sb22.append(operationResult.getCodeValue());
        a222.g = sb22.toString();
        a222.h = str2;
        a222.i = str;
        return operationResult;
    }
}
