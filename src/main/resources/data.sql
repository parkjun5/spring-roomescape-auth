CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE theme
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    thumbnail VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,
    theme_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id) ON DELETE RESTRICT,
    FOREIGN KEY (theme_id) REFERENCES theme (id) ON DELETE RESTRICT
);

CREATE TABLE member
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE member_role
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    member_id   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE RESTRICT
);

INSERT INTO reservation_time (id, start_at)
VALUES  (1, '10:00'),
        (2, '12:00'),
        (3, '14:00'),
        (4, '16:00'),
        (5, '18:00'),
        (6, '20:00');

INSERT INTO theme (name, description, thumbnail)
VALUES ('고양이 테마', '고양이 카페에서 탈출하는 테마입니다', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('쏘우 테마', '심약자 주의! 공포 테마입니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg'),
       ('포켓몬 테마', '포켓몬고를 설치하셔야 합니다.', 'https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg');

INSERT INTO member (id, name, email, password)
VALUES  (1, '테스트_유저', 'teser@gmail.com', '1');

INSERT INTO member_role (id, name, member_id)
VALUES  (1, 'ADMIN', 1);
