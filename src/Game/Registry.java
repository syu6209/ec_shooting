package Game;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class Registry {
	private static String keyPath = null;
	private static String keyName = null;
	private static String keyValue = null;
	Game g;

	public String makeData() {
		String data = "";
		int arr[] = g.getData();
		for(int i=0;i<arr.length-1;i++){
			data += arr[i] + ",";
		}
		data+=arr[arr.length-1];
		return data;
	}

	public Registry(Game g) {
		this.g = g;
		String temp = "";
		int arr[] = g.getData();
		keyPath = "Software\\Microsoft\\Windows\\CurrentVersion";

		keyName = "JavaShootingGame";

		for(int i=0;i<arr.length-1;i++){
			temp += arr[i] + ",";
		}
		temp+=arr[arr.length-1];
		keyValue = temp;

		Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, keyPath,
				keyName);
		// if (!Advapi32Util.registryKeyExists(WinReg.HKEY_CURRENT_USER,
		// keyName)) {

		// }
	}

	public void save() {
		keyValue = makeData();
		String data = "";
		System.out.println("Saving Data : " + keyValue);
		Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, keyPath,
				keyName, keyValue);
		data = (Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER,
				keyPath, keyName));
		System.out.println("Saved Data : " + data);
	}

	public String load() {
		String data = "";
		try {
			data = (Advapi32Util.registryGetStringValue(
					WinReg.HKEY_CURRENT_USER, keyPath, keyName));
		} catch (Exception e) {
			System.out.println("no Data -> init()");
			data = "1,1000,15,0,0,0,0,0,0,0,0,0,0,0,0,0";
		}
		// }else{
		// System.out.println("no Data -> init()");
		// data = "1,0,10,200,120";
		// }
		return data;
	}
	public void deleteData(){
		try{
			Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, keyPath,
					keyName);

			Advapi32Util.registryDeleteKey(WinReg.HKEY_CURRENT_USER, keyPath,
					keyName);
			}catch(Exception e){
				System.out.println("레지 삭제 오류");
			}
	}
	public void initData(){
		//걍 지워버림 ... delete 랑 같음
		deleteData();
	}
}
