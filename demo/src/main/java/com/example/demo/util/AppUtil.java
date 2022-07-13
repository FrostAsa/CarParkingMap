package com.example.demo.util;



import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AppUtil {
    /**
     * Determine whether the object is Empty (null or element is 0)<br>
     *
     * Practical for judging the following objects: String Collection and its subclasses Map and its subclasses
     *
     * @param pObj object to be checked
     * @return boolean returned boolean value
     */
    public static boolean isNullOrEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).trim().length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T> T isNullOrEmpty(Object pObj, T defaultValue) {
        if (pObj == null)
            return defaultValue;
        if (pObj instanceof String) {
            if (((String) pObj).trim().length() == 0) {
                return defaultValue;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return defaultValue;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static <T extends Integer> T parseInt(Object source) {
        if (source == null) {
            return (T) new Integer(0);
        } else {
            return (T) source;
        }
    }

    public static <T extends Long> T parseLong(Object source) {
        if (source == null) {
            return (T) new Long(0);
        } else {
            return (T) source;
        }
    }

    public static <T extends Float> T parseFloat(Object source) {
        if (source == null) {
            return (T) new Float(0);
        } else {
            return (T) source;
        }
    }

    public static <T extends BigDecimal> T parseBigDecimal(Object source) {
        if (source == null) {
            return (T) new BigDecimal(0);
        } else {
            return (T) new BigDecimal(source.toString());
        }
    }

    public static <T extends Double> T parseDouble(Object source) {
        if (source == null) {
            return (T) new Double(0);
        } else {
            return (T) source;
        }
    }

    public static String randomString(int length) {
        String ku = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder newStr = new StringBuilder();
        Random r = new Random();

        for (int i = 0; i < length; i++) {
            int r2 = r.nextInt(ku.length());
            newStr.append(ku.charAt(r2));
        }

        return newStr.toString();
    }

    public static String getDeviceId() {

        return null;
    }

    /**
     * read ID information
     */
//    public static IdCardDto getIdCardInfo(String idCard) {
//        IdCardDto result = new IdCardDto();
//
//        if (idCard.length() == 18) {
//
//            idCard = idCard.toLowerCase();
//            // Determine whether the check code is correct
//            String validationCode = calcValidationCode(idCard);
//            if (idCard.substring(idCard.length() - 1, idCard.length()).equals(validationCode)) {
//
//                // Read home address
//                boolean flag = false;
//                String nativePlace = idCard.substring(0, 6);
////                for (District district : districtDtos) {
////                    if (district.getNumber().equals(nativePlace)) {
////                        flag = true;
////                        nativePlace = district.getDescription();
////                        break;
////                    }
////                }
//                if (!flag) {
//                    nativePlace = "";
//                }
//                result.setNativePlace(nativePlace);
//
//                String birthday = idCard.substring(6, 14);
//                String year = birthday.substring(0, 4);
//                String month = birthday.substring(4, 6);
//                String day = birthday.substring(6, 8);
//                birthday = year + "-" + month + "-" + day;
//                // Determine if the date of birth is correct
//                boolean birthdayFlag = validBirthday(birthday);
//                if (birthdayFlag) {
//                    result.setBirthday(birthday);
//                } else {
//                    result.setMessage("wrong ID number！");
//                    return result;
//                }
//                // calculate age
//                int monthTotal =periodAsMonths(birthday, getCurrentDateString());
//                int age = monthTotal / 12;
//                if (monthTotal % 12 != 0 && monthTotal > 0) {
//                    age = age + 1;
//                }
//                result.setAge(age);
//
//                // read gender
//                int sex = Integer.parseInt(idCard.substring(idCard.length() - 2, idCard.length() - 1));
//                sex = sex % 2 == 0 ? 0 : 1;
//                result.setSex(sex);
//            } else {
//                result.setMessage("wrong ID number");
//                return result;
//            }
//
//        } else {
//            result.setMessage("ID should be 18-digit number");
//        }
//
//        return result;
//    }

    /**
     * Calculate ID verification code
     */
    public static String calcValidationCode(String idCard) {
        String result = "";
        int[] x = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] y = {'1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < idCard.length() - 1; i++) {
            int n = idCard.charAt(i) - 48;
            sum += n * x[i];
        }
        sum = sum % 11;
        result = String.valueOf(y[sum]);

        return result;
    }

    /**
     * Determine if the date of birth is correct
     */
    public static boolean validBirthday(String birthday) {

        // current date
        String nowDate = getCurrentDateString();
        String nowYear = nowDate.substring(0, 4);
        String nowMonth = nowDate.substring(5, 7);
        String nowDay = nowDate.substring(8, 10);

        // date of birth
        String year = birthday.substring(0, 4);
        String month = birthday.substring(5, 7);
        String day = birthday.substring(8, 10);

        // determine if month is correct
        if (Integer.valueOf(month) > 12) {
            return false;
        }

        //Determine whether the number of days is greater than the number of days in the current year and month
        int monthDay = getDaysInMonth(Integer.valueOf(year), Integer.valueOf(month));
        if (Integer.valueOf(day) > monthDay) {
            return false;
        }

        int y = compareStrNumber(year, nowYear);
        int m = compareStrNumber(month, nowMonth);
        int d = compareStrNumber(day, nowDay);

        // Determine if the date of birth is greater than the current date
        if (y == 1) {
            return false;
        }
        if (y == 0 && m == 1) {
            return false;
        }
        if (y == 0 && m == 0 && d == 1) {
            return false;
        }

        return true;

    }


    /**
     *
     * Calculate the number of months in the interval based on the given start and end times
     *
     * @param startDate start time
     * @param endDate   end time
     * @return int return interval month
     */
    public static int periodAsMonths(String startDate, String endDate) {
        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(sdf.parse(startDate));
            c2.setTime(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int calcYear = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int totalMonth = calcYear * 12;
        int calcMonth = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        result = totalMonth + calcMonth;

        return result;
    }

    public static int getDaysInMonth(int year, int month) {
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
            return 31;
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            return 30;
        } else {
            if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
                return 29;
            } else {
                return 28;
            }
        }
    }

    /**
     * Determine the size of a pure numeric string
     */
    public static int compareStrNumber(String str1, String str2) {
        int result = 0;
        int num1 = Integer.valueOf(str1);
        int num2 = Integer.valueOf(str2);
        if (num1 > num2) {
            result = 1;
        }
        if (num1 < num2) {
            result = -1;
        }
        if (num1 == num2) {
            result = 0;
        }
        return result;
    }


    /**
     * Get the current date string.
     */
    public static String getCurrentDateString() {
        return parseDateString(new Date(), "yyyy-MM-dd");
    }

    /**
     * Format date to specified format
     *
     * @param date   date
     * @param format the specified date format
     * @return
     */
    public static String parseDateString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
    public  static  List<BigDecimal>   get(BigDecimal max,BigDecimal min,Integer size){
        BigDecimal subtract = max.subtract(min);
        BigDecimal divide = subtract.divide(BigDecimal.valueOf(size));
        ArrayList<BigDecimal> list = new ArrayList<>();
        BigDecimal addPriceCopy=min;
        list.add(min);
        for (int i = 0; i <size ; i++) {
            addPriceCopy=  addPriceCopy.add(divide);
            if (addPriceCopy.compareTo(max)!=-1){
                list.add(max);
                return list;
            }
            list.add(addPriceCopy);
        }
        return list;
    }

    /**
     *
     * Determine whether the current time is in the [startTime, endTime] interval, pay attention to the time format of the three parameters to be consistent
     * @param nowTime
     * @param startDate
     * @param endDate
     * @return Returns true during the time period, false when not
     */
    public static boolean isEffectiveDate(Date nowTime, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        String format = sdf.format(nowTime);
        Date startTime=null;
        Date endTime=null;
        try {
             startTime = sdf.parse(startDate);
             endTime = sdf.parse(endDate);
            nowTime = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);


        return date.after(begin) && date.before(end);
    }



}
