package gregory.dan.qdlibrary;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Daniel Gregory on 30/08/2018.
 */
public class jsonParser {


    private static final String HD_CODE = "1.1";
    private static final String PES_START = "PES_";

    private static final String D1 = "D1";
    private static final String D2 = "D2";
    private static final String D3 = "D3";
    private static final String D4 = "D4";
    private static final String D5 = "D5";
    private static final String D6 = "D6";
    private static final String D7 = "D7";
    private static final String D8 = "D8";
    private static final String D9 = "D9";
    private static final String D10 = "D10";
    private static final String D11 = "D11";
    private static final String D12 = "D12";
    private static final String D13 = "D13";
    private static final String D14 = "D14";
    private static final String D15 = "D15";
    private static final String D16 = "D16";
    private static final String D17 = "D17";
    private static final String D18 = "D18";
    private static final String BELOW_D13 = "neq_to_D13";
    private static final String ABOVE_D13 = "neq_after_d13";

    public static int getMaxNEQ(Context context, String code, int distance) throws IOException, JSONException {
        JSONObject jsonObject = getJSONObject(context.getResources().openRawResource(R.raw.qd1_1));
        switch (code) {
            case D1:
            case D2:
            case D3:
            case D4:
            case D5:
            case D6:
            case D7:
            case D8:
            case D9:
            case D10:
            case D11:
            case D12:
            case D13:
                return maxNeq(code, jsonObject, distance, BELOW_D13);
            case D14:
            case D15:
            case D16:
            case D17:
            case D18:
                return maxNeq(code, jsonObject, distance, ABOVE_D13);
            default:
                return 0;
        }
    }

    /*
     * A function to calculate the max neq from a given distance from a defined code.
     * */
    private static int maxNeq(String column, JSONObject jsonObject, int distance, String table) {
        int maxNEQ = 0;
        try {
            JSONArray array = jsonObject.getJSONArray(column);
            JSONArray neqBelowD13 = jsonObject.getJSONArray(table);

            for (int i = array.length() - 1; i >= 0; i--) {
                int distances = array.getInt(i);
                if (distance < distances && distances != 0) {

                } else if (i == 0) {
                    return neqBelowD13.getInt(array.length() - 1);
                } else {
                    if (distance == distances) {
                        return neqBelowD13.getInt(i);
                    } else if (i < array.length() - 1) {
                        return neqBelowD13.getInt(i + 1);
                    } else {
                        return neqBelowD13.getInt(array.length() - 1);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maxNEQ;
    }

    public static String getQDCode(Context context, int pes, int es) throws JSONException {

        JSONObject jsonObject = null;
        try {
            jsonObject = getJSONObject(context.getResources().openRawResource(R.raw.hd1_1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert jsonObject != null;
        JSONArray hdArray = jsonObject.getJSONArray(HD_CODE);
        JSONObject pesS = hdArray.getJSONObject(0);
        switch (pes) {
            case 0:
                return getCode(pesS, "a", es);
            case 1:
                return getCode(pesS, "b", es);
            case 2:
                return getCode(pesS, "c", es);
            case 3:
                return getCode(pesS, "d", es);
            case 4:
                return getCode(pesS, "e", es);
            case 5:
                return getCode(pesS, "f", es);
            case 6:
                return getCode(pesS, "g", es);
            case 7:
                return getCode(pesS, "h", es);
            case 8:
                return getCode(pesS, "i", es);
            case 9:
                return getCode(pesS, "j", es);
            default:
                return  "D13";

        }
    }

    private static String getCode(JSONObject pes, String letter, int es){
        try {
            JSONArray mPes = pes.getJSONArray(PES_START + letter);
            return mPes.getString(es);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "D13";
    }

    private static JSONObject getJSONObject(InputStream inputStream) throws IOException, JSONException {

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        return new JSONObject(responseStrBuilder.toString());
    }

}
