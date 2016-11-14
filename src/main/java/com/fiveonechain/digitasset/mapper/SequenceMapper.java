package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface SequenceMapper {
}

/*

CREATE TABLE `sys_sequence` (
   `NAME` varchar(10) NOT NULL,
   `CURRENT_VALUE` int(11) NOT NULL DEFAULT '0',
   `INCREMENT` int(11) NOT NULL DEFAULT '1',
   PRIMARY KEY (`NAME`)
)

INSERT INTO SYS_SEQUENCE(NAME,CURRENT_VALUE,INCREMENT) VALUES('TBL_FS', 1,1)


DELIMITER $$

DROP FUNCTION IF EXISTS `currval`$$

CREATE DEFINER=`blockchain`@`%` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS INT(11)
BEGIN
DECLARE VALUE INTEGER;
SET VALUE=0;
SELECT current_value INTO VALUE
FROM sys_sequence
WHERE NAME=seq_name;
RETURN VALUE;
END$$

DELIMITER ;


DELIMITER $$
DROP FUNCTION IF EXISTS `nextval`$$

CREATE DEFINER=`blockchain`@`%` FUNCTION `nextval`(seq_name varchar(50)) RETURNS int(11)
BEGIN
UPDATE sys_sequence
SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
where name=seq_name;
return currval(seq_name);
END$$

DELIMITER ;
 */