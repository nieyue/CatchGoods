# 数据库 
#创建数据库
DROP DATABASE IF EXISTS catch_goods_db;
CREATE DATABASE catch_goods_db;

#使用数据库 
use catch_goods_db;

#创建角色表
CREATE TABLE role_tb(
role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建账户表 
CREATE TABLE acount_tb(
acount_id int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
nickname varchar(255) COMMENT '昵称',
icon varchar(255) COMMENT '图像',
scale decimal(11,2) DEFAULT 0 COMMENT '合伙人收益比例',
openid varchar(255) COMMENT 'openid',
uuid varchar(255) COMMENT 'uuid',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
country varchar(255) COMMENT '国家',
province varchar(255) COMMENT '省',
city varchar(255) COMMENT '城市',
realname varchar(255) COMMENT '真实姓名',
phone varchar(255) COMMENT '电话',
email varchar(255) COMMENT 'email',
password varchar(255) COMMENT '密码',
identity_cards varchar(255) COMMENT '身份证',
qq varchar(255) COMMENT 'QQ',
wechat varchar(255) COMMENT '微信号',
microblog varchar(255) COMMENT '微博',
alipay varchar(255) COMMENT '支付宝账号',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1锁定，2，异常',
spread_id int(11) COMMENT '推广id',
master_id int(11) COMMENT '师傅id',
role_id int(11) COMMENT '角色id外键',
PRIMARY KEY (acount_id),
INDEX INDEX_SPREADID (spread_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建财务表 
CREATE TABLE finance_tb(
finance_id int(11) NOT NULL AUTO_INCREMENT COMMENT '财务id',
money decimal(11,2) DEFAULT 0 COMMENT '余额',
recharge decimal(11,2) DEFAULT 0 COMMENT '充值金额',
consume decimal(11,2) DEFAULT 0 COMMENT '消费金额',
withdrawals decimal(11,2) DEFAULT 0 COMMENT '提现金额',
self_profit decimal(11,2) DEFAULT 0 COMMENT '自身总收益',
partner_profit decimal(11,2) DEFAULT 0 COMMENT '合伙人总收益',
base_profit decimal(11,2) DEFAULT 0 COMMENT '赠送金钱',
bank_user_name varchar(255) COMMENT '开户人',
bank_name varchar(255) COMMENT '开户银行',
bank_account varchar(255) COMMENT '银行账号',
bank_address varchar(255) COMMENT '开户银行地址',
update_date datetime COMMENT '更新时间',
acount_id int(11) COMMENT '账户id外键',
PRIMARY KEY (finance_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='财务表';

#创建物品类型表 
CREATE TABLE goods_cate_tb(
goods_cate_id int(11) NOT NULL AUTO_INCREMENT COMMENT '物品类型id',
name varchar(255) COMMENT '物品类型名称',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (goods_cate_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='物品类型表';

#创建物品表 
CREATE TABLE goods_tb(
goods_id int(11) NOT NULL AUTO_INCREMENT COMMENT '物品id',
goods_name varchar(255) COMMENT '物品名称',
img_address varchar(255) COMMENT '封面',
camera_number tinyint(4) DEFAULT 1 COMMENT '摄像头个数',
single_charge decimal(11,2) DEFAULT 0  COMMENT '单次收费',
free_number int(11) DEFAULT 0  COMMENT '免费已玩次数',
charge_number int(11) DEFAULT 0  COMMENT '收费已玩次数',
total_charge decimal(11,2) DEFAULT 0  COMMENT '总收费',
recommend tinyint(4) DEFAULT 0 COMMENT '推荐，默认0不推，1封推，2精推，3优推',
pvs bigint(20) DEFAULT 0 COMMENT 'pv',
uvs bigint(20) DEFAULT 0 COMMENT 'uv',
ips bigint(20) DEFAULT 0 COMMENT 'ip',
reading_number bigint(20) DEFAULT 0 COMMENT '阅读数',
status tinyint(4) DEFAULT 1 COMMENT '0故障,1空闲中，2使用中',
goods_cate_id int(11) COMMENT '物品类型id,外键',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
PRIMARY KEY (goods_id),
INDEX INDEX_RECOMMEND (recommend) USING BTREE,
INDEX INDEX_SINGLECHARGE (single_charge) USING BTREE,
INDEX INDEX_CHARGENUMBER (charge_number) USING BTREE,
INDEX INDEX_READINGNUMBER (reading_number) USING BTREE,
INDEX INDEX_ROOMCATEID (goods_cate_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='物品表';

#创建公告表 
CREATE TABLE notice_tb(
notice_id int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
title varchar(255) COMMENT '标题',
img_address varchar(255) COMMENT '图片地址',
content longtext COMMENT '内容',
create_date datetime COMMENT '创建时间',
PRIMARY KEY (notice_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='公告表';

#创建收货信息表 
CREATE TABLE receipt_info_tb(
receipt_info_id int(11) NOT NULL AUTO_INCREMENT COMMENT '收货信息id',
name varchar(255) COMMENT '收货地址姓名',
phone varchar(255) COMMENT '收货地址手机号',
address varchar(255) COMMENT '收货地址',
is_default tinyint(4) DEFAULT 0 COMMENT '默认为0不是，1是',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
acount_id int(11) COMMENT '账户id,外键',
PRIMARY KEY (receipt_info_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_ISDEFAULT (is_default) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='收货地址表 ';

#创建充值项表 
CREATE TABLE recharge_term_tb(
recharge_term_id int(11) NOT NULL AUTO_INCREMENT COMMENT '充值项id',
title varchar(255) COMMENT '附加标题',
recharge_money decimal(11,2) COMMENT '充值真钱',
give_money decimal(11,2) COMMENT '赠送金额',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
status tinyint(4) DEFAULT 0 COMMENT '0下架，默认为1上架',
PRIMARY KEY (recharge_term_id),
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='充值项表 ';

#创建充值记录表 
CREATE TABLE recharge_record_tb(
recharge_record_id int(11) NOT NULL AUTO_INCREMENT COMMENT '充值记录id',
type tinyint(4) COMMENT '充值类型，1支付宝支付，2微信支付，3银联支付',
money decimal(11,2) COMMENT '金额',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
status tinyint(4) DEFAULT 0 COMMENT '默认为1成功，2失败',
acount_id int(11) COMMENT '账户id,外键',
PRIMARY KEY (recharge_record_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='充值记录表 ';

#创建抓取记录表 
CREATE TABLE catch_record_tb(
catch_record_id int(11) NOT NULL AUTO_INCREMENT COMMENT '抓取记录id',
name varchar(255) COMMENT '抓取物品名称',
img_address varchar(255) COMMENT '封面图',
money decimal(11,2) DEFAULT 0 COMMENT '消费金额',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
status tinyint(4) DEFAULT 0 COMMENT '抓取状态，默认为1成功，2失败',
goods_id int(11) COMMENT '物品id,外键',
acount_id int(11) COMMENT '账户id,外键',
PRIMARY KEY (catch_record_id),
INDEX INDEX_GOODSID (goods_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='抓取记录表 ';

#创建物品订单表 
CREATE TABLE goods_order_tb(
goods_order_id int(11) NOT NULL AUTO_INCREMENT COMMENT '物品订单id',
order_number varchar(255) COMMENT '订单号',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
acount_id int(11) COMMENT '下单人',
PRIMARY KEY (goods_order_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='物品订单表';

#创建物品订单详情表 
CREATE TABLE goods_order_detail_tb(
goods_order_detail_id int(11) NOT NULL AUTO_INCREMENT COMMENT '物品订单详情id',
goods_name varchar(255) COMMENT '物品名称',
img_address varchar(255) COMMENT '封面',
name varchar(255) COMMENT '收货地址姓名',
phone varchar(255) COMMENT '收货地址手机号',
address varchar(255) COMMENT '收货地址',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
status tinyint(4) COMMENT '订单状态，0已下单，1已发货',
goods_order_id int(11) COMMENT '物品订单ID',
PRIMARY KEY (goods_order_detail_id),
INDEX INDEX_GOODSORDERID (goods_order_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='物品订单详情表';

#创建app版本表 
CREATE TABLE app_version_tb(
app_version_id int(11) NOT NULL AUTO_INCREMENT COMMENT 'app版本id',
platform tinyint(4)  COMMENT 'app平台，默认0安卓，1为IOS',
name varchar(255)  COMMENT 'app版本名',
type tinyint(4) DEFAULT 0 COMMENT 'app类型，默认0普通，1为强制',
content varchar(255)  COMMENT 'app更新内容',
link varchar(255)  COMMENT 'app版本链接',
update_date datetime COMMENT '更新时间',
status tinyint(4)  COMMENT 'app状态，默认0上线，1为未上线',
PRIMARY KEY (app_version_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='app版本表';

#创建推广表 
CREATE TABLE spread_tb(
spread_id int(11) NOT NULL AUTO_INCREMENT COMMENT '推广id',
platform tinyint(4) DEFAULT 0  COMMENT '推广平台，默认0安卓，1为IOS',
name varchar(255)  COMMENT '推广名',
method varchar(255) COMMENT '推广方式',
link varchar(255)  COMMENT 'app链接',
model tinyint(4) DEFAULT 0 COMMENT '计费方式，0注册，1下载',
unit_price decimal(11,2) DEFAULT 0 COMMENT '单价',
total_price decimal(11,2) DEFAULT 0 COMMENT '总价',
download_number bigint(20) DEFAULT 0 COMMENT '下载次数',
register_number bigint(20) DEFAULT 0 COMMENT '注册次数',
now_total_price decimal(11,2) DEFAULT 0 COMMENT '消耗金额',
create_date datetime COMMENT '创建时间',
end_date datetime COMMENT '结束时间',
status varchar(255) DEFAULT '正常' COMMENT '状态',
PRIMARY KEY (spread_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='推广表';

#创建每日数据表 
CREATE TABLE daily_data_tb(
daily_data_id int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
pvs bigint(20) COMMENT 'pvs',
uvs bigint(20) COMMENT 'uvs',
ips bigint(20) COMMENT 'ips',
reading_number bigint(20) COMMENT '阅读数',
create_date datetime COMMENT '创建时间',
goods_id int(11) COMMENT '物品id外键',
acount_id int(11) COMMENT '账户id外键',
PRIMARY KEY (daily_data_id),
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_GOODSID (goods_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
UNIQUE INDEX DAY_DATA (create_date,goods_id,acount_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='每日数据表';

#创建数据表 
CREATE TABLE data_tb(
data_id int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
pvs bigint(20) COMMENT 'pvs',
uvs bigint(20) COMMENT 'uvs',
ips bigint(20) COMMENT 'ips',
reading_number bigint(20) COMMENT '阅读数',
create_date datetime COMMENT '创建时间',
goods_id int(11) COMMENT '物品id外键',
acount_id int(11) COMMENT '账户id外键',
PRIMARY KEY (data_id),
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_GOODSID (goods_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
UNIQUE INDEX TIME_DATA (create_date,goods_id,acount_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='数据表';


#设置初始角色
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("超级管理员","超级管理员",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("运营管理员","运营管理员",now());
INSERT IGNORE INTO role_tb (name,duty,update_date) 
VALUES ("用户","用户",now());

#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO acount_tb (nickname,scale,phone,email,password,create_date,login_date,role_id) 
VALUES ("聂跃",0,"15111336587","278076304@qq.com","11874bb6149dd45428da628c9766b252",now(),now(),"1000"); 
#财务
INSERT IGNORE INTO finance_tb (money,recharge,consume,withdrawals,update_date,acount_id) 
VALUES (0,0,0,0,now(),1000);  