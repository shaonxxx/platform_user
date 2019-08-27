package com.woniu.woniuticket.platform_user.controller;


import com.woniu.woniuticket.platform_user.exception.WalletException;
import com.woniu.woniuticket.platform_user.pojo.Wallet;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.service.WalletOrderService;
import com.woniu.woniuticket.platform_user.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WalletController {
    @Autowired
    WalletService walletService;
    @Autowired
    WalletOrderService walletOrderService;
    /*
    * 查找用户钱包
    *
    * */

    @GetMapping("/wallet/@{userId}")
    @ResponseBody
    public Wallet findWalletByUserName(@PathVariable("userId") Integer userId){
        Wallet wallet = walletService.findWalletByUserId(userId);
        return wallet;
    }

    /*
    *钱包创建
    *
    * */

    @PostMapping("/wallet/{userId}")
    public Map createWallet(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        try {
            int code = walletService.createWallet(userId);
            if(code==0){
                result.put("msg","已存在钱包");
                result.put("code",500);
            }
            result.put("msg","钱包创建成功");
            result.put("code",0);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg","钱包创建失败");
            result.put("code",500);
        }
        return result;
    }


    /*
    * 钱包余额充值
    *
    * */

    @PutMapping("/walletAdd/{userId}")
    public Map AddMoney(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        try {
            Wallet wallet = walletService.findWalletByUserId(userId);
            List<WalletOrder> walletOrderList = walletOrderService.findChargeOrderByTime(userId);
            Integer chargeMoney = walletOrderList.get(0).getWalletOrderMoney();
            wallet.setResAmount(wallet.getResAmount() + chargeMoney);
            int code = walletService.modifyWallet(wallet);
            result.put("code",0);
            result.put("msg","充值成功");
        } catch (WalletException e) {
            e.printStackTrace();
            result.put("msg","充值失败");
            result.put("code",500);
        }
        return  result;
    }


    /*
    * 钱包余额使用
    *
    * */

    @PutMapping("/walletReduce/{userId}")
    public Map ReduceMoney(@PathVariable("userId")Integer userId){
        Map result=new HashMap();
        // 查找用户钱包
        Wallet wallet = walletService.findWalletByUserId(userId);
        //查找钱包订单
        List<WalletOrder> walletOrderList = walletOrderService.findReduceOrderByTime(userId);
            if(wallet.getResAmount() >= walletOrderList.get(0).getWalletOrderMoney()) {
            wallet.setResAmount(wallet.getResAmount() - walletOrderList.get(0).getWalletOrderMoney());
            }else{
                result.put("msg","余额不足");
                result.put("code",500);
                throw new WalletException();
            }
        try {
            int code = walletService.modifyWallet(wallet);
            result.put("code",0);
            result.put("msg","支付成功");
        } catch (WalletException e) {
            e.printStackTrace();
            result.put("msg","支付失败");
            result.put("code",500);
        }
        return result;
    }


    /*
    *钱包订单集合查找
    *
    * */

    @GetMapping("/walletOrderList/{userId}")
    @ResponseBody
    public List<WalletOrder> findWalletOrderListByUserId(@PathVariable("userId") Integer userId){
        List<WalletOrder> walletOrderList = walletOrderService.findOrderByUserId(userId);
        return walletOrderList;
    }

    /*
    * 钱包订单信息
    *
    * */

    @GetMapping("/walletOrder/{walletOrderId}")
    @ResponseBody
    public WalletOrder findWalletOrderByOrderId(@PathVariable("walletOrderId")Integer walletOrderId){
        WalletOrder order = walletOrderService.findOrderByOrderId(walletOrderId);
        return order;
    }

    /*
    * 钱包订单删除
    *
    * */

    @DeleteMapping("/walletOrder/{walletOrderId}")
    public Map deleteOrderByUserId(@PathVariable("walletOrderId")Integer walletOrderId){
        Map result=new HashMap();
        WalletOrder walletOrder = walletOrderService.findOrderByOrderId(walletOrderId);
        //钱包订单状态 walletOrderState
        //1已完成
        //2未完成
        //3已删除
        walletOrder.setWalletOrderState(3);
        try {
            int code = walletOrderService.updateWalletOrder(walletOrder);
            result.put("msg","删除成功");
            result.put("code",0);
        } catch (WalletException e) {
            e.printStackTrace();
            result.put("msg","删除失败");
            result.put("code",500);
        }
        return result;
    }


    /*
    * 电影票支付钱包订单生成
    *
    * */

    @PostMapping("/walletOrder")
    public Map createWalletOrder(WalletOrder walletOrder,Integer userId){
        //订单支付类型 walletOrderType
        //1购票观影支付 余额-
        //2会员充值支付 余额-
        //3余额充入 余额+
        walletOrder.setWalletOrderCreatetime(new Date());
        walletOrder.setUserId(userId);
        walletOrder.setWalletOrderType(1);
        walletOrder.setWalletOrderState(1);
        Map result=new HashMap();
        try {
            int code = walletOrderService.createWalletOrder(walletOrder);
            result.put("code",0);
            result.put("msg","订单生成成功");
        } catch (WalletException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","订单生成失败");
        }
        return  result;
    }


    /*
     * 会员支付钱包订单生成
     *
     * */

    @PostMapping("/walletOrderVip")
    public Map createVipWalletOrder(WalletOrder walletOrder,Integer userId){
        walletOrder.setWalletOrderCreatetime(new Date());
        walletOrder.setUserId(userId);
        walletOrder.setWalletOrderType(2);
        walletOrder.setWalletOrderState(1);
        Map result=new HashMap();
        try {
            int code = walletOrderService.createWalletOrder(walletOrder);
            result.put("code",0);
            result.put("msg","订单生成成功");
        } catch (WalletException e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg","订单生成失败");
        }
        return  result;
    }


}
