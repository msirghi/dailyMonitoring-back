package com.example.dailymonitoring.respositories;

import com.example.dailymonitoring.models.entities.EmailTemplateEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplateEntity, Long> {

  @Query("SELECT template FROM EmailTemplateEntity template "
      + "WHERE template.name = :name "
      + "AND template.deleted = false")
  Optional<EmailTemplateEntity> getTemplateByName(
      @Param("name") String name
  );

  @Query("SELECT template FROM EmailTemplateEntity template "
      + "WHERE template.deleted = false "
      + "AND template.id = :templateId")
  Optional<EmailTemplateEntity> getNotDeletedTemplateById(
      @Param("templateId") Long templateId
  );

  @Transactional
  @Modifying
  @Query("UPDATE EmailTemplateEntity template "
      + "SET template.deleted = true "
      + "WHERE template.id = :templateId")
  int markAsDeleted(
      @Param("templateId") Long templateId
  );

  @Query("SELECT template FROM EmailTemplateEntity template "
      + "WHERE template.deleted = false")
  List<EmailTemplateEntity> getNotDeletedTemplates();
}
