package com.alipay.mobile.nebula.util;

public final class H5Base64 {
    private static final int BASELENGTH = 128;
    private static final int EIGHTBIT = 8;
    private static final int FOURBYTE = 4;
    private static final int LOOKUPLENGTH = 64;
    private static final char PAD = '=';
    private static final int SIGN = -128;
    private static final int SIXTEENBIT = 16;
    private static final int TWENTYFOURBITGROUP = 24;
    private static final byte[] base64Alphabet = new byte[128];
    private static final char[] lookUpBase64Alphabet = new char[64];

    static {
        for (int i = 0; i < 128; i++) {
            base64Alphabet[i] = -1;
        }
        for (int i2 = 90; i2 >= 65; i2--) {
            base64Alphabet[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 122; i3 >= 97; i3--) {
            base64Alphabet[i3] = (byte) ((i3 - 97) + 26);
        }
        for (int i4 = 57; i4 >= 48; i4--) {
            base64Alphabet[i4] = (byte) ((i4 - 48) + 52);
        }
        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;
        for (int i5 = 0; i5 <= 25; i5++) {
            lookUpBase64Alphabet[i5] = (char) (i5 + 65);
        }
        int i6 = 26;
        int j = 0;
        while (i6 <= 51) {
            lookUpBase64Alphabet[i6] = (char) (j + 97);
            i6++;
            j++;
        }
        int i7 = 52;
        int j2 = 0;
        while (i7 <= 61) {
            lookUpBase64Alphabet[i7] = (char) (j2 + 48);
            i7++;
            j2++;
        }
        lookUpBase64Alphabet[62] = '+';
        lookUpBase64Alphabet[63] = '/';
    }

    private static boolean isWhiteSpace(char octect) {
        return octect == ' ' || octect == 13 || octect == 10 || octect == 9;
    }

    private static boolean isPad(char octect) {
        return octect == '=';
    }

    private static boolean isData(char octect) {
        return octect < 128 && base64Alphabet[octect] != -1;
    }

    public static String encode(byte[] binaryData) {
        int i;
        if (binaryData == null) {
            return null;
        }
        int lengthDataBits = binaryData.length * 8;
        if (lengthDataBits == 0) {
            return "";
        }
        int fewerThan24bits = lengthDataBits % 24;
        int numberTriplets = lengthDataBits / 24;
        if (fewerThan24bits != 0) {
            i = numberTriplets + 1;
        } else {
            i = numberTriplets;
        }
        char[] encodedData = new char[(i * 4)];
        int i2 = 0;
        int dataIndex = 0;
        int encodedIndex = 0;
        while (i2 < numberTriplets) {
            int dataIndex2 = dataIndex + 1;
            byte b1 = binaryData[dataIndex];
            int dataIndex3 = dataIndex2 + 1;
            byte b2 = binaryData[dataIndex2];
            int dataIndex4 = dataIndex3 + 1;
            byte b3 = binaryData[dataIndex3];
            byte l = (byte) (b2 & 15);
            byte k = (byte) (b1 & 3);
            byte val1 = (b1 & Byte.MIN_VALUE) == 0 ? (byte) (b1 >> 2) : (byte) ((b1 >> 2) ^ 192);
            byte val2 = (b2 & Byte.MIN_VALUE) == 0 ? (byte) (b2 >> 4) : (byte) ((b2 >> 4) ^ 240);
            int i3 = (b3 & Byte.MIN_VALUE) == 0 ? b3 >> 6 : (b3 >> 6) ^ 252;
            int encodedIndex2 = encodedIndex + 1;
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            int encodedIndex3 = encodedIndex2 + 1;
            encodedData[encodedIndex2] = lookUpBase64Alphabet[(k << 4) | val2];
            int encodedIndex4 = encodedIndex3 + 1;
            encodedData[encodedIndex3] = lookUpBase64Alphabet[(l << 2) | ((byte) i3)];
            encodedIndex = encodedIndex4 + 1;
            encodedData[encodedIndex4] = lookUpBase64Alphabet[b3 & 63];
            i2++;
            dataIndex = dataIndex4;
        }
        if (fewerThan24bits == 8) {
            byte b12 = binaryData[dataIndex];
            byte k2 = (byte) (b12 & 3);
            int encodedIndex5 = encodedIndex + 1;
            encodedData[encodedIndex] = lookUpBase64Alphabet[(b12 & Byte.MIN_VALUE) == 0 ? (byte) (b12 >> 2) : (byte) ((b12 >> 2) ^ 192)];
            int encodedIndex6 = encodedIndex5 + 1;
            encodedData[encodedIndex5] = lookUpBase64Alphabet[k2 << 4];
            encodedData[encodedIndex6] = PAD;
            encodedData[encodedIndex6 + 1] = PAD;
        } else if (fewerThan24bits == 16) {
            byte b13 = binaryData[dataIndex];
            byte b22 = binaryData[dataIndex + 1];
            byte l2 = (byte) (b22 & 15);
            byte k3 = (byte) (b13 & 3);
            byte val12 = (b13 & Byte.MIN_VALUE) == 0 ? (byte) (b13 >> 2) : (byte) ((b13 >> 2) ^ 192);
            byte val22 = (b22 & Byte.MIN_VALUE) == 0 ? (byte) (b22 >> 4) : (byte) ((b22 >> 4) ^ 240);
            int encodedIndex7 = encodedIndex + 1;
            encodedData[encodedIndex] = lookUpBase64Alphabet[val12];
            int encodedIndex8 = encodedIndex7 + 1;
            encodedData[encodedIndex7] = lookUpBase64Alphabet[(k3 << 4) | val22];
            encodedData[encodedIndex8] = lookUpBase64Alphabet[l2 << 2];
            encodedData[encodedIndex8 + 1] = PAD;
        } else {
            int i4 = encodedIndex;
        }
        String str = new String(encodedData);
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005f, code lost:
        r13 = r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r25) {
        /*
            if (r25 != 0) goto L_0x0005
            r21 = 0
        L_0x0004:
            return r21
        L_0x0005:
            char[] r8 = r25.toCharArray()
            int r19 = removeWhiteSpace(r8)
            int r22 = r19 % 4
            if (r22 == 0) goto L_0x0014
            r21 = 0
            goto L_0x0004
        L_0x0014:
            int r20 = r19 / 4
            if (r20 != 0) goto L_0x0021
            r22 = 0
            r0 = r22
            byte[] r0 = new byte[r0]
            r21 = r0
            goto L_0x0004
        L_0x0021:
            r18 = 0
            r16 = 0
            r13 = 0
            int r22 = r20 * 3
            r0 = r22
            byte[] r15 = new byte[r0]
            r14 = r13
            r17 = r16
        L_0x002f:
            int r22 = r20 + -1
            r0 = r18
            r1 = r22
            if (r0 >= r1) goto L_0x00a7
            int r13 = r14 + 1
            char r9 = r8[r14]
            boolean r22 = isData(r9)
            if (r22 == 0) goto L_0x0060
            int r14 = r13 + 1
            char r10 = r8[r13]
            boolean r22 = isData(r10)
            if (r22 == 0) goto L_0x005f
            int r13 = r14 + 1
            char r11 = r8[r14]
            boolean r22 = isData(r11)
            if (r22 == 0) goto L_0x0060
            int r14 = r13 + 1
            char r12 = r8[r13]
            boolean r22 = isData(r12)
            if (r22 != 0) goto L_0x0063
        L_0x005f:
            r13 = r14
        L_0x0060:
            r21 = 0
            goto L_0x0004
        L_0x0063:
            byte[] r22 = base64Alphabet
            byte r4 = r22[r9]
            byte[] r22 = base64Alphabet
            byte r5 = r22[r10]
            byte[] r22 = base64Alphabet
            byte r6 = r22[r11]
            byte[] r22 = base64Alphabet
            byte r7 = r22[r12]
            int r16 = r17 + 1
            int r22 = r4 << 2
            int r23 = r5 >> 4
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r17] = r22
            int r17 = r16 + 1
            r22 = r5 & 15
            int r22 = r22 << 4
            int r23 = r6 >> 2
            r23 = r23 & 15
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r16] = r22
            int r16 = r17 + 1
            int r22 = r6 << 6
            r22 = r22 | r7
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r17] = r22
            int r18 = r18 + 1
            r17 = r16
            goto L_0x002f
        L_0x00a7:
            int r13 = r14 + 1
            char r9 = r8[r14]
            boolean r22 = isData(r9)
            if (r22 == 0) goto L_0x00bc
            int r14 = r13 + 1
            char r10 = r8[r13]
            boolean r22 = isData(r10)
            if (r22 != 0) goto L_0x00c0
            r13 = r14
        L_0x00bc:
            r21 = 0
            goto L_0x0004
        L_0x00c0:
            byte[] r22 = base64Alphabet
            byte r4 = r22[r9]
            byte[] r22 = base64Alphabet
            byte r5 = r22[r10]
            int r13 = r14 + 1
            char r11 = r8[r14]
            char r12 = r8[r13]
            boolean r22 = isData(r11)
            if (r22 == 0) goto L_0x00da
            boolean r22 = isData(r12)
            if (r22 != 0) goto L_0x0171
        L_0x00da:
            boolean r22 = isPad(r11)
            if (r22 == 0) goto L_0x0118
            boolean r22 = isPad(r12)
            if (r22 == 0) goto L_0x0118
            r22 = r5 & 15
            if (r22 == 0) goto L_0x00ee
            r21 = 0
            goto L_0x0004
        L_0x00ee:
            int r22 = r18 * 3
            int r22 = r22 + 1
            r0 = r22
            byte[] r0 = new byte[r0]
            r21 = r0
            r22 = 0
            r23 = 0
            int r24 = r18 * 3
            r0 = r22
            r1 = r21
            r2 = r23
            r3 = r24
            java.lang.System.arraycopy(r15, r0, r1, r2, r3)
            int r22 = r4 << 2
            int r23 = r5 >> 4
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r21[r17] = r22
            goto L_0x0004
        L_0x0118:
            boolean r22 = isPad(r11)
            if (r22 != 0) goto L_0x016d
            boolean r22 = isPad(r12)
            if (r22 == 0) goto L_0x016d
            byte[] r22 = base64Alphabet
            byte r6 = r22[r11]
            r22 = r6 & 3
            if (r22 == 0) goto L_0x0130
            r21 = 0
            goto L_0x0004
        L_0x0130:
            int r22 = r18 * 3
            int r22 = r22 + 2
            r0 = r22
            byte[] r0 = new byte[r0]
            r21 = r0
            r22 = 0
            r23 = 0
            int r24 = r18 * 3
            r0 = r22
            r1 = r21
            r2 = r23
            r3 = r24
            java.lang.System.arraycopy(r15, r0, r1, r2, r3)
            int r16 = r17 + 1
            int r22 = r4 << 2
            int r23 = r5 >> 4
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r21[r17] = r22
            r22 = r5 & 15
            int r22 = r22 << 4
            int r23 = r6 >> 2
            r23 = r23 & 15
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r21[r16] = r22
            goto L_0x0004
        L_0x016d:
            r21 = 0
            goto L_0x0004
        L_0x0171:
            byte[] r22 = base64Alphabet
            byte r6 = r22[r11]
            byte[] r22 = base64Alphabet
            byte r7 = r22[r12]
            int r16 = r17 + 1
            int r22 = r4 << 2
            int r23 = r5 >> 4
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r17] = r22
            int r17 = r16 + 1
            r22 = r5 & 15
            int r22 = r22 << 4
            int r23 = r6 >> 2
            r23 = r23 & 15
            r22 = r22 | r23
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r16] = r22
            int r22 = r6 << 6
            r22 = r22 | r7
            r0 = r22
            byte r0 = (byte) r0
            r22 = r0
            r15[r17] = r22
            r21 = r15
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5Base64.decode(java.lang.String):byte[]");
    }

    private static int removeWhiteSpace(char[] data) {
        int newSize;
        if (data == null) {
            return 0;
        }
        int len = data.length;
        int i = 0;
        int newSize2 = 0;
        while (i < len) {
            if (!isWhiteSpace(data[i])) {
                newSize = newSize2 + 1;
                data[newSize2] = data[i];
            } else {
                newSize = newSize2;
            }
            i++;
            newSize2 = newSize;
        }
        return newSize2;
    }
}
