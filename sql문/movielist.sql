-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 21-11-26 23:49
-- 서버 버전: 8.0.25
-- PHP 버전: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `movielist`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `movie`
--


CREATE TABLE `movie` (
  `movie_id` int NOT NULL COMMENT '영화번호',
  `title` varchar(30) NOT NULL COMMENT '제목',
  `director` varchar(24) NOT NULL COMMENT '감독',
  `summary` varchar(18) NOT NULL COMMENT '장르',
  `time` int NOT NULL COMMENT '상영시간',
  `performer` varchar(100) NOT NULL COMMENT '출연자',
  `score` float(3,2) NOT NULL COMMENT '평점(총10점)',
  `date` date NOT NULL COMMENT '개봉날짜',
  `rate` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상영등급'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='영화리스트테이블';

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`movie_id`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `movie`
--
ALTER TABLE `movie`
  MODIFY `movie_id` int NOT NULL AUTO_INCREMENT COMMENT '영화번호';
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
