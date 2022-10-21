package com.chen.handler;

/**
 * @author Robert V
 * @create 2022-10-20-上午10:53
 */

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chen.pojo.CommonResult;

/**
 * 自定义限流处理兜底方法
 */
public class CustomerBlockHandler {

    public static CommonResult handleException1(BlockException exception) {
        return new CommonResult(333, "自定义限流处理。。。-----1");
    }

    public static CommonResult handleException2(BlockException exception) {
        return new CommonResult(444, "自定义限流处理。。。-----2");
    }
}
