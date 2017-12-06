package secure.share;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/12/6.
 */
public class EncryBean {

    private String encryptKey = "";

    public EncryBean() {
    }

    public EncryBean(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    /**
     * AES128 加密
     *
     * @param plainText 明文
     * @return
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec());
            byte[] encryptBt = cipher.doFinal(plainText.getBytes("utf-8"));
            return bytesToHexString(encryptBt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES128 解密
     *
     * @param cipherText 密文
     * @return
     */
    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec());
            byte[] decryBt = cipher.doFinal(hexStringToBytes(cipherText));
            return new String(decryBt, "utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        //如果有错就返加nulll
        return null;
    }

    private SecretKey getSecretKeySpec() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom random = null;
        if (android.os.Build.VERSION.SDK_INT >= 17) { // 在4.2以上版本中，SecureRandom获取方式发生了改变
            random = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            random = SecureRandom.getInstance("SHA1PRNG");
        }
        random.setSeed(encryptKey.getBytes());
        generator.init(128, random);
        SecretKey secretKey = generator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        SecretKey spec = new SecretKeySpec(encoded, "AES");
        return spec;
    }

    /**
     * byte to String
     *
     * @param src
     * @return
     */
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * String to byte
     *
     * @param hexString
     * @return
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((byte) "0123456789ABCDEF".indexOf(hexChars[pos]) <<
                    4 | (byte) "0123456789ABCDEF".indexOf(hexChars[pos + 1]));
        }
        return d;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }
}
