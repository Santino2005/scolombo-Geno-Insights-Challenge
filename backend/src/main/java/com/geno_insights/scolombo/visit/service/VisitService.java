package com.geno_insights.scolombo.visit.service;

import com.geno_insights.scolombo.errorHandler.exceptions.NoActiveVisitException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitAlreadyClosedException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitExportException;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitNotFoundException;
import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.dto.VisitResponse;
import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visit.model.mapper.VisitMapper;
import com.geno_insights.scolombo.visit.model.rules.GenerateCredentialRuleStrategy;
import com.geno_insights.scolombo.visit.model.rules.GenerateCredentialRulesExecutor;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visitor.model.dto.VisitorResponse;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitorService visitorService;
    private final VisitMapper visitMapper;
    private final GenerateCredentialRulesExecutor generateCredentialRules;

    public VisitResponse generateCredential(
            GenerateCredentialRequest request
    ) {
        Visitor visitor = getVisitor(request);
        validateCredentialGeneration(request, visitor);
        Visit visit = createPendingVisit(request, visitor);
        Visit savedVisit = saveVisit(visit);

        return toResponse(savedVisit);
    }

    public VisitResponse scanQr(String qrToken) {
        Visit visit = findByQrToken(qrToken);

        if (visit.getStatus() == VisitState.PENDING) {
            return activateVisit(visit);
        }

        if (visit.getStatus() == VisitState.ACTIVE) {
            return closeVisit(visit);
        }

        throw new VisitAlreadyClosedException();
    }

    public VisitResponse getCredential(String qrToken) {
        return visitMapper.toResponse(findByQrToken(qrToken));
    }

    public VisitResponse getActiveCredentialByDni(String dni) {
        Visit visit = visitRepository.findByVisitorDniAndExitTimeIsNull(dni)
                .orElseThrow(NoActiveVisitException::new);

        return visitMapper.toResponse(visit);
    }

    public List<VisitResponse> getTodayVisits() {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        return visitRepository.findByEntryTimeBetween(start, end)
                .stream()
                .map(visitMapper::toResponse)
                .toList();
    }

    public List<VisitResponse> getHistory() {
        return visitRepository.findAll()
                .stream()
                .map(visitMapper::toResponse)
                .toList();
    }

    public byte[] exportVisitHistory() {
        List<Visit> visits = visitRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Historial");
            createHeader(sheet);
            fillRows(sheet, visits);
            resizeColumns(sheet);

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException exception) {
            throw new VisitExportException(exception);
        }
    }

    private Visit findByQrToken(String qrToken) {
        return visitRepository.findByQrToken(qrToken)
                .orElseThrow(VisitNotFoundException::new);
    }

    private VisitResponse activateVisit(Visit visit) {
        visit.setEntryTime(LocalDateTime.now());
        visit.setStatus(VisitState.ACTIVE);
        return visitMapper.toResponse(visitRepository.save(visit));
    }

    private VisitResponse closeVisit(Visit visit) {
        visit.setExitTime(LocalDateTime.now());
        visit.setStatus(VisitState.ENDED);
        return visitMapper.toResponse(visitRepository.save(visit));
    }

    private void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Fecha");
        header.createCell(1).setCellValue("Ingreso");
        header.createCell(2).setCellValue("Salida");
        header.createCell(3).setCellValue("DNI");
        header.createCell(4).setCellValue("Nombre");
        header.createCell(5).setCellValue("Empresa");
        header.createCell(6).setCellValue("Sector");
        header.createCell(7).setCellValue("Estado");
    }

    private void fillRows(Sheet sheet, List<Visit> visits) {
        int rowIndex = 1;

        for (Visit visit : visits) {
            Row row = sheet.createRow(rowIndex);
            fillRow(row, visit);
            rowIndex++;
        }
    }

    private void fillRow(Row row, Visit visit) {
        row.createCell(0).setCellValue(formatDate(visit.getEntryTime()));
        row.createCell(1).setCellValue(formatTime(visit.getEntryTime()));
        row.createCell(2).setCellValue(formatTime(visit.getExitTime()));
        row.createCell(3).setCellValue(visit.getVisitor().getDni());
        row.createCell(4).setCellValue(visit.getVisitor().getFullName());
        row.createCell(5).setCellValue(visit.getVisitor().getCompany());
        row.createCell(6).setCellValue(visit.getSector().name());
        row.createCell(7).setCellValue(getVisitStatusLabel(visit));
    }

    private String getVisitStatusLabel(Visit visit) {
        if (visit.getStatus() == VisitState.PENDING) {
            return "Pendiente";
        }

        if (visit.getStatus() == VisitState.ACTIVE) {
            return "Activo";
        }

        return "Finalizado";
    }

    private void resizeColumns(Sheet sheet) {
        sheet.setColumnWidth(0, 5000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 8000);
        sheet.setColumnWidth(5, 7000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 5000);
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    private Visit createPendingVisit(
            Visitor visitor,
            Sector sector
    ) {
        Visit visit = new Visit();

        visit.setVisitor(visitor);
        visit.setSector(sector);
        visit.setQrToken(UUID.randomUUID().toString());
        visit.setStatus(VisitState.PENDING);
        visit.setEntryTime(null);
        visit.setExitTime(null);

        return visit;
    }
    private Visitor getVisitor(
            GenerateCredentialRequest request
    ) {
        return visitorService.findEntityByDni(
                request.dni()
        );
    }

    private void validateCredentialGeneration(
            GenerateCredentialRequest request,
            Visitor visitor
    ) {
        generateCredentialRules.validate(
                request,
                visitor
        );
    }

    private Visit createPendingVisit(
            GenerateCredentialRequest request,
            Visitor visitor
    ) {
        Visit visit = new Visit();

        visit.setVisitor(visitor);
        visit.setSector(request.sector());
        visit.setQrToken(UUID.randomUUID().toString());
        visit.setStatus(VisitState.PENDING);
        visit.setEntryTime(null);
        visit.setExitTime(null);

        return visit;
    }

    private Visit saveVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    private VisitResponse toResponse(Visit visit) {
        return visitMapper.toResponse(visit);
    }
}