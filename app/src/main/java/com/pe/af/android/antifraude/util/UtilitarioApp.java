package com.pe.af.android.antifraude.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.view.widget.ToastCustom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Properties;

public class UtilitarioApp {

    private static final String TAG = "UtilitarioApp";

    public static String CONSPROPERTYFILE = "params.properties";

    public static String fnVersion(Context poContext) {
        String lsVersion = "";
        PackageInfo loPackageInfo = null;
        try {
            loPackageInfo = poContext.getPackageManager().getPackageInfo(
                    poContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.v(TAG, "Verificar el manifest: " + e.getMessage());
            return "ERROR VERSION";
        }

        lsVersion = loPackageInfo.versionName;

        return lsVersion;
    }

    public static Properties readProperties(Context ctx) {
        return readProperties(ctx, Configuracion.CONSPROPERTYFILE);
    }

    public static Properties readProperties(Context ctx, String arc) {
        Properties prop = null;
        try {
            AssetManager am = ctx.getAssets();
            InputStream is = am.open(arc);
            prop = new Properties();
            prop.load(is);
        } catch (Exception ex) {
            Log.e(TAG, "readProperties: ", ex);
        }
        return prop;
    }

    public static void mostrarToast(Context context, String mensaje) {
        final ToastCustom toastCustom = ToastCustom.makeText(context, mensaje, Toast.LENGTH_SHORT);
        toastCustom.show();

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                toastCustom.show();
            }

            public void onFinish() {
                toastCustom.cancel();
            }
        }.start();
    }

    public static void mostrarToast(Context context, String mensaje, int tipoMensaje) {
        final ToastCustom toastCustom = new ToastCustom(context, mensaje, tipoMensaje);
        toastCustom.show();

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                toastCustom.show();
            }

            public void onFinish() {
                toastCustom.cancel();
            }
        }.start();
    }

    public static void ExportarBD(Context context){
        try {
            exportarBaseDatos(context, context.getString(R.string.db_name));
            UtilitarioApp.mostrarToast(context, "Exportacion de BD exitosa");
        }catch (Exception e){
            UtilitarioApp.mostrarToast(context, "Error en la aplicación" + e != null ? e.getMessage() : e + "");
        }
    }
    public static void exportarBaseDatos(Context ioContext, String baseDatos) throws IOException {

        File dbFile = new File(Environment.getDataDirectory() + "/data/" + ioContext.getApplicationContext().getPackageName() + "/databases/" + baseDatos);
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File file = new File(exportDir, dbFile.getName());
        file.createNewFile();

        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(file).getChannel();

        inChannel.transferTo(0, inChannel.size(), outChannel);

        if (inChannel != null)
            inChannel.close();
        if (outChannel != null)
            outChannel.close();
    }

    public static String getRaiz(Context context){
        return context.getString(R.string.app_name);
    }

    public static Bitmap getPreview(String lsRuta) throws IOException {
        File file = new File(lsRuta);
        Uri ioURIPicture = Uri.fromFile(file);
        String path = ioURIPicture.getPath();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inDither = false; // Disable Dithering mode
        // esta deprecado
        //options.inPurgeable = true; // Tell to gc that whether it

        // deprecado
        //options.inInputShareable = true; // Which kind of reference
        options.inTempStorage = new byte[32 * 1024];
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bMap = BitmapFactory.decodeFile(path, options);

        ExifInterface exif = new ExifInterface(path);

        int rotation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = UtilitarioApp.exifToDegrees(rotation);

        Bitmap bMapRotate;
        if (rotationInDegrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationInDegrees);
            bMapRotate = Bitmap
                    .createBitmap(bMap, 0, 0, bMap.getWidth(),
                            bMap.getHeight(), matrix, true);
        } else
            bMapRotate = Bitmap.createScaledBitmap(bMap,
                    bMap.getWidth(), bMap.getHeight(), true);

        File loFile = new File(path);
        loFile.mkdirs();
        if (loFile.exists()) {
            loFile.delete();
        }

        FileOutputStream loFOUT = new FileOutputStream(loFile);

        Log.v(TAG,"se comprime foto: "
                + bMapRotate.compress(Bitmap.CompressFormat.JPEG,
                100, loFOUT));

        loFOUT.flush();
        loFOUT.close();
        return bMapRotate;
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static void grabarFoto(String lsRuta, Bitmap bMapRotate) throws IOException {
        File loFile = new File(lsRuta);
        FileOutputStream loFOUT = new FileOutputStream(loFile);
        bMapRotate.compress(Bitmap.CompressFormat.JPEG,
                100, loFOUT);
        loFOUT.flush();
        loFOUT.close();
    }

    public static Bitmap rotarMapaBits(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static String fnReadParamProperty(Context poContext, String propertyName)
    {
        return fnReadProperty(poContext, CONSPROPERTYFILE, propertyName);
    }

    public static String fnReadProperty(Context poContext, String arc, String propertyName)
    {
        String lsvalor = "";
        try
        {
            Properties loProperties = readProperties(poContext, arc);
            lsvalor = loProperties.getProperty(propertyName,"");
        }
        catch(Exception e)
        {
            lsvalor = "";
        }
        //Log.v("XXX", lsvalor);
        return lsvalor;
    }
}
