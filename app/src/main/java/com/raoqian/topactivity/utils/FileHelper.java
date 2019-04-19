package com.raoqian.topactivity.utils;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by raoqian on 2017/7/22.
 */

public class FileHelper {

    //空参数构造函数，传入的值为空时，不出错
    public FileHelper() {
    }


    // write SDCard
    public void writeFileSdcardFile(String fileName, String writeStr) {// throws IOException
        try {
            writeStr = readFileSdcardFile(fileName) + "\n" + writeStr;
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = writeStr.getBytes();

            fout.write(bytes);
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("FileHelper", "writeFileSdcardFile");
        }
    }

    //    read SDCard
    private String readFileSdcardFile(String fileName) {// throws IOException
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();

            byte[] buffer = new byte[length];
            fin.read(buffer);

            res = new String(buffer, "UTF-8");

            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
