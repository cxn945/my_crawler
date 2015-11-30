package cxn.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeUtil {

	private static String defaultPattern = "yyyy-MM-dd";

    private static String[] numWordArray = {"零","壹","贰","叁","肆","伍",
    	                                  "陆","柒","捌","玖"};
    
    private static final int[] DAY_OF_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31,
        30, 31, 30, 31 };
    
	
	/**
	 * 判断日期是否有效
	 * @param dateStr	日期字串
	 * @param pattern	匹配模式	默认为"yyyy-MM-dd"
	 * @return	boolean true:日期有效/false:日期字串非法
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		boolean isValid = false;
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String date = sdf.format(sdf.parse(dateStr));
			if (date.equalsIgnoreCase(dateStr)) {
				isValid = true;
			}
		} catch (Exception e) {
			isValid = false;
		}
		//如果目标格式不正确，判断是否是其它格式的日期
		if(!isValid){
			isValid = isValidDatePatterns(dateStr,"");
		}
		return isValid;
	}
	/**
	 * 判断日期是否有效
	 * @param dateStr	日期字串
	 * @param pattern	匹配模式	默认为"yyyy-MM-dd;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm"
	 * @return	boolean true:日期有效/false:日期字串非法
	 */
	public static boolean isValidDatePatterns(String dateStr,String patterns){
		if(patterns==null || "".equalsIgnoreCase(patterns) || patterns.length()<1){
			patterns = "yyyy-MM-dd;dd/MM/yyyy;yyyy/MM/dd;yyyy/M/d h:mm";
		}
		boolean isValid = false;
		String[] patternArr = patterns.split(";");
		for(int i=0;i<patternArr.length;i++){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(patternArr[i]);
				String date = sdf.format(sdf.parse(dateStr));
				if (date.equalsIgnoreCase(dateStr)) {
					isValid = true;
					defaultPattern = patternArr[i];
					break;
				}
			} catch (Exception e) {
				isValid = false;
			}
		}
		return isValid;
	}
	/**
	 * 返回日期字符串
	 * @param dateStr	日期字符串
	 * @param pattern	匹配模式 如:yyyy-MM-dd
	 * @return
	 */
	public static String getFormatDate(String dateStr, String pattern){
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String date = format.format(sdf.parse(dateStr));
			return date;
		} catch (Exception e) {
			System.out.println("日期格转换失败！");
		}
		return null;
	}
	
	/**
	 * 格式转换
	 * @param date		日期变量
	 * @param pattern	匹配模式
	 * @return			日期字符号串
	 */
	public static String getFormatDate(Date date,String pattern){
		if (pattern == null || pattern.length() < 1) {
			pattern = "yyyy-MM-dd";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String strDate = sdf.format(date);
		return strDate;
		
		
		
	}
	/**
	 * 格式化为中文大写日期
	 */
	public static String getChinaDate(Date date){
		//String pattern = "yyyy年MM月dd日";
		
		
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DATE);
			int year = cal.get(Calendar.YEAR);
		
			String yearStr = "" ;
			while(year > 0){
				int num  = year % 10;
				yearStr = numWordArray[num]+ yearStr;
				year = (int)year/10;
			}
		    
			String monthStr = "";
			if(month < 10){
				monthStr= "零"+ numWordArray[month];
			}else if(month == 10){
				monthStr= "壹拾";
			}else{
				monthStr= "壹拾"+numWordArray[month%10];
			}
			
			String dayStr = "";
			if(day < 10){
				dayStr = "零"+ numWordArray[day];
			}else if(day%10==0){
				
				dayStr = numWordArray[day/10]+ "拾" ;
				
			}else{
				
				dayStr = numWordArray[day/10]+ "拾" +  numWordArray[day%10];
				
			}
			
			String resultStr= yearStr+"年"+monthStr+"月"+dayStr+"日";
			
			
			
			return resultStr;
		
	}
	
	/**
	 * 格式化为中文大写日期
	 * @throws ParseException 
	 */
	public static String getChinaDate(String  dateStr) throws ParseException {
		
		
				 String pattern ="";
				 
				 if(dateStr.indexOf("-") != -1 ){
					 pattern = "yyyy-MM-dd";
				 }else if(dateStr.indexOf("/") != -1 ){
					 pattern = "yyyy/MM/dd";
				 }else{
					 pattern = "yyyyMMDD";
				 }
				 
				 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				 
				 Date date  = sdf.parse(dateStr);
				 
				 return getChinaDate(date);
		
		
	}
	

	
	
	public  static Date getStringToDate(String str) {
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date parse=null;
		str=str.replace("T"," ");
		try {
			parse = date.parse(str);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return parse;
	}
	
	public  static Date getSecondToDate(Integer seconds) {
		Date newDate= new Date((Long)(seconds*1000L));
		return newDate;
	}
	
	public  static Date getSecondToDate(Long seconds) {
		Date newDate= new Date(seconds*1000L);
		return newDate;
	}
	
	public  static Date getStringToDateMinute(String str) {
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
		Date parse=null;
		str=str.replace("T"," ");
		try {
			parse = date.parse(str);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return parse;
	}
	
	public static String getMsgIdCreDtTm(Date date) {	
		if(date==null){
			date=new Date();
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		String creDtTm = sdf1.format(date) + "T" + sdf2.format(date);
		return creDtTm;
	}
	
	
	/**
	 * 获取当前日期，格式：yyyyMMdd
	 * @return
	 */
	public static String getCurDate(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dataStr = sdf.format(date);
		return dataStr;
	}
	/**
	 * 获取当前日期，格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurDateTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataStr = sdf.format(date);
		return dataStr;
	}

	
	public static String getCurTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		String timeStr = sdf.format(date);
		return timeStr;
	}
	
	
	
	//----------------------------------------
	
	public static Date getNowDateTime(){
		return Calendar.getInstance().getTime();		
	}
	
	public static Long getNowMillis() {
		Calendar cd=Calendar.getInstance();
		return cd.getTimeInMillis();
	}
	
	public static Integer getNowSeconds() {
		Calendar cd=Calendar.getInstance();
		return (int) (cd.getTimeInMillis()/1000);
	}
	
	/**
	 * 日期的字符串
	 *
	 * @param date
	 * @return 'yyyy-MM-dd'格式
	 */
	public static String toDateString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdff.format(date);
		return dateStr;
	}
	/**
	 * 日期与时间的字符串表示
	 *
	 * @param date
	 * @return 'yyyy-MM-dd HH:mm:ss'格式
	 */
	public static String toDateTimeString(Date date) {
		if (date == null) {
			return null;
		}		
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdff.format(date);
		return dateStr;
	}
	/**
	 * 时间的字符串
	 *
	 * @param date
	 * @return 'HH:mm:ss'格式
	 */
	public static String toTimeString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdff = new SimpleDateFormat("HH:mm:ss");
		String dateStr = sdff.format(getNowDateTime());
		return dateStr;
	}
	
	/**
	 * 将日期转换为yyyyMMdd字符串.
	 */
	public static String get_YYYYMMDD_Date(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dt = sdf.format(date);
		return dt;
	}
	
	/**
	 * 将日期转换为yyyy-MM-dd字符串.
	 */
	public static String get_YYYY_MM_DD_Date(Date date){	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
		return sdf.format(date);
	}
	
	/*
	 * 获得当前时间格式的字符串
	 */
	public static String get_HHmmss_Time(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	
	public static String get_Sixhhmmss_time(){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
			Calendar cal=Calendar.getInstance();
			dt=sdf.format(cal.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}
	

	/**
	 * 计算某日期之后N天的日期
	 * 
	 * @param theDateStr
	 * @param days
	 * @return String
	 */
	public static String getDate(String theDateStr, int days) {
		Date theDate = java.sql.Date.valueOf(theDateStr);
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(GregorianCalendar.DATE, days);
		java.sql.Date d = new java.sql.Date(c.getTime().getTime());
		return d.toString();
	}
	/**
	 * 计算一旬的头一天
	 * @param theDate
	 * @param days
	 * @return
	 */
     public static Date getDayOfPerMonth(String theDataStr){
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	int day=c.get(Calendar.DAY_OF_MONTH);
     	if(day<=10){
     		c.set(Calendar.DAY_OF_MONTH,1);
     	}else if(day>10&&day<=20){
     		c.set(Calendar.DAY_OF_MONTH,11);
     	}else{
     		c.set(Calendar.DAY_OF_MONTH,21);
     	}
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	
     	
     	return new Date(c.getTime().getTime());
     }
     /**
      * 判断是否为该月最后一天
      * @param theDate
      * @param days
      * @return
      */
     public static boolean isLastDayOfMonth(String theDataStr){
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	int nowDay=c.get(Calendar.DAY_OF_MONTH);
     	c.set(Calendar.DAY_OF_MONTH,1);
     	c.add(Calendar.MONTH,1);
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	int lowDayOfMonth=c.get(Calendar.DAY_OF_MONTH);
     	if(nowDay==lowDayOfMonth){
     		return true;
     	}else{
     		return false;
     	}
     }

     /**
      * 获取指定月份的最后一天
      * @param theDataStr
      * @return
      */
     public static String LastDateOfMonth(String theDataStr){
     	Date theDate = java.sql.Date.valueOf(theDataStr);
     	Calendar c= new GregorianCalendar();
     	c.setTime(theDate);
     	c.set(Calendar.DAY_OF_MONTH,1);
     	c.add(Calendar.MONTH,1);
     	c.add(Calendar.DAY_OF_MONTH,-1);
     	return (new java.sql.Date(c.getTime().getTime())).toString();
     }
     
     /**
      * 获取指定日期的的当月第一天
      * @param theDate
      * @return
      */
     public static Date getFirstDateOfMonth(Date theDate){
      	Calendar c= new GregorianCalendar();
      	c.setTime(theDate);
      	c.set(Calendar.DAY_OF_MONTH,1);
      	c.set(Calendar.HOUR_OF_DAY, 0);
      	c.set(Calendar.MINUTE, 0);
      	c.set(Calendar.SECOND, 0);
      	c.set(Calendar.MILLISECOND, 0);
      	return c.getTime();
      }
     
     

     /**
	 * 计算某日期之后N天的日期
	 * 
	 * @param theDate
	 * @param days
	 * @return Date
	 */
	public static Date getDate(Date theDate, int days) {
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(GregorianCalendar.DATE, days);
		return new Date(c.getTime().getTime());
	}
	
	/**
	 * 计算某日期之后N的日期
	 * 
	 * @param theDate
	 * @param field，如GregorianCalendar.DATE,GregorianCalendar.MONTH
	 * @param amount 数目
	 * @return Date
	 */
	public static Date getDate(Date theDate, int field, int amount) {
		Calendar c = new GregorianCalendar();
		c.setTime(theDate);
		c.add(field, amount);
		return new Date(c.getTime().getTime());
	}
	
	
    //获得两个日期(字符串)之间的天数
	public static int getDiffDays(String begin_dt,String end_dt){
		Date end = java.sql.Date.valueOf(end_dt);
		Date begin = java.sql.Date.valueOf(begin_dt);
		int days = getDaysBetween(begin,end);
		return days;
	}	

	/**
	 * 计算两日期之间的天数
	 * 
	 * @param start
	 * @param end
	 * @return int
	 */
	public static int getDaysBetween(Date start, Date end) {
		if(start==null)return 0;
		boolean negative = false;
		if (end.before(start)) {
			negative = true;
			Date temp = start;
			start = end;
			end = temp;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar calEnd = new GregorianCalendar();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		if (cal.get(Calendar.YEAR) == calEnd.get(Calendar.YEAR)) {
			if (negative)
				return (calEnd.get(Calendar.DAY_OF_YEAR) - cal
						.get(Calendar.DAY_OF_YEAR))
						* -1;
			return calEnd.get(Calendar.DAY_OF_YEAR)
					- cal.get(Calendar.DAY_OF_YEAR);
		}
		int counter = 0;
		while (calEnd.after(cal)) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			counter++;
		}
		if (negative)
			return counter * -1;
		return counter;
	}
	
	/**
	 * 以指定时间格式返回指定时间
	 * 
	 * @param dt 指定时间
	 * @param format 时间格式，如yyyyMMdd
	 * @return 返回指定格式的时间
	 */
	public static String getTime(Date dt, String format) {
		SimpleDateFormat st = new SimpleDateFormat(format);
		return st.format(dt);
	}

	/**
	 * 日期解析
	 * 
	 * @param source 日期字符
	 * @param format 解析格式，如果为空，使用系统默认格式解析
	 * @return 日期
	 * @throws ParseException 
	 */
	public static Date parse(String source, String format) throws ParseException {
		if (source == null) {
			return null;
		}

		DateFormat df = null;
		if (format != null) {
			df = new SimpleDateFormat(format);
		} else {
			df = DateFormat.getDateInstance(DateFormat.DEFAULT);
		}
		
		return df.parse(source);
		
	}

	
	/**
	 * 两时间比较
	 *
	 * @param date1 
	 * @param date2
	 * @return date1==date2返回0, date1大于date2返回1, date1小于date2返回-1 
	 */
	public static int compareDate(Date date1, Date date2) {

		long due = date1.getTime() + 0 * 24 * 60 * 60 * 1000;
		long due2 = date2.getTime()+ 0 * 24 * 60 * 60 * 1000;
		if (due == due2) {
			return 0;
		}else if (due > due2) {
			return 1;
		}else
			return -1;
	}
	
	/**
	 * 计算两日期之间的自然月份数
	 * 
	 * @param startDate 开始日期
	 * @param endDate  结束日期
	 * @return int
	 */
	public static int getMonthsBetween(Date startDate, Date endDate) {
		int counter = 0;
		boolean negative = false;
		Date start = (Date)startDate.clone();
		Date end = (Date)endDate.clone();
		
		////如果start,比end大，需要互换后，算出相差月份数，最后再取相反数。
		if (end.before(start)) {
			negative = true;
			Date temp = start;
			start = end;
			end = temp;
		}
		Date b = getFirstDateOfMonth(start);
		Date e = getFirstDateOfMonth(end);
		
		int bd = getDaysBetween(b, start);
		int ed = getDaysBetween(e, end);

		GregorianCalendar bCal = new GregorianCalendar();
		bCal.setTime(b);
		bCal.set(Calendar.HOUR_OF_DAY, 0);
		bCal.set(Calendar.MINUTE, 0);
		bCal.set(Calendar.SECOND, 0);
		bCal.set(Calendar.MILLISECOND, 0);
		GregorianCalendar eCal = new GregorianCalendar();
		eCal.setTime(e);
		eCal.set(Calendar.HOUR_OF_DAY, 0);
		eCal.set(Calendar.MINUTE, 0);
		eCal.set(Calendar.SECOND, 0);
		eCal.set(Calendar.MILLISECOND, 0);
		//先得到两个月初相差的月份数
		while (eCal.after(bCal)) {
			bCal.add(Calendar.MONTH, 1);
			counter++;
		}
		//如果结束日期离月初的天数比开始日期离月初的天数大，需要再加1
		if (ed - bd > 0) {
			counter++;
		}
		//如果start,比end大，需要取相反数。
		if (negative) {
			counter = counter * -1;
		}
		return counter;
	}
	
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);

		return cal.getTime();
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.set(14, 999);

		return cal.getTime();
	}
	
	public static Date addMonth(Date date, int monthAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthAmount);
		return calendar.getTime();
	}

	public static Date addWeek(Date date, int weekAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.WEDNESDAY, weekAmount);
		return calendar.getTime();
	}

	public static Date addDay(Date date, int dayAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dayAmount);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int hourAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hourAmount);
		return calendar.getTime();
	}

	public static Date addMinute(Date date, int minuteAmount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minuteAmount);
		return calendar.getTime();
	}
	
    public static int get(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }
	
    public static int getYear(Date date) {
        return get(date, 1);
    }

    public static int getMonth(Date date) {
        return get(date, 2);
    }
	
    public static Date getStartMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return getStartDate(calendar.getTime());
    }

    public static Date getEndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        //临时处理
        if (month == -1) {
        	year = year - 1;
        	month = 11;
        }
        //=========
        calendar.set(year, month, getMaxDayOfMonth(year, month));
        return getEndDate(calendar.getTime());
    }
	
    public static int getMaxDayOfMonth(int year, int month) {
        if ((month == 1) && (isLeapYear(year)))
            return 29;

        return DAY_OF_MONTH[month];
    }
    
    public static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        return ((GregorianCalendar) calendar).isLeapYear(year);
    }
    
    public static final boolean isWorkDay(Date date){
    	if (date == null) {
    		throw new IllegalArgumentException("date is null.");
    	}
    	boolean ret = true;
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
    		ret = false;
    	}
    	return ret;
    }
    
    /**
     * 
     * @param date 格式为 Fri Mar 26 00:20:18 CST 2010；
     * @return 格式为 1432051200
     */
    
    public static Long DateToInteger(Date date){
    	SimpleDateFormat f=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
		String t=date.toString();
		Long dateLong = null;
		try{
			Date d=f.parse(t);
			dateLong = d.getTime()/1000;
		}catch (ParseException e){		
			e.printStackTrace();
		}
		return dateLong;
    }
    /**
     * 获取当前日期（不带时分秒）
     * @author cxn
     * @date 2015年5月27日 上午11:18:05
     */
    public static Date getNowDate(){
    	return getStringToDateMinute(getFormatDate(new Date(),null));
    }
    /**
     * 获取当前时间串，格式：yyyy-MM-dd HH:mm:ss.SSS
     * @return String
     * @author cxn 2015年11月11日
     */
    public static String getNowTimeStr(){
    	Date d = new Date();
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateStr = sdff.format(d);
    	return dateStr;
    }
    
}
