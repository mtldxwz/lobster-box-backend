-- 龙虾装扮盲盒 - 装扮模板初始化数据
-- 2026年4月4日最终版

-- 清空现有数据
DELETE FROM costumes;

-- 传说款（4款，各限量50个，概率2%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('养龙虾大神', 'imgs/260404_15_生图/养龙虾大神_1.jpg', 'LEGENDARY', '2026热梗', 0.005, true),
('今日份已养肥', 'imgs/260404_15_生图/今日份已养肥_1.jpg', 'LEGENDARY', '2026热梗', 0.005, true),
('我的刀盾虾', 'imgs/260404_15_生图/我的刀盾虾_1.jpg', 'LEGENDARY', '2026热梗', 0.005, true),
('比比拉布虾', 'imgs/260404_15_生图/比比拉布虾_1.jpg', 'LEGENDARY', '2026热梗', 0.005, true);

-- 史诗款（5款，各限量300个，概率8%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('精神离职虾', 'imgs/260404_15_生图/精神离职虾_1.jpg', 'EPIC', '打工人热梗', 0.016, true),
('班味入脑', 'imgs/260404_15_生图/班味入脑_1.jpg', 'EPIC', '打工人热梗', 0.016, true),
('沈腾鼓掌虾', 'imgs/260404_15_生图/沈腾鼓掌虾_1.jpg', 'EPIC', '2026热梗', 0.016, true),
('当个事儿办虾', 'imgs/260404_15_生图/当个事儿办虾_1.jpg', 'EPIC', '2026热梗', 0.016, true),
('爱你老己虾', 'imgs/260404_15_生图/爱你老己虾_1.jpg', 'EPIC', '2026热梗', 0.016, true);

-- 稀有款（3款，各限量1000个，概率30%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('酸黄瓜虾', 'imgs/260404_15_生图/酸黄瓜虾_1.jpg', 'RARE', '打工人热梗', 0.10, true),
('已下班免打扰', 'imgs/260404_15_生图/已下班免打扰_1.jpg', 'RARE', '打工人热梗', 0.10, true),
('歪比巴卜虾', 'imgs/260404_15_生图/歪比巴卜虾_1.jpg', 'RARE', '游戏梗', 0.10, true);

-- 普通款（5款，各限量5000个，概率60%）
INSERT INTO costumes (name, image, rarity, style, probability, is_limited) VALUES
('蒜鸟蒜鸟虾', 'imgs/260404_15_生图/蒜鸟蒜鸟虾_1.jpg', 'COMMON', '谐音梗', 0.12, true),
('别太荒谬虾', 'imgs/260404_15_生图/别太荒谬虾_1.jpg', 'COMMON', '吐槽梗', 0.12, true),
('ww虾', 'imgs/260404_15_生图/ww虾_1.jpg', 'COMMON', '情绪梗', 0.12, true),
('质疑刀盾虾', 'imgs/260404_15_生图/质疑刀盾虾_1.jpg', 'COMMON', '2026热梗', 0.12, true),
('成为刀盾虾', 'imgs/260404_15_生图/成为刀盾虾_1.jpg', 'COMMON', '2026热梗', 0.12, true);
