package com.wikitude.samples.plugins;

import com.wikitude.common.plugins.Plugin;
import com.wikitude.common.tracking.Frame;
import com.wikitude.common.tracking.RecognizedTarget;

/**
 * Created by Sharath_Mk on 3/3/2016.
 */
public class MyPlugin extends Plugin {
    public MyPlugin(String identifier) {
        super(identifier);
    }

    @Override
    public void cameraFrameAvailable(Frame frame) {

    }

    @Override
    public void update(RecognizedTarget[] recognizedTargets) {

    }
}
