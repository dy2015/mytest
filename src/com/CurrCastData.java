package com;

import java.io.Serializable;

/**
 * 调度中心的当前派发数据<br>
 * @author dingjinlong<br>
 */
public class CurrCastData implements Serializable{
		
	/**
	 * 投放方式 
	 */
	public static enum DELIVER_TYPE {
		CPM, 	// CPM投放：dc.cpm>0
		CPC,	// CPC投放：dc.cpc>0
		CPV,	// cpv投放:dc.cpv>0
        CPP, // cpv投放:dc.cpp>0
		TOM, // 开机图缓存明天
		ATOM, // 开机图缓存后天
	}
	
}
