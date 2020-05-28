-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 28 Maj 2020, 13:28
-- Wersja serwera: 10.4.8-MariaDB
-- Wersja PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `memorygame`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rank`
--

CREATE TABLE `rank` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `time` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `rank`
--

INSERT INTO `rank` (`id`, `userID`, `time`) VALUES
(5, 7, 2.489),
(6, 5, 57.478),
(7, 4, 2.235),
(8, 9, 58.999),
(9, 10, 30.239);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `nick` varchar(16) NOT NULL,
  `password` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `nick`, `password`) VALUES
(1, 'alan', '$2y$10$TpKwDxNeUfUyCtpnQBlx1uv9QxkyFfyUQHMyK9IJsCVXkOCfKw6cO'),
(4, 'tomek', '$2y$10$clL4/tNLIHfCpt3eFeQYYOqjiyu9C/yx/HeHjGOTUirxgSsqPzh5i'),
(5, 'agata', '$2y$10$QTfnfYwDbSJFyMPimHsF.eLDKbfkmB.EdkfDKhK.6gOSQvroRP8US'),
(7, 'adam', '$2y$10$XAITHQd8/1xARO88PabIyOaY5iqk9SEYamegntfhdMoX18Si3AVey'),
(8, 'radek', '$2y$10$6MjK4Ervg3738s9EM5g7XujRx3dEvMO9tsKu9VGL76HZZUP.U2Sfa'),
(9, 'newbie666', '$2y$10$8UyQRNPmnqcu86iVhN.IHe.oLRvwgerjgUptfRpFsuV4IKOuCXlEK'),
(10, 'user82', '$2y$10$MeZ.0CqI1JuxdyqWIUk.KO7Akeqd3A/yYvj9jQR4Z7DbG0sMIdejK');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `rank`
--
ALTER TABLE `rank`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userID` (`userID`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `rank`
--
ALTER TABLE `rank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `rank`
--
ALTER TABLE `rank`
  ADD CONSTRAINT `rank_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
