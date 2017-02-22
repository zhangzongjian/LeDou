package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import QQLogin.QQLogin;
import core.设置面板;

public class LoginUtil extends QQLogin{

	public static Map<String, String> checkResultMap = new HashMap<String, String>();
	public static Map<String, String[]> sigMap = new HashMap<String, String[]>();
	
	/**
	 * 登录入口
	 * @param uin
	 * @param password
	 * @param vcode
	 * @return 登录成功返回 0， 密码错误返回 2， 需要验证码返回 1， 验证码错误返回 -1
	 * @throws IOException
	 */
	public static int login(String uin, String password, String vcode) throws IOException {
		if(uin.length() == 0 || password.length() == 0) return 2;
		String checkStatus = ""; //login接口参数pt_vcode_v1，对应check接口的0,1状态
		String verifycode = ""; //login接口参数
		String verifysession = ""; //login接口参数
		String p = ""; //login接口参数
		String time = new SimpleDateFormat("YYYY/MM/dd HH:mm").format(new Date());
		if(vcode.length() == 0) {
			checkResultMap.put(uin, check(uin));
		}
		String checkResult = checkResultMap.get(uin);
		if("0".equals(checkResult.charAt(14)+"")) {
			checkStatus = "0";
			verifycode = checkResult.split(",")[1].replaceAll("\'", "");
			verifysession = checkResult.split(",")[3].replaceAll("\'", "");
			p = encryptPassword(uin, password, verifycode);
			String login_result = login1(uin, p, checkStatus, verifycode, verifysession);
			login_result = login_result.substring(0, login_result.lastIndexOf(")"));
			System.out.println(login_result.split(",")[4]+" "+login_result.split(",")[5] +" "+ time);
			if(login_result.contains("登录成功")) {
				checkResultMap.put(uin, ""); //登录完成后，账号check状态清空掉
				return 0;
			}
			else {
				return 2;
			}
		}
		else {
			System.out.println("需要输入验证码登录！（"+ uin + "）" + time);
			checkStatus = "1";
			String cap_cd = checkResult.split(",")[1].replaceAll("'", "");
			String sess = getSess(uin, cap_cd);
			if(vcode.length() == 0) {
				String[] sess_cap_sig = {sess, cap_cd, getSig(uin, sess, cap_cd)};
				sigMap.put(uin, sess_cap_sig);
				//获取并输入验证码
				getVerifyCode(uin, sess_cap_sig[0], sess_cap_sig[1], sess_cap_sig[2]);
				设置面板.showVerifyCode(true);
				return 1;
			}
			String body = getVerifysession(uin, vcode, sigMap.get(uin)[0], sigMap.get(uin)[1], sigMap.get(uin)[2]);
			verifysession = body.split(",")[2].replaceAll("\"ticket\" : \"", "").replaceAll("\"", "").trim();
			verifycode = body.split(",")[1].replaceAll("\"randstr\" : \"", "").replaceAll("\"", "").trim();
			
			if(!body.contains("OK") && vcode.length() > 0) {
				String[] sess_cap_sig = {sess, cap_cd, getSig(uin, sess, cap_cd)};
				sigMap.put(uin, sess_cap_sig);
				getVerifyCode(uin, sess_cap_sig[0], sess_cap_sig[1], sess_cap_sig[2]);
				设置面板.showVerifyCode(true);
				return -1;
			}
			p = encryptPassword(uin, password, verifycode);
			String login_result = login1(uin, p, checkStatus, verifycode, verifysession);
			System.out.println(login_result.split(",")[4]+","+login_result.split(",")[5] + time);
			return 0;
		}
	}
	
	public static void refreshVerifycode(String uin) throws IOException {
		String[] sess_cap_sig = sigMap.get(uin);
//		sess_cap_sig[2] = refreshSig(uin, sess_cap_sig[0], sess_cap_sig[1]);
//		sigMap.put(uin, sess_cap_sig);
		getVerifyCode(uin, sess_cap_sig[0], sess_cap_sig[1], sess_cap_sig[2]);
		设置面板.showVerifyCode(true);
	}
}
