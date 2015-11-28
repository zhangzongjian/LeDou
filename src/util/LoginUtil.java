package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import core.设置面板;

public class LoginUtil {

	public static String checkResult = "";
	public static String sig = "";
	
	/**
	 * 登录入口
	 * @param uin
	 * @param password
	 * @param vcode
	 * @return 
	 * @throws IOException
	 */
	public static int login(String uin, String password, String vcode) throws IOException {
		String checkStatus = ""; //login接口参数pt_vcode_v1，对应check接口的0,1状态
		String verifycode = ""; //login接口参数
		String verifysession = ""; //login接口参数
		String p = ""; //login接口参数
		if(vcode.length() == 0) {
			checkResult = check(uin);
		}
		if("0".equals(checkResult.charAt(14)+"")) {
			System.out.println("无需验证码登录！");
			checkStatus = "0";
			verifycode = checkResult.split(",")[1].replaceAll("\'", "");
			verifysession = checkResult.split(",")[3].replaceAll("\'", "");
			p = encryptPassword(uin, password, verifycode);
			String login_result = login1(uin, p, checkStatus, verifycode, verifysession);
			System.out.println(login_result.split(",")[4].replaceAll("\'", ""));
			return 0;
		}
		else {
			System.out.println("需要输入验证码登录！。");
			checkStatus = "1";
			String cap_cd = checkResult.split(",")[1].replaceAll("'", "");
			if(vcode.length() == 0) {
				sig = getSig(uin, cap_cd);
				//获取并输入验证码
				getVerifyCode(uin, sig);
				设置面板.showVerifyCode(true);
				return 1;
			}
			String body = getVerifysession(uin, vcode, sig);
			verifysession = body.split(",")[2].replaceAll("sig:\"", "").replaceAll("\"", "");
			verifycode = body.split(",")[1].replaceAll("randstr:\"", "").replaceAll("\"", "");
			
			if(!body.contains("rcode:0") && vcode.length() > 0) {
				sig = refreshSig(uin, sig);
				getVerifyCode(uin, sig);
				设置面板.showVerifyCode(true);
				return -1;
			}
			p = encryptPassword(uin, password, verifycode);
			String login_result = login1(uin, p, checkStatus, verifycode, verifysession);
			System.out.println(login_result.split(",")[4].replaceAll("\'", ""));
			return 0;
		}
	}
	
	
	/*
	  http://ptlogin2.qq.com/login？....
	     模拟登录流程分析以及login接口重要参数分析：
	      【接口(重要参数) -> 返回的重要信息（说明）】
	  1、网上资料都说login_sig参数(来自xlogin接口)挺重要的，也许是tx改版了，个人亲测login_sig为空也行，连各个接口请求头的cookie都不需要设置。
	  2、需要注入的参数有：u,verifycode,pt_vcode_v1,pt_verifysession_v1,p。其他参数抓包时照搬就好。
	  3、u，p： Q号和加密过的密码。
	  4、pt_vcode_v1：此次登录是否需要验证码，不需要则为0，需要则为1，与check接口返回样例的第一个参数一致
	  5、verifycode：个人取它名为真实验证码，因为它不是手动输入的那个验证码。
	  6、pt_verifysession_v1：真实验证码对应的一个session值。
	  7、无需输入验证码时比较简单，verifycode，pt_verifysession_v1这两个参数直接能从check接口的responseBody中获取。
	     check(u) -> pt_vcode_v1（返回字符串中的第一个参数），verifycode（返回字符串中的第二个参数）,pt_verifysession_v1（返回字符串中的第四个参数）
	     login(u,verifycode,pt_vcode_v1,pt_verifysession_v1,p) -> 登录成功cookie
	  8、需要输入验证码时复杂很多，因为上面那个两个参数并没有在check接口的返回值中给出，其中有用的是返回值中的第二个参数名为cap_cd。
	     check(u) -> cap_cd（返回字符串中的第二个参数）
	     cap_union_show(cap_cd) -> sig（响应体 var g_click_cap_sig="SIG"）
	     getimgbysig(sig) -> ans（手动输入的验证码）
	     cap_union_verify（ans, sig） -> randstr（即login接口所需的verifycode参数）, sig（即login接口所需的pt_verifysession_v1参数）
	     login(u,verifycode,pt_vcode_v1,pt_verifysession_v1,p) -> 登录成功cookie
	  9、若上述各个接口的参数没对上，常见的错误提示是验证码错误！
	 */
	
