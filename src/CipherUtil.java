//This is where I am handling the encryption/decryption logic, it handles the generation of random keys,
//encrypt and decrypt logic with the required validation and error checking.

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator;

public class CipherUtil {
    //Function to generate a unique random key
    public static String generateNewKey() {
        try {
            //using KeyGenerator to create a random key compatible with AES 256
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            //converting java secretkey to Base64 and returning it as a String value
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (Exception e) {
            System.out.println("Error generating key: " + e.getMessage());
            return null;
        }
    }

    //Function to create the cipher to be used for encrypting/decrypting using either ENCRYPT_MODE or DECRYPT_MODE
    public static Cipher setupCipher(String key, int cipherMode) {
        //using a static ivSpec
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
        try {
            //converting base64 string to a java secretkey compatible with the AES encryption
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key),"AES");

            //creating an AES cipher in CBC mode and return
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, secretKey, ivSpec);
            return cipher;
        } catch (Exception e) {
            System.out.println("Error initialising cipher: " + e.getMessage());
            return null;
        }
    }

    //Function to encrypt a provided string, in this program's case, the fileContent taken from file
    public static String encrypt(String fileContent, String key) {
        try {
            Cipher cipher = setupCipher(key, Cipher.ENCRYPT_MODE);
            if (cipher != null) {
                //converts string into bytes, encrypts, then converts and returns string
                return Base64.getEncoder().encodeToString(cipher.doFinal(fileContent.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.getMessage());
            return null;
        }
        return null;
    }

    //Function to decrypt a provided string, in this program's case, the fileContent taken from file
    public static String decrypt(String fileContent, String key) {
        try {
            Cipher cipher = setupCipher(key, Cipher.DECRYPT_MODE);
            if (cipher != null) {
                //converts encrypted string into bytes, decrypts, then converts and returns string
                return new String(cipher.doFinal(Base64.getDecoder().decode(fileContent)));
            }
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.getMessage());
            return null;
        }
        return null;
    }
}