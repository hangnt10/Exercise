package Utilities;

import Functions.Channel;
import Functions.HiritOrGood;
import Functions.CheckTarget;
import Word.ReportToWord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sikuli.script.*;
import org.sikuli.util.Run;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Client {
    public Channel channel;
    public HiritOrGood isGood;
    public CheckTarget checkTarget;
    public ReportToWord wordReport;


    public String title;
    public float width;
    public float height;

    public App app;
    public Region window;

    public AdbLog adbLog;
    //    public String adbAddress;
    public static final String imgPath = System.getProperty("user.dir") + "\\Image\\";
    public static JSONObject config = Json.read(System.getProperty("user.dir") + "\\Config\\config.json");

    public float screenWidth = config.getFloat("screenWidth");
    public float screenHeight = config.getFloat("screenHeight");

    private static final int topBarHeight = 35;


    /**
     * @param title title of app
     */
    public Client(String title) throws IOException {
        this.title = title;
        this.app = new App(title);
        if (app.window(0) == null) return;
        Region window1 = app.window(0);
        this.window = new Region((window1.x), window1.y + topBarHeight, window1.w - topBarHeight, window1.h - topBarHeight);
        this.width = window.w;
        this.height = window.h;
        this.window.highlight(1);

        JSONObject adbObject = config.getJSONObject(title);
        if (adbObject == null)// EM CHAY DI // co ve oki rui anh =)) .. anh co dang noi khong the? Anh dannoi ma thoi dneo  cehmay   kiem tra lai tai nghe, k nghe th atyho i thoi no chay dc la oke roi em. ANh out nhe.
//            tu tu anh oi em check not cai nay dakook
            // khong chay noi,
            return;

        String adbAddress = adbObject.getString("adbAddress");// adbAddress: khong co dinh
        final String[] POSSIBLE_ADB_ADDRESSES = new String[]{adbAddress, "127.0.0.1:5555", "127.0.0.1:5557"};
        for (String ADB_ADDRESS : POSSIBLE_ADB_ADDRESSES)
            if (ADB_ADDRESS != null) {
                String[] command = new String[]{"adb", "-s", ADB_ADDRESS, "logcat", "-t", "50000"};
                Process p = Runtime.getRuntime().exec(command);

                if (p != null) {
                    System.out.println("Executed " + command + " successfully");
                    break;
                } else {
                    System.out.println("Executed " + command + " fail");
                }

            }

        adbLog = new AdbLog(adbAddress);
        this.channel = new Channel(this);
        this.isGood = new HiritOrGood(this);
        this.checkTarget = new CheckTarget(this);
        this.wordReport = new ReportToWord(this);
    }


    /**
     * Calculate absolute location
     *
     * @param location relative location
     * @return absolute location
     */
    public Location calLocation(Location location) {
        return new Location(location.x + window.x, location.y + window.y);
    }

    private Location convertNodeLocation(Node node) {
        return calLocation(new Location(node.x * width / screenWidth, (screenHeight - node.y) * height / screenHeight));
    }

    public boolean refreshLog() throws Exception {
        window.click(imgPath + "cheatIcon.png");
        Thread.sleep(100);
        return true;
    }


    public JSONObject getUserInfo() throws Exception {
        JSONObject userInfo = null;
        refreshLog();
        try {
            userInfo = adbLog.getUserInfo();
        } catch (JSONException e) {
            System.out.println("JSONException");
            Thread.sleep(500);
            userInfo = getUserInfo();
        } catch (StringIndexOutOfBoundsException e) {
//            e.printStackTrace();
            System.out.println("String index out of bounds exception");
            Thread.sleep(500);
            userInfo = getUserInfo();
        }
        return userInfo;
    }

    public JSONArray getPositionLog() throws Exception {
        app.focus();
        JSONArray positionLog = null;
        refreshLog();
        try {
            positionLog = adbLog.getPositionLog();
        } catch (JSONException e) {
            System.out.println("JSONException");
            Thread.sleep(500);
            positionLog = getPositionLog();
        } catch (StringIndexOutOfBoundsException e) {
//            e.printStackTrace();
            System.out.println("String index out of bounds exception");
            Thread.sleep(500);
            positionLog = getPositionLog();
        }
        return positionLog;
    }

    public int getUserGold() throws Exception {
        int gold = 0;
        gold = adbLog.getUserInfo().getInt("gold");
        return gold;
    }

//    public boolean checkTarget(int targetGold) throws Exception{
//        if(this.getUserGold() != targetGold){
//            return false;
//        }
//        return true;
//    }

    public void capScreen(String bugName) throws Exception {

//        return new Screen().capture(this.window);

        try {
            String captureCommand = "adb shell screencap /sdcard/" + bugName + ".png";
            Runtime.getRuntime().exec(captureCommand);
            System.out.println("Requested to execute [" + captureCommand + "]");
            String out = "C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs\\" + bugName + ".png";

            while (!new File(out).exists()) {
                System.out.println("Waiting for 5 seconds");
                Thread.sleep(5000);
                Runtime.getRuntime().exec("adb pull /sdcard/" + bugName + ".png " + out);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //adb pull /sdcard/bug.png C:\\Users\\LAP60536_Local\\Downloads\\Exercise-main\\Exercise-main\\Bugs
        }
    }

    public void click(ArrayList<String> filter) throws Exception {
        app.focus();
        JSONArray positionLog = getPositionLog();
        JSONObject jsonObject = Json.findJsonObjectByFilter(positionLog, filter);
        assert jsonObject != null;
        Node node = new Node(jsonObject);
        System.out.println("Relative location: " + node.x + " " + node.y);
        this.window.click(convertNodeLocation(node));
    }

    public void click(String name) throws Exception {
        ArrayList<String> filter = new ArrayList<>();
        filter.add("name:" + name);
        click(filter);
    }


    public void type(String text) {
//      window.type(Key.BACKSPACE);
        window.type(text);
    }

    public void openGame() throws Exception {
        this.app.focus();
        window.wait(imgPath + "luckyNineIcon.png", Double.POSITIVE_INFINITY);
        window.click(imgPath + "luckyNineIcon.png");
    }

    public void login(String id) throws Exception {
        window.wait(imgPath + "loginOptions.png", Double.POSITIVE_INFINITY);
        Thread.sleep(2000);
        this.click("btn_ZID");
        Thread.sleep(100);
        this.click("sprite_Name");
        Thread.sleep(100);
        this.type(id);
        Thread.sleep(100);
        this.click("btn_Ok");
        this.refreshLog();
    }

    public void closeGame() throws Exception {
//        window.wait(imgPath + "back.png", Double.POSITIVE_INFINITY);
//        window.wait(imgPath + "back.png");
        Thread.sleep(1000);
        Runtime.getRuntime().exec(getBack());
        window.wait(imgPath + "closeGame.png", Double.POSITIVE_INFINITY);
        window.click(imgPath + "closeGame.png");
        window.click(imgPath + "btn_Ok.png");
        this.refreshLog();
    }


    private String getBack() {
        return "adb shell input keyevent 4";
    }

    public void clickGiftCode(String giftCode) throws Exception {
        window.wait(imgPath + "enterGiftcode.png", Double.POSITIVE_INFINITY);
        window.click(imgPath + "enterGiftcode.png");
        window.click(imgPath + "touchinput.png");
        this.type(giftCode);
        window.click(imgPath + "claim.png");
        window.wait(imgPath + "congratScreen.png", Double.POSITIVE_INFINITY);
        window.click(imgPath + "receive.png");
    }

    //chọn trực tiếp icon GiftCode tại màn hình lobby
    public void enterGiftCode_directly(String giftCode) throws Exception {
        window.wait(imgPath + "giftcode.png", Double.POSITIVE_INFINITY);
        this.window.click(imgPath + "giftcode.png");
        clickGiftCode(giftCode);
        this.refreshLog();
    }

    // chọn GiftCode tại màn hình Settings
    public void enterGiftCode_settings(String giftCode) throws Exception {
        window.wait(imgPath + "settings.png", Double.POSITIVE_INFINITY);
        this.window.click(imgPath + "settings.png");
        this.click("btn_GiftCode");
        clickGiftCode(giftCode);
        this.refreshLog();
    }

    public String checkCode(String code) throws Exception {
        String result = "";

        return result;
    }
}
