package Shingu.JobCompletion.tool;

import java.security.MessageDigest;

public class JobCompletionEncode {
    public static String encode(String email, String password) {
        String hashPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            StringBuilder tempString = new StringBuilder();
            for (int i = 0; i < password.length(); i++) {
                tempString.append(password.charAt(i));
                if (email.length() > i)
                    tempString.append(email.charAt(i));
                if (i % 5 == 0)
                    tempString.append(i);
                if (i % 7 == 0)
                    tempString.append("J");
                if (i % 13 == 0)
                    tempString.append("C");
            }

            messageDigest.update(tempString.toString().getBytes());

            byte byteData[] = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : byteData) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            hashPassword = stringBuilder.toString();
        } catch (Exception e) {
            hashPassword = null;
        }
        return hashPassword;
    }
}
