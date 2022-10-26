create table access
(
    id          int auto_increment
        primary key,
    count       int        default 0      not null,
    warning     tinyint(1) default 0      not null,
    create_time datetime                  not null,
    threshold   int        default 100000 null,
    token       varchar(500)              not null,
    cleanday    int        default 0      not null,
    constraint access_id_uindex
        unique (id)
);



create table mp4url
(
    id          int auto_increment,
    url         varchar(256) not null,
    create_time datetime      not null,
    create_user varchar(40)   not null comment '创建时间',
    state       int default 0 not null comment '看过的人数',
    sisk        int default 0 not null comment '看过的人数',
    remark      varchar(100)  null comment '备注',
    constraint mp4url_url_uindex
        unique (url),
    constraint url_id_uindex
        unique (id)
);

alter table mp4url
    add primary key (id);


create table imagesurl
(
    id          int auto_increment,
    url         varchar(700)  not null,
    state       int default 0 not null,
    type        varchar(10)   not null,
    bz          varchar(200)  not null,
    create_time datetime      not null,
    create_user varchar(40)   not null,
    constraint imagesurl_id_uindex
        unique (id),
    constraint imagesurl_url_uindex
        unique (url)
);

alter table imagesurl
    add primary key (id);



create table ipaddress
(
    id          int auto_increment
        primary key,
    ip          varchar(40)  not null,
    address     varchar(100) not null,
    create_time datetime     not null,
    constraint ipaddress_id_uindex
        unique (id),
    constraint ipaddress_ip_uindex
        unique (ip)
);


create table ipaddress
(
    id          int auto_increment,
    ip          varchar(40)  not null,
    address     varchar(100) not null,
    create_time datetime     not null,
    constraint ipaddress_id_uindex
        unique (id),
    constraint ipaddress_ip_uindex
        unique (ip)
);

alter table ipaddress
    add primary key (id);




create table user
(
    id          int auto_increment
        primary key,
    username    varchar(40)                not null,
    password    varchar(40)                not null,
    email       varchar(100)               not null,
    permission  varchar(10) default 'user' not null,
    create_time datetime                   null,
    token       varchar(200)               not null,
    constraint user_username_uindex
        unique (username)
);





create table vipvideo
(
    id          int auto_increment,
    url         varchar(300) not null,
    create_time datetime     not null,
    create_user varchar(20)  not null,
    constraint vipvideo_id_uindex
        unique (id),
    constraint vipvideo_url_uindex
        unique (url)
);

alter table vipvideo
    add primary key (id);



create table announcement
(
    context     varchar(500) not null,
    create_user varchar(20)  null,
    create_time datetime     not null
);






create table link
(
    id          int auto_increment
        primary key,
    url         varchar(100)  not null,
    risk        int default 0 not null,
    create_time datetime      not null,
    token       varchar(100)  not null,
    constraint link_id_uindex
        unique (id)
);





INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (1, 'https://apd-vlive.apdcdn.tc.qq.com/vipzj.video.tc.qq.com/w0017mxh0c4.mp4?vkey=1A3A2FD5B8064F387747B82416951BABC78E26F0A940D32225FE371E6C037EE69FECBF185B4DDA29C823DC2243A733089804AEE0F649C2D16D2696177DD3FF0C86AF4A578C437C0806AE1B8720BF1CC413B64D19A704825C825ED707FA23A6C4A856FCA698370F92080C3371DD3701E56BB6DDBCF7E011D6DB1F0535F5B4258C2520C59342DC36EFFCD5142CE278C64D', '2022-07-19 12:31:51', 'chen', 1, 0, '名侦探柯南');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (2, 'https://lf9-cdn-tos.bytecdntp.com/cdn/expire-1-M/byted-player-videos/1.0.0/xgplayer-demo.mp4', '2022-07-19 12:38:21', 'chen', 1, 0, '西瓜视频');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (3, 'http://vd3.bdstatic.com/mda-nad1xqh2ewjyn6wn/360p/h264_delogo/1642123652079019842/mda-nad1xqh2ewjyn6wn.mp4', '2022-07-19 12:42:24', 'chen', 1, 0, '烟');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (4, 'http://txmov2.a.yximgs.com/upic/2019/06/11/23/BMjAxOTA2MTEyMzQ2MTBfMjg5NzE0Mjc5XzEzOTgzNDc3NDIwXzFfMw==_b_B1bd5a56b98aae13173090c233d14b36f.mp4', '2022-07-19 12:43:58', 'chen', 1, 0, '视频');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (5, 'http://hwmov.a.yximgs.com/upic/2018/12/29/22/BMjAxODEyMjkyMjAzMzNfNjQ3NzY3NDQxXzk2MjgyMTg3NjVfMV8z_b_B1db25cf283b3af3ac2b7578187567c74.mp4', '2022-07-19 12:43:58', 'chen', 1, 0, '视频');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (6, 'http://jsmov2.a.yximgs.com/upic/2019/05/17/21/BMjAxOTA1MTcyMTA1MDVfMjM2NzkyNTA0XzEzMTUxNTU1Mzg3XzFfMw==_b_Badda6f9ebb6f8ab610876f88beeea053.mp4', '2022-07-28 22:15:48', 'chen', 1, 0, '视频1');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (7, 'http://txmov2.a.yximgs.com/upic/2019/06/24/21/BMjAxOTA2MjQyMTUxNTdfNDc3Mzc2MTZfMTQ0MjYzNzE5NTVfMV8z_b_B7b8ebc5e3bd5579d901301fa6019b611.mp4', '2022-07-28 22:18:21', 'chen', 1, 0, '视频');
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (8, 'http://txmov2.a.yximgs.com/upic/2019/10/06/22/BMjAxOTEwMDYyMjA5MTZfMzg5OTM5OV8xODI4NTQ2NDY4M18xXzM=_b_Bbd1ba46d39a007834a546214a77293ee.mp4', '2022-07-28 22:19:04', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (9, 'http://txmov2.a.yximgs.com/upic/2018/10/29/21/BMjAxODEwMjkyMTMzMzRfMjIxNjcxNDIyXzg2ODM0ODU0MjRfMV8z_b_Ba55ec398ce0d04afc9f21eb2ccf17a74.mp4', '2022-07-28 22:19:55', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (10, 'http://hwmov.a.yximgs.com/upic/2019/05/11/01/BMjAxOTA1MTEwMTIwMTFfMzg1NTQxNTk1XzEyOTQ3MjI0ODM1XzJfMw==_b_B659d428c9ba828b06299e3d5764277b0.mp4', '2022-07-28 22:20:51', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (11, 'http://txmov2.a.yximgs.com/upic/2016/02/17/06/BMjAxNjAyMTcwNjIzMDNfMTMxOTAyMDc3XzU4ODIwMjI0Nl8xXzM=.mp4', '2022-07-28 22:21:13', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (12, 'http://jsmov2.a.yximgs.com/upic/2018/10/28/16/BMjAxODEwMjgxNjUzNDZfODkzODUyNTU5Xzg2NjU1NzA2NTVfMV8z_b_Bff1f7980fe72587194f7fc1813549980.mp4', '2022-07-28 22:21:55', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (13, 'http://alimov2.a.yximgs.com/upic/2018/10/09/23/BMjAxODEwMDkyMzM2MTlfNzYyNjk0NjIwXzg0MDQwODY2MzRfMV8z_b_B5dbe64eabd968b286239e54ef2e088b4.mp4', '2022-07-28 22:24:04', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (14, 'http://jsmov2.a.yximgs.com/upic/2019/04/14/16/BMjAxOTA0MTQxNjIwMTFfOTgzNzc0ODdfMTIyMDE1MDA2MjlfMV8z_b_Bb26c8107f5f6c7ff88934c04e1f79d3a.mp4', '2022-07-29 22:24:22', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (15, 'http://txmov2.a.yximgs.com/upic/2019/06/28/11/BMjAxOTA2MjgxMTUxMTNfMTIwMDYzMjc5OF8xNDUzODA3Mzg4NF8xXzM=_b_B141bb31cdd8424c4909e729ce2254799.mp4', '2022-07-28 22:24:58', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (16, 'http://alimov2.a.yximgs.com/upic/2019/02/05/20/BMjAxOTAyMDUyMDQ3MzZfMTEzMzYwODE0XzEwNTE1NDk2MjE3XzFfMw==_b_Bbb3952b5905ad3548e85517c8c2c9fc2.mp4', '2022-07-28 22:25:22', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (17, 'http://txmov3.a.yximgs.com/upic/2018/11/20/21/BMjAxODExMjAyMTIzMDBfNDcwNDY2MzRfOTAwNjQ5MzA0M18xXzM=_b_Bd820d62fdca4106d8c93579029b56cfe.mp4', '2022-07-28 22:25:41', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (18, 'http://jsmov2.a.yximgs.com/upic/2019/05/13/20/BMjAxOTA1MTMyMDEzNDRfNDUwMjkyNjEzXzEzMDQxMjM2NDQxXzFfMw==_b_B0528850dec1f3d1650a0b36b8021c334.mp4', '2022-07-28 22:26:00', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (19, 'http://txmov2.a.yximgs.com/upic/2019/06/09/08/BMjAxOTA2MDkwODQ3NDlfMTM1MDM1MTYxMV8xMzg5MzU2NDY4MV8xXzM=_b_Bb7f864c830549ad74390af03ddd3842e.mp4', '2022-07-28 22:26:44', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (20, 'http://txmov2.a.yximgs.com/upic/2019/10/05/19/BMjAxOTEwMDUxOTI4MzNfMTU2MDE1OTBfMTgyMzU5MDg5MTZfMV8z_b_Bd1cbd1bc0591449baec137623cc2f4ae.mp4', '2022-07-28 22:27:23', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (21, 'http://cdn.video.picasso.dandanjiang.tv/5b8bed9031f613614e13d002.mp4?sign=7cd04e82d135239502542008c4a70658&t=62e60d6a', '2022-07-31 11:05:52', 'chen
', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (22, 'http://cdn.video.picasso.dandanjiang.tv/59f7f6fd31f61342921d4b4d.mp4?sign=f87f828fdc3549e29ae630e8257cbe99&t=62e60e07', '2022-07-31 11:07:38', 'chen', 1, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (23, 'http://cdn.video.picasso.dandanjiang.tv/5aa2442631f613444a4a05af.mp4?sign=3c7cd88866ea8a2d963c291e5f2e7cba&t=62e60e46', '2022-07-31 11:09:04', 'chen', 0, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (24, 'http://cdn.video.picasso.dandanjiang.tv/5a795ebd31f6130fd30b2d67.mp4?sign=230112ebb3a456bebf26749a2c9c1bca&t=62e60e58', '2022-07-31 11:09:07', 'chen', 0, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (25, 'http://cdn.video.picasso.dandanjiang.tv/5b24e4dd31f6131de8b9a28d.mp4?sign=709658c8040f4b227daa5e8a827f09a3&t=62e60e67', '2022-07-31 11:09:08', 'chen', 0, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (26, 'http://cdn.video.picasso.dandanjiang.tv/59a53f14e7bce71161081b69.mp4?newver=0.608625336846&sign=a03e3e6fc4ad027d4e6042ecf09b3334&t=62e60e7d', '2022-07-31 11:09:28', 'chen', 0, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (27, 'http://cdn.video.picasso.dandanjiang.tv/59a53f14e7bce71161081b69.mp4?newver=0.608625336846&sign=a03e3e6fc4ad027d4e6042ecf09b3334&t=62e60e7d', '2022-07-31 11:09:49', 'chen', 0, 0, null);
INSERT INTO network.mp4url (id, url, create_time, create_user, state, sisk, remark) VALUES (28, 'http://cdn.video.picasso.dandanjiang.tv/594b834fe7bce75c0b5d794d.mp4?newver=0.373627986434&sign=f21a8ea013b92d062c31840f6202d8bb&t=62e60ea8', '2022-07-31 11:09:51', 'chen', 0, 0, null);



INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (1, 'https://w.wallhaven.cc/full/j3/wallhaven-j3g7yy.jpg', 1, '风景', '动漫风景', '2022-07-19 19:57:08', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (2, 'https://w.wallhaven.cc/full/rd/wallhaven-rdwjj7.jpg', 1, '动漫', '动漫女草帽', '2022-07-19 20:43:27', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (3, 'https://w.wallhaven.cc/full/y8/wallhaven-y8vlyk.jpg', 1, '动漫', '河边人', '2022-07-19 20:45:13', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (4, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2020-07-10%2F5f08277eb034a.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660829696&t=615b8c36fcfc91840a656b6e1d511640', 1, '动漫', '萌系可爱初音未来桌面壁纸', '2022-07-19 21:35:36', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (5, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F0F620093053%2F200F6093053-9-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660829696&t=00114377e642f067920385a91db199b0', 1, '动漫', '二次元动漫美女桌面壁纸', '2022-07-19 21:36:49', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (6, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1112%2F12091Q03R7%2F1Q209103R7-5.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660829696&t=48004dc362d31a106fcc8a384c8a3dc9', 1, '动漫', '唯美动漫美女壁纸大全', '2022-07-19 21:40:07', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (7, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2020-02-26%2F5e562b96c4da2.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660829696&t=2651996ce38de88da710d184c922d6b9', 1, '动漫', '唯美二次元美女插画高清桌面壁纸', '2022-07-19 21:42:05', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (8, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-12-27%2F5a4366ec5b18d.png%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1660829981&t=bd5fd84791b218edb7dbd00193ff8302', 1, '动漫', '精美日系动漫同人作品精选高清宽屏桌面壁纸', '2022-07-19 21:42:43', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (9, 'https://upload.zang.link/articles/d2ad6d32c459cf2e3f8120acff277b4a.jpg', 1, '动漫', '萝莉-椅子', '2022-07-19 21:43:42', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (10, 'https://s1.ax1x.com/2022/07/01/jMrbhd.png', 1, '动漫', '动漫草坪女天空', '2022-07-19 21:49:02', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (11, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-06-19%2F5d09f874606c9.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659347889&t=228972a2955ff664e2d2e6ecb1efb27c', 1, '风景', '动漫风景', '2022-07-19 21:57:06', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (12, 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp3.itc.cn%2Fq_70%2Fimages03%2F20201103%2F234112bd0fd9404386f972a7ddc357a4.png&refer=http%3A%2F%2Fp3.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659173015&t=374d71ed594eb251ef818f0756d0dddc', 1, '风景', '风景黄昏天空', '2022-07-19 21:57:39', 'chen');
INSERT INTO network.imagesurl (id, url, state, type, bz, create_time, create_user) VALUES (13, 'https://img.blog.ymeety.com/wp-content/themes/ASky/images/temp.jpg', 1, '动漫', '狐狸女', '2022-07-19 21:59:20', 'chen');


INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (1, 'https://jx.xmflv.com/?url=', '2022-07-20 14:17:39', 'chen');
INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (2, 'http://jx.nikucms.com/pc/?url=', '2022-07-20 17:43:55', 'chen');
INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (3, 'https://www.1717yun.com/api/?url=', '2022-07-20 17:46:25', 'chen');
INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (4, 'https://jx.bozrc.com:4433/player/?url=', '2022-07-20 17:49:48', 'chen');
INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (5, 'https://www.mtosz.com/erzi.php?url=', '2022-07-20 17:55:21', 'chen');
INSERT INTO network.vipvideo (id, url, create_time, create_user) VALUES (6, 'https://okjx.cc/?url=', '2022-07-21 08:10:02', 'chen');