	/**
	 * qq空间模拟登录 main
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		String uin = "1105451491";
//		String password = "kk258..";
//		String uin = "1102349546";
//		String password = "qq3510534";
		String uin = "2099221914";
		String password = "zzjian";
		String checkStatus = ""; //login接口参数pt_vcode_v1，对应check接口的0,1状态
		String verifycode = ""; //login接口参数
		String verifysession = ""; //login接口参数
		String p = ""; //login接口参数
		String checkResult = check(uin);
		System.out.println(checkResult);
		if("0".equals(checkResult.charAt(14)+"")) {
			System.out.println("无需验证码登录！");
			checkStatus = "0";
			verifycode = checkResult.split(",")[1].replaceAll("\'", "");
			verifysession = checkResult.split(",")[3].replaceAll("\'", "");
		}
		else {
			System.out.println("需要输入验证码登录！。");
			checkStatus = "1";
			String cap_cd = checkResult.split(",")[1].replaceAll("'", "");
			String sig = getSig(uin, cap_cd);
			//获取并输入验证码
			getVerifyCode(uin, sig);
			System.out.println("请输入验证码：");
			Scanner scanf = new Scanner(System.in);
			String vcode = scanf.next(); //输入验证码
			String body = getVerifysession(uin, vcode, sig);
			verifysession = body.split(",")[2].replaceAll("sig:\"", "").replaceAll("\"", "");
			verifycode = body.split(",")[1].replaceAll("randstr:\"", "").replaceAll("\"", "");
			
			while(!body.contains("rcode:0")) {
				sig = refreshSig(uin, sig);
				getVerifyCode(uin, sig);
				System.out.println("error,请重新输入验证码：");
				vcode = scanf.next();
				body = getVerifysession(uin, vcode, sig);
				verifysession = body.split(",")[2].replaceAll("sig:\"", "").replaceAll("\"", "");
				verifycode = body.split(",")[1].replaceAll("randstr:\"", "").replaceAll("\"", "");
			}
		}
		p = encryptPassword(uin, password, verifycode);
		String login_result = login1(uin, p, checkStatus, verifycode, verifysession);
		System.out.println(login_result.split(",")[4].replaceAll("\'", ""));
	}

	public static Map<String, String> cookies;
	
	////////////////////////////////////////////////////////////////////////////////////////////
	/* 不需验证码 begin */
	
	/**
	 * 检查帐号状态(登录时是否需要验证码)。
	 * 若不需要，返回样例  ptui_checkVC('0','!GWD', '\x00\x....\x29','96b...5wf','0')，
	 * 样例说明：!GWD(真实验证码)为login接口的verifycode参数，'96b...5wf'为login接口的pt_verifysession_v1参数；
	 * 若需要，返回样例   ptui_checkVC('1','576429...df98', '\x00\x00...f1\x29','','0');
	 * 样例说明：'576429...df98'为cap_union_show接口的cap_cd参数
	 * @return 
	 * @throws IOException 
	 */
	public static String check(String uin) throws IOException {
		Response response = Jsoup.connect("http://check.ptlogin2.qq.com/check?" +
								"regmaster=" +
								"&pt_tea=1" +
								"&pt_vcode=1" +
								"&uin=" + uin +
								"&appid=549000912" +
								"&js_ver=10140" +
								"&js_type=1" +
								"&login_sig=" +
								"&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone" +
								"&r=0.6051186741306294")
				  				.ignoreContentType(true)
				  				.execute();
		cookies = response.cookies();
		return response.body();
	}
	
