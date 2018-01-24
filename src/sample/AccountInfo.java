package sample;

import com.twilio.type.PhoneNumber;

import java.io.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by Jordan on 19/01/2018.
 */
public class AccountInfo
{
    private File accountFile;
    private String password;
    private double balance;
    private String phoneNumber;
    private Instant seasonPass;
    private Instant weekPass;
    private Instant dayPass;

    static AccountInfo newAccount(File file, String password){
        return new AccountInfo(file, password);
    }

    private AccountInfo(File file, String password){
        accountFile = file;
        this.password = password;
        balance = 0.0;
        phoneNumber = "";
        seasonPass = Instant.EPOCH;
        weekPass = Instant.EPOCH;
        dayPass = Instant.EPOCH;
        update();
    }

    AccountInfo(File file){
        accountFile = file;
        try
        {
            Scanner scanner = new Scanner(new FileInputStream(accountFile));
            read(scanner);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public String Verify(String input)
    {
        String key = String.valueOf(input)+accountFile.getName()+input;
        int hash = Integer.MAX_VALUE / (key.length() + 1);
        for (int i = 0; i < key.length(); i++)
            hash = hash * (6977 - key.charAt(i));
        int returnInt = Math.abs(hash)%1000000;
        String returnString = String.valueOf(returnInt);
        while(returnString.length() < 6)
            returnString = "0"+returnString;
        return returnString;
    }

    public File getAccountFile()
    {
        return accountFile;
    }

    private void read(Scanner scanner){
        password = scanner.nextLine();
        balance = Double.parseDouble(scanner.nextLine());
        phoneNumber = scanner.nextLine();
        seasonPass = Instant.parse(scanner.nextLine());
        weekPass = Instant.parse(scanner.nextLine());
        dayPass = Instant.parse(scanner.nextLine());
        scanner.close();
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public boolean setPhoneNumber(String phoneNumber)
    {
        try
        {
            new PhoneNumber(phoneNumber);
            this.phoneNumber = phoneNumber;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public double getBalance()
    {
        return balance;
    }

    public void update(){

        FileWriter fw = null;
        try
        {
            fw = new FileWriter(accountFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(password);
            bw.newLine();
            bw.write(String.valueOf(balance));
            bw.newLine();
            bw.write(phoneNumber);
            bw.newLine();
            bw.write(seasonPass.toString());
            bw.newLine();
            bw.write(weekPass.toString());
            bw.newLine();
            bw.write(dayPass.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        /*
                Instant instant = Instant.now();
                ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
                Calendar cal1 = GregorianCalendar.from(zdt);
         */
    }
}
