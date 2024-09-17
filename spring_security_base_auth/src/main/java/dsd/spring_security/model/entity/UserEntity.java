package dsd.spring_security.model.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.roles", attributeNodes = @NamedAttributeNode("role"))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 15)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    public UserEntity(String username, String password, RoleEntity role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}