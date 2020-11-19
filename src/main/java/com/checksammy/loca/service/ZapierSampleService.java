package com.checksammy.loca.service;

import com.checksammy.loca.model.ZapierSample;
import com.checksammy.loca.model.ZapierList;

public interface ZapierSampleService {

	ZapierSample saveData(ZapierSample zapierSample);
	
	ZapierList saveDate(ZapierList zapierList);

}
