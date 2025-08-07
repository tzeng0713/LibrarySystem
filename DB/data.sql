
INSERT INTO book (isbn, name, author, introduction) VALUES
('B001', '森林裡的小狐狸', '李明慧', '這是一個關於小狐狸尋找家的溫馨故事，透過繪本風格，帶領孩子探索勇氣與友情的價值。'),
('B002', '月亮的禮物', '黃曉晴', '每到夜晚，月亮總會送出一份神秘禮物。這本色彩繽紛的繪本讓孩子學會分享與感恩。'),
('B003', '總裁的契約新娘', '蘇芷涵', '一紙契約，讓他娶了她。她本以為這段婚姻只是交易，卻不知已走入他的世界與心。'),
('B004', '冷酷總裁的獨寵', '喬安琪', '他是冷酷無情的商界霸主，卻在遇見她後漸漸卸下心防。這是一場愛與信任的角力。'),
('B005', '你很好，值得被愛', '林子柔', '獻給在低潮中努力生活的你。每一篇文字都是一道溫柔的光，照亮你心中最柔軟的角落。'),
('B006', '情緒的修復練習', '陳思妤', '當生活讓你感到疲憊，不妨靜下來，與情緒好好相處。本書教你自我修復與接納的力量。'),
('B007', '追風箏的人', '卡勒德・胡賽尼', '一段關於背叛與救贖的故事，橫跨阿富汗與美國，感人至深的兄弟情與生命旅程。'),
('B008', '解憂雜貨店', '東野圭吾', '一家收信的雜貨店，三個年輕人，一段穿越時空的回信旅程，溫暖無數人的心。');


INSERT INTO inventory (isbn, store_time, status) VALUES ('B001', '2025-05-04 19:25:35', 'borrowed');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B001', '2025-06-08 08:01:54', 'available');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B002', '2025-04-21 22:03:46', 'borrowed');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B002', '2025-06-14 13:35:09', 'available');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B003', '2025-07-05 14:16:12', 'processing');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B003', '2025-04-09 20:26:27', 'borrowed');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B004', '2025-04-03 23:40:59', 'available');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B005', '2025-05-11 15:10:17', 'processing');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B005', '2025-06-27 17:57:11', 'available');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B005', '2025-04-30 11:33:44', 'lost');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B006', '2025-06-20 10:48:31', 'available');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B006', '2025-07-30 20:26:53', 'damaged');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B007', '2025-04-10 13:41:24', 'available');

INSERT INTO inventory (isbn, store_time, status) VALUES ('B008', '2025-05-07 09:23:14', 'available');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B008', '2025-06-15 21:58:45', 'available');
INSERT INTO inventory (isbn, store_time, status) VALUES ('B008', '2025-07-24 12:10:07', 'borrowed');
