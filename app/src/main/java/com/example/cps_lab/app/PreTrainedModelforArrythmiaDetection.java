package com.example.cps_lab.app;


import static java.util.Arrays.*;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PreTrainedModelforArrythmiaDetection {
    private final Interpreter interpreter;

    public PreTrainedModelforArrythmiaDetection(Context context, String modelPath) throws IOException {
        // Load model from file
        MappedByteBuffer modelBuffer = loadModelFile(context.getAssets(), modelPath);
        Interpreter.Options options = new Interpreter.Options();
        interpreter = new Interpreter(modelBuffer, options);
    }

    // Load the model file from the assets folder
    private static MappedByteBuffer loadModelFile(AssetManager assetManager, String fileName) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // Create a ByteBuffer object to hold the input data
    private ByteBuffer createInputBuffer(float[] inputData) {
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(inputData.length * 4);
        inputBuffer.order(ByteOrder.nativeOrder());
        for (float input : inputData) {
            inputBuffer.putFloat(input);
        }
        return inputBuffer;
    }

    public int predictArrhythmiaClass(float[] inputData, Context context, String modelPath) throws IOException {
        // Preprocess the input data
        float[] preprocessedSignal = preprocessSignal(inputData, 159);

        // Initialize the interpreter
        Interpreter interpreter = new Interpreter(loadModelFile(context.getAssets(), modelPath));

        // Prepare the input data buffer
        int inputSize = preprocessedSignal.length;
        TensorBuffer inputBuffer = TensorBuffer.createFixedSize(new int[]{1, inputSize}, DataType.FLOAT32);
        ByteBuffer inputByteBuffer = ByteBuffer.allocateDirect(inputSize * 4).order(ByteOrder.nativeOrder());
        inputByteBuffer.asFloatBuffer().put(preprocessedSignal);
        inputBuffer.loadBuffer(inputByteBuffer);

        // Prepare the output data buffer
        int outputSize = 4;  // number of output classes
        TensorBuffer outputBuffer = TensorBuffer.createFixedSize(new int[]{1, outputSize}, DataType.FLOAT32);

        // Make predictions on the input data
        interpreter.run(inputBuffer.getBuffer(), outputBuffer.getBuffer());

        // Get the predicted class
        float[] output = outputBuffer.getFloatArray();
        int predictedClass = 0;
        float maxScore = Float.MIN_VALUE;
        for (int i = 0; i < outputSize; i++) {
            float score = output[i];
            if (score > maxScore) {
                predictedClass = i;
                maxScore = score;
            }
        }
        return predictedClass;
    }


    public static float[] preprocessSignal(float[] signal, int signalLength) {
        float[] preprocessedSignal = new float[signalLength];

        // Reshape the signal
        if (signal.length > signalLength) {
            signal = copyOf(signal, signalLength);
        }

        // Scale the signal using a StandardScaler
        SummaryStatistics stats = new SummaryStatistics();
        for (double value : signal) {
            stats.addValue(value);
        }
        double mean = stats.getMean();
        double std = stats.getStandardDeviation();
        for (int i = 0, j=0; i < signalLength; i++, j++) {
            if(j >= signal.length) {
                j = 0;
            }
            preprocessedSignal[i] = (float) ((signal[j] - mean) / std);
        }

        return preprocessedSignal;
    }

}
