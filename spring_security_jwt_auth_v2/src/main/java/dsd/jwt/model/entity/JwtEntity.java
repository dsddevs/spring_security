package dsd.jwt.model.entity;

import dsd.jwt.model.audit.Auditable;
import dsd.jwt.type.JwtType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Table(name = "jwts", indexes = {
        @Index(name = "idx_jwt_token", columnList = "jwt"),
        @Index(name = "idx_jwt_user", columnList = "user_id")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class JwtEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String jwt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JwtType jwtType;
    @Column(nullable = false)
    private boolean expired;
    @Column(nullable = false)
    private boolean revoked;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        JwtEntity jwtEntity = (JwtEntity) o;
        return getId() != null && Objects.equals(getId(), jwtEntity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
