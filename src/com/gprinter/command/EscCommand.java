//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gprinter.command;

import com.gprinter.command.TscCommand.FOOT;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class EscCommand {
    private static final String DEBUG_TAG = "EscCommand";
    Vector<Byte> Command = null;

    public EscCommand() {
        this.Command = new Vector(4096, 1024);
    }

    private void addArrayToCommand(byte[] array) {
        for(int i = 0; i < array.length; ++i) {
            this.Command.add(array[i]);
        }

    }

    private void addStrToCommand(String str) {
        byte[] bs = null;
        if(!str.equals("")) {
            try {
                bs = str.getBytes("GB2312");
            } catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }

            for(int i = 0; i < bs.length; ++i) {
                this.Command.add(bs[i]);
            }
        }

    }

    private void addStrToCommand(String str, String charset) {
        byte[] bs = null;
        if(!str.equals("")) {
            try {
                bs = str.getBytes(charset);
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }

            for(int i = 0; i < bs.length; ++i) {
                this.Command.add(bs[i]);
            }
        }

    }

    private void addStrToCommand(String str, int length) {
        byte[] bs = null;
        if(!str.equals("")) {
            try {
                bs = str.getBytes("GB2312");
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }

            if(length > bs.length) {
                length = bs.length;
            }

            for(int i = 0; i < length; ++i) {
                this.Command.add(bs[i]);
            }
        }

    }

    public void addHorTab() {
        byte[] command = new byte[]{(byte)9};
        this.addArrayToCommand(command);
    }

    public void addText(String text) {
        this.addStrToCommand(text);
    }

    public void addText(String text, String charsetName) {
        this.addStrToCommand(text, charsetName);
    }

    public void addPrintAndLineFeed() {
        byte[] command = new byte[]{(byte)10};
        this.addArrayToCommand(command);
    }

    public void RealtimeStatusTransmission(EscCommand.STATUS status) {
        byte[] command = new byte[]{(byte)16, (byte)4, status.getValue()};
        this.addArrayToCommand(command);
    }

    public void addGeneratePluseAtRealtime(FOOT foot, byte t) {
        byte[] command = new byte[]{(byte)16, (byte)20, (byte)1, (byte)foot.getValue(), (byte)0};
        if(t > 8) {
            t = 8;
        }

        command[4] = t;
        this.addArrayToCommand(command);
    }

    public void addSetRightSideCharacterSpacing(byte n) {
        byte[] command = new byte[]{(byte)27, (byte)32, n};
        this.addArrayToCommand(command);
    }

    public Vector<Byte> getCommand() {
        return this.Command;
    }

    public void addSelectPrintModes(EscCommand.FONT font, EscCommand.ENABLE emphasized, EscCommand.ENABLE doubleheight, EscCommand.ENABLE doublewidth, EscCommand.ENABLE underline) {
        byte temp = 0;
        if(font == EscCommand.FONT.FONTB) {
            temp = 1;
        }

        if(emphasized == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 8);
        }

        if(doubleheight == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 16);
        }

        if(doublewidth == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 32);
        }

        if(underline == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 128);
        }

        byte[] command = new byte[]{(byte)27, (byte)33, temp};
        this.addArrayToCommand(command);
    }

    public void addSetAbsolutePrintPosition(short n) {
        byte[] command = new byte[]{(byte)27, (byte)36, (byte)0, (byte)0};
        byte nl = (byte)(n % 256);
        byte nh = (byte)(n / 256);
        command[2] = nl;
        command[3] = nh;
        this.addArrayToCommand(command);
    }

    public void addSelectOrCancelUserDefineCharacter(EscCommand.ENABLE enable) {
        byte[] command = new byte[]{(byte)27, (byte)37, (byte)0};
        if(enable == EscCommand.ENABLE.ON) {
            command[2] = 1;
        } else {
            command[2] = 0;
        }

        this.addArrayToCommand(command);
    }

    public void addTurnUnderlineModeOnOrOff(EscCommand.UNDERLINE_MODE underline) {
        byte[] command = new byte[]{(byte)27, (byte)45, underline.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSelectDefualtLineSpacing() {
        byte[] command = new byte[]{(byte)27, (byte)50};
        this.addArrayToCommand(command);
    }

    public void addSetLineSpacing(byte n) {
        byte[] command = new byte[]{(byte)27, (byte)51, n};
        this.addArrayToCommand(command);
    }

    public void addCancelUserDefinedCharacters(byte n) {
        byte[] command = new byte[]{(byte)27, (byte)63, (byte)0};
        if(n >= 32 && n <= 126) {
            command[2] = n;
        } else {
            command[2] = 32;
        }

        this.addArrayToCommand(command);
    }

    public void addInitializePrinter() {
        byte[] command = new byte[]{(byte)27, (byte)64};
        this.addArrayToCommand(command);
    }

    public void addTurnEmphasizedModeOnOrOff(EscCommand.ENABLE enabel) {
        byte[] command = new byte[]{(byte)27, (byte)69, enabel.getValue()};
        this.addArrayToCommand(command);
    }

    public void addTurnDoubleStrikeOnOrOff(EscCommand.ENABLE enabel) {
        byte[] command = new byte[]{(byte)27, (byte)71, enabel.getValue()};
        this.addArrayToCommand(command);
    }

    public void addPrintAndFeedPaper(byte n) {
        byte[] command = new byte[]{(byte)27, (byte)74, n};
        this.addArrayToCommand(command);
    }

    public void addSelectCharacterFont(EscCommand.FONT font) {
        byte[] command = new byte[]{(byte)27, (byte)77, font.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSelectInternationalCharacterSet(EscCommand.CHARACTER_SET set) {
        byte[] command = new byte[]{(byte)27, (byte)82, set.getValue()};
        this.addArrayToCommand(command);
    }

    public void addTurn90ClockWiseRotatin(EscCommand.ENABLE enabel) {
        byte[] command = new byte[]{(byte)27, (byte)86, enabel.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSetRelativePrintPositon(short n) {
        byte[] command = new byte[]{(byte)27, (byte)92, (byte)0, (byte)0};
        byte nl = (byte)(n % 256);
        byte nh = (byte)(n / 256);
        command[2] = nl;
        command[3] = nh;
        this.addArrayToCommand(command);
    }

    public void addSelectJustification(EscCommand.JUSTIFICATION just) {
        byte[] command = new byte[]{(byte)27, (byte)97, just.getValue()};
        this.addArrayToCommand(command);
    }

    public void addPrintAndFeedLines(byte n) {
        byte[] command = new byte[]{(byte)27, (byte)100, n};
        this.addArrayToCommand(command);
    }

    public void addGeneratePlus(FOOT foot, byte t1, byte t2) {
        byte[] command = new byte[]{(byte)27, (byte)112, (byte)foot.getValue(), t1, t2};
        this.addArrayToCommand(command);
    }

    public void addSelectCodePage(EscCommand.CODEPAGE page) {
        byte[] command = new byte[]{(byte)27, (byte)116, page.getValue()};
        this.addArrayToCommand(command);
    }

    public void addTurnUpsideDownModeOnOrOff(EscCommand.ENABLE enable) {
        byte[] command = new byte[]{(byte)27, (byte)123, enable.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSetCharcterSize(EscCommand.WIDTH_ZOOM width, EscCommand.HEIGHT_ZOOM height) {
        byte[] command = new byte[]{(byte)29, (byte)33, (byte)0};
        byte temp = 0;
        byte temp1 = (byte)(temp | width.getValue());
        temp1 |= height.getValue();
        command[2] = temp1;
        this.addArrayToCommand(command);
    }

    public void addTurnReverseModeOnOrOff(EscCommand.ENABLE enable) {
        byte[] command = new byte[]{(byte)29, (byte)66, enable.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSelectPrintingPositionForHRICharacters(EscCommand.HRI_POSITION position) {
        byte[] command = new byte[]{(byte)29, (byte)72, position.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSetLeftMargin(short n) {
        byte[] command = new byte[]{(byte)29, (byte)76, (byte)0, (byte)0};
        byte nl = (byte)(n % 256);
        byte nh = (byte)(n / 256);
        command[2] = nl;
        command[3] = nh;
        this.addArrayToCommand(command);
    }

    public void addSetHorAndVerMotionUnits(byte x, byte y) {
        byte[] command = new byte[]{(byte)29, (byte)80, x, y};
        this.addArrayToCommand(command);
    }

    public void addCutAndFeedPaper(byte length) {
        byte[] command = new byte[]{(byte)29, (byte)86, (byte)66, length};
        this.addArrayToCommand(command);
    }

    public void addCutPaper() {
        byte[] command = new byte[]{(byte)29, (byte)86, (byte)1};
        this.addArrayToCommand(command);
    }

    public void addSetPrintingAreaWidth(short width) {
        byte nl = (byte)(width % 256);
        byte nh = (byte)(width / 256);
        byte[] command = new byte[]{(byte)29, (byte)87, nl, nh};
        this.addArrayToCommand(command);
    }

    public void addSetAutoSatusBack(EscCommand.ENABLE enable) {
        byte[] command = new byte[]{(byte)29, (byte)97, (byte)0};
        if(enable == EscCommand.ENABLE.OFF) {
            command[2] = 0;
        } else {
            command[2] = -1;
        }

        this.addArrayToCommand(command);
    }

    public void addSetFontForHRICharacter(EscCommand.FONT font) {
        byte[] command = new byte[]{(byte)29, (byte)102, font.getValue()};
        this.addArrayToCommand(command);
    }

    public void addSetBarcodeHeight(byte height) {
        byte[] command = new byte[]{(byte)29, (byte)104, height};
        this.addArrayToCommand(command);
    }

    public void addSetBarcodeWidth(byte width) {
        byte[] command = new byte[]{(byte)29, (byte)119, (byte)0};
        if(width > 6) {
            width = 6;
        }

        if(width < 2) {
            width = 2;
        }

        command[2] = width;
        this.addArrayToCommand(command);
    }

    public void addSetKanjiFontMode(EscCommand.ENABLE DoubleWidth, EscCommand.ENABLE DoubleHeight, EscCommand.ENABLE Underline) {
        byte[] command = new byte[]{(byte)28, (byte)33, (byte)0};
        byte temp = 0;
        if(DoubleWidth == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 4);
        }

        if(DoubleHeight == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 8);
        }

        if(Underline == EscCommand.ENABLE.ON) {
            temp = (byte)(temp | 128);
        }

        command[2] = temp;
        this.addArrayToCommand(command);
    }

    public void addSelectKanjiMode() {
        byte[] command = new byte[]{(byte)28, (byte)38};
        this.addArrayToCommand(command);
    }

    public void addSetKanjiUnderLine(EscCommand.UNDERLINE_MODE underline) {
        byte[] command = new byte[]{(byte)28, (byte)45, (byte)0};
        command[3] = underline.getValue();
        this.addArrayToCommand(command);
    }

    public void addCancelKanjiMode() {
        byte[] command = new byte[]{(byte)28, (byte)46};
        this.addArrayToCommand(command);
    }

    public void addSetKanjiLefttandRightSpace(byte left, byte right) {
        byte[] command = new byte[]{(byte)28, (byte)83, left, right};
        this.addArrayToCommand(command);
    }

    public void addSetQuadrupleModeForKanji(EscCommand.ENABLE enable) {
        byte[] command = new byte[]{(byte)28, (byte)87, enable.getValue()};
        this.addArrayToCommand(command);
    }

    public void addUPCA(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)65, (byte)11};
        if(content.length() >= command[3]) {
            this.addArrayToCommand(command);
            this.addStrToCommand(content, 11);
        }
    }

    public void addUPCE(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)66, (byte)11};
        if(content.length() >= command[3]) {
            this.addArrayToCommand(command);
            this.addStrToCommand(content, command[3]);
        }
    }

    public void addEAN13(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)67, (byte)12};
        if(content.length() >= command[3]) {
            this.addArrayToCommand(command);
            this.addStrToCommand(content, command[3]);
        }
    }

    public void addEAN8(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)68, (byte)7};
        if(content.length() >= command[3]) {
            this.addArrayToCommand(command);
            this.addStrToCommand(content, command[3]);
        }
    }

    public void addCODE39(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)69, (byte)content.length()};
        content = content.toUpperCase();
        this.addArrayToCommand(command);
        this.addStrToCommand(content, command[3]);
    }

    public void addITF(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)70, (byte)content.length()};
        this.addArrayToCommand(command);
        this.addStrToCommand(content, command[3]);
    }

    public void addCODABAR(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)71, (byte)content.length()};
        this.addArrayToCommand(command);
        this.addStrToCommand(content, command[3]);
    }

    public void addCODE93(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)72, (byte)content.length()};
        this.addArrayToCommand(command);
        this.addStrToCommand(content, command[3]);
    }

    public void addCODE128(String content) {
        byte[] command = new byte[]{(byte)29, (byte)107, (byte)73, (byte)content.length()};
        this.addArrayToCommand(command);
        this.addStrToCommand(content, command[3]);
    }

    public void addSelectSizeOfModuleForQRCode(byte n) {
        byte[] command = new byte[]{(byte)29, (byte)40, (byte)107, (byte)3, (byte)0, (byte)49, (byte)67, (byte)3};
        command[7] = n;
        this.addArrayToCommand(command);
    }

    public void addSelectErrorCorrectionLevelForQRCode(byte n) {
        byte[] command = new byte[]{(byte)29, (byte)40, (byte)107, (byte)3, (byte)0, (byte)49, (byte)69, n};
        this.addArrayToCommand(command);
    }

    public void addStoreQRCodeData(String content) {
        byte[] command = new byte[]{(byte)29, (byte)40, (byte)107, (byte)((content.length() + 3) % 256), (byte)((content.length() + 3) / 256), (byte)49, (byte)80, (byte)48};
        this.addArrayToCommand(command);
        this.addStrToCommand(content, content.length());
    }

    public void addPrintQRCode() {
        byte[] command = new byte[]{(byte)29, (byte)40, (byte)107, (byte)3, (byte)0, (byte)49, (byte)81, (byte)48};
        this.addArrayToCommand(command);
    }

    public void addUserCommand(byte[] command) {
        this.addArrayToCommand(command);
    }

    public static enum CHARACTER_SET {
        USA(0),
        FRANCE(1),
        GERMANY(2),
        UK(3),
        DENMARK_I(4),
        SWEDEN(5),
        ITALY(6),
        SPAIN_I(7),
        JAPAN(8),
        NORWAY(9),
        DENMARK_II(10),
        SPAIN_II(11),
        LATIN_AMERCIA(12),
        KOREAN(13),
        SLOVENIA(14),
        CHINA(15);

        private final int value;

        private CHARACTER_SET(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum CODEPAGE {
        PC437(0),
        KATAKANA(1),
        PC850(2),
        PC860(3),
        PC863(4),
        PC865(5),
        WEST_EUROPE(6),
        GREEK(7),
        HEBREW(8),
        EAST_EUROPE(9),
        IRAN(10),
        WPC1252(16),
        PC866(17),
        PC852(18),
        PC858(19),
        IRANII(20),
        LATVIAN(21),
        ARABIC(22),
        PT151(23),
        PC747(24),
        WPC1257(25),
        VIETNAM(27),
        PC864(28),
        PC1001(29),
        UYGUR(30),
        THAI(255);

        private final int value;

        private CODEPAGE(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum ENABLE {
        OFF(0),
        ON(1);

        private final int value;

        private ENABLE(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum FONT {
        FONTA(0),
        FONTB(1);

        private final int value;

        private FONT(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum HEIGHT_ZOOM {
        MUL_1(0),
        MUL_2(1),
        MUL_3(2),
        MUL_4(3),
        MUL_5(4),
        MUL_6(5),
        MUL_7(6),
        MUL_8(7);

        private final int value;

        private HEIGHT_ZOOM(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum HRI_POSITION {
        NO_PRINT(0),
        ABOVE(1),
        BELOW(2),
        ABOVE_AND_BELOW(3);

        private final int value;

        private HRI_POSITION(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum JUSTIFICATION {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        private final int value;

        private JUSTIFICATION(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum STATUS {
        PRINTER_STATUS(1),
        PRINTER_OFFLINE(2),
        PRINTER_ERROR(3),
        PRINTER_PAPER(4);

        private final int value;

        private STATUS(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum UNDERLINE_MODE {
        OFF(0),
        UNDERLINE_1DOT(1),
        UNDERLINE_2DOT(2);

        private final int value;

        private UNDERLINE_MODE(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }

    public static enum WIDTH_ZOOM {
        MUL_1(0),
        MUL_2(16),
        MUL_3(32),
        MUL_4(48),
        MUL_5(64),
        MUL_6(80),
        MUL_7(96),
        MUL_8(112);

        private final int value;

        private WIDTH_ZOOM(int value) {
            this.value = value;
        }

        public byte getValue() {
            return (byte)this.value;
        }
    }
}
