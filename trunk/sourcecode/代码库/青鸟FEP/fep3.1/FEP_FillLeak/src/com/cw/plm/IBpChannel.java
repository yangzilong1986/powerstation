package com.cw.plm;

import com.hzjbbis.fas.model.FaalRequest;

public interface IBpChannel {
	boolean sendRequest(FaalRequest req);
}
