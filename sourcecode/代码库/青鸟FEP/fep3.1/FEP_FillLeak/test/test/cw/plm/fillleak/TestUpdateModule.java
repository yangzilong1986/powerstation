package test.cw.plm.fillleak;

import com.cw.plm.bpserver.FillLeakEventHandler;
import com.cw.plm.updatertu.UpdateRtuModule;

public class TestUpdateModule {
	static UpdateRtuModule update = UpdateRtuModule.getInstance();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		update.setHostIp("127.0.0.1");
		update.setEventHandler(new FillLeakEventHandler());
		update.start();
	}

}
