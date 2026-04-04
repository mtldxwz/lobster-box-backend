-- 龙虾装扮盲盒 - 装扮模板初始化数据
-- 2026年4月4日最终版

-- 传说款（4款，各限量50个，概率2%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('养龙虾大神', 'https://s.coze.cn/image/3QerV5ssdrc/', 'LEGENDARY', '2026热梗', 0.005, true),
('今日份已养肥', 'https://s.coze.cn/image/TjwuWklb_jc/', 'LEGENDARY', '2026热梗', 0.005, true),
('我的刀盾虾', 'https://s.coze.cn/image/58AY7cjHImo/', 'LEGENDARY', '2026热梗', 0.005, true),
('比比拉布虾', 'https://s.coze.cn/image/UlMjFq3Qhwc/', 'LEGENDARY', '2026热梗', 0.005, true);

-- 史诗款（5款，各限量300个，概率8%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('精神离职虾', 'https://s.coze.cn/image/Bs0aq1i5qCs/', 'EPIC', '打工人热梗', 0.016, true),
('班味入脑', 'https://s.coze.cn/image/WN7BhigzESQ/', 'EPIC', '打工人热梗', 0.016, true),
('沈腾鼓掌虾', 'https://s.coze.cn/image/uvAFEZvGW5Q/', 'EPIC', '2026热梗', 0.016, true),
('当个事儿办虾', 'https://s.coze.cn/image/3QerV5ssdrc/', 'EPIC', '2026热梗', 0.016, true),
('爱你老己虾', 'https://s.coze.cn/image/cuLUWAf1M0Y/', 'EPIC', '2026热梗', 0.016, true);

-- 稀有款（3款，各限量1000个，概率30%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('酸黄瓜虾', 'https://s.coze.cn/image/EnH7z6sQtks/', 'RARE', '打工人热梗', 0.10, true),
('已下班免打扰', 'https://s.coze.cn/image/niOEd8JnHTw/', 'RARE', '打工人热梗', 0.10, true),
('歪比巴卜虾', 'https://s.coze.cn/image/xeo9Ni-7a2k/', 'RARE', '游戏梗', 0.10, true);

-- 普通款（5款，各限量5000个，概率60%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('蒜鸟蒜鸟虾', 'https://s.coze.cn/image/0QCznfU2UYo/', 'COMMON', '谐音梗', 0.12, true),
('别太荒谬虾', 'https://s.coze.cn/image/9W8050vQK-I/', 'COMMON', '吐槽梗', 0.12, true),
('ww虾', 'https://s.coze.cn/image/h1gOkMnOpRo/', 'COMMON', '情绪梗', 0.12, true),
('质疑刀盾虾', 'https://s.coze.cn/image/2eipprO8HEI/', 'COMMON', '2026热梗', 0.12, true),
('成为刀盾虾', 'https://s.coze.cn/image/MtFEeg8zqkA/', 'COMMON', '2026热梗', 0.12, true);