	/**
	 * 登录(第一次？)
	 * @return 登录成功时返回字符串：ptuiCB('0','0','http://web.qq.com/loginproxy.html?login2qq=1&webqq_type=10','0','登录成功','你的名字');
	 * @throws IOException 
	 */
	public static String login1(String uin, String p, String checkStatus, String verifycode, String verifysession) throws IOException {
		Response response = Jsoup.connect("http://ptlogin2.qq.com/login?" +
								"u=" + uin +
								"&verifycode=" + verifycode +
								"&pt_vcode_v1=" + checkStatus +
								"&pt_verifysession_v1=" + verifysession +
								"&p="+p+
								"&pt_randsalt=0" +
								"&u1=http%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone" +
								"&ptredirect=0" +
								"&h=1" +
								"&t=1" +
								"&g=1" +
								"&from_ui=1" +
								"&ptlang=2052" +
								"&action=2-1-1447938345482" +
								"&js_ver=10140" +
								"&js_type=1" +
								"&login_sig=" +
								"&pt_uistyle=32" +
								"&aid=549000912" +
								"&daid=5&")
								.ignoreContentType(true)
				  				.execute();
		cookies.putAll(response.cookies());
		return response.body();
	}
	/* 不需验证码 end */
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 返回值sig为getimgbysig接口(获取验证码接口)所需参数，
	 * 同时也是cap_union_verify接口(校验验证码正确性接口)所需参数。
	 * @param uin
	 * @param cap_cd
	 * @return
	 * @throws IOException
	 */
	public static String getSig(String uin, String cap_cd) throws IOException {
		Response sigResponse = Jsoup.connect("http://captcha.qq.com/cap_union_show?" +
									"clientype=2" +
									"&aid=549000912" +
									"&uin=" + uin +
									"&cap_cd=" + cap_cd +//由check接口响应获得
				 					"&0.07040563155896962")
				 					.execute();
		String body = sigResponse.body();
		String temp = body.substring(body.indexOf("<script"));
		String beginString = "var g_vsig = \"";
		String sig = temp.substring(temp.indexOf(beginString)+beginString.length(), temp.indexOf("\";"));
		return sig;
	}
	
	/**
	 * 刷新sig，用来获取新的验证码图片
	 * @param oldSig
	 * @return 返回样例：cap_setQue("",0);cap_showOption(""); cap_getCapBySig("gOCP..m4-8CswYA**");
	 * @throws IOException
	 */
	public static String refreshSig(String uin, String oldSig) throws IOException {
		Response response = Jsoup.connect("http://captcha.qq.com/getQueSig?" +
									"aid=549000912" +
									"&uin=" + uin +
									"&captype=2" +
									"&sig=" + oldSig +
									"&0.6583711083512753")
									.execute();
		//截取结果中cap_getCapBySig("gOCP..m4-8CswYA**")引号部分
		String newSig = response.body().split(";")[2].split("\"")[1];
		return newSig;
	}
	
	/**
	 * 保存验证码图片
	 * @param
	 * @throws IOException 
	 */
	public static void getVerifyCode(String uin, String sig) throws IOException {
		Response imgResponse = Jsoup.connect("http://captcha.qq.com/getimgbysig?" +
								"uin="+uin+
								"&aid=549000912" +
								"&sig="+sig)
								.ignoreContentType(true)
								.execute();
		File imge = new File("resources/VerifyCode.jpg");
		FileOutputStream out = new FileOutputStream(imge);
		out.write(imgResponse.bodyAsBytes());
		out.close();
	}
	
	/**
	 * 校验验证码正确性，返回结果包含rcode:0，则验证码输入正确
	 * @param uin
	 * @param verifycode 填写的验证码
	 * @param sig
	 * @return 正确时返回样例 : cap_InnerCBVerify({rcode:0,randstr:"@fmP",sig:"t02...aP12",errmsg:"..........................."})
	 * 			从中获取 randstr:"@AIU"(真实验证码)，以及sig:"..."(login接口所需的pt_verifysession_v1参数)。
	 *          验证码错误返回样例: cap_InnerCBVerify({rcode:5,randstr:"",sig:"",errmsg:"验证失败，请重试。"});
	 * @throws IOException
	 */
	public static String getVerifysession(String uin, String verifycode, String sig) throws IOException {
		Response response = Jsoup.connect("http://captcha.qq.com/cap_union_verify?" +
								 "aid=549000912" +
								 "&uin=" + uin +
								 "&captype=50" +
								 "&ans=" + verifycode +
								 "&sig=" + sig +
								 "&0.49537746398709714")
								 .execute();
		return response.body();
	}
	
	/**
	 * 密码加密，通过调用js函数加密
	 * @param verifycode 真实验证码，非手动输入的那个验证码
	 * @return 返回加密后的密码，login接口所需的p参数
	 */
	public static String encryptPassword(String uin, String password, String verifycode) {
		String p_result = "";
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine engine = sem.getEngineByName("js");
			FileReader fr = new FileReader("resources/login.js");
			engine.eval(fr);
			Invocable inv = (Invocable) engine;
			p_result = inv.invokeFunction("getEncryption", password,
					uin, verifycode).toString();
		} catch (ScriptException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		return p_result;
	}

	/**
	 * 第二次登录
	 * @return
	 * @throws IOException
	 */
	public static String login2() throws IOException {
		Response response = Jsoup.connect("")
								 .ignoreContentType(true)
								 .execute();
		return response.body();
	}
	
}
