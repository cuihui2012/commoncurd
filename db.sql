--IP访问次数统计表
create table COLLECT_REMOTE_ADDR
(
  REMOTE_ADDR  VARCHAR2(64),
  REMOTE_COUNT NUMBER default 1,
  UPDATE_TIME  DATE default SYSDATE
);

--外部接口url配置表
create table API_URL_CONFIG
(
  V_ID        VARCHAR2(8) not null,
  V_TYPE      VARCHAR2(4),
  V_URL       VARCHAR2(200),
  UPDATE_TIME DATE default sysdate
);
comment on table API_URL_CONFIG
  is '外部接口url配置表';
-- Add comments to the columns 
comment on column API_URL_CONFIG.V_ID
  is 'url唯一标识';
comment on column API_URL_CONFIG.V_TYPE
  is '请求类型';
comment on column API_URL_CONFIG.V_URL
  is '接口url';
comment on column API_URL_CONFIG.UPDATE_TIME
  is '更新时间';
alter table API_URL_CONFIG
  add constraint PK_API_URL primary key (V_ID);
--初始化测试数据
insert into API_URL_CONFIG (V_ID, V_TYPE, V_URL, UPDATE_TIME)
values ('gdbvn', 'get', 'http://localhost:8080/common/getDataByViewName', to_date('14-12-2019 17:49:35', 'dd-mm-yyyy hh24:mi:ss'));
insert into API_URL_CONFIG (V_ID, V_TYPE, V_URL, UPDATE_TIME)
values ('aoudbtn', 'post', 'http://localhost:8080/common/addOrUpdateDataByTableNames', to_date('15-12-2019 11:52:01', 'dd-mm-yyyy hh24:mi:ss'));

