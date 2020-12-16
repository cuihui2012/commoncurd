--三张数据权限配置表
-- Create table
create table T_COMMON_IP_CONFIG_INFO
(
  IID         VARCHAR2(64) default sys_guid() not null,
  IP_ADDRESS  VARCHAR2(16) not null,
  IP_DESC     VARCHAR2(64),
  IS_USED     VARCHAR2(1) default '1' not null,
  IS_OPEND    VARCHAR2(1) default '0' not null,
  UPDATE_TIME DATE default sysdate
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table T_COMMON_IP_CONFIG_INFO
  is '应用IP基本信息维护表';
-- Add comments to the columns
comment on column T_COMMON_IP_CONFIG_INFO.IID
  is '主键ID';
comment on column T_COMMON_IP_CONFIG_INFO.IP_ADDRESS
  is '应用IP地址';
comment on column T_COMMON_IP_CONFIG_INFO.IP_DESC
  is '应用描述信息';
comment on column T_COMMON_IP_CONFIG_INFO.IS_USED
  is '使用标志 0-未使用 1-在用';
comment on column T_COMMON_IP_CONFIG_INFO.IS_OPEND
  is '开放标志 0-视图权限控制 1-全局开放';
comment on column T_COMMON_IP_CONFIG_INFO.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_COMMON_IP_CONFIG_INFO
  add constraint PK_IP_CONFIG_INFO primary key (IID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table T_COMMON_IP_CONFIG_INFO
  add constraint IP_ADDRESS_UNIQUE unique (IP_ADDRESS)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table T_COMMON_IP_VIEW_RELATION
(
  VID         VARCHAR2(64) default sys_guid() not null,
  IID         VARCHAR2(64) not null,
  VIEW_NAME   VARCHAR2(64) not null,
  UPDATE_TIME DATE default sysdate
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table T_COMMON_IP_VIEW_RELATION
  is '应用IP-视图关系对照表';
-- Add comments to the columns
comment on column T_COMMON_IP_VIEW_RELATION.VID
  is '主键ID';
comment on column T_COMMON_IP_VIEW_RELATION.IID
  is '应用IP基本信息外键';
comment on column T_COMMON_IP_VIEW_RELATION.VIEW_NAME
  is '视图名称';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_COMMON_IP_VIEW_RELATION
  add constraint PK_IP_VIEW_RELATION primary key (VID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table T_COMMON_IP_VIEW_RELATION
  add constraint IP_VIEW_UNIQUE unique (IID, VIEW_NAME)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

-- Create table
create table T_COMMON_VIEW_COLUMN_RELATION
(
  CID         VARCHAR2(64) default sys_guid() not null,
  VID         VARCHAR2(64) not null,
  COLUMN_NAME VARCHAR2(64) not null,
  AUTH_FLAG   VARCHAR2(1) default 'N' not null,
  UPDATE_TIME DATE default sysdate
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table T_COMMON_VIEW_COLUMN_RELATION
  is '应用IP-视图-字段关系对照表';
-- Add comments to the columns
comment on column T_COMMON_VIEW_COLUMN_RELATION.CID
  is '主键ID';
comment on column T_COMMON_VIEW_COLUMN_RELATION.VID
  is 'IP视图关系表外键';
comment on column T_COMMON_VIEW_COLUMN_RELATION.COLUMN_NAME
  is '字段名称';
comment on column T_COMMON_VIEW_COLUMN_RELATION.AUTH_FLAG
  is '字段权限标志 U-仅查询 C-查询/修改 N-无权限';
comment on column T_COMMON_VIEW_COLUMN_RELATION.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_COMMON_VIEW_COLUMN_RELATION
  add constraint PK_VIEW_COLUMN_RELATION primary key (CID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table T_COMMON_VIEW_COLUMN_RELATION
  add constraint VIEW_COLUMN_UNIQUE unique (VID, COLUMN_NAME)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

--公共接口访问日志表
-- Create table
create table T_COMMON_LOG_INFO
(
  LID          VARCHAR2(64) default sys_guid() not null,
  IP_ADDRESS   VARCHAR2(16),
  URI_PATH     VARCHAR2(64),
  URL_PATH     VARCHAR2(200),
  METHOD       VARCHAR2(8),
  PARAM_FOLLOW VARCHAR2(3000),
  PARAM_ALL    VARCHAR2(3000),
  UPDATE_TIME  DATE default sysdate
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table T_COMMON_LOG_INFO
  is '公共接口日志信息表';
-- Add comments to the columns
comment on column T_COMMON_LOG_INFO.LID
  is '日志主键ID';
comment on column T_COMMON_LOG_INFO.IP_ADDRESS
  is 'IP地址';
comment on column T_COMMON_LOG_INFO.URI_PATH
  is 'uri路径';
comment on column T_COMMON_LOG_INFO.URL_PATH
  is 'url路径';
comment on column T_COMMON_LOG_INFO.METHOD
  is '请求方法';
comment on column T_COMMON_LOG_INFO.PARAM_FOLLOW
  is '链接后缀参数';
comment on column T_COMMON_LOG_INFO.PARAM_ALL
  is '所有请求参数';
comment on column T_COMMON_LOG_INFO.UPDATE_TIME
  is '插入时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_COMMON_LOG_INFO
  add constraint PK_LOG_INFO primary key (LID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

--外部接口url配置表
-- Create table
create table T_COMMON_API_URL_CONFIG
(
  V_ID        VARCHAR2(8) not null,
  V_TYPE      VARCHAR2(4),
  V_URL       VARCHAR2(200),
  UPDATE_TIME DATE default sysdate
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table T_COMMON_API_URL_CONFIG
  is '外部接口url配置表';
-- Add comments to the columns
comment on column T_COMMON_API_URL_CONFIG.V_ID
  is 'url唯一标识';
comment on column T_COMMON_API_URL_CONFIG.V_TYPE
  is '请求类型';
comment on column T_COMMON_API_URL_CONFIG.V_URL
  is '接口url';
comment on column T_COMMON_API_URL_CONFIG.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table T_COMMON_API_URL_CONFIG
  add constraint PK_API_URL primary key (V_ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
--初始化测试数据
insert into t_common_api_url_config (V_ID, V_TYPE, V_URL, UPDATE_TIME)
values ('gdbvn', 'get', 'http://localhost:8080/common/getDataByViewName', to_date('14-12-2019 17:49:35', 'dd-mm-yyyy hh24:mi:ss'));

insert into t_common_api_url_config (V_ID, V_TYPE, V_URL, UPDATE_TIME)
values ('aoudbtn', 'post', 'http://localhost:8080/common/addOrUpdateDataByTableNames', to_date('15-12-2019 11:52:01', 'dd-mm-yyyy hh24:mi:ss'));
