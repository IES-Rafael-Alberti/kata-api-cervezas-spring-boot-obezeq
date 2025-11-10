package api.kata.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cat_name", nullable = false)
    private String catName;

    @Column(name = "last_mod", nullable = false)
    private LocalDateTime lastMod;



    /*
    *
    *   DROP TABLE IF EXISTS `categories`;
        CREATE TABLE `categories` (
          `id` int(11) NOT NULL auto_increment,
          `cat_name` varchar(255) NOT NULL default '',
          `last_mod` datetime NOT NULL default '0000-00-00 00:00:00',
          PRIMARY KEY  (`id`)
        ) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
    *
    * */

}
