package diary.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MSI on 2018/1/5.
 */
public class Tester {
    public static void main(String[] args){
        SimpleDateFormat fullsdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat daysdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timesdf=new SimpleDateFormat("HH:mm");
        String date="14:09";
        Date d=new Date();
        date=daysdf.format(d)+" "+date;
        System.out.println(date);
        System.out.println(fullsdf.format(d));
        System.out.println(date.compareTo(fullsdf.format(d)));
    }
}
