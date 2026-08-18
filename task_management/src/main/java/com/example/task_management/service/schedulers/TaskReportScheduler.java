package com.example.task_management.service.schedulers;

import com.example.task_management.entity.Task;
import com.example.task_management.repository.TaskRepository;

import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class TaskReportScheduler {

    private final TaskRepository taskRepository;

    public TaskReportScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

   @Scheduled(cron = "0 0 18 * * *")
    public void generateReport() {

        try {
             log.info("Generating the report of Tasks");
            // 1. Get all tasks from database
            List<Task> tasks = taskRepository.findAll();
            
            log.info("Size of the file :{} " , tasks.size() );
            // 2. Create Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // 3. Create sheet
            Sheet sheet = workbook.createSheet("Tasks");

            // 4. Create header
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Title");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Completed");

            // 5. Add task data
            int rowNumber = 1;

            for (Task task : tasks) {

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0).setCellValue(task.getId());
                row.createCell(1).setCellValue(task.getTitle());
                row.createCell(2).setCellValue(task.getDescription());
                row.createCell(3).setCellValue(task.isCompleted());
            }

            // 6. Resize columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            // 7. Create reports directory
            Path directory = Paths.get("reports");

            Files.createDirectories(directory);

            // 8. Create file
            String fileName = "tasks-report.xlsx";

            Path filePath = directory.resolve(fileName);

            // 9. Write workbook to file
            try (FileOutputStream outputStream =
                         new FileOutputStream(filePath.toFile())) {

                workbook.write(outputStream);
            }

            // 10. Close workbook
            workbook.close();
            log.info("Tasks report is downloaded successfully");

            System.out.println(
                "Report generated successfully: "
                + filePath.toAbsolutePath()
            );

        } catch (IOException e) {
            log.error("Error occured while generating the file : {} " , e);

            System.err.println(
                "Failed to generate task report: "
                + e.getMessage()
            );
        }
    }
}