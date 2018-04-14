package Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

public abstract class SettingAccess {

	public enum Type{
		REMOTE, LOCAL
	}
	
	public final static String ACCESS_TYPE = "AccessType";
	public final static String DOMAIN = "domain";
	public final static String USERNAME = "username";
	public final static String PASSWORD = "password";
	public final static String USER_INFO = "user_info";
	public final static String HOST = "host";
	
	private static SettingAccess access = null;
	
	public static void init(HashMap<String, Object> settings) throws Exception{
		if(settings.containsKey(ACCESS_TYPE) == false)
			throw new IllegalArgumentException("Invalid access type");
		Type t = (Type)settings.get(ACCESS_TYPE);
		if(t == Type.LOCAL)
			access = new LocalSettingAccess();
		/*else if(t == Type.REMOTE)
			access = new RemoteSettingAccess(settings);*/
	}
	
	public static InputStream getInputStream(String url) throws IOException{
		return access.getInputStreamImpl(url);
	}
	
	protected abstract InputStream getInputStreamImpl(String url) throws IOException;
	
	private static class LocalSettingAccess extends SettingAccess{

		public LocalSettingAccess(){
			
		}
		@Override
		protected InputStream getInputStreamImpl(String url) throws IOException {
			InputStream stream = new FileInputStream(url);
			return stream;
		}
	}
	/*
	public static class RemoteSettingAccess extends SettingAccess{

		private NtlmPasswordAuthentication auth = null;
		private static HashMap<UserInfo, RemoteSettingAccess> UserAccessMap = new HashMap<UserInfo, RemoteSettingAccess>();		
				
		public RemoteSettingAccess(HashMap<String, Object> setting) throws Exception{
			UserInfo info = null;
			if(setting.containsKey(USER_INFO))
				info = (UserInfo)setting.get(USER_INFO);
			else{
				info = new UserInfo((String)setting.get(DOMAIN),
									(String)setting.get(USERNAME),
									(String)setting.get(PASSWORD),
									(String)setting.get(HOST));
			}
			
			getAccess(info);
		}
		
		public RemoteSettingAccess(){
		}
		
		public Set<UserInfo> getAllUsersInfo(){
			return UserAccessMap.keySet();
		}
		
		public SettingAccess getAccessByUserInfo(UserInfo info){
			return UserAccessMap.get(info);
		}
		
		private void getAccess(UserInfo info) throws Exception{
			
			
			if(info == null)
				throw new IllegalArgumentException("UserInfo object is null");
			if(info.getDomain() == null)
				throw new IllegalArgumentException("UserInfo.domain object is null");
			if(info.getUserName() == null)
				throw new IllegalArgumentException("UserInfo.username object is null");	
			if(info.getPassword() == null)
				throw new IllegalArgumentException("UserInfo.password object is null");			
			
			boolean bAccessed = UserAccessMap.containsKey(info);
			RemoteSettingAccess settingAccess = null;
			if(bAccessed)
				settingAccess = UserAccessMap.get(info);
			else{
				settingAccess = new RemoteSettingAccess();			
				settingAccess.auth = new NtlmPasswordAuthentication(info.getDomain(), info.getUserName(), info.getPassword());
				auth = settingAccess.auth;
				try{
					SmbSession.logon(UniAddress.getByName(info.getHost()), settingAccess.auth);
				}
				catch(Exception ex){
					
				}
				UserAccessMap.put(info, settingAccess);
			}				
		}	
		
		@Override
		protected InputStream getInputStreamImpl(String url) throws IOException{
			SmbFile file = new SmbFile(url, auth, SmbFile.FILE_SHARE_READ);
			SmbFileInputStream stream = new SmbFileInputStream(file);			
			return stream;
		}		
	}
	*/
}
