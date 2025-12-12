package api.kata.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "breweries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brewery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address1;

    @Column(nullable = false)
    private String address2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String filepath;

    @Column(nullable = false)
    private String descript;

    @Column(name = "add_user", nullable = false)
    private Integer addUser;

    @Column(name = "last_mod", nullable = false)
    private LocalDateTime lastMod;

    /*
    Table structure for table `breweries`

    DROP TABLE IF EXISTS `breweries`;
    CREATE TABLE `breweries` (
            `id` int(21) NOT NULL auto_increment,
            `name` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `address1` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `address2` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `city` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `state` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `code` varchar(25) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `country` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `phone` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `website` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `filepath` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `descript` text character set utf8 collate utf8_unicode_ci NOT NULL,
            `add_user` int(11) NOT NULL default '0',
            `last_mod` datetime NOT NULL default '0000-00-00 00:00:00',
            PRIMARY KEY  (`id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=1424 DEFAULT CHARSET=utf8mb4;
     */

}
