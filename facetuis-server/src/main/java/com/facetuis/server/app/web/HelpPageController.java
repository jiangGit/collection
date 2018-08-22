package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*新手必看帮助页面
* */
@RestController
@RequestMapping("/1.0/helppage")
public class HelpPageController extends FacetuisController {

    public class HelpPage{
        private String title;
        private String content;
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse list(){
        HelpPage hp1 = new HelpPage();
        hp1.setTitle("自己下单有佣金吗？");
        hp1.setContent("有的，自己购买或者分享给朋友，您都可以拿到佣金");
        HelpPage hp2 = new HelpPage();
        hp2.setTitle("脸推APP是拼多多官方的吗？");
        hp2.setContent("脸推是拼多多第三方合作平台，不是官方");
        HelpPage hp3 = new HelpPage();
        hp3.setTitle("怎么进行裂变？");
        hp3.setContent("现在的裂变也是需要一定的资源，并非人人都能裂变，靠的是坚持。有资源就利用资源，没有资源就找资源，微信人多、群多、微商多都是很好的切入点。");
        HelpPage hp4 = new HelpPage();
        hp4.setTitle("自己购买的，在拼多多可以看到订单吗？");
        hp4.setContent("因为您是通过跳转拼多多网页版购买的商品，登陆账号是手机号。您通过APP打开的话是直接微信登陆，不是一个账号。");
        HelpPage hp5 = new HelpPage();
        hp5.setTitle("为什么App头条里面给我通知了出单消息，我这边找不到呢？");
        hp5.setContent("请注意查看订单出单时间，并非消息发送时间，所以请悉知");
        HelpPage hp6 = new HelpPage();
        hp6.setTitle("自己买的商品，订单在哪里看？");
        hp6.setContent("自买的商品在“我的订单”里面看，团队的订单在我的团队里面看");
        HelpPage hp7 = new HelpPage();
        hp7.setTitle("为什么下级出单了，我这边看不到订单和佣金？");
        hp7.setContent("自己的单子可以在我的订单查看，团队的订单和佣金需要在我的团队中查看。");
        HelpPage hp71 = new HelpPage();
        hp71.setTitle("为什么领了优惠券，下单不会扣优惠券呢？");
        hp71.setContent("可能是满减券的那种，遇到的时候可以提供给我们，我们这边提交给拼多多官方处理。");
        HelpPage hp8 = new HelpPage();
        hp8.setTitle("我是超级团长，我的下级出单，我能得到多少佣金？");
        hp8.setContent("超级团长可以获得自己招募的直属团长的约20%的佣金奖励。");
        HelpPage hp9 = new HelpPage();
        hp9.setTitle("升级SVIP总监需要交钱吗？");
        hp9.setContent("需要，99/月或880/年");
        HelpPage hp10 = new HelpPage();
        hp10.setTitle("直接交钱就可以做SVIP总监吗？");
        hp10.setContent("不是的，需要完成基础的升级条件，才可以付费成为SVIP总监的，具体的升级条件，可参看App内升级介绍。");
        HelpPage hp11 = new HelpPage();
        hp11.setTitle("订单佣金什么时候能提现？");
        hp11.setContent("每月21日可以提现本月15号之前已经完结的订单");
        HelpPage hp111 = new HelpPage();
        hp111.setTitle("下单了怎么样申请退款？");
        hp111.setContent("如果是在App里面分享自买的，请用手机号登陆网页版拼多多即可申请退款，如果是分享到微信下单的，请在微信里打开拼多多公众号就可以进入申请退款的。");
        HelpPage hp12 = new HelpPage();
        hp12.setTitle("超级团长能拿到几级会员的佣金？");
        hp12.setContent("超级团长可以拿到直属超级团长的佣金，也可以拿到自己直属SVIP总监本人及其一二级下线的所有人的部分佣金");
        HelpPage hp13 = new HelpPage();
        hp13.setTitle("升级SVIP总监后，一个月后没续费，之后续费还行吗？");
        hp13.setContent("是可以的，但是需要重新满足之前升级的基础条件一");
        HelpPage hp15 = new HelpPage();
        hp15.setTitle("在微信里面注册过之后，可以直接在App里面选择微信登陆吗？");
        hp15.setContent("可以的");
        HelpPage hp16 = new HelpPage();
        hp16.setTitle("拼多多客服电话有吗？");
        hp16.setContent("消费者热线：021-61897088");
        HelpPage hp17 = new HelpPage();
        hp17.setTitle("点击别人发的邀请链接后，提示微信已经绑定，怎么办？");
        hp17.setContent("说明您的微信已经授权过，不需要再次注册，可以直接下载App登陆使用");
        HelpPage hp18 = new HelpPage();
        hp18.setTitle("点击别人的邀请链接后，是直接绑定邀请码了吗？");
        hp18.setContent("注册成功后是直接绑定关系的。");
        HelpPage hp19 = new HelpPage();
        hp19.setTitle("我是超级团长，我的直属升级了SVIP总监，我有什么好处？");
        hp19.setContent("超级团长只能享受直属的订单佣金，但是如果直属升级SVIP总监后，您就可以享受三级的所有人的6%提成。");
        HelpPage hp20 = new HelpPage();
        hp20.setTitle("总监可以查看自己直属SVIP总监的订单收益吗？");
        hp20.setContent("不能查看单个团队详情，但是只要他本人及其团队出单，您在团队订单可以看订单明细");
        HelpPage hp21 = new HelpPage();
        hp21.setTitle("登陆App时，点绑定微信后无法正常登陆？");
        hp21.setContent("授权登陆的微信号要先设置好微信号，否则可能会授权不了，请先打开手机微信，设置好微信号，重新打开脸推App授权即可。");
        HelpPage hp22 = new HelpPage();
        hp22.setTitle("下载App后，输入手机号及验证码就自动登陆了，没输入邀请码是为什么呢？");
        hp22.setContent("授权登陆的微信号要先设置好微信号，否则可能会授权不了，请先打开手机微信，设置好微信号，重新打开脸推App授权即可。");
        HelpPage hp23 = new HelpPage();
        hp23.setTitle("买家退货，退货邮费是谁承担？");
        hp23.setContent("拼多多平台退货规则处理的哦，一般质量问题肯定是商家承担退货邮费，但建议退货之前先跟商家沟通。");
        HelpPage hp24 = new HelpPage();
        hp24.setTitle("升级SVIP总监后，邀请码可以换吗，对之前邀请码有没有影响？");
        hp24.setContent("升级SVIP总监后可以更换一次，对之前没有影响，老的邀请链接发出去的，客户打开后依然是绑定您。");
        HelpPage hp25 = new HelpPage();
        hp25.setTitle("为什么自己在App里购买的商品自动退款了？");
        hp25.setContent("有可能您下单的产品需要拼团，虽然您付款了，但是拼图没有完成，系统自动退款并关闭了交易");
        HelpPage hp26 = new HelpPage();
        hp26.setTitle("我是SVIP总监，如何通过手机号知道他是不是我团队里面的呢？");
        hp26.setContent("登陆App-个人中心-我的团队-总人数，里面搜索手机号，是的话就会显示，不是的话就不会显示。");
        HelpPage hp27 = new HelpPage();
        hp27.setTitle("为什么我在APP里面自购的时候会需要拼团呢？");
        hp27.setContent("这种情况一般是您手机安装了拼多多APP导致，需要等拼多多App升级后，可以解决该问题，您也可以换个手机下单，就可以解决。");
        HelpPage hp29 = new HelpPage();
        hp29.setTitle("有发单机器人吗？");
        hp29.setContent("有的，发单机器人免费支持用户使用。可以多开，支持挂电脑或服务器。");

        List<HelpPage> list = new ArrayList<>();
        list.add(hp1);
        list.add(hp2);
        list.add(hp3);
        list.add(hp4);
        list.add(hp5);
        list.add(hp6);
        list.add(hp7);
        list.add(hp8);
        list.add(hp9);
        list.add(hp10);
        list.add(hp11);
        list.add(hp12);
        list.add(hp13);
        list.add(hp71);
        list.add(hp15);
        list.add(hp16);
        list.add(hp17);
        list.add(hp18);
        list.add(hp19);
        list.add(hp20);
        list.add(hp21);
        list.add(hp22);
        list.add(hp23);
        list.add(hp24);
        list.add(hp25);
        list.add(hp26);
        list.add(hp27);
        list.add(hp111);
        list.add(hp29);
        Page<HelpPage> page = new PageImpl<>(list);
        return successResult(page);
    }

}
