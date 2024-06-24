package com.online.projectmanager.project.DTOs;

import com.online.projectmanager.project.models.Status;
import com.online.projectmanager.users.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectDTO {
    private String name;

    private LocalDateTime startDate;

    private LocalDateTime expectedEndDate;

    private String description;;

    private Long userId;
}
