package com.damon.Service;

import com.damon.yundun.YunDunCheckRecord;

/**
 * 功能：
 *
 * @author Damon Chow
 * @since 2018/6/28 11:54
 */
public interface YunDunCheckRecordService {

    /**
     * 保存云盾检测记录
     *
     * @param record
     * @return
     */
    boolean saveRecord(YunDunCheckRecord record);
}
