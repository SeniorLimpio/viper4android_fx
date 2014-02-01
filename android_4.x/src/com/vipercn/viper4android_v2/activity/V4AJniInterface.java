package com.vipercn.viper4android_v2.activity;

import android.util.Log;

import java.nio.charset.Charset;

public class V4AJniInterface {

    private static boolean m_JniLoadOK;

    static {
        try {
            System.loadLibrary("V4AJniUtils");
            m_JniLoadOK = true;
            Log.i("ViPER4Android_Utils", "libV4AJniUtils.so loaded");
        } catch (UnsatisfiedLinkError e) {
            m_JniLoadOK = false;
            Log.e("ViPER4Android_Utils", "[Fatal] Can't load libV4AJniUtils.so");
        }
    }

    /* Library Check Utils */
    private native static int CheckLibraryUsable();

    /* CPU Check Utils */
    private native static int CheckCPUHasNEON();

    private native static int CheckCPUHasVFP();

    /* Impulse Response Utils */
    private native static int[] GetImpulseResponseInfo(byte[] mIRFileName);

    private native static byte[] ReadImpulseResponse(byte[] mIRFileName);

    private native static int[] HashImpulseResponse(byte[] baBuffer, int nBufferSize);

    /* This method is just making sure jni has been loaded */
    public static boolean CheckLibrary() {
        if (!m_JniLoadOK) {
            return false;
        }
        int nUsable = CheckLibraryUsable();
        return nUsable == 1;
    }

    public static boolean IsLibraryUsable() {
        return m_JniLoadOK;
    }

    public static boolean IsCPUSupportNEON() {
        if (!m_JniLoadOK) {
            return false;
        }
        int nResult = CheckCPUHasNEON();
        Log.i("ViPER4Android_Utils", "CpuInfo[jni] = NEON:" + nResult);
        return nResult != 0;
    }

    public static boolean IsCPUSupportVFP() {
        if (!m_JniLoadOK) {
            return false;
        }
        int nResult = CheckCPUHasVFP();
        Log.i("ViPER4Android_Utils", "CpuInfo[jni] = VFP:" + nResult);
        return nResult != 0;
    }

    public static int[] GetImpulseResponseInfoArray(String mIRFileName) {
        if (!m_JniLoadOK) {
            return null;
        }
        // Convert unicode string to multi-byte string
        byte[] stringBytes = mIRFileName.getBytes(Charset.forName("US-ASCII"));
        if (stringBytes == null) {
            return null;
        }
        // Call native
        return GetImpulseResponseInfo(stringBytes);
    }

    public static byte[] ReadImpulseResponseToArray(String mIRFileName) {
        if (!m_JniLoadOK) {
            return null;
        }
        // Convert unicode string to multi-byte string
        byte[] stringBytes = mIRFileName.getBytes(Charset.forName("US-ASCII"));
        if (stringBytes == null) {
            return null;
        }
        // Call native
        return ReadImpulseResponse(stringBytes);
    }

    public static int[] GetHashImpulseResponseArray(byte[] baBuffer) {
        if (!m_JniLoadOK) {
            return null;
        }
        if (baBuffer == null) {
            return null;
        }
        // Call native
        return HashImpulseResponse(baBuffer, baBuffer.length);
    }
}
