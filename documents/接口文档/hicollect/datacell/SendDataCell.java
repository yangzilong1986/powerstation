package peis.interfaces.hicollect.datacell;

import peis.interfaces.hicollect.CommandItem;

/**
 * 下行数据单元接口
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090703
 */
public interface SendDataCell {
    /**
     * 转化为CommandItem对象
     * @return : CommandItem
     * @throws Exception
     */
    public CommandItem renderCommandItem() throws Exception;
}
