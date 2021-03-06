package com.autonavi.minimap.route.coach.net;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class CoachOrderRequestCallback<T extends AbstractAOSParser> implements AosResponseCallback<AosByteResponse> {
    protected T a;
    protected Callback<T> b;
    private Handler c = new Handler(Looper.getMainLooper());

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            this.a.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
            if (!this.a.isSuccessRequest()) {
                if (this.a.errorCode != 7) {
                    this.c.post(new Runnable() {
                        public final void run() {
                            CoachOrderRequestCallback.this.b.error(null, true);
                        }
                    });
                    return;
                }
            }
            this.c.post(new Runnable() {
                public final void run() {
                    if (CoachOrderRequestCallback.this.b != null) {
                        CoachOrderRequestCallback.this.b.callback(CoachOrderRequestCallback.this.a);
                    }
                }
            });
        } catch (UnsupportedEncodingException | JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CoachOrderRequestCallback(T t, Callback<T> callback) {
        this.a = t;
        this.b = callback;
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        this.c.post(new Runnable() {
            public final void run() {
                if (CoachOrderRequestCallback.this.b != null) {
                    CoachOrderRequestCallback.this.b.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
                }
            }
        });
    }
}
