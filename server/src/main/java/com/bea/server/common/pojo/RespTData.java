package com.bea.server.common.pojo;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

/**
 * @author yanjun
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class RespTData {
    /**
     * 总记录数
     */
    private long iTotalRecords;

    /**
     * 总显示记录数
     */
    private int iTotalDisplayRecords;

    /**
     * 开始记录
     */
    private int iDisplayStart;

    /**
     * 表记录集合
     * */
    private Collection<?> aaData;

    public RespTData(long total, int dtotal, int start, Collection<?> data){
        this.iTotalRecords = total;
        this.iTotalDisplayRecords = dtotal;
        this.iDisplayStart = start;
        this.aaData = data;
    }

    public RespTData(PageInfo<?> pageInfo){
        this.iTotalRecords = pageInfo.getTotal();
        this.iTotalDisplayRecords = pageInfo.getSize();
        this.iDisplayStart = pageInfo.getStartRow();
        this.aaData = pageInfo.getList();
    }

    public static RespTData ok(long total, int dtotal, int start, Collection<?> data){
        return new RespTData(total, dtotal, start, data);
    }

    public static RespTData ok(PageInfo<?> pageInfo){
        return new RespTData(pageInfo);
    }
}
