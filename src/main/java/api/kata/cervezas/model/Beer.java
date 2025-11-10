package api.kata.cervezas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "beers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MUCHAS cervezas pueden estar en la MISMA brewery
    // UNA cerveza solo tiene UNA cervezeria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id", nullable = false)
    private Brewery brewery;

    @Column(nullable = false)
    private String name;

    // MUCHAS cervezas pueden tener la MISMA categoria
    // UNA cerveza solo tiene UNA CATEGORIA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private Category cat;

    // MUCHAS cervezas pueden tener el MISMO estilo
    // UNA cerveza solo tiene UN style
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="style_id", nullable = false)
    private Style style;

    @Column(nullable = false)
    private Float abv;

    @Column(nullable = false)
    private Float ibu;

    @Column(nullable = false)
    private Float srm;

    @Column(nullable = false)
    private Integer upc;

    @Column(nullable = false)
    private String filepath;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descript;

    @Column(name = "add_user", nullable = false)
    private Integer addUser;

    @Column(name = "last_mod", nullable = false)
    private LocalDateTime lastMod;

    /*
    beers.sql tables

            `id` int(21) NOT NULL auto_increment,
            `brewery_id` int(21) NOT NULL default '0',
            `name` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `cat_id` int(11) NOT NULL default '0',
            `style_id` int(11) NOT NULL default '0',
            `abv` float NOT NULL default '0',
            `ibu` float NOT NULL default '0',
            `srm` float NOT NULL default '0',
            `upc` int(40) NOT NULL default '0',
            `filepath` varchar(255) character set utf8 collate utf8_unicode_ci NOT NULL default '',
            `descript` text character set utf8 collate utf8_unicode_ci NOT NULL,
            `add_user` int(11) NOT NULL default '0',
            `last_mod` datetime NOT NULL default '0000-00-00 00:00:00',

     */
}
