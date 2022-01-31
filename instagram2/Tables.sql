CREATE DATABASE sql10435459;
USE sql10435459;

CREATE TABLE `users` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) UNIQUE NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` BLOB NOT NULL
);

CREATE TABLE `images` (
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `url` VARCHAR(255) NOT NULL,
    `user_id` INT NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE 
    ON UPDATE CASCADE
);

delimiter //
CREATE PROCEDURE `insertUser`(IN username VARCHAR(255), IN useremail VARCHAR(255), IN userpassword VARCHAR(255))
    BEGIN
        INSERT INTO `users`(`username`, `email`, `password`)
        VALUES(username, useremail, AES_ENCRYPT(userpassword, "instagram2"));
    END//
delimiter ;

delimiter //
CREATE PROCEDURE `insertImage`(IN imagename VARCHAR(255), IN imageurl VARCHAR(255), IN imageuserid INT)
    BEGIN
        INSERT INTO `images`(`name`, `url`, `user_id`)
        VALUES(imagename, imageurl, imageuserid);
    END//
delimiter ; 

CALL `insertUser`("Guilherme", "guilherme@hotmail.com", "senha123456");

delimiter //
CREATE PROCEDURE `authUser`(IN `usernameAuth` VARCHAR(255), IN `passwordAuth` VARCHAR(255))
    BEGIN
        SELECT * FROM `users` WHERE username = `usernameAuth` AND password = AES_ENCRYPT(`passwordAuth`, "instagram2");
    END//
delimiter ;

delimiter //
CREATE PROCEDURE `selectImages`()
    BEGIN
        SELECT * FROM `images` INNER JOIN `users` ON `users`.id = user_id;
    END//

CREATE PROCEDURE `selectUsers`()
    BEGIN
        SELECT * FROM `users`;
    END//

CREATE PROCEDURE `deleteImage`(IN `imageId` INT)
    BEGIN
        DELETE FROM `images` WHERE id = `imageId`;
    END//

CREATE PROCEDURE `deleteUser`(IN `userId` INT)
    BEGIN
        DELETE FROM `users` WHERE id = `userId`;
    END//

CREATE PROCEDURE `updateImage`(IN `imageId` INT, IN `nameImage` VARCHAR(255))
    BEGIN
        UPDATE `images` SET name = `nameImage` WHERE id = `imageId`;
    END//

CREATE PROCEDURE `updateUser`(IN `userId` INT, IN `userName` VARCHAR(255), IN `userEmail` VARCHAR(255), IN `userPassword` VARCHAR(255))
    BEGIN
        UPDATE `users` SET username = `userName`, email = `userEmail`, password = AES_ENCRYPT(`userPassword`, "instagram2") WHERE id = `userId`;
    END//

CREATE PROCEDURE `selectUser`(IN `user_id` INT)
    BEGIN
        SELECT id, username, email, CAST(AES_DECRYPT(password, "instagram2") AS CHAR(255)) FROM `users` WHERE id = `user_id`;
    END//

CREATE PROCEDURE `selectImage`(In `image_id` INT)
    BEGIN
        SELECT * FROM `images` WHERE id = `imageId`;
    END//

CREATE PROCEDURE `selectUserImages`(IN `name` VARCHAR(255))
    BEGIN
        SELECT * FROM `images` RIGHT JOIN `users` ON `users`.id = user_id WHERE username = `name`;
    END//

delimiter ;


