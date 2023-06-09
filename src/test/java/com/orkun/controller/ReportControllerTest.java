package com.orkun.controller;

import com.foottech.foodproject.controllers.ReportController;
import com.orkun.dto.FileDTO;
import com.orkun.service.ReportService;
import com.orkun.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@Tag("unit")
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    private FileDTO report;

    @BeforeEach
    void setUp() throws IOException {

        String filePath = "jsonfile/mockedFileDTO.json";

        String fileName = TestHelper.extractJsonPropertyFromFile(filePath, "fileName");
        String fileContent = TestHelper.extractJsonPropertyFromFile(filePath, "fileContent");

        report = new FileDTO(fileName, fileContent);
    }

    @Test
    @DisplayName("Generate an Excel report containing all the departments")
    void givenNoInput_whenGenerateDepartmentsExcelReport_thenReturnInputStreamResource() throws Exception {

        // given - precondition or setup
        String filePath = "jsonfile/mockedFileDTO.json";


        String fileName = TestHelper.extractJsonPropertyFromFile(filePath, "fileName");
        String fileContent = TestHelper.extractJsonPropertyFromFile(filePath, "fileContent");

        FileDTO report = new FileDTO(fileName, fileContent);

        given(reportService.generateDepartmentsExcelReport()).willReturn(report);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/reports/excel/departments"));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Generate an Excel report containing all the employees")
    void givenNoInput_whenGenerateEmployeesExcelReport_thenReturnInputStreamResource() throws Exception {

        // given - precondition or setup
        given(reportService.generateEmployeesExcelReport()).willReturn(report);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/reports/excel/employees"));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Generate a PDF report containing all the departments along with all the employees")
    void givenNoInput_whenGeneratePdfFullReport_thenReturnInputStreamResource() throws Exception {

        // given - precondition or setup
        given(reportService.generatePdfFullReport()).willReturn(report);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/reports/pdf/full-report"));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Generate a zip file which contains two excel reports")
    void givenNoInput_whenGenerateAndZipReports_thenReturnInputStreamResource() throws Exception {

        // given - precondition or setup
        given(reportService.generateAndZipReports()).willReturn(report);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/reports/zip"));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Generate a multi-sheet Excel report containing departments and employees")
    void givenNoInput_whenGenerateMultiSheetExcelReport_thenReturnInputStreamResource() throws Exception {

        // given - precondition or setup
        given(reportService.generateMultiSheetExcelReport()).willReturn(report);

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/v1/reports/multi-sheet-excel"));

        // then - verify the output
        response.andDo(print())
                // verify the status code that is returned
                .andExpect(status().isOk());
    }

}
