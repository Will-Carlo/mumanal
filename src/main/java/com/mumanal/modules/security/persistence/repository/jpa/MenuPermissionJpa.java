package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecMenuPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuPermissionJpa extends JpaRepository<SecMenuPermissionEntity, Integer> {

    // Buscar todos los permisos de un menú específico
    List<SecMenuPermissionEntity> findAllByMenuIdAndEnabledTrue(Integer menuId);

    // Validar duplicados
    boolean existsByMenuIdAndPermissionIdAndEnabledTrue(Integer menuId, Integer permissionId);

    // Borrado seguro por relación
    @Modifying
    @Query("DELETE FROM SecMenuPermissionEntity mp WHERE mp.menu.id = :menuId AND mp.permission.id = :permissionId AND mp.enabled IS TRUE")
    void deleteByMenuIdAndPermissionId(Integer menuId, Integer permissionId);

    @Modifying
    @Query("DELETE FROM SecMenuPermissionEntity mp WHERE mp.id = :id AND mp.enabled IS TRUE")
    void deleteByIdNative(Integer id);



    // Buscar incluso los borrados para poder reactivarlos si es necesario
    Optional<SecMenuPermissionEntity> findByMenuIdAndPermissionId(Integer menuId, Integer permissionId);

    // Listar solo activos (para el frontend)
    @Query("SELECT mp FROM SecMenuPermissionEntity mp WHERE mp.menu.id = :menuId AND mp.enabled = true")
    List<SecMenuPermissionEntity> findAllByMenuIdActive(Integer menuId);

    // ✅ BORRADO LÓGICO (Soft Delete)
    @Modifying
    @Query("UPDATE SecMenuPermissionEntity mp SET mp.enabled = false, mp.updatedAt = CURRENT_TIMESTAMP WHERE mp.menu.id = :menuId AND mp.permission.id = :permissionId")
    void softDeleteByMenuIdAndPermissionId(Integer menuId, Integer permissionId);
}