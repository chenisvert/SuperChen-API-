create table imagesurl
(
    id          int auto_increment
        primary key,
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

create table mp4url
(
    id          int auto_increment
        primary key,
    url         varchar(1000) not null,
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
    constraint admin_id_uindex
        unique (id),
    constraint user_tocken_uindex
        unique (token),
    constraint user_username_uindex
        unique (username)
);

create table vipvideo
(
    id          int auto_increment
        primary key,
    url         varchar(300) not null,
    create_time datetime     not null,
    create_user varchar(20)  not null,
    constraint vipvideo_id_uindex
        unique (id),
    constraint vipvideo_url_uindex
        unique (url)
);



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
