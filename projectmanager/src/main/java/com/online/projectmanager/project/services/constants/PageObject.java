package com.online.projectmanager.project.services.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class PageObject {
    private Integer pageNumber;
    private Integer pageSize;
}
