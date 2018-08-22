package com.facetuis.server.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机生成随机数
 * @author 王继永
 *
 */
public class RandomUtils {

	private static final String temp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String[] temps = temp.split("");

	private static final String tempNumber = "0123456789";
	private static final String[] tempsNumber = tempNumber.split("");

	private static final String tempWORD = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String[] tempsWORD = tempWORD.split("");

	private static final List<String> retain = new ArrayList<>();

	/**
	 * 生成自定义长度字符串
	 * @param size
	 * @return
	 */
	public static String random(int size){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i ++){
			int num = Math.abs(random.nextInt()%temp.length()) ;
			sb.append(temps[num]);
		}
		return sb.toString();
	}

	/**
	 * 生成自定义长度字数字
	 * @param size
	 * @return
	 */
	public static String randomNumber(int size){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i ++){
			int num = Math.abs(random.nextInt()%tempNumber.length()) ;
			sb.append(tempsNumber[num]);
		}
		if(isRetain(sb.toString())){
			return randomNumber(size);
		}
		return sb.toString();
	}

	private static boolean isRetain(String s) {
		if(retain.size() == 0){
			retain.add("66666");
			retain.add("88888");
			retain.add("99999");
			retain.add("666666");
			retain.add("888888");
			retain.add("999999");
		}
		return retain.contains(s);
	}

	/**
	 * 生成自定义长度大写字母
	 * @param size
	 * @return
	 */
	public static String randomWord(int size){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i ++){
			int num = Math.abs(random.nextInt()%tempWORD.length()) ;
			sb.append(tempsWORD[num]);
		}
		return sb.toString();
	}


	/**
	 * 指定范围生成随机码
	 * @param min
	 * @param max
	 * @return
	 */
	public static String rate(int min,int max) {
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s + "";
	}

	public static void main(String[] args) {
		System.out.println(rate(0,10));
	}
}
