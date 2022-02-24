-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-09-2021 a las 02:10:47
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fundacion_inventario`
--
CREATE DATABASE IF NOT EXISTS `fundacion_inventario` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `fundacion_inventario`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alimento`
--

DROP TABLE IF EXISTS `alimento`;
CREATE TABLE `alimento` (
  `id` int(11) NOT NULL,
  `codigo` int(11) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alimento`
--

INSERT INTO `alimento` (`id`, `codigo`, `tipo`, `nombre`, `descripcion`) VALUES
(2, 333, 'Percedero', 'Manzana', 'Por paquetes'),
(3, 2222, 'asda', 'dddddd', 'sdfsdfdsfsdf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE `empleado` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `tipo` varchar(15) NOT NULL,
  `dni` varchar(11) NOT NULL,
  `telefono` varchar(11) NOT NULL,
  `direccion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id`, `nombre`, `apellidos`, `tipo`, `dni`, `telefono`, `direccion`) VALUES
(2, 'Piedra', 'Calisa', 'Conductor', '23123', '23123123', 'Monteria'),
(3, 'John', 'Claks Jean', 'Conductor', '123213', '23213123', 'asda12sdasd'),
(4, 'Julian', 'Berrio', 'Asistente', '321233', '121232', 'sa 213 sad'),
(5, 'German', 'Gomez', 'Asistente', '12321', '2223123', 'Lorica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario_compras`
--

DROP TABLE IF EXISTS `inventario_compras`;
CREATE TABLE `inventario_compras` (
  `id` int(10) UNSIGNED NOT NULL,
  `proveedor` int(11) NOT NULL,
  `comprobante` int(11) NOT NULL,
  `documento` int(11) NOT NULL,
  `alimento` varchar(20) NOT NULL,
  `valor` float NOT NULL,
  `iva` float NOT NULL,
  `descuento` float NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `inventario_compras`
--

INSERT INTO `inventario_compras` (`id`, `proveedor`, `comprobante`, `documento`, `alimento`, `valor`, `iva`, `descuento`, `cantidad`) VALUES
(2, 2, 2, 3, 'man', 60, 10, 30, 2),
(3, 5, 1, 45, 'man', 30, 2, 10, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario_existencias`
--

DROP TABLE IF EXISTS `inventario_existencias`;
CREATE TABLE `inventario_existencias` (
  `id` int(11) NOT NULL,
  `periodo` varchar(15) NOT NULL,
  `cant_inicial` int(11) NOT NULL,
  `cant_salida` int(11) NOT NULL,
  `cant_fisico` int(11) NOT NULL,
  `cant_ajuste` int(11) NOT NULL,
  `cant_final` int(11) NOT NULL,
  `fk_codigo_alimento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `inventario_existencias`
--

INSERT INTO `inventario_existencias` (`id`, `periodo`, `cant_inicial`, `cant_salida`, `cant_fisico`, `cant_ajuste`, `cant_final`, `fk_codigo_alimento`) VALUES
(2, 'Mensual', 45, 15, 30, 10, 11, 333);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta`
--

DROP TABLE IF EXISTS `ruta`;
CREATE TABLE `ruta` (
  `id` int(11) UNSIGNED NOT NULL,
  `lugar` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ruta`
--

INSERT INTO `ruta` (`id`, `lugar`) VALUES
(1, 'Moñitos'),
(2, 'San Bernardo del viento'),
(3, 'Momil'),
(4, 'Purísima'),
(5, 'San Antero'),
(6, 'Cotorra');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ruta_asignada`
--

DROP TABLE IF EXISTS `ruta_asignada`;
CREATE TABLE `ruta_asignada` (
  `id` int(10) UNSIGNED NOT NULL,
  `hora_salida` varchar(6) NOT NULL,
  `tiempo` int(11) NOT NULL COMMENT 'expresado en horas',
  `fk_conductor` varchar(11) NOT NULL,
  `fk_asistente` varchar(11) NOT NULL,
  `fk_vehiculo` varchar(7) NOT NULL,
  `fk_ruta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `idusuario` int(11) NOT NULL,
  `user` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idusuario`, `user`, `password`, `nombre`) VALUES
(1, 'admin', 'admin', 'Maria Jose');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE `vehiculo` (
  `id` int(10) UNSIGNED NOT NULL,
  `marca` varchar(25) NOT NULL,
  `modelo` varchar(25) NOT NULL,
  `matricula` varchar(10) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id`, `marca`, `modelo`, `matricula`, `descripcion`) VALUES
(1, 'Audi', 'Prisma', '34B-45S', 'Rojo');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alimento`
--
ALTER TABLE `alimento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `inventario_compras`
--
ALTER TABLE `inventario_compras`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `inventario_existencias`
--
ALTER TABLE `inventario_existencias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ruta`
--
ALTER TABLE `ruta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ruta_asignada`
--
ALTER TABLE `ruta_asignada`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idusuario`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alimento`
--
ALTER TABLE `alimento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `inventario_compras`
--
ALTER TABLE `inventario_compras`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `inventario_existencias`
--
ALTER TABLE `inventario_existencias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ruta`
--
ALTER TABLE `ruta`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ruta_asignada`
--
ALTER TABLE `ruta_asignada`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
