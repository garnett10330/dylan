CREATE DATABASE `weitest` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;



-- weitest.`3Preport_weekly_response_ratio` definition

CREATE TABLE `3Preport_weekly_response_ratio` (
  `sn` int NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
  `entpcode` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '廠編',
  `goodscode` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '品編',
  `c_msgtime` datetime DEFAULT NULL COMMENT 'user發文最早時間',
  `e_msgtime` datetime DEFAULT NULL COMMENT '廠商發文最早時間',
  `roomid` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '聊天室id',
  `ticketid` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '聊天室ticket id',
  `e_unread` tinyint DEFAULT NULL COMMENT '廠商未讀旗標_0已讀_1未讀',
  `resp_elapsed_hr` float DEFAULT NULL COMMENT '廠商回應時間小時',
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO weitest.`3Preport_weekly_response_ratio`
(sn, entpcode, goodscode, c_msgtime, e_msgtime, roomid, ticketid, e_unread, resp_elapsed_hr)
VALUES(1, 'TP1234567', 'Test123', '2024-07-18 10:54:37', NULL, '1|202311463370|TP1234567', '2024070001', 1, -1.0);
INSERT INTO weitest.`3Preport_weekly_response_ratio`
(sn, entpcode, goodscode, c_msgtime, e_msgtime, roomid, ticketid, e_unread, resp_elapsed_hr)
VALUES(2, 'TP7777799', 'Test125', '2024-07-19 14:35:42', '2024-07-19 14:39:12', '1|201708826077|TP7777799', '2024070002', 0, 1.0);
INSERT INTO weitest.`3Preport_weekly_response_ratio`
(sn, entpcode, goodscode, c_msgtime, e_msgtime, roomid, ticketid, e_unread, resp_elapsed_hr)
VALUES(3, 'TP8777799', 'Test125', '2024-08-20 14:35:42', '2024-08-20 14:39:12', '1|201708826077|TP8777799', '2024080002', 0, 1.0);

-- weitest.company_stock definition

CREATE TABLE `company_stock` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'mysql流水號',
  `symbol` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '股票代號',
  `name` varchar(225) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '股票名稱',
  `stock_exchange` varchar(225) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '交易所名稱',
  `exchange_short_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '交易所名稱縮寫',
  `currency` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '幣值',
  `gmt_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19576 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


