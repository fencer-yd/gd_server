package com.ngj.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * Created by guanxinquan on 16/3/1.
 */
public class TicketUtil {
    private final static String KEY = "((*!feew12@#%$JU78";

    private static final String T_VERSION = "B";

    public static String ENCODING_TYPE = "UTF-8";

    public static String generateTicket(String info) {
        if (StringUtils.isEmpty(info)) {
            return null;
        }

        long now = System.currentTimeMillis();
        String mix = mixContent(T_VERSION + now, info + "");

        String ticket = null;
        try {
            ticket = T_VERSION + encrypt(mix, KEY);
            ticket = ticket.replace("\n", "").replace("\r", "").replace("/", "-").replace("+", "_")
                    .replace("=", ".");
        } catch (Exception e) {
        }

        return ticket;
    }

    public static String decryptTicket(String ticket) {
        String ret = null;
        if (StringUtils.isEmpty(ticket)) {
            return ret;
        }
        try {
            ticket = ticket.substring(1);
            ticket = ticket.replace("-", "/").replace("_", "+").replace(".", "=");
            String dStr = decrypt(ticket, KEY);
            ret = getUserIdFromMix(dStr);
        } catch (Exception e) {
//	            logger.error("decryptTicket", e);
	           /* if (logger.isDebugEnabled()) {
	                logger.debug("decryptTicket", e);
	            }*/
        }
        return ret;
    }

    private static String mixContent(String maskStr, String uidStr) {
        int maskIdx = 0;
        try {
            byte[] maskBytes = maskStr.getBytes(ENCODING_TYPE);
            byte[] uidBytes = uidStr.getBytes(ENCODING_TYPE);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            for (int i = 0; i < uidBytes.length; i++) {
                maskIdx = maskIdx % maskBytes.length;
                byteOut.write(uidBytes[i]);
                byteOut.write(maskBytes[maskIdx++]);
            }
            return new String(byteOut.toByteArray(), ENCODING_TYPE);
        } catch (UnsupportedEncodingException e) {
	           /* logger.error("", e);*/
        }

        return null;
    }

    private static String getUserIdFromMix(String mix) {
        try {
            byte[] mixBytes = mix.getBytes(ENCODING_TYPE);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            for (int i = 0; i < mixBytes.length; i++) {
                if (i % 2 == 0) {
                    byteOut.write(mixBytes[i]);
                }
            }
            return new String(byteOut.toByteArray(), ENCODING_TYPE);
        } catch (UnsupportedEncodingException e) {
	           /* logger.error("", e);*/
        }

        return "";
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
	           /* logger.error("serialize", e);*/
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
	               /* logger.error("serialize", e);*/
            }
        }
        return null;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


    /**
     * encrypt
     *
     * @param txt
     * @param key
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static byte[] encrypt(byte[] txt, byte[] key) throws UnsupportedEncodingException {
        int rand = new Double(Math.random() * 32000).intValue();
        byte[] encrypt_key = DigestUtils.md5Hex(rand + "").toLowerCase().getBytes(ENCODING_TYPE);

        byte ctr = 0;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        for (int i = 0; i < txt.length; i++) {
            ctr = ctr == encrypt_key.length ? 0 : ctr;
            byteOut.write(encrypt_key[ctr]);
            byteOut.write(txt[i] ^ encrypt_key[ctr++]);
        }
        return Base64.encodeBase64String(encodeKey(byteOut.toByteArray(), key)).getBytes(ENCODING_TYPE);
    }

    public static String encrypt(String txt, String key) {
        try {
            return new String(encrypt(txt.getBytes(ENCODING_TYPE), key.getBytes(ENCODING_TYPE)));
        } catch (UnsupportedEncodingException e) {
            // logger.error("", e);
            return null;
        }
    }

    /**
     * decrypt
     *
     * @param txt
     * @param key
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static byte[] decrypt(byte[] txt, byte[] key) throws UnsupportedEncodingException {
        txt = encodeKey(Base64.decodeBase64(txt), key);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        for (int i = 0; i < txt.length; i++) {
            byte md5 = txt[i];
            byteOut.write(txt[++i] ^ md5);
        }
        return byteOut.toByteArray();
    }

    /**
     *
     * @param txt
     * @param key
     * @return
     */
    public static String decrypt(String txt, String key) {
        try {
            return new String(decrypt(txt.getBytes(ENCODING_TYPE), key.getBytes(ENCODING_TYPE)));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     *
     * @param txt
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static byte[] encodeKey(byte[] txt, byte[] encrypt_key) throws UnsupportedEncodingException {

        encrypt_key = DigestUtils.md5Hex(new String(encrypt_key)).toLowerCase().getBytes(ENCODING_TYPE);

        byte ctr = 0;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        for (int i = 0; i < txt.length; i++) {
            ctr = ctr == encrypt_key.length ? 0 : ctr;
            byteOut.write(txt[i] ^ encrypt_key[ctr++]);
        }
        return byteOut.toByteArray();
    }
}
