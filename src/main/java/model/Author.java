package model;
@Entity
@Table(name = "\"author\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgresEnumType.class)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;
    private String name;
    @Type(type = "pgsql_enum")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    public enum Sex {
        M, F
    }
}
