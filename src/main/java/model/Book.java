package model;

import java.util.Date;
@Entity
@Table(name = "\"Book\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private String bookName;
    @Type(type = "pgsql_enum")
    private Topic topic;
    public enum Topic {
        ROMANCE, COMEDY, OTHER
    }
    private int pageNumbre;
    private Date releaseDate;
    @Type(type = "pgsql_enum")
    private Availability avalability;
    public enum Availability {
        Disponible, Borowed
    }
    @ManyToOne
    @JoinCullumn(name = author_id , nullable = false)
    private Author author;
}
