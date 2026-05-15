package com.geno_insights.scolombo.visitTest.service;

import com.geno_insights.scolombo.errorHandler.exceptions.VisitAlreadyClosedException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitNotFoundException;
import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.dto.VisitResponse;
import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visit.model.mapper.VisitMapper;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visit.service.VisitService;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private VisitorService visitorService;

    @Mock
    private VisitMapper visitMapper;

    @InjectMocks
    private VisitService visitService;

    @Test
    void generateCredentialShouldCreatePendingVisit() {
        GenerateCredentialRequest request =
                new GenerateCredentialRequest("12345678", Sector.OPERACIONES);

        Visitor visitor = new Visitor();
        visitor.setDni("12345678");

        Visit visit = new Visit();

        VisitResponse response = mock(VisitResponse.class);

        when(visitorService.findEntityByDni("12345678")).thenReturn(visitor);
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.toResponse(visit)).thenReturn(response);

        VisitResponse result = visitService.generateCredential(request);

        assertNotNull(result);
        verify(visitRepository).save(any(Visit.class));
    }

    @Test
    void scanQrShouldActivatePendingVisit() {
        Visit visit = new Visit();
        visit.setStatus(VisitState.PENDING);
        visit.setQrToken("token");

        VisitResponse response = mock(VisitResponse.class);

        when(visitRepository.findByQrToken("token"))
                .thenReturn(Optional.of(visit));

        when(visitRepository.save(any(Visit.class)))
                .thenReturn(visit);

        when(visitMapper.toResponse(visit))
                .thenReturn(response);

        VisitResponse result = visitService.scanQr("token");

        assertNotNull(result);
        assertEquals(VisitState.ACTIVE, visit.getStatus());
    }

    @Test
    void scanQrShouldCloseActiveVisit() {
        Visit visit = new Visit();
        visit.setStatus(VisitState.ACTIVE);
        visit.setQrToken("token");

        VisitResponse response = mock(VisitResponse.class);

        when(visitRepository.findByQrToken("token"))
                .thenReturn(Optional.of(visit));

        when(visitRepository.save(any(Visit.class)))
                .thenReturn(visit);

        when(visitMapper.toResponse(visit))
                .thenReturn(response);

        VisitResponse result = visitService.scanQr("token");

        assertNotNull(result);
        assertEquals(VisitState.ENDED, visit.getStatus());
    }

    @Test
    void scanQrShouldThrowWhenVisitAlreadyEnded() {
        Visit visit = new Visit();
        visit.setStatus(VisitState.ENDED);

        when(visitRepository.findByQrToken("token"))
                .thenReturn(Optional.of(visit));

        assertThrows(
                VisitAlreadyClosedException.class,
                () -> visitService.scanQr("token")
        );
    }

    @Test
    void scanQrShouldThrowWhenVisitNotFound() {
        when(visitRepository.findByQrToken("token"))
                .thenReturn(Optional.empty());

        assertThrows(
                VisitNotFoundException.class,
                () -> visitService.scanQr("token")
        );
    }
}