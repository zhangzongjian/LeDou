package util;

import java.io.IOException;

import QQLogin.QQLogin;
import core.设置面板;

public class LoginUtil extends QQLogin{

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
		if(uin.length() == 0 || password.length() == 0) return 2;
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
			System.out.println(login_result.split(",")[4]+","+login_result.split(",")[5]);
			if(login_result.contains("登录成功")) {
				return 0;
			}
			else {
				return 2;
			}
		}
		else {
			System.out.println("需要输入验证码登录！");
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
			System.out.println(login_result.split(",")[4]+","+login_result.split(",")[5]);
			return 0;
		}
	}
	
	public static void refreshVerifycode(String uin) throws IOException {
		sig = refreshSig(uin, sig);
		getVerifyCode(uin, sig);
		设置面板.showVerifyCode(true);
	}
}
