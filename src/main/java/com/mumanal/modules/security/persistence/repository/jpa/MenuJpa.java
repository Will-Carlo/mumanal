package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecMenuEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuJpa extends JpaRepository<SecMenuEntity, Integer> {

    // 1. Para el ADMIN (Backoffice): Trae todo, incluso inactivos, pero optimizado
    @EntityGraph(attributePaths = {"requiredPermissions"})
    @Query("SELECT m FROM SecMenuEntity m WHERE m.enabled = true ORDER BY m.sortOrder ASC")
    List<SecMenuEntity> findAllEnabled();

    // 2. Para el USUARIO (Frontend): Trae solo activos y CARGA LOS PERMISOS
    // El @EntityGraph es vital aquí. Le dice a JPA: "Haz el JOIN con requiredPermissions ahora mismo".
    @EntityGraph(attributePaths = {"requiredPermissions"})
    @Query("SELECT m FROM SecMenuEntity m WHERE m.status = true AND m.enabled = true ORDER BY m.sortOrder ASC")
    List<SecMenuEntity> findAllActiveAndEnabled();

    // 3. Buscar por ID cargando sus permisos (Para el Update)
    @EntityGraph(attributePaths = {"requiredPermissions"})
    Optional<SecMenuEntity> findByIdAndEnabledTrue(Integer id);

    // ... tus validaciones de duplicados (existsByName...) se quedan igual ...
    // Esas no necesitan EntityGraph porque devuelven boolean, no entidades.
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM SecMenuEntity m WHERE m.name = :name AND (:parentId IS NULL AND m.parentMenu IS NULL OR m.parentMenu.id = :parentId) AND m.enabled = true")
    boolean existsByNameAndParentId(String name, Integer parentId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM SecMenuEntity m WHERE m.name = :name AND (:parentId IS NULL AND m.parentMenu IS NULL OR m.parentMenu.id = :parentId) AND m.id <> :id AND m.enabled = true")
    boolean existsByNameAndParentIdAndIdNot(String name, Integer parentId, Integer id);

    boolean existsByIdAndEnabledTrue(Integer id);
}