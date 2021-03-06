package fr.bamlab.rncameraroll;

import android.content.Context;
import android.graphics.Camera;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

/**
 * Created by almouro on 11/12/15.
 */
class CameraRollModule extends ReactContextBaseJavaModule {
    private Context context;

    public CameraRollModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    /**
     * @return the name of this module. This will be the name used to {@code require()} this module
     * from javascript.
     */
    @Override
    public String getName() {
        return "CameraRollAndroid";
    }

    @ReactMethod
    public void getCameraImages(ReadableMap data, Callback onSuccess) {
        WritableNativeArray result = new WritableNativeArray();

        for(CameraImage imageData : CameraImagesManager.getCameraImages(this.context,
                data.getInt("first"), data.hasKey("after") ? data.getString("after") : "")) {
            WritableMap imageDataMap = new WritableNativeMap();
            imageDataMap.putString("uri", imageData.getLocalPath());
            imageDataMap.putInt("width", imageData.getWidth());
            imageDataMap.putInt("height", imageData.getHeight());
            imageDataMap.putInt("orientation", imageData.getOrientation());
            imageDataMap.putString("timestamp", Long.toString(imageData.getTimestamp()));

            result.pushMap(imageDataMap);
        }

        onSuccess.invoke(result);
    }
}
