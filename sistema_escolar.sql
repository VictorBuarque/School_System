-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 29/06/2023 às 23:30
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistema_escolar`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `alunos`
--

CREATE TABLE `alunos` (
  `nr_Matricula` int(11) NOT NULL,
  `nome_Aluno` varchar(70) NOT NULL,
  `cpf_Aluno` varchar(11) NOT NULL,
  `endereco_Aluno` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `alunos`
--

INSERT INTO `alunos` (`nr_Matricula`, `nome_Aluno`, `cpf_Aluno`, `endereco_Aluno`) VALUES
(1, 'victor', '12312333421', 'teste@gmail.com'),
(2, 'Victore', '99999999957', 'rua 1'),
(12, 'Victor G', '12345677899', ''),
(123, 'eunice', '12336472662', 'rua 8'),
(451, 'eraerra', '12345665', 'dasdadas'),
(908, 'roselmy', '12345678910', 'ruehuerhuerhur'),
(1234, 'eustaqui', '12343532111', 'tchurubangos');

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno_curso`
--

CREATE TABLE `aluno_curso` (
  `nr_Matricula` int(11) NOT NULL,
  `id_Curso` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno_turma`
--

CREATE TABLE `aluno_turma` (
  `nr_Matricula` int(11) NOT NULL,
  `id_Turma` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aluno_turma`
--

INSERT INTO `aluno_turma` (`nr_Matricula`, `id_Turma`) VALUES
(1, 1),
(2, 1),
(908, 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `coordenacao`
--

CREATE TABLE `coordenacao` (
  `id_Coordenador` int(11) NOT NULL,
  `nome_Coordenador` varchar(50) NOT NULL,
  `email_Coordenador` varchar(70) NOT NULL,
  `cel_Coordenador` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `coordenacao`
--

INSERT INTO `coordenacao` (`id_Coordenador`, `nome_Coordenador`, `email_Coordenador`, `cel_Coordenador`) VALUES
(1, 'Iran', 'iran@senai.com.br', '7199999999'),
(2, 'jorge', 'jorge@senai.com.br', '75999999999'),
(3, 'celso', 'celsaoa@senai.com.br', '73999999999');

-- --------------------------------------------------------

--
-- Estrutura para tabela `cronograma`
--

CREATE TABLE `cronograma` (
  `dt_Cronograma` date NOT NULL,
  `id_Professor` int(11) NOT NULL,
  `id_Disciplina` varchar(7) NOT NULL,
  `hr_Inicio` varchar(5) NOT NULL,
  `hr_Fim` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cronograma`
--

INSERT INTO `cronograma` (`dt_Cronograma`, `id_Professor`, `id_Disciplina`, `hr_Inicio`, `hr_Fim`) VALUES
('2003-03-03', 1, '1', '15:00', '17:00'),
('2003-03-04', 1, '1', '08:00', '08:00'),
('2003-03-05', 1, '1', '10:00', '12:00'),
('2003-03-20', 2, '2', '10:00', '13:00');

-- --------------------------------------------------------

--
-- Estrutura para tabela `curso`
--

CREATE TABLE `curso` (
  `id_Curso` varchar(7) NOT NULL,
  `nome_Curso` varchar(50) NOT NULL,
  `id_Coordenador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `curso`
--

INSERT INTO `curso` (`id_Curso`, `nome_Curso`, `id_Coordenador`) VALUES
('1', 'Adm', 1),
('2', 'Des', 3),
('3', 'ti', 2),
('4', 'Geografia', 3),
('5', 'ciencia', 1),
('6', 'Geologia', 3),
('7', 'portugues', 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `disciplina`
--

CREATE TABLE `disciplina` (
  `id_Disciplina` varchar(7) NOT NULL,
  `nome_Disciplina` varchar(50) NOT NULL,
  `carga_Horaria` int(11) NOT NULL,
  `descricao_Disciplina` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `disciplina`
--

INSERT INTO `disciplina` (`id_Disciplina`, `nome_Disciplina`, `carga_Horaria`, `descricao_Disciplina`) VALUES
('1', 'logica', 120, 'logica'),
('2', 'web', 70, 'web'),
('3', 'comunicação', 36, 'oral e escrita');

-- --------------------------------------------------------

--
-- Estrutura para tabela `disciplina_curso`
--

CREATE TABLE `disciplina_curso` (
  `id_Disciplina` varchar(7) NOT NULL,
  `id_Curso` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `disciplina_curso`
--

INSERT INTO `disciplina_curso` (`id_Disciplina`, `id_Curso`) VALUES
('1', '1'),
('1', '2');

-- --------------------------------------------------------

--
-- Estrutura para tabela `disponibilidade_professores`
--

CREATE TABLE `disponibilidade_professores` (
  `id_Professor` int(11) NOT NULL,
  `turno_Disponibilidade` varchar(45) NOT NULL,
  `data_Disponibilidade` varchar(45) NOT NULL,
  `hora_Inicio` varchar(45) NOT NULL,
  `hora_Fim` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `frequencia_aluno`
--

CREATE TABLE `frequencia_aluno` (
  `nr_Matricula` int(11) NOT NULL,
  `id_Disciplina` varchar(7) NOT NULL,
  `data_Falta` date NOT NULL,
  `falta_Aluno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `notas`
--

CREATE TABLE `notas` (
  `id_Professor` int(11) NOT NULL,
  `id_Disciplina` varchar(7) NOT NULL,
  `id_Turma` int(11) NOT NULL,
  `nr_Matricula` int(11) NOT NULL,
  `data_Nota` date NOT NULL,
  `nota_Disciplina` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `notas`
--

INSERT INTO `notas` (`id_Professor`, `id_Disciplina`, `id_Turma`, `nr_Matricula`, `data_Nota`, `nota_Disciplina`) VALUES
(3, '3', 2, 908, '1999-03-05', 7),
(2, '2', 1, 2, '1999-03-06', 7),
(2, '1', 1, 12, '2010-05-03', 6),
(1, '1', 1, 123, '2020-03-03', 7),
(1, '1', 1, 1, '2020-04-04', 8),
(1, '1', 1, 12, '2023-02-02', 10);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pagamentos`
--

CREATE TABLE `pagamentos` (
  `nr_Matricula` int(11) NOT NULL,
  `id_Curso` int(11) NOT NULL,
  `data_Pagamento` date NOT NULL,
  `valor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pagamentos`
--

INSERT INTO `pagamentos` (`nr_Matricula`, `id_Curso`, `data_Pagamento`, `valor`) VALUES
(1, 1, '1993-03-20', 500),
(2, 2, '2003-03-20', 600);

-- --------------------------------------------------------

--
-- Estrutura para tabela `professor`
--

CREATE TABLE `professor` (
  `id_Professor` int(11) NOT NULL,
  `nome_Professor` varchar(50) NOT NULL,
  `cpf_Professor` varchar(11) NOT NULL,
  `email_Professor` varchar(70) NOT NULL,
  `telefone_Professor` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `professor`
--

INSERT INTO `professor` (`id_Professor`, `nome_Professor`, `cpf_Professor`, `email_Professor`, `telefone_Professor`) VALUES
(1, 'jorge', '11234566677', 'senai', '71999999999'),
(2, 'celso', '32132132131', 'sesnai', '99999999'),
(3, 'iran', '12312312', 'dasdadsa', '999999999'),
(4, 'barbara', '1234341431', 'dasdadazx', '123131321'),
(5, 'maria', '5646548', 'sdeawasad', '213131234');

-- --------------------------------------------------------

--
-- Estrutura para tabela `professor_disciplina`
--

CREATE TABLE `professor_disciplina` (
  `id_Professor` int(11) NOT NULL,
  `id_Disciplina` varchar(7) NOT NULL,
  `dt_Inicio` datetime NOT NULL,
  `dt_Fim` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `turmas`
--

CREATE TABLE `turmas` (
  `id_Turma` int(11) NOT NULL,
  `nr_Matricula` int(11) NOT NULL,
  `id_Curso` varchar(7) NOT NULL,
  `turno_Turma` varchar(30) NOT NULL,
  `data_Turma` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `turmas`
--

INSERT INTO `turmas` (`id_Turma`, `nr_Matricula`, `id_Curso`, `turno_Turma`, `data_Turma`) VALUES
(1, 1, '1', 'matutino', '2003-02-28 00:00:00'),
(2, 2, '2', 'Vespertino', '2023-09-09 00:00:00');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `alunos`
--
ALTER TABLE `alunos`
  ADD PRIMARY KEY (`nr_Matricula`),
  ADD UNIQUE KEY `cpf_Aluno` (`cpf_Aluno`);

--
-- Índices de tabela `aluno_curso`
--
ALTER TABLE `aluno_curso`
  ADD KEY `fk_Aluno1` (`nr_Matricula`),
  ADD KEY `fk_Curso5` (`id_Curso`);

--
-- Índices de tabela `aluno_turma`
--
ALTER TABLE `aluno_turma`
  ADD KEY `fk_Aluno5` (`nr_Matricula`),
  ADD KEY `fk_Turma` (`id_Turma`);

--
-- Índices de tabela `coordenacao`
--
ALTER TABLE `coordenacao`
  ADD PRIMARY KEY (`id_Coordenador`);

--
-- Índices de tabela `cronograma`
--
ALTER TABLE `cronograma`
  ADD PRIMARY KEY (`dt_Cronograma`),
  ADD KEY `fk_Professor4` (`id_Professor`),
  ADD KEY `fk_Disciplina2` (`id_Disciplina`);

--
-- Índices de tabela `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id_Curso`),
  ADD KEY `fk_coord` (`id_Coordenador`);

--
-- Índices de tabela `disciplina`
--
ALTER TABLE `disciplina`
  ADD PRIMARY KEY (`id_Disciplina`);

--
-- Índices de tabela `disciplina_curso`
--
ALTER TABLE `disciplina_curso`
  ADD KEY `fk_Curso4` (`id_Curso`);

--
-- Índices de tabela `disponibilidade_professores`
--
ALTER TABLE `disponibilidade_professores`
  ADD PRIMARY KEY (`turno_Disponibilidade`,`data_Disponibilidade`),
  ADD KEY `fk_Professor2` (`id_Professor`);

--
-- Índices de tabela `frequencia_aluno`
--
ALTER TABLE `frequencia_aluno`
  ADD KEY `fk_Aluno4` (`nr_Matricula`),
  ADD KEY `fk_Disciplina4` (`id_Disciplina`);

--
-- Índices de tabela `notas`
--
ALTER TABLE `notas`
  ADD PRIMARY KEY (`data_Nota`),
  ADD KEY `fk_Professor` (`id_Professor`),
  ADD KEY `fk_Aluno` (`nr_Matricula`),
  ADD KEY `fk_Turmas` (`id_Turma`),
  ADD KEY `fk_Disciplinas` (`id_Disciplina`);

--
-- Índices de tabela `pagamentos`
--
ALTER TABLE `pagamentos`
  ADD PRIMARY KEY (`data_Pagamento`),
  ADD KEY `fk_aluno3` (`nr_Matricula`);

--
-- Índices de tabela `professor`
--
ALTER TABLE `professor`
  ADD PRIMARY KEY (`id_Professor`),
  ADD UNIQUE KEY `cpf_Professor` (`cpf_Professor`);

--
-- Índices de tabela `professor_disciplina`
--
ALTER TABLE `professor_disciplina`
  ADD PRIMARY KEY (`dt_Inicio`),
  ADD KEY `fk_Professor5` (`id_Professor`),
  ADD KEY `fk_Disciplina3` (`id_Disciplina`);

--
-- Índices de tabela `turmas`
--
ALTER TABLE `turmas`
  ADD PRIMARY KEY (`id_Turma`),
  ADD KEY `fk_Aluno2` (`nr_Matricula`),
  ADD KEY `fk_Curso1` (`id_Curso`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `professor`
--
ALTER TABLE `professor`
  MODIFY `id_Professor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `aluno_curso`
--
ALTER TABLE `aluno_curso`
  ADD CONSTRAINT `fk_Aluno1` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`),
  ADD CONSTRAINT `fk_Curso5` FOREIGN KEY (`id_Curso`) REFERENCES `curso` (`id_Curso`);

--
-- Restrições para tabelas `aluno_turma`
--
ALTER TABLE `aluno_turma`
  ADD CONSTRAINT `fk_Aluno5` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`),
  ADD CONSTRAINT `fk_Turma` FOREIGN KEY (`id_Turma`) REFERENCES `turmas` (`id_Turma`);

--
-- Restrições para tabelas `cronograma`
--
ALTER TABLE `cronograma`
  ADD CONSTRAINT `fk_Disciplina2` FOREIGN KEY (`id_Disciplina`) REFERENCES `disciplina` (`id_Disciplina`),
  ADD CONSTRAINT `fk_Professor4` FOREIGN KEY (`id_Professor`) REFERENCES `professor` (`id_Professor`);

--
-- Restrições para tabelas `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `fk_coord` FOREIGN KEY (`id_Coordenador`) REFERENCES `coordenacao` (`id_Coordenador`);

--
-- Restrições para tabelas `disciplina_curso`
--
ALTER TABLE `disciplina_curso`
  ADD CONSTRAINT `fk_Curso4` FOREIGN KEY (`id_Curso`) REFERENCES `curso` (`id_Curso`);

--
-- Restrições para tabelas `disponibilidade_professores`
--
ALTER TABLE `disponibilidade_professores`
  ADD CONSTRAINT `fk_Professor2` FOREIGN KEY (`id_Professor`) REFERENCES `professor` (`id_Professor`);

--
-- Restrições para tabelas `frequencia_aluno`
--
ALTER TABLE `frequencia_aluno`
  ADD CONSTRAINT `fk_Aluno4` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`),
  ADD CONSTRAINT `fk_Disciplina4` FOREIGN KEY (`id_Disciplina`) REFERENCES `disciplina` (`id_Disciplina`);

--
-- Restrições para tabelas `notas`
--
ALTER TABLE `notas`
  ADD CONSTRAINT `fk_Aluno` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`),
  ADD CONSTRAINT `fk_Disciplinas` FOREIGN KEY (`id_Disciplina`) REFERENCES `disciplina` (`id_Disciplina`),
  ADD CONSTRAINT `fk_Professor` FOREIGN KEY (`id_Professor`) REFERENCES `professor` (`id_Professor`),
  ADD CONSTRAINT `fk_Turmas` FOREIGN KEY (`id_Turma`) REFERENCES `turmas` (`id_Turma`);

--
-- Restrições para tabelas `pagamentos`
--
ALTER TABLE `pagamentos`
  ADD CONSTRAINT `fk_aluno3` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`);

--
-- Restrições para tabelas `professor_disciplina`
--
ALTER TABLE `professor_disciplina`
  ADD CONSTRAINT `fk_Disciplina3` FOREIGN KEY (`id_Disciplina`) REFERENCES `disciplina` (`id_Disciplina`),
  ADD CONSTRAINT `fk_Professor5` FOREIGN KEY (`id_Professor`) REFERENCES `professor` (`id_Professor`);

--
-- Restrições para tabelas `turmas`
--
ALTER TABLE `turmas`
  ADD CONSTRAINT `fk_Aluno2` FOREIGN KEY (`nr_Matricula`) REFERENCES `alunos` (`nr_Matricula`),
  ADD CONSTRAINT `fk_Curso1` FOREIGN KEY (`id_Curso`) REFERENCES `curso` (`id_Curso`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
