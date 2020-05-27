package src;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.ArrayList;

public class notMD5 {
    private ArrayList<String> vehicles = new ArrayList<String>();
    private ArrayList<String> numbers = new ArrayList<String>();

    public notMD5() {
        vehicles.add(getMd5("Helicopter"));
        vehicles.add(getMd5("JetPlane"));
        vehicles.add(getMd5("Baloon"));

        for (int j = 0; j < 10001; j++) {
            numbers.add(getMd5(Integer.toString(j)));
        }
    }

    private String getMd5(String input) { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] messageDigest = md.digest(input.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        } 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }

    public boolean validHash(String s) {
        String[] tmp = s.split(" ");
        return (typeCheck(tmp[0]) && validSim(tmp[2]) && validSim(tmp[3]) && validSim(tmp[4]));
    }

    public boolean typeCheck(String s) {
        return (this.vehicles.contains(s));
    }

    public boolean validSim(String s) {
        return (this.numbers.contains(s));
    }

    public int getNumb(String s) {
        return (this.numbers.indexOf(s));
    }

    public String getType(String s) {
        String[] Types = {"Helicopter", "JetPlane", "Baloon"};
        return (Types[this.vehicles.indexOf(s)]);
    }

}