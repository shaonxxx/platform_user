package com.woniu.woniuticket.platform_user.controller;


import com.github.pagehelper.PageInfo;
import com.woniu.woniuticket.platform_user.exception.WalletException;
import com.woniu.woniuticket.platform_user.pojo.Wallet;
import com.woniu.woniuticket.platform_user.pojo.WalletOrder;
import com.woniu.woniuticket.platform_user.service.UserService;
import com.woniu.woniuticket.platform_user.service.WalletOrderService;
import com.woniu.woniuticket.platform_user.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@Api("用户钱包管理")
public class WalletController {

    @Autowired
    WalletService walletService;
    @Autowired
    WalletOrderService walletOrderService;

    /*
    * 查找用户钱包
    * @param userId
    * @return 返回钱包对象
    * */
    @ApiOperation(value = "获取钱包信息",notes = "根据url中的用户id查询用户钱包")
    @GetMapping("/wallet/@{userId}")
    @ResponseBody
    public Wallet findWalletByUserName(@PathVariable("userId") Integer userId){
        Wallet wallet = walletService.findWalletByUserId(userId);
        return wallet;
    }

    /*
    * 钱包创建
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "创建用户钱包",notes = "根据url的用户id查找用户是否拥有钱包，没有创造一个")
    @PostMapping("/wallet/{userId}")
    public Map createWallet(@PathVariable("userId")@RequestBody Integer userId){
        Map result=new HashMap();
        try {
            int code = walletService.createWallet(userId);
            if(code==0){
                result.put("msg","已存在钱包");
                result.put("code",500);
            }else{
                result.put("msg","钱包创建成功");
                result.put("code",0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg","钱包创建失败");
            result.put("code",500);
        }
        return result;
    }


    /*
    * 钱包余额充值
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "对用户钱包的余额进行充值",notes = "根据url的用户id找到钱包，对余额进行增加")
    @PutMapping("/walletAdd/{userId}")
    public Map AddMoney(@PathVariable("userId") Integer userId){
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
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "找到用户钱包，对余额进行减少",notes ="根据url的用户id找到钱包，对余额进行减少" )
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
    * 钱包订单集合查找
    * @param userId
    * @return 返回钱包订单list集合
    * */
    @ApiOperation(value ="获取用户钱包订单列表",notes = "根据url中的用户id查找到钱包订单集合展示")
    @GetMapping("/walletOrderList/{userId}")
    @ResponseBody
    public PageInfo<WalletOrder> findWalletOrderListByUserId(@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                                             @RequestParam(value = "currentPage",defaultValue = "1",required = false)Integer currentPage,
                                                             @PathVariable("userId") Integer userId){
        List<WalletOrder> walletOrderList = walletOrderService.findOrderByUserId(pageSize,currentPage,userId);
        PageInfo<WalletOrder> pageInfo=new PageInfo<>(walletOrderList);
        return pageInfo;
    }

    /*
    * 钱包订单信息
    * @param walletOrderId
    * @return 返回订单对象
    * */
    @ApiOperation(value ="获取用户钱包订单",notes = "根据url中的钱包订单id查找到钱包订单信息")
    @GetMapping("/walletOrder/{walletOrderId}")
    @ResponseBody
    public WalletOrder findWalletOrderByOrderId(@PathVariable("walletOrderId")Integer walletOrderId){
        WalletOrder order = walletOrderService.findOrderByOrderId(walletOrderId);
        return order;
    }

    /*
    * 钱包订单删除
    * @param walletOrderId
    * @return 返回结果集合
    * */
    @ApiOperation(value ="对用户钱包订单状态修改，完成假删",notes = "根据url中的钱包订单id查找到钱包订单并修改状态")
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
    * @param walletOder
    * @param userId
    * @return 返回结果集合
    * */
    @ApiOperation(value = "生成钱包电影票支付订单",notes = "")
    @PostMapping("/walletTicketOrder/{userId}")
    public Map createWalletOrder(@PathVariable("userId")  Integer userId,@RequestBody Integer money){
        //订单支付类型 walletOrderType
        //1购票观影支付 余额-
        //2会员充值支付 余额-
        //3余额充入 余额+

        WalletOrder walletOrder=new WalletOrder();
        walletOrder.setWalletOrderCreatetime(new Date());
        walletOrder.setWalletOrderMoney(money);
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
     * @param walletOrder
     * @param userId
     * @return 返回结果集合
     * */
    @ApiOperation(value = "生成钱包vip充值支付订单",notes = "")
    @PostMapping("/walletVipOrder/{userId}")
    public Map createVipWalletOrder(@PathVariable("userId") @RequestBody Integer userId){
        WalletOrder walletOrder=new WalletOrder();
        walletOrder.setWalletOrderMoney(10);
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


    /*
     * 电影票支付钱包订单生成
     * @param walletOder
     * @param userId
     * @return 返回结果集合
     * */
    @ApiOperation(value = "生成钱包余额充值订单",notes = "")
    @PostMapping("/walletChargeOrder/{userId}")
    public Map createWalletChargeOrder(@PathVariable("userId")Integer userId,@RequestBody Integer money){
        WalletOrder walletOrder=new WalletOrder();
        walletOrder.setWalletOrderCreatetime(new Date());
        walletOrder.setUserId(userId);
        walletOrder.setWalletOrderMoney(money);
        walletOrder.setWalletOrderType(3);
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
