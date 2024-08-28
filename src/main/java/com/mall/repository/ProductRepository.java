package com.mall.repository;

import com.mall.domain.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @EntityGraph(attributePaths = {"imageList"})
    @Query("select p from Product p where p.id = :pno")
    Optional<Product> findByIdWithImages(@Param("pno") UUID pno);

    @Modifying
    @Transactional
    @Query("update Product p set p.delFlag = :flag where p.id = :pno")
    void updateDelFlag(@Param("pno") UUID pno, @Param("flag") boolean flag);

    @Query("select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false")
    Page<Object[]> selectList(Pageable pageable);
}
