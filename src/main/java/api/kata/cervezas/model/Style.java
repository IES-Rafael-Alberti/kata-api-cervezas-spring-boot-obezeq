package api.kata.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "styles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category cat;

    @Column(name = "style_name", nullable = false)
    private String styleName;

    @Column(name = "last_mod", nullable = false)
    private LocalDateTime lastMod;

    /*
    styles.sql tables

    DROP TABLE IF EXISTS `styles`;
    CREATE TABLE `styles` (
            `id` int(11) NOT NULL auto_increment,
            `cat_id` int(11) NOT NULL default '0',
            `style_name` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `last_mod` datetime NOT NULL default '0000-00-00 00:00:00',
            PRIMARY KEY  (`id`)
    ) ENGINE=MyISAM AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4;

    */
}
