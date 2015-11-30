package util;

import java.lang.reflect.Method;


public class ObjectUtil {
	
	/**
	 * 通过反射机制，执行对象指定函数
	 * @param object
	 * @param objectParams 构造参数，没有则null
	 * @param methodName
	 * @param methodParams 函数参数，没有则null
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object doObjectMethod(Object object, Object[] objectParams, String methodName, Object[] methodParams) {
		try {
			Class[] o_paramsType = null;
			Class[] m_paramsType = null;
			if(objectParams != null) {
				o_paramsType = new Class[objectParams.length];
				for(int i = 0; i < objectParams.length; i++) {
					o_paramsType[i] = objectParams[i].getClass();
				}
			}
			if (methodParams != null) {
				m_paramsType = new Class[methodParams.length];
				for (int i = 0; i < methodParams.length; i++) {
					m_paramsType[i] = methodParams[i].getClass();
				}
			}
			Class<?> c = Class.forName(object.getClass().getName());
			//反射机制创建出来的对象
			Object objectInstance = null;
			if(objectParams == null) {
				objectInstance = c.newInstance();
			}
			else {
				objectInstance = c.getConstructor(o_paramsType).newInstance(objectParams);
			}
			Method method = c.getDeclaredMethod(methodName, m_paramsType);
			return method.invoke(objectInstance, methodParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
