package com.geno_insights.scolombo.visitTest.visitorTest.service;

import com.geno_insights.scolombo.config.PhotoService;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitorNotFoundException;
import com.geno_insights.scolombo.visitor.model.dto.CreateVisitorDto;
import com.geno_insights.scolombo.visitor.model.dto.VisitorResponse;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.model.mapper.VisitorMapper;
import com.geno_insights.scolombo.visitor.repository.VisitorRepository;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    @Mock
    private PhotoService visitorPhotoService;

    @Mock
    private MultipartFile photo;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void findByDniShouldReturnVisitorResponse() {
        Visitor visitor = new Visitor(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "photo-url"
        );

        VisitorResponse response = new VisitorResponse(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "photo-url"
        );

        when(visitorRepository.findByDni("12345678"))
                .thenReturn(Optional.of(visitor));

        when(visitorMapper.toResponse(visitor))
                .thenReturn(response);

        VisitorResponse result = visitorService.findByDni("12345678");

        assertEquals("12345678", result.dni());
        assertEquals("Juan Perez", result.fullName());
        assertEquals("ACME", result.company());

        verify(visitorRepository).findByDni("12345678");
        verify(visitorMapper).toResponse(visitor);
    }

    @Test
    void findByDniShouldThrowWhenVisitorDoesNotExist() {
        when(visitorRepository.findByDni("999"))
                .thenReturn(Optional.empty());

        assertThrows(
                VisitorNotFoundException.class,
                () -> visitorService.findByDni("999")
        );

        verify(visitorRepository).findByDni("999");
        verifyNoInteractions(visitorMapper);
    }

    @Test
    void registerVisitorShouldCreateNewVisitorWhenDniDoesNotExist() {
        CreateVisitorDto dto = new CreateVisitorDto(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                photo
        );

        Visitor savedVisitor = new Visitor(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "photo-url"
        );

        VisitorResponse response = new VisitorResponse(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "photo-url"
        );

        when(visitorPhotoService.upload(photo))
                .thenReturn("photo-url");

        when(visitorRepository.findByDni("12345678"))
                .thenReturn(Optional.empty());

        when(visitorRepository.save(any(Visitor.class)))
                .thenReturn(savedVisitor);

        when(visitorMapper.toResponse(savedVisitor))
                .thenReturn(response);

        VisitorResponse result = visitorService.registerVisitor(dto);

        assertEquals("12345678", result.dni());
        assertEquals("Juan Perez", result.fullName());
        assertEquals("ACME", result.company());

        verify(visitorPhotoService).upload(photo);
        verify(visitorRepository).findByDni("12345678");
        verify(visitorRepository).save(any(Visitor.class));
        verify(visitorMapper).toResponse(savedVisitor);
    }

    @Test
    void registerVisitorShouldUpdateExistingVisitorWhenDniExists() {
        CreateVisitorDto dto = new CreateVisitorDto(
                "12345678",
                "Juan Updated",
                "New Company",
                Sector.OPERACIONES,
                photo
        );

        Visitor existingVisitor = new Visitor(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "old-photo-url"
        );

        VisitorResponse response = new VisitorResponse(
                "12345678",
                "Juan Updated",
                "New Company",
                Sector.OPERACIONES,
                "new-photo-url"
        );

        when(visitorPhotoService.upload(photo))
                .thenReturn("new-photo-url");

        when(visitorRepository.findByDni("12345678"))
                .thenReturn(Optional.of(existingVisitor));

        when(visitorRepository.save(existingVisitor))
                .thenReturn(existingVisitor);

        when(visitorMapper.toResponse(existingVisitor))
                .thenReturn(response);

        VisitorResponse result = visitorService.registerVisitor(dto);

        assertEquals("Juan Updated", result.fullName());
        assertEquals("New Company", result.company());
        assertEquals("new-photo-url", result.photoUrl());

        assertEquals("Juan Updated", existingVisitor.getFullName());
        assertEquals("New Company", existingVisitor.getCompany());
        assertEquals("new-photo-url", existingVisitor.getPhotoUrl());

        verify(visitorPhotoService).upload(photo);
        verify(visitorRepository).findByDni("12345678");
        verify(visitorRepository).save(existingVisitor);
        verify(visitorMapper).toResponse(existingVisitor);
    }

    @Test
    void countVisitorsShouldReturnRepositoryCount() {
        when(visitorRepository.count()).thenReturn(3L);

        long result = visitorService.countVisitors();

        assertEquals(3L, result);
        verify(visitorRepository).count();
    }
}