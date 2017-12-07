package secure.share.inter;

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

import secure.share.util.HexUtil;

/**
 * Created by PuJin on 2017/12/6.
 */

public class NormalEncry implements InterEncry {

    private static String RANDOM_ALGORITHM = "SHA1PRNG";
    private static String RANDOM_PROVIDER = "Crypto";

    private String algorithm;
    private String charsetName = "utf-8";
    private String encryptKey = "qwerty";

    public NormalEncry(String algorithm) {
        this.algorithm = algorithm;
    }

    public NormalEncry(String algorithm,String encryptKey) {
        this.algorithm = algorithm;
        this.encryptKey = encryptKey;
    }

    /**
     * @param plainText 明文
     * @return
     */
    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec());
            byte[] encryptBt = cipher.doFinal(plainText.getBytes(charsetName));
            return HexUtil.bytesToHexString(encryptBt);
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
     * @param cipherText 密文
     * @return
     */
    public String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec());
            byte[] decryBt = cipher.doFinal(HexUtil.hexStringToBytes(cipherText));
            return new String(decryBt, charsetName);
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
        return null;
    }

    private SecretKey getSecretKeySpec() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance(algorithm);
        SecureRandom random = null;
        if (android.os.Build.VERSION.SDK_INT >= 17) { // 在4.2以上版本中，SecureRandom获取方式发生了改变
            random = SecureRandom.getInstance(RANDOM_ALGORITHM, RANDOM_PROVIDER);
        } else {
            random = SecureRandom.getInstance(RANDOM_ALGORITHM);
        }
        random.setSeed(encryptKey.getBytes());
        generator.init(128, random);
        SecretKey secretKey = generator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        SecretKey spec = new SecretKeySpec(encoded, algorithm);
        return spec;
    }
}
