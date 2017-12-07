package secure.share.inter;

/**
 * Created by PuJin on 2017/12/7.
 */

public interface InterEncry {

    public String encrypt(String plainText);

    public String decrypt(String cipherText);
}
