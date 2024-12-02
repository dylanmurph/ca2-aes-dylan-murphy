//This is where I am handling the encryption/decryption logic, it handles the generation of random keys,
//encrypt and decrypt logic with the required validation and error checking.

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
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
            System.out.println(ColourUtil.red("Error generating key: " + e.getMessage()));
            return null;
        }
    }

    //Function to create the cipher to be used for encrypting/decrypting using either ENCRYPT_MODE or DECRYPT_MODE
    public static Cipher setupCipher(String key, int cipherMode, byte[] iv) {
        //using a randomised ivSpec
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            //converting base64 string to a java secretkey compatible with the AES encryption
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key),"AES");

            //creating an AES cipher in CBC mode and return
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, secretKey, ivSpec);
            return cipher;
        } catch (Exception e) {
            System.out.println(ColourUtil.red("Error initialising cipher: " + e.getMessage()));
            return null;
        }
    }

    //Function to encrypt a provided string, in this program's case, the fileContent taken from file
    public static String encrypt(String fileContent, String key) {
        byte[] iv, encryptedContent;
        String ivString, encryptedContentString;
        try {
            //creating iv and randomising with secureRandom()
            iv = new byte[16];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = setupCipher(key, Cipher.ENCRYPT_MODE, iv);

            if (cipher != null) {
                //encrypting fileContent
                encryptedContent = cipher.doFinal(fileContent.getBytes(StandardCharsets.UTF_8));

                //converting both to base64 and returning them as string
                ivString = Base64.getEncoder().encodeToString(iv);
                encryptedContentString = Base64.getEncoder().encodeToString(encryptedContent);
                return ivString + encryptedContentString;
            }
        } catch (Exception e) {
            System.out.println(ColourUtil.red("Error while encrypting: " + e.getMessage()));
            return null;
        }
        return null;
    }

    //Function to decrypt a provided string, in this program's case, the fileContent taken from file
    public static String decrypt(String fileContent, String key) {
        byte[] iv, decryptedContent;
        String decryptedContentString;
        try {
            //extracting the IV from encrypted content using substring of fileContent
            //16 bytes = 24 characters in base64. https://stackoverflow.com/a/19600191
            iv = Base64.getDecoder().decode(fileContent.substring(0, 24));

            Cipher cipher = setupCipher(key, Cipher.DECRYPT_MODE, iv);
            if (cipher != null) {
                //converting encrypted string to bytes whilst excluding the iv
                decryptedContent = Base64.getDecoder().decode(fileContent.substring(24));
                //decrypting content, converting to string and returning it
                decryptedContentString = new String(cipher.doFinal(decryptedContent));
                return decryptedContentString;
            }
        } catch (Exception e) {
            System.out.println(ColourUtil.red("Error while decrypting: " + e.getMessage()));
            return null;
        }
        return null;
    }
}