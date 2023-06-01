package com.example.cps_lab.app;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PreTrainedModelforAnn {

    private final Interpreter interpreter;

    public PreTrainedModelforAnn(Context context, String modelPath) throws IOException {
        // Load model from file
        MappedByteBuffer modelBuffer = loadModelFile(context.getAssets(), modelPath);
        Interpreter.Options options = new Interpreter.Options();
        interpreter = new Interpreter(modelBuffer, options);
    }

    public void predict(float[] input) {
        try {
            // Prepare the input data
            float[][] inputData = {input};

            // Prepare the output data buffer
            float[][] outputData = new float[1][4];

            // Run the inference
            interpreter.run(inputData, outputData);

            // Get the predicted class
            float maxVal = outputData[0][0];
            int maxIdx = 0;
            for (int i = 1; i < 3; i++) {
                if (outputData[0][i] > maxVal) {
                    maxVal = outputData[0][i];
                    maxIdx = i;
                }
            }
            Log.d(TAG, "Predicted class: " + maxIdx);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MappedByteBuffer loadModelFile(AssetManager assetManager, String fileName) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
